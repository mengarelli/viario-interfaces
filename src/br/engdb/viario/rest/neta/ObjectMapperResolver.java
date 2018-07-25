package br.engdb.viario.rest.neta;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
	public static final String DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";

    private final ObjectMapper mapper;

    public ObjectMapperResolver() {
        System.out.println("new ObjectMapperResolver()");
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

}