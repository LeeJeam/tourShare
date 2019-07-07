package top.ibase4j.core.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.Constants;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.Token;
import top.ibase4j.core.util.*;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * APP登录TOKEN过滤器, expire有效期30天(单位：秒)
 *
 * @author ShenHuaJie
 * @since 2017年3月19日 上午10:21:59
 */
public class TokenFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    private Long expire = null; //30天
    private List<String> whiteUrls;
    private int _size = 0;

    @Override
    public void init(FilterConfig config) {
        logger.info("init TokenFilter..");
        // 读取文件
        String path = CsrfFilter.class.getResource("/").getFile();
        whiteUrls = FileUtil.readFile(path + "white/tokenWhite.txt");
        _size = null == whiteUrls ? 0 : whiteUrls.size();
        expire = PropertiesUtil.getLong("token.expire", 0);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("SESSID");
        logger.info("SESSID==>" + token);

        if (StringUtils.isNotBlank(token)) {
            try {
                Token tokenInfo = TokenUtil.getTokenInfo(token);
                if (tokenInfo != null) {
                    request.setAttribute(Constants.CURRENT_USER, tokenInfo.getValue());
                    if (expire > 0) {
                        if (System.currentTimeMillis() - tokenInfo.getTime() > expire * 1000) {
                            //token超过30天过期，踢出token
                            request.removeAttribute(Constants.CURRENT_USER);
                            TokenUtil.delToken(token);
                        } else {
                            //否则延长token时效
                            TokenUtil.setTokenInfo(token, tokenInfo.getValue());
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("token检查发生异常:", e);
            }
        }

        String url = request.getRequestURI();
        if (WebUtil.isWhiteRequest(url, _size, whiteUrls)) {
            //token白名单
            chain.doFilter(request, response);
        } else if (DataUtil.isEmpty(request.getAttribute(Constants.CURRENT_USER))) {
            //无效token，判断是未登录还是被踢出，返回对应结果
            response.setContentType("text/html; charset=UTF-8");
            Integer code = HttpCode.UNAUTHORIZED.value();
            String msg = HttpCode.UNAUTHORIZED.msg();

            //判断是否被踢
            if (StringUtils.isNotBlank(token)) {
                Token kickOutToken = KickOutTokenUtil.getKickOutTokenInfo(token);
                if (kickOutToken != null) {
                    code = HttpCode.REQUEST_KICKOUT.value();
                    msg = HttpCode.REQUEST_KICKOUT.msg();
                }
            }
            Map<String, Object> modelMap = InstanceUtil.newLinkedHashMap();
            modelMap.put("code", code.toString());
            modelMap.put("msg", msg);
            modelMap.put("timestamp", System.currentTimeMillis());
            String result = JSON.toJSONString(modelMap);
            logger.warn(url + " ====> " + result);
            PrintWriter out = response.getWriter();
            out.println(result);
            out.flush();
            out.close();
        } else {
            //有效token
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        logger.info("destroy TokenFilter.");
    }
}
