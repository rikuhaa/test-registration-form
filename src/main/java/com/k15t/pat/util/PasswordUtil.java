package com.k15t.pat.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    private static final int SALT_BYTES = 32;
    private static final int HASH_BYTES = 32;

    private static final int ITERATE_COUNT = 1000;

    public static byte[] hashPw(String pw) {

	SecureRandom secRand = new SecureRandom();

	byte[] genSaltBytes = secRand.generateSeed(SALT_BYTES);

	PKCS5S2ParametersGenerator keyGen = new PKCS5S2ParametersGenerator();

	keyGen.init(pw.getBytes(Charset.forName("UTF-8")), genSaltBytes, ITERATE_COUNT);

	byte[] hash = ((KeyParameter) keyGen.generateDerivedMacParameters(8 * HASH_BYTES)).getKey();

	return Arrays.concatenate(genSaltBytes, hash);
    }

    public static boolean passwordMatchesHash(String pw, byte[] saltAndHash) {

	byte[] storedSaltBytes = Arrays.copyOfRange(saltAndHash, 0, SALT_BYTES);

	byte[] storedHashBytes = Arrays.copyOfRange(saltAndHash, SALT_BYTES, SALT_BYTES + HASH_BYTES);

	PKCS5S2ParametersGenerator keyGen = new PKCS5S2ParametersGenerator();

	keyGen.init(pw.getBytes(Charset.forName("UTF-8")), storedSaltBytes, ITERATE_COUNT);

	byte[] hashDerivFromGivenPw = ((KeyParameter) keyGen.generateDerivedMacParameters(8 * HASH_BYTES)).getKey();

	return Arrays.areEqual(storedHashBytes, hashDerivFromGivenPw);
    }

}
