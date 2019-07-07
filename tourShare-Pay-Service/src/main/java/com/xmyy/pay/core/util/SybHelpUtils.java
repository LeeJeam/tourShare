package com.xmyy.pay.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * 通联收银宝辅助工具类，对接云商通不需要使用
 * @author wangzejun
 */
public class SybHelpUtils {

	/**
	 * md5 加密<br>
	 * @param b
	 * @return
	 */
	public static String md5(byte[] b) {
        try {
        	MessageDigest md = MessageDigest.getInstance("MD5");
        	 md.reset();
             md.update(b);
             byte[] hash = md.digest();
             StringBuffer outStrBuf = new StringBuffer(32);
             for (int i = 0; i < hash.length; i++) {
                 int v = hash[i] & 0xFF;
                 if (v < 16) {
                 	outStrBuf.append('0');
                 }
                 outStrBuf.append(Integer.toString(v, 16).toLowerCase());
             }
             return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
	
	/**
	 * 判断字符串是否为空<br>
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(s==null||"".equals(s.trim()))
			return true;
		return false;
	}
	
	/**
	 * 生成随机码<br>
	 * @param n
	 * @return
	 */
	public static String getValidatecode(int n) {
		Random random = new Random();
		String sRand = "";
		n = n == 0 ? 4 : n;// default 4
		for (int i = 0; i < n; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}
	
	/**
	 * 签名<br>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sign(TreeMap<String,String> params,String appkey) throws Exception{
		if(params.containsKey("sign"))//签名明文组装不包含sign字段
			params.remove("sign");
		params.put("key", appkey);
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, String> entry:params.entrySet()){
			if(entry.getValue()!=null&&entry.getValue().length()>0){
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		String sign = md5(sb.toString().getBytes("UTF-8"));//记得是md5编码的加签
		params.remove("key");
		return sign;
	}
	 
	 /**
	  * 校验签名<br>
	 * @param param
	 * @param appkey
	 * @return
	 * @throws Exception
	 */
	public static boolean validSign(TreeMap<String,String> param,String appkey) throws Exception{
		 if(param!=null&&!param.isEmpty()){
			 if(!param.containsKey("sign"))
	    			return false;
			 String sign = param.get("sign").toString();
			 String mysign = sign(param, appkey);
			 return sign.toLowerCase().equals(mysign.toLowerCase());
		 }
		 return false;
	 }

}
