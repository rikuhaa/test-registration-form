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

    /**
     * <p>
     * An entry point for POST queries to '/rest/registration'. Expects a valid
     * and non-null {@link Registration} as a JSON payload on the request, and
     * tries to add the received {@link Registration}
     * </p>
     * <p>
     * If a {@link Registration} with matching e-mail was not already present in
     * the {@link RegistrationDaoService}, the new registration is added. In
     * this case this method returns a response with status
     * {@link Response.Status#CREATED} and with the added entry as JSON as the
     * response body (with hidden password).
     * </p>
     * <p>
     * If a registration with matching e-mail was already present in the
     * {@link RegistrationDaoService}, does not add a new registration and
     * returns a response with {@link Response.Status#CONFLICT}
     * </p>
     * 
     * @param registration
     *            {@link Registration} to add (if not already added)
     * @return {@link Response} with correct status and possible added data
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    public Response registerNew(@NotNull @Valid Registration registration) {

	boolean wasNew = registrationDaoService.addRegistration(registration);

	if (wasNew) {
	    registration.setPassword("********");
	    return Response.status(Response.Status.CREATED).entity(registration).build();
	} else {
	    return Response.status(Response.Status.CONFLICT).build();
	}
    }

    /**
     * <p>
     * An entry point for GET queries to '/rest/registration'.
     * </p>
     * <p>
     * Returns a list of all present {@link Registration}s as JSON. For admin
     * use.
     * </p>
     * 
     * @return a list of all {@link Registration}s present in the service as
     *         JSON
     */
    // FIXME this should really be restricted to admin-role
    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Registration> getRegistrations() {
	return registrationDaoService.getRegistrations();
    }
}
