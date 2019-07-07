package com.xmyy.pay.core.util;

/**
 * 十六进制和二进制转换工具<br>
 *
 * @author wangzejun
 * @time 2018年 09月07日 17:36:25
 * @since 1.0.0
 */
public final class HexByteUtils {

    /**
     * 二进制转十六进制<br>
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuffer retString = new StringBuffer();

        for (int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(256 + (bytes[i] & 255)).substring(1).toUpperCase());
        }

        return retString.toString();
    }

    /**
     * 二进制转十六进制<br>
     * @param bytes
     * @return
     */
    public static String byte2hex(byte[] bytes, int index, int len) {
        StringBuffer retString = new StringBuffer();

        for (int i = index; i < len; ++i) {
            retString.append(Integer.toHexString(256 + (bytes[i] & 255)).substring(1).toUpperCase());
        }

        return retString.toString();
    }

    /**
     * 十六进制转二进制<br>
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];

        for (int i = 0; i < bts.length; ++i) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return bts;
    }
}