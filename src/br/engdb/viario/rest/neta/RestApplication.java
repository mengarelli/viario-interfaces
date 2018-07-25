package br.engdb.viario.rest.neta;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/services")
public class RestApplication extends ResourceConfig {

	public RestApplication() {
		super();
		packages("br.engdb.viario.rest.neta");
		register(ObjectMapperResolver.class);
	}
	
}
