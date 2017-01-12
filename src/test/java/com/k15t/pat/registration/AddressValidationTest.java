package com.k15t.pat.registration;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import com.k15t.pat.registration.Registration.Address;

public class AddressValidationTest {

    private Validator validator;

    @Before
    public void init() {

	ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
	validator = vf.getValidator();

    }

    private static Address newAddress(String streetAddr, String zip, String city, String country) {
	Address res = new Address();
	res.setCity(city);
	res.setCountry(country);
	res.setZipCode(zip);
	res.setStreetAddress(streetAddr);
	return res;
    }

    private static Address defaultAddress() {

	return newAddress("Test str. 24 A 35", "A402-247", "Sao Paolo", "Brazil");

    }

    @Test
    public void streetAddressRequired() {
	Address addr = defaultAddress();
	addr.setStreetAddress(null);

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void validStreetAddressAccepdted() {
	Address addr = defaultAddress();
	addr.setStreetAddress("Street 2");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

	addr.setStreetAddress("Small Town Square");

	constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

    }

    @Test
    public void invalidStreetAddressRejected() {
	Address addr = defaultAddress();
	addr.setStreetAddress(" Street A");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

	addr.setStreetAddress("sahtsoeha usho esuhoes auhnues hosen uahsueoa heounas huesoa hesn euas + "
		+ "aoesnuhsn inosehoenasuh nueoh snaueo  ues hasuon");

	constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void zipcodeRequired() {
	Address addr = defaultAddress();
	addr.setZipCode(null);

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void validZipcodesAccepdted() {
	Address addr = defaultAddress();
	addr.setZipCode("12345");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

	addr.setZipCode("Z2404-242G");

	constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

    }

    @Test
    public void invalidZipcodesRejected() {
	Address addr = defaultAddress();
	addr.setZipCode(" 1573");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

	addr.setZipCode("111111111111111111111111111115555555555555555555511111111111117777777111111");

	constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void cityRequired() {
	Address addr = defaultAddress();
	addr.setCity(null);

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void validCitiesAccepdted() {
	Address addr = defaultAddress();
	addr.setCity("New York");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

	addr.setCity("Tokio, District 5");

	constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

    }

    @Test
    public void invalidCitiesRejected() {
	Address addr = defaultAddress();
	addr.setCity(" City");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

	addr.setCity(
		"aihsoehseohu soaeushou eauseoa 111111111111111111111111111115555555555555555555511111111111117777777111111");

	constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void countryRequired() {
	Address addr = defaultAddress();
	addr.setCountry(null);

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

    @Test
    public void validCountriesAccepdted() {
	Address addr = defaultAddress();
	addr.setCountry("New Zealand");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

	addr.setCountry("3. Lichtenstein");

	constraintViolations = validator.validate(addr);
	assertEquals(0, constraintViolations.size());

    }

    @Test
    public void invalidCountriesRejected() {
	Address addr = defaultAddress();
	addr.setCountry(" aths");

	Set<ConstraintViolation<Address>> constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

	addr.setCountry(
		"aihsoehseohu soaeushou eauseoa 111111111111111111111111111115555555555555555555511111111111117777777111111");

	constraintViolations = validator.validate(addr);
	assertEquals(1, constraintViolations.size());

    }

}
