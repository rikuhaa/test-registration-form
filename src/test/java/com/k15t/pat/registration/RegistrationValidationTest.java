package com.k15t.pat.registration;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.k15t.pat.registration.Registration.Address;

public class RegistrationValidationTest {

    private Validator validator;

    @Before
    public void init() {

	ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
	validator = vf.getValidator();

    }

    private static Registration newRegistration(String email, String name, String password, Address address,
	    String phone) {
	Registration res = new Registration();
	res.setAddress(address);
	res.setEmail(email);
	res.setName(name);
	res.setPassword(password);
	res.setPhoneNumber(phone);
	return res;
    }

    private static Address newAddress(String streetAddr, String zip, String city, String country) {
	Address res = new Address();
	res.setCity(city);
	res.setCountry(country);
	res.setZipCode(zip);
	res.setStreetAddress(streetAddr);
	return res;
    }

    private static Registration defaultReg() {

	return newRegistration("test@test.com", "Test van Testing", "abcd1234",
		newAddress("Test str. 24 A 35", "A402-247", "Sao Paolo", "Brazil"), "+73-426 731 731");

    }

    @Test
    public void emailRequired() {
	Registration reg = defaultReg();
	reg.setEmail(null);

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(2, constraintViolations.size()); // also non-empty fails

    }

    @Test
    public void validEmailsAccepted() {
	Registration reg = defaultReg();

	reg.setEmail("test@test.com");

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(0, constraintViolations.size());

	reg.setEmail("TEST@test.company.nu");

	assertEquals(0, validator.validate(reg).size());

    }

    @Test
    public void invalidEmailsRejected() {
	Registration reg = defaultReg();

	reg.setEmail("test.test.com");

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("not a well-formed email address", constraintViolations.iterator().next().getMessage());

	reg.setEmail("");

	constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("may not be empty", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void nameRequired() {
	Registration reg = defaultReg();
	reg.setName(null);

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("may not be null", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void validNamesAccepted() {
	Registration reg = defaultReg();

	reg.setName("testinf Testingåö");

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(0, constraintViolations.size());

	reg.setName("testinf wan Tester");

	assertEquals(0, validator.validate(reg).size());

    }

    @Test
    public void invalidNamesRejected() {
	Registration reg = defaultReg();

	reg.setName("  name");

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());

	reg.setName("");

	constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());

	reg.setName("aoeshnthoeautttttttttttttttttttttttttttttttttttttt"
		+ "aseoutnhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"
		+ "saidroehushaouenttttttttttttttttttttttttttttttttttttsauoenth"
		+ "oaeusathnsoeauh ueoasnhueoanseouahoaesnouensoaueoeuasuoeaoeu");

	constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void passwordRequired() {
	Registration reg = defaultReg();
	reg.setName(null);

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("may not be null", constraintViolations.iterator().next().getMessage());

    }

    @Test
    @Ignore
    public void strongEnoughPasswordAccepted() {

    }

    @Test
    @Ignore
    public void tooWeakPasswordRejected() {

    }

    @Test
    public void phoneNumberOptional() {

	Registration reg = defaultReg();

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(0, constraintViolations.size());

	reg.setPhoneNumber(null);

	assertEquals(0, validator.validate(reg).size());

    }

    @Test
    public void addresRequired() {

	Registration reg = defaultReg();
	reg.setAddress(null);

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("may not be null", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void invalidAddressRejected() {

	Registration reg = defaultReg();
	reg.setAddress(newAddress(null, "1537", "New York", "USA"));

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(1, constraintViolations.size());
	assertEquals("may not be null", constraintViolations.iterator().next().getMessage());

    }

    @Test
    public void validAddressAccepted() {

	Registration reg = defaultReg();
	reg.setAddress(newAddress("Street 2", "1537", "New York", "USA"));

	Set<ConstraintViolation<Registration>> constraintViolations = validator.validate(reg);
	assertEquals(0, constraintViolations.size());

    }

}
