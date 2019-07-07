package top.ibase4j.core.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import java.util.ArrayList;
import java.util.List;

public class BindingUtil {

    /*获取验证出错信息 返回<br/>分割的字符串*/
    public static String errorMessage(BindingResult bindingResult) {

        if (bindingResult == null || !bindingResult.hasErrors()) {
            return null;
        }

        RequestContext requestContext = new RequestContext(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        List<String> errorList = new ArrayList<String>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorList.add(requestContext.getMessage(error));
        }

        String msg = "";
        for (String errmsg : errorList) {
            msg += "， " + errmsg;
        }
        return msg.isEmpty() ? null : msg.substring(2); // remove head <br/>
    }
}
