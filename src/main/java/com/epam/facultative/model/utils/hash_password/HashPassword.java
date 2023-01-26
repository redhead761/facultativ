package com.epam.facultative.model.utils.hash_password;

import de.mkammerer.argon2.*;

public class HashPassword {
    private static final Argon2 argon2 = Argon2Factory.create();

    private HashPassword(){}

    public static String encode(String password) {
        return argon2.hash(2,22*2222,2, password.toCharArray());
    }

    public static boolean verify(String hash, String password) {
        return argon2.verify(hash, password.toCharArray());
    }
}
