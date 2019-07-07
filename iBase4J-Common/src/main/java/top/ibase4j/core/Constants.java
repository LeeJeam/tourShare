package top.ibase4j.core;

import top.ibase4j.core.support.cache.CacheKey;
import top.ibase4j.core.util.InstanceUtil;

import java.util.Map;

/**
 * 常量表
 *
 * @author ShenHuaJie
 */
public interface Constants {
    /** 异常统一头信息 */
    String Exception_Head = "SOME ERRORS OCCURED.";
    /** 缓存键值 */
    Map<Class<?>, CacheKey> cacheKeyMap = InstanceUtil.newHashMap();
    /** 操作名称 */
    String OPERATION_NAME = "OPERATION_NAME";
    /** 客户端语言 */
    String USERLANGUAGE = "userLanguage";
    /** 客户端主题 */
    String WEBTHEME = "webTheme";
    /** 当前用户 */
    String CURRENT_USER = "CURRENT_USER";
    /** 客户端信息 */
    String USER_AGENT = "USER-AGENT";
    /** 客户端信息 */
    String USER_IP = "USER_IP";
    /** 登录地址 */
    String LOGIN_URL = "/login.html";
    /** 缓存命名空间 */
    String CACHE_NAMESPACE = "iBase4J:";
    /** 缓存命名空间 */
    String SYSTEM_CACHE_NAMESPACE = "S:iBase4J:";
    /** 缓存命名空间 */
    String CACHE_NAMESPACE_LOCK = "L:iBase4J:";
    /** 上次请求地址 */
    String PREREQUEST = CACHE_NAMESPACE + "PREREQUEST";
    /** 上次请求时间 */
    String PREREQUEST_TIME = CACHE_NAMESPACE + "PREREQUEST_TIME";
    /** 非法请求次数 */
    String MALICIOUS_REQUEST_TIMES = CACHE_NAMESPACE + "MALICIOUS_REQUEST_TIMES";
    /** 在线用户数量 */
    String ALLUSER_NUMBER = SYSTEM_CACHE_NAMESPACE + "ALLUSER_NUMBER";
    /** TOKEN */
    String TOKEN_KEY = SYSTEM_CACHE_NAMESPACE + "TOKEN_KEY";
    /** shiro cache */
    String REDIS_SHIRO_CACHE = SYSTEM_CACHE_NAMESPACE + "SHIRO-CACHE:";
    /** SESSION */
    String REDIS_SHIRO_SESSION = SYSTEM_CACHE_NAMESPACE + "SHIRO-SESSION:";
    /** CACHE */
    String MYBATIS_CACHE = "D:iBase4J:MYBATIS:";
    /** 默认数据库密码加密key */
    String DB_KEY = "90139119";
    /** 临时目录 */
    String TEMP_DIR = "/temp/";

    /** 日志表状态 */
    interface JOBSTATE {
        /** 日志表状态，初始状态，插入 */
        String INIT_STATS = "I";
        /** 日志表状态，成功 */
        String SUCCESS_STATS = "S";
        /** 日志表状态，失败 */
        String ERROR_STATS = "E";
        /** 日志表状态，未执行 */
        String UN_STATS = "N";
    }

    /** 短信验证码类型 */
    public interface MSGCHKTYPE {
        /** 注册 */
        String REGISTER = CACHE_NAMESPACE + "REGISTER:";
        /** 登录 */
        String LOGIN = CACHE_NAMESPACE + "LOGIN:";
        /** 修改密码验证码 */
        String CHGPWD = CACHE_NAMESPACE + "CHGPWD:";
        /** 身份验证验证码 */
        String VLDID = CACHE_NAMESPACE + "VLDID:";
        /** 信息变更验证码 */
        String CHGINFO = CACHE_NAMESPACE + "CHGINFO:";
        /** 活动确认验证码 */
        String AVTCMF = CACHE_NAMESPACE + "AVTCMF:";
    }

}
