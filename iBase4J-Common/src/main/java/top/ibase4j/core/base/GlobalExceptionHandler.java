package top.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.ibase4j.core.exception.BaseException;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理，处理Controller层抛出的异常
 *
 * @author Simon
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(HttpServletResponse response, Exception ex) throws Exception {
        ModelMap modelMap = new ModelMap();

        if (ex instanceof BaseException) {
            ((BaseException) ex).handler(modelMap);

        } else if (ex instanceof BizException) {
            BizException customException = (BizException) ex;
            modelMap.put("code", customException.getCode());
            modelMap.put("msg", customException.getMsg());

        } else if ("org.apache.shiro.authz.UnauthorizedException".equals(ex.getClass().getName())) {
            modelMap.put("code", HttpCode.FORBIDDEN.value());
            modelMap.put("msg", HttpCode.FORBIDDEN.msg());

        } else {
            // 其他异常
            String msg = StringUtils.defaultIfBlank(ex.getMessage(), HttpCode.INTERNAL_SERVER_ERROR.msg());
            logger.error(msg);
            modelMap.put("code", HttpCode.INTERNAL_SERVER_ERROR.value());
            modelMap.put("msg", "小背包出错啦");
        }

        response.setContentType("application/json;charset=UTF-8");
        modelMap.put("timestamp", System.currentTimeMillis());
        logger.info("response===>{}", JSON.toJSON(modelMap));
        byte[] bytes = JSON.toJSONBytes(modelMap, SerializerFeature.DisableCircularReferenceDetect);
        response.getOutputStream().write(bytes);
    }

}
