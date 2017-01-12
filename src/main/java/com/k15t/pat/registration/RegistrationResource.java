package com.k15t.pat.registration;

import javax.servlet.Registration;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Path("/registration")
@Component
public class RegistrationResource {
	
	// Extend the current resource to receive and store the data in memory.
	// Return a success information to the user including the entered information.
	// In case of the address split the information into a better format/structure
	// for better handling later on.
	public Response save() {
		return Response.ok().build();
	}
	
	@POST
	@Consumes({ "application/json" })
	public void registerNew(Registration registration) {
		
	}
	
}
