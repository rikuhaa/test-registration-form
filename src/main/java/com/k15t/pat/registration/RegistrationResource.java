package com.k15t.pat.registration;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Path("registration")
@Component
public class RegistrationResource {

    @Autowired
    private RegistrationDaoService registrationDaoService;

    // Extend the current resource to receive and store the data in memory.
    // Return a success information to the user including the entered
    // information.
    // In case of the address split the information into a better
    // format/structure
    // for better handling later on.

    // TODO should have a access restricted method for getting
    // all the current registrations

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public Response registerNew(@NotNull @Valid Registration registration) {

	boolean wasNew = registrationDaoService.addRegistration(registration);

	if (wasNew) {
	    return Response.status(Response.Status.CREATED).build();
	} else {
	    return Response.status(Response.Status.CONFLICT).build();
	}
    }

    // FIXME this should really be restricted to admin-role
    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Registration> getRegistrations() {
	return registrationDaoService.getRegistrations();
    }
}
