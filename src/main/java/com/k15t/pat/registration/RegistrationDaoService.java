package com.k15t.pat.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import com.k15t.pat.registration.Registration.Address;
import com.k15t.pat.util.PasswordUtil;

/**
 * A service wrapping access to persistent storage with information about stored
 * registrations.
 */
@Singleton
@Service
public class RegistrationDaoService {

    private Map<String, ImmutableRegistration> registrations = new HashMap<>();

    /**
     * <p>
     * Adds a new registration if a person with identical e-mail has not yet
     * been added. Returns true if the new registration was added.
     * </p>
     * <p>
     * If there already was a registration with matching e-mail present, does
     * not do a new registration and returns false.
     * </p>
     * 
     * @param toAdd
     *            {@link Registration} to add
     * @return true if a new registration was added, false if not
     */
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

    /**
     * <p>
     * Returns a {@link List} of all the {@link Registration}s added.
     * </p>
     * <p>
     * Accessing this method should be access controlled to admins.
     * </p>
     * 
     * @return all the {@link Registration}s present.
     */
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
	    reg.setPassword("HASH,Base64: " + Base64.toBase64String(immutableReg.passwordHash));
	    reg.setPhoneNumber(immutableReg.phone);

	    reg.setAddress(addr);

	    res.add(reg);
	});

	return res;
    }

    /**
     * Hashes the password using the chosen hashing configuration in
     * {@link PasswordUtil}
     * 
     * @param password
     *            password to hash
     * @return the hashed bytes (and other needed information) as a byte array
     */
    private byte[] hashPassword(String password) {
	return PasswordUtil.hashPw(password);
    }

    /**
     * Helper class used in the "persistent" storage for representing a
     * {@link Registration}
     */
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

    /**
     * Helper class used in the "persistent" storage for representing an
     * {@link Address}
     */
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
