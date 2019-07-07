package top.ibase4j.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.config.Configs;
import top.ibase4j.core.exception.BusinessException;
import top.ibase4j.core.support.cache.CacheManager;
import top.ibase4j.core.support.generator.Sequence;
import top.ibase4j.mapper.LockMapper;
import top.ibase4j.model.Lock;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ShenHuaJie
 * @since 2018年5月24日 下午6:37:31
 */
public final class CacheUtil {
    private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
    private static LockMapper lockMapper;
    private static CacheManager cacheManager; //RedisHelper实现，使用lettuce
    private static CacheManager lockManager; //RedissonHelper实现，使用redisson
    private static Map<String, Thread> safeThread = InstanceUtil.newHashMap();
    private static Map<String, ReentrantLock> thread = InstanceUtil.newConcurrentHashMap();
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void setLockMapper(LockMapper lockMapper) {
        CacheUtil.lockMapper = lockMapper;
    }

    public static void setCacheManager(CacheManager cacheManager) {
        CacheUtil.cacheManager = cacheManager;
    }

    public static void setLockManager(CacheManager cacheManager) {
        CacheUtil.lockManager = cacheManager;
    }

    public static CacheManager getCache() {
        return cacheManager;
    }

    public static CacheManager getLockManager() {
        return lockManager;
    }

    /**默认锁定一分钟*/
    public static boolean getLock(String key, String requestId) {
        return getLock(key, key, requestId);
    }

    public static boolean getLock(String key, String requestId, int seconds) {
        return getLock(key, key, requestId, seconds);
    }

    /**默认锁定一分钟*/
    public static boolean getLock(String key, String name, String requestId) {
        return getLock(key, name, requestId, 60);
    }

    public static boolean getLock(String key, String name, String requestId, int seconds) {
        boolean success = tryLock(key, name, requestId, seconds);
        if (success) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    if (!isInterrupted()) {
                        ThreadUtil.sleep((seconds - 1) * 10);
                    }
                    while (lockManager.get(key) != null) {
                        logger.info("守护{}", key);
                        lockManager.expire(key, seconds);
                        Lock param = new Lock();
                        param.setKey(key);
                        param.setCreateTime(new Date());
                        lockMapper.updateById(param);
                        if (!isInterrupted()) {
                            ThreadUtil.sleep(seconds * 10);
                        }
                    }
                }
            };
            thread.start();
            safeThread.put(key, thread);
        }
        return success;
    }

    private static boolean tryLock(String key, String name, String requestId, int seconds) {
        logger.debug("TOLOCK : " + key);
        try {
            boolean success = lockManager.lock(key, requestId, seconds);
            if (success) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        getDBLock(key, name, seconds);
                    }
                });
            }
            return success;
        } catch (Exception e) {
            logger.error("从redis获取锁信息失败", e);
            return getDBLock(key, name, seconds);
        }
    }

    /**
     */
    private static Boolean getDBLock(String key, String name, int seconds) {
        if (!PropertiesUtil.getBoolean("dblock.open", false)) {
            return false;
        }
        try {
            if (thread.get(key) == null) {
                thread.put(key, new ReentrantLock());
            }
            thread.get(key).lock();
            try {
                Lock param = new Lock();
                param.setKey(key);
                Lock lock = lockMapper.selectOne(param);
                if (lock == null) {
                    return executorService.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            logger.debug("保存锁信息到数据库>" + key);
                            param.setName(name);
                            param.setExpireSecond(seconds);
                            return lockMapper.insert(param) == 1;
                        }
                    }).get();
                }
                return false;
            } finally {
                if (thread.get(key) != null) {
                    thread.get(key).unlock();
                }
            }
        } catch (Exception e) {
            logger.error("保存锁信息失败", e);
            ThreadUtil.sleep(50);
            return getDBLock(key, name, seconds);
        }
    }

    /** 解锁 */
    public static void unLock(String key, String requestId) {
        logger.debug("UNLOCK : " + key);
        try {
            lockManager.unlock(key, requestId);
        } catch (Exception e) {
            logger.error("从redis删除锁信息失败", e);
        }
        if (PropertiesUtil.getBoolean("dblock.open", false)) {
            deleteLock(key, 1);
        }
        safeThread.get(key).interrupt();
    }

    private static void deleteLock(String key, int times) {
        boolean success = false;
        try {
            if (thread.containsKey(key)) {
                thread.get(key).lock();
                try {
                    logger.debug("从数据库删除锁信息>" + key);
                    success = executorService.submit(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            Map<String, Object> columnMap = InstanceUtil.newHashMap("key_", key);
                            return lockMapper.deleteByMap(columnMap) > 0;
                        }
                    }).get();
                } finally {
                    thread.get(key).unlock();
                }
            }
        } catch (Exception e) {
            logger.error("从数据库删除锁信息失败", e);
        }
        if (!success) {
            if (times > PropertiesUtil.getInt("deleteLock.maxTimes", 20)) {
                return;
            }
            if (thread.containsKey(key)) {
                logger.warn(key + "从数据库删除锁信息失败,稍候再次尝试...");
            }
            ThreadUtil.sleep(100, 1000);
            deleteLock(key, times + 1);
        } else {
            thread.remove(key);
        }
    }

    /**
     * 次数检查
     *
     * @param key
     * @param seconds
     *            缓存时间
     * @param frequency
     *            最多次数
     * @param message
     *            超出次数提示信息
     */
    public static void refreshTimes(String key, int seconds, int frequency, String message) {
        String requestId = Sequence.next().toString();
        if (getLock(key + "-LOCK", "次数限制", requestId, 10)) {
            try {
                Integer times = 1;
                String timesStr = (String)lockManager.get(key);
                if (DataUtil.isNotEmpty(timesStr)) {
                    times = Integer.valueOf(timesStr) + 1;
                    if (times > frequency) {
                        throw new BusinessException(message);
                    }
                }
                lockManager.set(key, times.toString(), seconds);
            } finally {
                unLock(key + "-LOCK", requestId);
            }
        } else {
            refreshTimes(key, seconds, frequency, message);
        }
    }
}
