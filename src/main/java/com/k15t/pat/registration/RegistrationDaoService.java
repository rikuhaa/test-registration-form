package com.k15t.pat.registration;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.springframework.stereotype.Service;

import com.k15t.pat.registration.Registration.Address;

@Singleton
@Service
public class RegistrationDaoService {

    private Map<String, ImmutableRegistration> registrations = new HashMap<>();

    public boolean addRegistration(Registration toAdd) {
	if (registrations.containsKey(toAdd.getEmail())) {
	    return false;
	} else {

	    ImmutableAddress addr = new ImmutableAddress(toAdd.getAddress().getStreetAddress(),
		    toAdd.getAddress().getZipCode(), toAdd.getAddress().getCity(), toAdd.getAddress().getCountry());

	    byte[] passwordHash = hashPassword(toAdd.getPassword());

	    ImmutableRegistration reg = new ImmutableRegistration(toAdd.getEmail(), toAdd.getName(), passwordHash,
		    toAdd.getPhoneNumber(), addr);

	    registrations.put(reg.email, reg);

	    return true;
	}
    }

    public List<Registration> getRegistrations() {
	List<Registration> res = new ArrayList<Registration>();

	registrations.forEach((id, immutableReg) -> {
	    Address addr = new Address();

	    addr.setStreetAddress(immutableReg.address.streetAddress);
	    addr.setCountry(immutableReg.address.country);
	    addr.setCity(immutableReg.address.city);
	    addr.setZipCode(immutableReg.address.zipCode);

	    Registration reg = new Registration();

	    reg.setEmail(immutableReg.email);
	    reg.setName(immutableReg.name);
	    reg.setPassword("****");
	    reg.setPhoneNumber(immutableReg.phone);

	    reg.setAddress(addr);

	    res.add(reg);
	});

	return res;
    }

    private byte[] hashPassword(String password) {
	return password.getBytes(Charset.forName("UTF-8"));
    }

    private static class ImmutableRegistration {

	private final String email;
	private final String name;
	private final byte[] passwordHash;

	private final String phone;
	private final ImmutableAddress address;

	public ImmutableRegistration(String email, String name, byte[] passwordHash, String phone,
		ImmutableAddress address) {
	    super();
	    this.email = email;
	    this.name = name;
	    this.passwordHash = passwordHash;
	    this.phone = phone;
	    this.address = address;
	}

    }

    private final static class ImmutableAddress {

	private final String streetAddress;
	private final String zipCode;
	private final String city;
	private final String country;

	public ImmutableAddress(String streetAddress, String zipCode, String city, String country) {
	    super();
	    this.streetAddress = streetAddress;
	    this.zipCode = zipCode;
	    this.city = city;
	    this.country = country;
	}

    }

}
