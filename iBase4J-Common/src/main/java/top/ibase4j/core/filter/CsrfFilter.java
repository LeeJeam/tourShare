package top.ibase4j.core.filter;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.util.DateUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 跨站请求拦截
 *
 * @author ShenHuaJie
 * @since 2018年7月27日 上午10:58:14
 */
public class CsrfFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(CsrfFilter.class);

    // 白名单
    private List<String> whiteUrls;

    private int _size = 0;

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("init CsrfFilter..");
        // 读取白名单
        String path = CsrfFilter.class.getResource("/").getFile();
        whiteUrls = FileUtil.readFile(path + "white/csrfWhite.txt");
        _size = null == whiteUrls ? 0 : whiteUrls.size();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            // 获取请求url地址
            String url = req.getRequestURL().toString();
            String referurl = req.getHeader("Referer");
            if (WebUtil.isWhiteRequest(referurl, _size, whiteUrls)) {
                chain.doFilter(request, response);
            } else {
                // 记录跨站请求日志
                logger.warn("跨站请求---->>>{} || {} || {} || {}", url, referurl, WebUtil.getHost(req),
                        DateUtil.getDateTime());
                WebUtil.write(response, 308, "错误的请求头信息");
            }
        } catch (Exception e) {
            logger.error("doFilter", e);
        }
    }

    @Override
    public void destroy() {
        logger.info("destroy CsrfFilter.");
    }
}
