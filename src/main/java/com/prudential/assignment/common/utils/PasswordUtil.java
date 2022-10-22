package com.prudential.assignment.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class PasswordUtil {

    private static final Random random = new Random();

    public static String generateRandomSalt() {
        return String.valueOf(10000000 + random.nextInt(99999999));
    }

    public static String encodePassword(String password, String salt) {
        // split the salt to two piece
        int mid = salt.length() / 2;
        String firstSalt = salt.substring(0, mid);
        String secondSalt = salt.substring(mid);
        return DigestUtils.md5Hex(firstSalt + password + secondSalt);
    }

    public static boolean verifyPassword(String password, String dbPassword, String salt) {
        return dbPassword.equals(encodePassword(password, salt));
    }

    public static void main(String[] args) {
    }


}
