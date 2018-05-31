package cn.com.cs.rest.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class ApplicationAPI extends ResourceConfig {
	public ApplicationAPI() {
		packages("cn.com.cs.pay.controller");
    }
}
