package br.engdb.viario.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Database {
	private Connection con = null;

	public static final Connection getConn() throws NamingException, SQLException {
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");
		DataSource ds = (DataSource) envContext.lookup("jdbc/ViarioDB");
		Connection conn = ds.getConnection();
		System.out.println("Obtendo conexao do Pool...");
		return conn;
	}

	public Database() throws NamingException, SQLException {
		con = getConn();
	}

	public void close() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public final Statement createStatement() throws SQLException, NamingException {
		Statement statement = con.createStatement();
		return statement;
	}

	public final PreparedStatement prepareStatement(String sql) throws SQLException, NamingException {
		PreparedStatement statement = con.prepareStatement(sql);
		return statement;
	}
}
