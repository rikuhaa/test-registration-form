package com.k15t.pat.registration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.k15t.pat.registration.Registration.Address;

public class RegistrationDaoServiceTest {

    private RegistrationDaoService regDaoService;

    @Before
    public void init() {
	regDaoService = new RegistrationDaoService();
    }

    @Test
    public void addsRegistration() {
	Address addr = new Address();

	addr.setCity("test");
	addr.setStreetAddress("test");
	addr.setZipCode("test");
	addr.setCountry("test");

	Registration toAdd = new Registration();

	toAdd.setAddress(addr);
	toAdd.setEmail("test");
	toAdd.setName("test");
	toAdd.setPassword("test");
	toAdd.setPhoneNumber("test");

	boolean res = regDaoService.addRegistration(toAdd);

	Assert.assertTrue(res);
    }

    @Test
    public void doesNotAddDuplicateRegistrations() {

	Address addr = new Address();

	addr.setCity("test");
	addr.setStreetAddress("test");
	addr.setZipCode("test");
	addr.setCountry("test");

	Registration toAdd = new Registration();

	toAdd.setAddress(addr);
	toAdd.setEmail("test");
	toAdd.setName("test");
	toAdd.setPassword("test");
	toAdd.setPhoneNumber("test");

	boolean res = regDaoService.addRegistration(toAdd);

	Assert.assertTrue(res);

	toAdd.setName("new name");

	boolean res2 = regDaoService.addRegistration(toAdd);

	Assert.assertFalse(res2);

    }

}
