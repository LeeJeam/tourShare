/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.util;


import com.xmyy.common.crypto.*;
import org.apache.commons.codec.binary.Base64;

import java.util.Random;

/**
 *
 * @author zlp
 */
public class PasswordEncoder {

    private static final ThreadLocal<Random> threadLocalRandom = new ThreadLocal<Random>() {

        @Override
        protected Random initialValue() {
            return new Random();
        }

    };

    private static final int SALT_LENGTH = 12;

    private static final int SHA512_LENGTH = 64;
    private static final int SHA384_LENGTH = 48;
    private static final int SHA256_LENGTH = 32;
    private static final int SHA_LENGTH = 20;
    private static final int MD5_LENGTH = 16;

    private static final String SSHA512_PREFIX = "{SSHA512}";
    private static final String SSHA512_PREFIX_LC = SSHA512_PREFIX.toLowerCase();
    private static final String SHA512_PREFIX = "{SHA512}";
    private static final String SHA512_PREFIX_LC = SHA512_PREFIX.toLowerCase();
    private static final String SSHA384_PREFIX = "{SSHA384}";
    private static final String SSHA384_PREFIX_LC = SSHA384_PREFIX.toLowerCase();
    private static final String SHA384_PREFIX = "{SHA384}";
    private static final String SHA384_PREFIX_LC = SHA384_PREFIX.toLowerCase();
    private static final String SSHA256_PREFIX = "{SSHA256}";
    private static final String SSHA256_PREFIX_LC = SSHA256_PREFIX.toLowerCase();
    private static final String SHA256_PREFIX = "{SHA256}";
    private static final String SHA256_PREFIX_LC = SHA256_PREFIX.toLowerCase();

    private static final String SSHA_PREFIX = "{SSHA}";
    private static final String SSHA_PREFIX_LC = SSHA_PREFIX.toLowerCase();
    private static final String SHA_PREFIX = "{SHA}";
    private static final String SHA_PREFIX_LC = SHA_PREFIX.toLowerCase();
    private static final String SMD5_PREFIX = "{SMD5}";
    private static final String SMD5_PREFIX_LC = SMD5_PREFIX.toLowerCase();
    private static final String MD5_PREFIX = "{MD5}";
    private static final String MD5_PREFIX_LC = MD5_PREFIX.toLowerCase();
    private static final String CLEARTEXT_PREFIX = "{CLEARTEXT}";
    private static final String CLEARTEXT_PREFIX_LC = CLEARTEXT_PREFIX.toLowerCase();

    /**
     * Calculates the hash of password and salt bytes and returns a base64
     * encoded concatenation of the hash and salt, prefixed with {SSHA}.
     *
     * @param rawPassword the password to be encoded.
     * @return the encoded password in the specified format
     */
    public static String encode(String rawPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        threadLocalRandom.get().nextBytes(salt);
        return SSHA_PREFIX + SHA1.hashToBase64String(rawPassword, salt);
    }

    private static String extractPrefix(String encPass) {
        if (!encPass.startsWith("{")) {
            return null;
        }
        int secondBrace = encPass.lastIndexOf('}');
        if (secondBrace < 0) {
            throw new IllegalArgumentException("Couldn't find closing brace for SHA prefix");
        }
        return encPass.substring(0, secondBrace + 1);
    }

    private static byte[] extractSalt(String encPass, int hash) {
        String encPassNoLabel = encPass.substring(6);
        byte[] hashAndSalt = Base64.decodeBase64(encPassNoLabel.getBytes());
        int saltLength = hashAndSalt.length - hash;
        byte[] salt = new byte[saltLength];
        System.arraycopy(hashAndSalt, hash, salt, 0, saltLength);
        return salt;
    }

    /**
     * Checks the validity of an unencoded password against an encoded one in
     * the form "{SSHA}sQuQF8vj8Eg2Y1hPdh3bkQhCKQBgjhQI".
     *
     * @param rawPassword unencoded password to be verified.
     * @param encodedPassword the actual SSHA/SHA/SMD5/MD5/CLEARTEXT encoded
     * password
     * @return true if they match (independent of the case of the prefix).
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        String prefix = extractPrefix(encodedPassword);
        String encodedRawPass;

        if (prefix == null) {
            return encodedPassword.equals(rawPassword);
        }

        if (prefix.equals(SSHA512_PREFIX) || prefix.equals(SSHA512_PREFIX_LC)) {
            encodedRawPass = SHA512.hashToBase64String(rawPassword, extractSalt(encodedPassword, SHA512_LENGTH));
        } else if (prefix.equals(SSHA384_PREFIX) || prefix.equals(SSHA384_PREFIX_LC)) {
            encodedRawPass = SHA384.hashToBase64String(rawPassword, extractSalt(encodedPassword, SHA384_LENGTH));
        } else if (prefix.equals(SSHA256_PREFIX) || prefix.equals(SSHA256_PREFIX_LC)) {
            encodedRawPass = SHA256.hashToBase64String(rawPassword, extractSalt(encodedPassword, SHA256_LENGTH));
        } else if (prefix.equals(SSHA_PREFIX) || prefix.equals(SSHA_PREFIX_LC)) {
            encodedRawPass = SHA1.hashToBase64String(rawPassword, extractSalt(encodedPassword, SHA_LENGTH));
        } else if (prefix.equals(SMD5_PREFIX) || prefix.equals(SMD5_PREFIX_LC)) {
            encodedRawPass = MD5.hashToBase64String(rawPassword, extractSalt(encodedPassword, MD5_LENGTH));
        } else if (prefix.equals(SHA512_PREFIX) || prefix.equals(SHA512_PREFIX_LC)) {
            encodedRawPass = SHA512.hashToBase64String(rawPassword);
        } else if (prefix.equals(SHA384_PREFIX) || prefix.equals(SHA384_PREFIX_LC)) {
            encodedRawPass = SHA384.hashToBase64String(rawPassword);
        } else if (prefix.equals(SHA256_PREFIX) || prefix.equals(SHA256_PREFIX_LC)) {
            encodedRawPass = SHA256.hashToBase64String(rawPassword);
        } else if (prefix.equals(SHA_PREFIX) || prefix.equals(SHA_PREFIX_LC)) {
            encodedRawPass = SHA1.hashToBase64String(rawPassword);
        } else if (prefix.equals(MD5_PREFIX) || prefix.equals(MD5_PREFIX_LC)) {
            encodedRawPass = MD5.hashToBase64String(rawPassword);
        } else if (prefix.equals(CLEARTEXT_PREFIX) || prefix.equals(CLEARTEXT_PREFIX_LC)) {
            encodedRawPass = rawPassword;
        } else {
            throw new IllegalArgumentException("Unsupported password prefix '" + prefix + "'");
        }
        return encodedRawPass.equals(encodedPassword.substring(prefix.length()));
    }

    public static void main(String[] args) {

        String s = encode("this is a password");
        System.out.println(s);
        boolean result = matches("this is a password", s);
        System.out.println(result);
        boolean result1 = matches("this is a password1", s);
        System.out.println(result1);
    }
}
