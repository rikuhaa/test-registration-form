package com.k15t.pat.registration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

/**
 * <p>
 * A bean wrapping information required for making a registration.
 * </p>
 * <p>
 * Contains the required and optional fields as well as value validation.
 * </p>
 */
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
    // TODO password validation (eg. strength validation)
    // could use eg. Pasasy library
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

    @Override
    public String toString() {
	return "Registration [email=" + email + ", name=" + name + ", password=" + password + ", address=" + address
		+ ", phoneNumber=" + phoneNumber + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Registration other = (Registration) obj;
	if (address == null) {
	    if (other.address != null) {
		return false;
	    }
	} else if (!address.equals(other.address)) {
	    return false;
	}
	if (email == null) {
	    if (other.email != null) {
		return false;
	    }
	} else if (!email.equals(other.email)) {
	    return false;
	}
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	if (password == null) {
	    if (other.password != null) {
		return false;
	    }
	} else if (!password.equals(other.password)) {
	    return false;
	}
	if (phoneNumber == null) {
	    if (other.phoneNumber != null) {
		return false;
	    }
	} else if (!phoneNumber.equals(other.phoneNumber)) {
	    return false;
	}
	return true;
    }

    /**
     * A Java bean wrapping address information fields and validation. Used as a
     * part of {@link Registration}
     */
    public static class Address {

	// TODO real validation would be best done
	// by calling some address service
	// (or could at least be given as a help in client side UI)
	// but in general case there is little universal patterns
	// for valid addresses and it would always be quite easy to
	// give false information in correct pattern, so user has
	// to be trusted somewhat here

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

	@Override
	public String toString() {
	    return "Address [streetAddress=" + streetAddress + ", zipCode=" + zipCode + ", city=" + city + ", country="
		    + country + "]";
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((city == null) ? 0 : city.hashCode());
	    result = prime * result + ((country == null) ? 0 : country.hashCode());
	    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
	    result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
	    return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
		return true;
	    }
	    if (obj == null) {
		return false;
	    }
	    if (getClass() != obj.getClass()) {
		return false;
	    }
	    Address other = (Address) obj;
	    if (city == null) {
		if (other.city != null) {
		    return false;
		}
	    } else if (!city.equals(other.city)) {
		return false;
	    }
	    if (country == null) {
		if (other.country != null) {
		    return false;
		}
	    } else if (!country.equals(other.country)) {
		return false;
	    }
	    if (streetAddress == null) {
		if (other.streetAddress != null) {
		    return false;
		}
	    } else if (!streetAddress.equals(other.streetAddress)) {
		return false;
	    }
	    if (zipCode == null) {
		if (other.zipCode != null) {
		    return false;
		}
	    } else if (!zipCode.equals(other.zipCode)) {
		return false;
	    }
	    return true;
	}

    }

}
