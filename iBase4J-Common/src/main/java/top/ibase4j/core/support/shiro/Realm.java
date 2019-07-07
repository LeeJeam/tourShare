package top.ibase4j.core.support.shiro;

/**
 * 
 * @author ShenHuaJie
 * @version 2018年3月2日 下午12:40:40
 */
public interface Realm {
    public void setSessionDAO(RedisSessionDAO sessionDAO);
}
