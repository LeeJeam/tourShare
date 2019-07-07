package com.xmyy.pay.core.util;

import com.xmyy.pay.allinpay.constants.YunConfig;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public final class RSAUtil {
    private static Provider provider = new BouncyCastleProvider();

    //证书路径：可以指定类路径，也可以指定绝对路径
    private static final String CERT_PATH = YunConfig.PATH;
    //证书名字
    private static final String ALIAS_NAME = YunConfig.ALIAS;
    //证书密码
    private static final String PASSWORD = YunConfig.PWD;

    /**
     * 获取证书的路径
     * @param path
     * @return
     */
    public static String getCertPath(String path) {
        if (StringUtils.isNotBlank(path) && path.startsWith("classpath")) {
            String[] pathParts = CERT_PATH.split(":");
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL resource = loader.getResource(pathParts[1]);
            String resourcePath = resource.getPath();
            return resourcePath;
        }
        return path;
    }

    /**
     * 加密 <br>
     * （使用该类自己的配置证书）<br>
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return Base64.encode(encrypt(loadPrivateKey(ALIAS_NAME,getCertPath(CERT_PATH),PASSWORD),data.getBytes()));
    }

    /**
     * 加密<br>
     * （使用该类自己的配置证书）<br>
     * @param data
     * @return
     * @throws Exception
     */
    public static String dencrypt(String data) throws Exception {
        return new String(decrypt(loadPublicKey(ALIAS_NAME, getCertPath(CERT_PATH), PASSWORD), Base64.decode(data)));
    }

    /**
     * 加密<br>
     * （使用该类自己的配置证书）<br>
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data) throws Exception {
        return encrypt(loadPrivateKey(ALIAS_NAME, getCertPath(CERT_PATH), PASSWORD), data);
    }

    /**
     * 加密<br>
     * （使用该类自己的配置证书）<br>
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] dencrypt(byte[] data) throws Exception {
        return decrypt(loadPublicKey(ALIAS_NAME, getCertPath(CERT_PATH), PASSWORD), data);
    }

    /**
     * 签名<br>
     * （使用该类自己的配置证书）<br>
     * @param text
     * @return
     * @throws Exception
     */
    public static String sign(String text) throws Exception {
        return sign(loadPrivateKey(ALIAS_NAME, getCertPath(CERT_PATH), PASSWORD), text);
    }


    /**
     * 验签<br>
     * （使用该类自己的配置证书）<br>
     * @param text
     * @return
     * @throws Exception
     */
    public static boolean verify(String text, String sign) throws Exception {
        return verify(loadPublicKey(ALIAS_NAME, getCertPath(CERT_PATH), PASSWORD), text, sign);
    }

    /**
     * 签名<br>
     * 需要传入证书<br>
     * @param privateKey
     * @param text
     * @return
     * @throws Exception
     */
    private static String sign(PrivateKey privateKey, String text) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA", provider);
        signature.initSign(privateKey);
        signature.update(text.getBytes("utf8"));
        byte[] data = signature.sign();
        return Base64.encode(data);
    }


    /**
     * 验签<br>
     * 需要自己传入证书<br>
     * @param publicKey
     * @param text
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
        Signature signature = Signature.getInstance("SHA1WithRSA", provider);
        signature.initVerify(publicKey);
        signature.update(text.getBytes("utf8"));
        byte[] signed = Base64.decode(sign);
        return signature.verify(signed);
    }

    /**
     *获取证书的私钥<br>
     * @param alias
     * @param path
     * @param password
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String alias, String path, String password)
            throws Exception {
        FileInputStream ksfis = null;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");

            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();
            char[] keyPwd = password.toCharArray();

            ks.load(ksfis, storePwd);

            return ((PrivateKey) ks.getKey(alias, keyPwd));
        } finally {
            if (ksfis != null)
                ksfis.close();
        }
    }

    /**
     * 获取证书的公钥<br>
     * @param alias
     * @param path
     * @param password
     * @return
     * @throws Exception
     */
    public static PublicKey loadPublicKey(String alias, String path, String password)
            throws Exception {
        FileInputStream ksfis = null;
        try {
            KeyStore ks = KeyStore.getInstance("pkcs12");

            ksfis = new FileInputStream(path);
            char[] storePwd = password.toCharArray();

            ks.load(ksfis, storePwd);

            return ks.getCertificate(alias).getPublicKey();
        } finally {
            if (ksfis != null)
                ksfis.close();
        }
    }

    /**
     * 获取证书的RSA算法的公钥<br>
     * @param modulus
     * @param publicExponent
     * @return
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent)
            throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", provider);
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return ((RSAPublicKey) keyFac.generatePublic(pubKeySpec));
        } catch (InvalidKeySpecException ex) {
            throw ex;
        }
    }

    /**
     * 获取证书的RSA算法的私钥<br>
     * @param modulus
     * @param privateExponent
     * @return
     * @throws Exception
     */
    private static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent)
            throws Exception {
        KeyFactory keyFac = null;
        try {
            keyFac = KeyFactory.getInstance("RSA", provider);
        } catch (NoSuchAlgorithmException ex) {
            throw ex;
        }

        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return ((RSAPrivateKey) keyFac.generatePrivate(priKeySpec));
        } catch (InvalidKeySpecException ex) {
            throw ex;
        }
    }

    /**
     * 加密<br>
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cipher.init(1, key);
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(data.length);
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];

            for (int i = 0; data.length - i * blockSize > 0; ++i) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
            }

            return raw;
        } catch (Exception var9) {
            throw var9;
        }
    }

    /**
     * 解密<br>
     * @param key
     * @param raw
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(Key key, byte[] raw) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            cipher.init(2, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);

            for (int j = 0; raw.length - j * blockSize > 0; ++j) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
            }

            return bout.toByteArray();
        } catch (Exception var6) {
            throw var6;
        }
    }

    /**
     * 获取公钥的字符串<br>
     * @param key
     * @return
     * @throws Exception
     */
    public static String getPublicKeyString(RSAPublicKey key) throws Exception {
        String exponent = HexByteUtils.byte2hex(key.getPublicExponent().toByteArray());
        String modulus = HexByteUtils.byte2hex(key.getModulus().toByteArray());
        StringBuffer sb = new StringBuffer();
        sb.append(modulus).append(" ").append(exponent);
        return sb.toString();
    }

    /**
     * 获取私钥的字符串<br>
     * @param key
     * @return
     * @throws Exception
     */
    public static String getPrivateKeyString(RSAPrivateKey key) throws Exception {
        String exponent = HexByteUtils.byte2hex(key.getPrivateExponent().toByteArray());
        String modulus = HexByteUtils.byte2hex(key.getModulus().toByteArray());
        StringBuffer sb = new StringBuffer();
        sb.append(modulus).append(" ").append(exponent);
        return sb.toString();
    }

    /**
     * 根据字符串获取RSA公钥<br>
     * @param keyString
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String keyString) throws Exception {
        String[] part = keyString.split(" ");
        if (part.length != 2) {
            throw new Exception("密钥文件错误。");
        } else {
            byte[] bytes = HexByteUtils.hex2byte(part[0]);
            BigInteger modulus = new BigInteger(bytes);
            bytes = HexByteUtils.hex2byte(part[1]);
            BigInteger publicExponent = new BigInteger(bytes);
            return generateRSAPublicKey(modulus.toByteArray(), publicExponent.toByteArray());
        }
    }

    /**
     * 根据字符串获取RSA私钥<br>
     * @param keyString
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String keyString) throws Exception {
        String[] part = keyString.split(" ");
        if (part.length != 2) {
            throw new Exception("密钥文件错误。");
        } else {
            byte[] bytes = HexByteUtils.hex2byte(part[0]);
            BigInteger modulus = new BigInteger(bytes);
            bytes = HexByteUtils.hex2byte(part[1]);
            BigInteger privateExponent = new BigInteger(bytes);
            return generateRSAPrivateKey(modulus.toByteArray(), privateExponent.toByteArray());
        }
    }

    public static void main(String[] args) throws Exception {
        String encrypt = encrypt("1212333");
        System.out.println(encrypt);
        System.out.println(dencrypt(encrypt));
    }
}
