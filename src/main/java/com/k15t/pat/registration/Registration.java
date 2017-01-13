package com.k15t.pat.registration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Component
public class Registration {

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    // names (at least internationally) don't have that
    // much common structure but would probably always start
    // with a non-whitespace character and some length limit
    // is okay to have
    @Pattern(regexp = "^\\S{1}.{0,79}\\S$")
    private String name;

    @NotNull
    // FIXME password validation
    private String password;

    @NotNull
    @Valid
    private Address address;

    // even phone numbers don't have any universally simple format
    // and since this an optional field, it should be okay to trust
    // that if someone entered a phone number it might also be okay

    // maybe there could be some service that could be used that
    // would (after country code selection) know the correct formats
    // (eg. google's libphonenumber looks like that it could be able
    // to do the job)

    private String phoneNumber;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public static class Address {

	// TODO real validation would be best done
	// by calling some address service, but generally

	@NotNull
	@Pattern(regexp = "\\S{1}.{0,79}\\S")
	private String streetAddress;

	@NotNull
	@Pattern(regexp = "\\S{1}.{0,19}\\S")
	private String zipCode;

	@NotNull
	@Pattern(regexp = "\\S{1}.{0,49}\\S")
	private String city;

	// TODO this could be an enum or some
	// list of valid country codes
	@NotNull
	@Pattern(regexp = "\\S{1}.{0,49}\\S")
	private String country;

	public String getStreetAddress() {
	    return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
	    this.streetAddress = streetAddress;
	}

	public String getZipCode() {
	    return zipCode;
	}

	public void setZipCode(String zipCode) {
	    this.zipCode = zipCode;
	}

	public String getCity() {
	    return city;
	}

	public void setCity(String city) {
	    this.city = city;
	}

	public String getCountry() {
	    return country;
	}

	public void setCountry(String country) {
	    this.country = country;
	}

    }

}
