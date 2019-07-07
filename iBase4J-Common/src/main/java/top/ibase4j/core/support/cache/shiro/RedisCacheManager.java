package top.ibase4j.core.support.cache.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.Constants;
import top.ibase4j.core.config.Configs;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * @author ShenHuaJie
 * @version 2017年3月24日 下午8:50:14
 */
public class RedisCacheManager implements CacheManager {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    @SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();
    /**
     * The Redis key prefix for caches 
     */
    private String keyPrefix = Constants.REDIS_SHIRO_CACHE;

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key 
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.cache.CacheManager#getCache(java.lang.String) */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {
            // create a new cache instance
            RedisCache cache = new RedisCache<K, V>(keyPrefix);
            // add it to the cache collection
            caches.put(name, cache);
        }
        return c;
    }

}
