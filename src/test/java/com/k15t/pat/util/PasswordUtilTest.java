package com.k15t.pat.util;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilTest {

    @Test
    public void hashesStrings() {

	String testPw1 = "tshanehouan";

	String testPw2 = "teashounsh";

	byte[] hash1 = PasswordUtil.hashPw(testPw1);

	byte[] hash2 = PasswordUtil.hashPw(testPw2);

	Assert.assertFalse(Arrays.equals(hash1, hash2));

	Assert.assertFalse(Arrays.equals(hash1, testPw1.getBytes(Charset.forName("UTF-8"))));
	Assert.assertFalse(Arrays.equals(hash1, testPw1.getBytes()));

	Assert.assertFalse(Arrays.equals(hash2, testPw2.getBytes(Charset.forName("UTF-8"))));
	Assert.assertFalse(Arrays.equals(hash2, testPw2.getBytes()));

    }

    @Test
    public void passwordAttemptsToHashesCorrectly() {

	String testPw = "snaohueh[(]*]{[(*];!yg*]/";
	String testPw2 = "snh{}[*!])+*&";

	byte[] hash1 = PasswordUtil.hashPw(testPw);
	byte[] hash2 = PasswordUtil.hashPw(testPw2);

	Assert.assertTrue(PasswordUtil.passwordMatchesHash("snaohueh[(]*]{[(*];!yg*]/", hash1));

	Assert.assertFalse(PasswordUtil.passwordMatchesHash("snaohueh[(]*]{[(*];!yg*]/", hash2));

	Assert.assertTrue(PasswordUtil.passwordMatchesHash("snh{}[*!])+*&", hash2));

	Assert.assertFalse(PasswordUtil.passwordMatchesHash("snh{}[*!])+*&", hash1));

	Assert.assertFalse(PasswordUtil.passwordMatchesHash("auehlrhr[{}", hash2));

	Assert.assertFalse(PasswordUtil.passwordMatchesHash("[({thsnhoaeu+c", hash1));

    }

    @Test
    public void usesRandomSalt() {
	String password = "password";

	byte[] hash1 = PasswordUtil.hashPw(password);
	byte[] hash2 = PasswordUtil.hashPw(password);

	Assert.assertFalse(Arrays.equals(hash1, hash2));

	Assert.assertTrue(PasswordUtil.passwordMatchesHash(password, hash1));
	Assert.assertTrue(PasswordUtil.passwordMatchesHash(password, hash2));

    }

}
