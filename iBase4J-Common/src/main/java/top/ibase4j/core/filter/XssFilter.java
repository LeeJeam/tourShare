package top.ibase4j.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.FileUtil;
import top.ibase4j.core.util.WebUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 非法字符过滤器（防SQL注入，防XSS漏洞）
 *
 * @author ShenHuaJie
 * @version 2017年12月1日 下午2:57:31
 */
public class XssFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(XssFilter.class);

    /**
     * 排除部分URL不做过滤
     */
    private List<String> excludeUrls = new ArrayList<>();

    /**
     * 公告新增、修改用到富文本，对标签进行转义
     */
    private List<String> noticeUrls = new ArrayList<>();

    @Override
    public void init(FilterConfig filterconfig1) {
        logger.info("init XssFilter..");
        // 读取文件
        String path = XssFilter.class.getResource("/").getFile();
        excludeUrls = FileUtil.readFile(path + "white/xssWhite.txt");
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) arg0;
        HttpServletResponse response = (HttpServletResponse) arg1;
        String uri = req.getRequestURI();
        boolean isNoticeUrl = false;
        // 排除部分URL不做过滤。
        if (WebUtil.isWhiteRequest(uri, excludeUrls.size(), excludeUrls)) {
            logger.info("该URL不作校验：" + uri);
            arg2.doFilter(req, response);
            return;
        }
        for (String st : noticeUrls) {
            if (uri.contains(st)) {
                isNoticeUrl = true;
                break;
            }
        }

        Enumeration<?> params = req.getParameterNames();
        String paramN;
        while (params.hasMoreElements()) {
            paramN = (String)params.nextElement();
            if ("sign".equals(paramN)) {
                logger.info("跳过签名字段");
                continue;
            }
            String paramVale = req.getParameter(paramN);
            if (!paramN.toLowerCase().contains("password")) {
                continue;
            }
            if (isNoticeUrl) {
                paramVale = DataUtil.xssEncode(paramVale);
            }
            // 校验是否存在SQL注入信息
            if (checkSQLInject(paramVale, uri)) {
                errorResponse(response, paramN);
                return;
            }
        }
        arg2.doFilter(req, response);
    }

    private void errorResponse(HttpServletResponse response, String paramNm) throws IOException {
        String warning = "输入项中不能包含非法字符。";

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("{\"code\":\"309\",\"msg\":\"" + warning + "\", \"fieldName\": \"" + paramNm + "\"}");
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        logger.info("destroy XssFilter.");
    }

    /**
     * 检查是否存在非法字符，防止SQL注入
     *
     * @param str 被检查的字符串
     * @return ture-字符串中存在非法字符，false-不存在非法字符
     */
    private boolean checkSQLInject(String str, String url) {
        if (StringUtils.isEmpty(str)) {
            return false;// 如果传入空串则认为不存在非法字符
        }

        if (DataUtil.checkSQLInject(str)) {
            logger.info("xss防攻击拦截url:" + url + "，原因：特殊字符，传入str=" + str);
            return true;
        }
        return false;
    }
}
