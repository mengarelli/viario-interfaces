package br.engdb.viario.rest.neta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.engdb.viario.database.Database;

@Path("/atcs")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class ATCService {
	public static final int MAX_ROWS = 10;

	@GET
	@Path("{timestamp}")
	public RestResponse<ATC> listAll(@PathParam("timestamp") final String timestamp, @QueryParam("pag") final int pag) {
		System.out.println("Iniciando listAll ATC...");
		RestResponse<ATC> resp = new RestResponse<>("1", RestResponse.TYPE_SUCCESS, "OK", 0);
		System.out.println("Instanciando Mapper...");
		final ObjectMapper mapper = new ObjectMapperResolver().getContext(Date.class);
		System.out.println("Mapper OK.");
		Database db = null;
		try {
			db = new Database();
			Date dd = mapper.getDateFormat().parse(timestamp);
			int start = (pag > 0 ? pag - 1 : 0) * MAX_ROWS;
			int max = start + MAX_ROWS + 1;
			String sql = "select rs.* from (select t.*, row_number() over (order by DATASTAMP) as row_num from XSVIAATCATO t where XSVIAAATID_XSVILATY = 1 AND DATASTAMP >= ?) rs where row_num > " + start + " and row_num <= " + max;
			System.out.println("Preparando Query...");
			PreparedStatement stmt = db.prepareStatement(sql);
			System.out.println("Query OK.");
			stmt.setObject(1, dd.toInstant().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDate());
			System.out.println("Executando Query...");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Execucao OK.");
			int count = 0;
			while (rs.next()) {
				count++;
				if (count > MAX_ROWS) {
					resp.setHasMore(true);
					break;
				}
				ATC atc = new ATC();
				if (rs.getString("XSVIAAATVISIBLE").equals("1")) {
					atc.setOperationType(RestResponse.OPERATION_UPDATE);
				} else {
					atc.setOperationType(RestResponse.OPERATION_DELETE);
				}
				atc.setAtcCode(rs.getString("XSVIAAATCODE"));
				atc.setAtcDescription(rs.getString("XSVIAAATDESCRIPTION"));
				atc.setAtcId(rs.getString("XSVIAAATID"));
				atc.setOperationType("Update");
				//atc.getAtcRelation().add(new ATC.ATCRelation("AR1", "PT1", "Update"));
				fillEntidadesOrganizacionais(db, atc);
				resp.getContents().add(atc);
			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			resp.setMessage(e.toString());
			resp.setMessageCode(-1);
			resp.setMessageType(RestResponse.TYPE_ERROR);
		} catch (ParseException e) {
			resp.setMessage("Timestamp no formato errado. Esperado: " + ObjectMapperResolver.DATE_FORMAT);
			resp.setMessageCode(9);
			resp.setMessageType(RestResponse.TYPE_ERROR);
		} finally {
			if (db != null) {
				db.close();
			}
		}
		System.out.println("Enviando Reposta.");
		return resp;
	}

	private void fillEntidadesOrganizacionais(Database db, final ATC atc) throws SQLException, NamingException {
		String sql = "SELECT oe.XSVIAOREID, oe.XSVIAORESABESPCODE, ll.XSVITOELID, ll.XSVITOELDESCRIPTION, "
				+ "rr.XSVIROEAFATHER, rr.XSVIROEAVISIBLE, oe.XSVIAORESABESPCODE\n" + 
				"FROM XSVIRORGANIZENTITYATCATO rr\n" + 
				"INNER JOIN XSVITORGANIZATIONENTITYLINK ll ON rr.XSVIROEAID_XSVITOEL = ll.XSVITOELID\n" + 
				"INNER JOIN XSVIAORGANIZATIONALENTITY oe ON rr.XSVIROEAID_XSVIAORE = oe.XSVIAOREID\n" + 
				"WHERE rr.XSVIROEAID_XSVIAAAT = ?";
		System.out.println("Executando Query...");
		System.out.println("Executando Query Secundaria...");
		PreparedStatement stmt = db.prepareStatement(sql);
		stmt.setInt(1, Integer.parseInt(atc.getAtcId()));
		ResultSet rs = stmt.executeQuery();
		System.out.println("Execucao OK.");
		while (rs.next()) {
			ATC.ATCRelation rel = new ATC.ATCRelation(rs.getString("XSVIAOREID"), rs.getString("XSVITOELID"), rs.getString("XSVIAORESABESPCODE"));
			rel.setUoParentType(rs.getString("XSVITOELDESCRIPTION").trim());
			int pos = rel.getUoParentType().indexOf("-");
			if (pos > 0) {
				rel.setUoParentType(rel.getUoParentType().substring(0, pos).trim());
			}
			if (rs.getString("XSVIROEAVISIBLE").equals("1")) {
				rel.setOperationType(RestResponse.OPERATION_UPDATE);
			} else {
				rel.setOperationType(RestResponse.OPERATION_DELETE);
			}
			if (rs.getString("XSVIROEAFATHER").equals("1")) {
				atc.setUoParentId(rel.getUoid());
			}
			atc.getAtcRelation().add(rel);
		}
	}
}
