package com.k15t.pat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.k15t.pat.registration.AddressValidationTest;
import com.k15t.pat.registration.RegistrationDaoServiceTest;
import com.k15t.pat.registration.RegistrationResourceTest;
import com.k15t.pat.registration.RegistrationValidationTest;
import com.k15t.pat.util.PasswordUtilTest;

@RunWith(Suite.class)
@SuiteClasses({ StaticContentServerTest.class, AddressValidationTest.class, RegistrationDaoServiceTest.class,
	RegistrationResourceTest.class, RegistrationValidationTest.class, PasswordUtilTest.class })
public class AllTests {

}
