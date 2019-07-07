package top.ibase4j.core.interceptor;

import com.alibaba.fastjson.JSON;
import top.ibase4j.core.Constants;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 恶意请求拦截器
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:16:57
 */
public class MaliciousRequestInterceptor extends BaseInterceptor {
    private boolean allRequest = false; // 拦截所有请求,否则拦截相同请求
    private boolean containsParamter = true; // 包含参数
    private int minRequestIntervalTime = 500; // 允许的最小请求间隔
    private int maxMaliciousTimes = 0; // 允许的最大恶意请求次数

    // 白名单
    private List<String> whiteUrls;
    private int _size = 0;

    public MaliciousRequestInterceptor() {
        // 读取文件
        String path = MaliciousRequestInterceptor.class.getResource("/").getFile();
        whiteUrls = FileUtil.readFile(path + "white/mrqWhite.txt");
        _size = null == whiteUrls ? 0 : whiteUrls.size();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getServletPath();
        if (url.endsWith("/unauthorized") || url.endsWith("/forbidden") || isWhiteReq(url.toLowerCase())) {
            return super.preHandle(request, response, handler);
        }
        if (containsParamter) {
            url += JSON.toJSONString(WebUtil.getParameterMap(request));
        }
        Object userId = WebUtil.getCurrentUser(request);
        String user = userId != null ? userId.toString() : WebUtil.getHost(request) + request.getHeader("USER-AGENT");
        String preRequest = (String) CacheUtil.getCache().getFire(Constants.PREREQUEST + user);
        Long preRequestTime = (Long) CacheUtil.getCache().getFire(Constants.PREREQUEST_TIME + user);
        int seconds = minRequestIntervalTime;
        if (preRequestTime != null && preRequest != null) { // 过滤频繁操作
            if ((url.equals(preRequest) || allRequest)
                    && System.currentTimeMillis() - preRequestTime < minRequestIntervalTime) {
                Integer maliciousRequestTimes = (Integer) CacheUtil.getCache()
                        .getFire(Constants.MALICIOUS_REQUEST_TIMES + user);
                if (maliciousRequestTimes == null) {
                    maliciousRequestTimes = 1;
                } else {
                    maliciousRequestTimes++;
                }
                CacheUtil.getCache().set(Constants.MALICIOUS_REQUEST_TIMES + user, maliciousRequestTimes, seconds);
                if (maliciousRequestTimes > maxMaliciousTimes) {
                    CacheUtil.getCache().set(Constants.MALICIOUS_REQUEST_TIMES + user, 0, seconds);
                    logger.warn("To intercept a malicious request : {}", url);
                    return WebUtil.write(response, HttpCode.MULTI_STATUS.value(),
                        HttpCode.MULTI_STATUS.msg());
                }
            } else {
                CacheUtil.getCache().set(Constants.MALICIOUS_REQUEST_TIMES + user, 0, seconds);
            }
        }
        CacheUtil.getCache().set(Constants.PREREQUEST + user, url, seconds);
        CacheUtil.getCache().set(Constants.PREREQUEST_TIME + user, System.currentTimeMillis(), seconds);
        return super.preHandle(request, response, handler);
    }

    /* 判断是否是白名单 */
    private boolean isWhiteReq(String requestUrl) {
        if (_size == 0) {
            return false;
        } else {
            for (String urlTemp : whiteUrls) {
                if (requestUrl.contains(urlTemp.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public MaliciousRequestInterceptor setAllRequest(boolean allRequest) {
        this.allRequest = allRequest;
        return this;
    }

    public MaliciousRequestInterceptor setContainsParamter(boolean containsParamter) {
        this.containsParamter = containsParamter;
        return this;
    }

    public MaliciousRequestInterceptor setMinRequestIntervalTime(int minRequestIntervalTime) {
        this.minRequestIntervalTime = minRequestIntervalTime;
        return this;
    }

    public MaliciousRequestInterceptor setMaxMaliciousTimes(int maxMaliciousTimes) {
        this.maxMaliciousTimes = maxMaliciousTimes;
        return this;
    }
}
