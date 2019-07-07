package com.xmyy.common.util;

/**
 * User: zlp
 */
public class PasswordUtil {

    public static String encodePassword(String input) {
        return  com.xmyy.common.util.PasswordEncoder.encode(input);
    }

    /**
     * 验证密码
     * @param input        需要校验的明文密码
     * @param encodedPass  加密后的密码字符串
     * @return 是否验证成功
     */
    public static boolean verifyPassword(String input, String encodedPass) {
    	if(input == null || encodedPass == null) {
    		return false;
    	}
    	
        return com.xmyy.common.util.PasswordEncoder.matches(input, encodedPass);
    }
    
}
