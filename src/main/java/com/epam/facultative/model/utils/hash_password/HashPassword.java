package com.epam.facultative.model.utils.hash_password;

import de.mkammerer.argon2.*;

/**
 * Encode and verify encoded passwords. Uses Argon2 library to encode
 *
 * @author Oleksandr Panchenko
 * @version 1.0
 */
public class HashPassword {
    private static final Argon2 argon2 = Argon2Factory.create();
    private static final int i = 2;
    private static final int i1 = 22 * 2222;
    private static final int i2 = 2;

    private HashPassword() {
    }

    public static String encode(String password) {
        return argon2.hash(i, i1, i2, password.toCharArray());
    }

    public static boolean verify(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}
