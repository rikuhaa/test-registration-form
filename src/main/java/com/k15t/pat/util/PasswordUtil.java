package com.k15t.pat.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    // TODO if something form these values (as well as the used hashing method
    // was changed) it would be required to start also storing the parameters in
    // the hashes in one way or another in order to be able to check whether
    // a password matches the hash

    private static final int SALT_BYTES = 32;
    private static final int HASH_BYTES = 32;

    private static final int ITERATE_COUNT = 1000;

    /**
     * <p>
     * Hashes the password in a hard to break method and returns a byte array of
     * resulting bytes.
     * </p>
     * <p>
     * Whether a password matches the returned bytes can later be checked by
     * using the {@link PasswordUtil#passwordMatchesHash(String, byte[])} method
     * </p>
     * 
     * @param pw
     *            password to hash
     * @return a byte array containing hashed password and needed config info
     */
    public static byte[] hashPw(String pw) {

	SecureRandom secRand = new SecureRandom();

	byte[] genSaltBytes = secRand.generateSeed(SALT_BYTES);

	PKCS5S2ParametersGenerator keyGen = new PKCS5S2ParametersGenerator();

	keyGen.init(pw.getBytes(Charset.forName("UTF-8")), genSaltBytes, ITERATE_COUNT);

	byte[] hash = ((KeyParameter) keyGen.generateDerivedMacParameters(8 * HASH_BYTES)).getKey();

	return Arrays.concatenate(genSaltBytes, hash);
    }

    /**
     * <p>
     * Tests whether given password string was used for getting the hash bytes
     * given as the second argument.
     * </p>
     * <p>
     * For correct operation, the byte array containing salt and hash bytes,
     * must be obtained by using {@link PasswordUtil#hashPw(String)} method (or
     * exactly matching procedure)
     * </p>
     * 
     * @param pw
     *            password string to test
     * @param saltAndHash
     *            hash (and config) bytes that the password is expected to match
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean passwordMatchesHash(String pw, byte[] saltAndHash) {

	byte[] storedSaltBytes = Arrays.copyOfRange(saltAndHash, 0, SALT_BYTES);

	byte[] storedHashBytes = Arrays.copyOfRange(saltAndHash, SALT_BYTES, SALT_BYTES + HASH_BYTES);

	PKCS5S2ParametersGenerator keyGen = new PKCS5S2ParametersGenerator();

	keyGen.init(pw.getBytes(Charset.forName("UTF-8")), storedSaltBytes, ITERATE_COUNT);

	byte[] hashDerivFromGivenPw = ((KeyParameter) keyGen.generateDerivedMacParameters(8 * HASH_BYTES)).getKey();

	return Arrays.areEqual(storedHashBytes, hashDerivFromGivenPw);
    }

}
