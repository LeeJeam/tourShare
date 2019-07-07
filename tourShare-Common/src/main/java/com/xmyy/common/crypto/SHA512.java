/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.crypto;

import com.xmyy.common.util.HexString;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author zlp
 */
public class SHA512 {

    private static final String ALGORITHM = "SHA-512";
    protected static final Logger logger = LoggerFactory.getLogger(SHA512.class);
    private static final int DEFAULT_BUFFER_SIZE = 16 * 1024;

    public static String hashToBase64String(String data) {
        try {
            return hashToBase64String(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToBase64String(byte[] data) {
        return Base64.encodeBase64String(hash(data));
    }

    public static String hashToHexString(String data) {
        try {
            return hashToHexString(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToHexString(byte[] data) {
        return HexString.bytesToHexString(hash(data));
    }

    public static byte[] hash(String data) {
        try {
            return hash(data.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static byte[] hash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            return md.digest(data);
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToBase64String(InputStream stream) {
        return Base64.encodeBase64String(hash(stream));
    }

    public static String hashToHexString(InputStream stream) {
        return HexString.bytesToHexString(hash(stream));
    }

    public static byte[] hash(InputStream stream) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] data = new byte[DEFAULT_BUFFER_SIZE];
            while (stream.read(data) != -1) {
                md.update(data);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToBase64String(String data, byte[] salt) {
        try {
            return hashToBase64String(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToBase64String(byte[] data, byte[] salt) {
        return Base64.encodeBase64String(hash(data, salt));
    }

    public static String hashToHexString(String data, byte[] salt) {
        try {
            return hashToHexString(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToHexString(byte[] data, byte[] salt) {
        return HexString.bytesToHexString(hash(data, salt));
    }

    public static byte[] hash(String data, byte[] salt) {
        try {
            return hash(data.getBytes("UTF-8"), salt);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static byte[] hash(byte[] data, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(data);
            md.update(salt);
            byte[] digest = md.digest();
            byte[] hash = new byte[digest.length + salt.length];
            System.arraycopy(digest, 0, hash, 0, digest.length);
            System.arraycopy(salt, 0, hash, digest.length, salt.length);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String hashToBase64String(InputStream stream, byte[] salt) {
        return Base64.encodeBase64String(hash(stream, salt));
    }

    public static String hashToHexString(InputStream stream, byte[] salt) {
        return HexString.bytesToHexString(hash(stream, salt));
    }

    public static byte[] hash(InputStream stream, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] data = new byte[DEFAULT_BUFFER_SIZE];
            while (stream.read(data) != -1) {
                md.update(data);
            }
            byte[] digest = md.digest();
            byte[] hash = new byte[digest.length + salt.length];
            System.arraycopy(digest, 0, hash, 0, digest.length);
            System.arraycopy(salt, 0, hash, digest.length, salt.length);
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static void main(String[] args) {
        String sourceString = "this is a test.";
        String s1 = hashToBase64String(sourceString);
        String s2 = hashToHexString(sourceString);

        System.out.println("sourceString: " + sourceString);
        System.out.println("sha512 base64: " + s1);
        System.out.println("sha512 hex: " + s2);

        java.util.Random random = new java.util.Random();
        byte[] salt = new byte[8];
        random.nextBytes(salt);

        String s3 = hashToBase64String(sourceString, salt);
        String s4 = hashToHexString(sourceString, salt);

        System.out.println("salt: " + HexString.bytesToHexString(salt));
        System.out.println("sha512 with salt base64: " + s3);
        System.out.println("sha512 with salt hex: " + s4);
    }
}
