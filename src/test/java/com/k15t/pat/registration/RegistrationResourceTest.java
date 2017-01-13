package com.k15t.pat.registration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.k15t.pat.ApplicationBootstrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegistrationResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // could mock / spy the dao service to isolate test to RegistrationResource
    /*
     * public static class MockRegistrationDaoFactory implements
     * Factory<RegistrationDaoService> {
     * 
     * @Override public RegistrationDaoService provide() { final
     * RegistrationDaoService mockRegistrationDao =
     * Mockito.mock(RegistrationDaoService.class);
     * Mockito.when(mockRegistrationDao.addRegistration(Mockito.any(Registration
     * .class))).thenReturn(true); return mockRegistrationDao; }
     * 
     * @Override public void dispose(RegistrationDaoService t) {
     * 
     * }
     * 
     * }
     */

    @Test
    public void createUserSucceeds() throws Exception {

	Registration.Address addr = new Registration.Address();

	addr.setCity("test");
	addr.setCountry("test");
	addr.setStreetAddress("test");
	addr.setZipCode("test");

	Registration reg = new Registration();

	reg.setAddress(addr);
	reg.setEmail("test");
	reg.setName("test");
	reg.setPassword("test");
	reg.setPhoneNumber("test");

	ResponseEntity<String> resp = restTemplate.postForEntity("/rest/registration", reg, String.class);

	Assert.assertEquals(HttpStatus.CREATED, resp.getStatusCode());

    }

    @Test
    public void tryingToCreuateDuplicateUserFails() throws Exception {

	Registration.Address addr = new Registration.Address();

	addr.setCity("test");
	addr.setCountry("test");
	addr.setStreetAddress("test");
	addr.setZipCode("test");

	Registration reg = new Registration();

	reg.setAddress(addr);
	reg.setEmail("test2");
	reg.setName("test");
	reg.setPassword("test");
	reg.setPhoneNumber("test");

	ResponseEntity<String> resp = restTemplate.postForEntity("/rest/registration", reg, String.class);

	Assert.assertEquals(HttpStatus.CREATED, resp.getStatusCode());

	ResponseEntity<String> resp2 = restTemplate.postForEntity("/rest/registration", reg, String.class);

	Assert.assertEquals(HttpStatus.CONFLICT, resp2.getStatusCode());

    }

}
