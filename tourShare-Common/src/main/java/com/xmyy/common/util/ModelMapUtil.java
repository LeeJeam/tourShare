package com.xmyy.common.util;

import com.xmyy.common.ServiceCode;
import org.springframework.ui.ModelMap;

/***
 * @author zlp
 */
public class ModelMapUtil {

    public static Object setModelMap(String code, String msg) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("serveCode",code);
        modelMap.put("msg",msg);
        return modelMap;
    }

    public static Object setModelMap(ServiceCode code) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("serveCode",code.code());
        modelMap.put("msg",code.msg());
        return modelMap;
    }

    public static Object setModelMap(ServiceCode code, String msg) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("serveCode",code.code());
        modelMap.put("msg",msg);
        return modelMap;
    }

    public static Object setModelMap(ServiceCode code, Object ...args) {
        ModelMap modelMap = new ModelMap();
        modelMap.put("serveCode",code.code());
        modelMap.put("msg",String.format(code.msg(), args));
        return modelMap;
    }
}
