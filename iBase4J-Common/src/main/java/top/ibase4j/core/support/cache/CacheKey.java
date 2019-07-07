package top.ibase4j.core.support.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisHash;

import top.ibase4j.core.Constants;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;

public class CacheKey {
    private String value;
    private int timeToLive;

    public CacheKey(String value, int timeToLive) {
        this.value = value;
        this.timeToLive = timeToLive;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public static CacheKey getInstance(Class<?> cls) {
        CacheKey cackKey = Constants.cacheKeyMap.get(cls);
        if (DataUtil.isEmpty(cackKey)) {
            String cacheName;
            RedisHash redisHash = null;
            Long timeToLive = PropertiesUtil.getLong("redis.expiration", 600L);
            ParameterizedType parameterizedType = (ParameterizedType)cls.getGenericSuperclass();
            if (parameterizedType != null) {
                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                if (actualTypes != null && actualTypes.length > 0) {
                    // 实体注解@RedisHash
                    redisHash = actualTypes[0].getClass().getAnnotation(RedisHash.class);
                }
            }
            if (redisHash != null) {
                cacheName = redisHash.value();
                timeToLive = redisHash.timeToLive();
            } else {
                // Service注解CacheConfig
                CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
                if (cacheConfig != null && ArrayUtils.isNotEmpty(cacheConfig.cacheNames())) {
                    cacheName = cacheConfig.cacheNames()[0];
                } else {
                    return null;
                }
            }
            cackKey = new CacheKey(Constants.CACHE_NAMESPACE + cacheName, timeToLive.intValue());
            Constants.cacheKeyMap.put(cls, cackKey);
        }
        return cackKey;
    }
}
