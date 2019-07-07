package top.ibase4j.core.util;

import com.alibaba.fastjson.JSON;
import top.ibase4j.core.support.Token;

public class KickOutTokenUtil {

	public static void setKickOutTokenInfo(String token, String value) {
		try {
			Token tokenInfo = new Token();
			tokenInfo.setTime(System.currentTimeMillis());
			tokenInfo.setValue(value);
			CacheUtil.getLockManager().hset("S:iBase4J:KICKOUT_TOKEN_KEY", token, JSON.toJSONString(tokenInfo));
		} catch (Exception e) {
			throw new RuntimeException("保存踢出token失败，错误信息：", e);
		}
	}

	public static void delKickOutToken(String token) {
		try {
			CacheUtil.getLockManager().hdel("S:iBase4J:KICKOUT_TOKEN_KEY", token);
		} catch (Exception e) {
			throw new RuntimeException("删除踢出token失败，错误信息：", e);
		}
	}

	public static Token getKickOutTokenInfo(String token) {
		String value = (String) CacheUtil.getLockManager().hget("S:iBase4J:KICKOUT_TOKEN_KEY", token);
		Token tokenInfo = JSON.parseObject(value, Token.class);
		return tokenInfo;
	}
}
