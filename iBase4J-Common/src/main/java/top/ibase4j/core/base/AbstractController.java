package top.ibase4j.core.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 控制器基类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class AbstractController {
    protected static Logger logger = LoggerFactory.getLogger(AbstractController.class);

    /**
     * 获取当前用户ID(shiro)
     */
    protected Long getShiroCurrUser() {
        return ShiroUtil.getCurrentUser();
    }

    /**
     * 获取当前用户ID(HttpServletRequest)
     */
    protected Long getCurrUser(HttpServletRequest request) {
        Object id = WebUtil.getCurrentUser(request);
        return id == null ? null : Long.parseLong(id.toString());
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap() {
        return setSuccessModelMap(new ModelMap(), null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap) {
        return setSuccessModelMap(modelMap, null);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(Object data) {
        return setModelMap(new ModelMap(), HttpCode.OK, data);
    }

    /**
     * 设置成功响应代码
     */
    protected ResponseEntity<ModelMap> setSuccessModelMap(ModelMap modelMap, Object data) {
        return setModelMap(modelMap, HttpCode.OK, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code) {
        return setModelMap(new ModelMap(), code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(String code, String msg) {
        return setModelMap(new ModelMap(), code, msg, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code) {
        return setModelMap(modelMap, code, null);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(HttpCode code, Object data) {
        return setModelMap(new ModelMap(), code, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(String code, String msg, Object data) {
        return setModelMap(new ModelMap(), code, msg, data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, HttpCode code, Object data) {
        return setModelMap(modelMap, code.value().toString(), code.msg(), data);
    }

    /**
     * 设置响应代码
     */
    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, String code, String msg, Object data) {
        return setModelMap(modelMap, code, msg, data, null);
    }

    /**
     * @param exec
     * @param <T>
     * @return
     */
    public <T> Callable<ResponseEntity<ModelMap>> exec(Executable exec) {
        return exec(exec, null, null);
    }

    /**
     * @param exec
     * @param bindingResult
     * @param <T>
     * @return
     */
    public <T> Callable<ResponseEntity<ModelMap>> exec(Executable exec, BindingResult bindingResult) {
        return exec(exec, bindingResult, null);
    }

    /**
     * @param exec
     * @param bindingResult
     * @param <T>
     * @return
     */
    public <T> Callable<ResponseEntity<ModelMap>> exec(Executable exec, BindingResult bindingResult, Object version) {

        StackTraceElement ste = new Exception().getStackTrace()[1];
        String method = ste.getClassName().substring(ste.getClassName().lastIndexOf(".") + 1) + "." + ste.getMethodName();

        return () -> {

            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            boolean debug = "true".equals(req.getParameter("debug"));

            if (debug) {
                if (bindingResult != null) {
                    logger.debug(method + " request params：" + JsonUtil.toJson(bindingResult.getTarget()));
                }
            }

            // 检查传入参数的有效性
            if (bindingResult != null && bindingResult.hasErrors()) {
                String errmsg = BindingUtil.errorMessage(bindingResult);
                if (debug) {
                    logger.info(method + " result：" + JsonUtil.toJson(errmsg));
                }
                return setModelMap(HttpCode.BAD_REQUEST.value().toString(), errmsg);
            }

            T data = (T) exec.exec();

            if (data instanceof String) {
                return setModelMap(HttpCode.BAD_REQUEST.value().toString(), data.toString());
            } else if (data instanceof ModelMap) {
                ModelMap map = (ModelMap) data;
                map.put("code", HttpCode.OK.value().toString());
                map.put("timestamp", System.currentTimeMillis());
                return ResponseEntity.ok(map);
            }
            return setModelMap(new ModelMap(), HttpCode.OK.value().toString(), HttpCode.OK.msg(), data, version);
        };
    }

    protected ResponseEntity<ModelMap> setModelMap(ModelMap modelMap, String code, String msg, Object data, Object version) {
        if (!modelMap.isEmpty()) {
            Map<String, Object> map = InstanceUtil.newLinkedHashMap();
            map.putAll(modelMap);
            modelMap.clear();
            for (String key : map.keySet()) {
                if (!key.startsWith("org.springframework.validation.BindingResult") && !key.equals("void")) {
                    modelMap.put(key, map.get(key));
                }
            }
        }

        if (data != null) {
            if (data instanceof Pagination<?>) {
                Pagination<?> page = (Pagination<?>) data;
                modelMap.put("rows", page.getRecords());
                modelMap.put("current", page.getCurrent());
                modelMap.put("size", page.getSize());
                modelMap.put("pages", page.getPages());
                modelMap.put("total", page.getTotal());
            } else if (data instanceof List<?>) {
                modelMap.put("rows", data);
                modelMap.put("total", ((List<?>) data).size());
            } else {
                modelMap.put("data", data);
            }
        }
        modelMap.put("code", code);
        modelMap.put("msg", msg);
        modelMap.put("version", version);
        modelMap.put("timestamp", System.currentTimeMillis());
        logger.info("response===>{}", JSON.toJSONString(modelMap));
        return ResponseEntity.ok(modelMap);
    }
}
