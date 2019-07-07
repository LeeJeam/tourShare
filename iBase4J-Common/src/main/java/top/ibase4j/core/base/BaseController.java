package top.ibase4j.core.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

/**
 * 控制器基类
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:47:58
 */
public abstract class BaseController<T extends BaseModel, S extends BaseService<T>> extends AbstractController {
    @Autowired
    protected S service;

    /** 分页查询 */
    public Object query(Map<String, Object> param) {
        return query(new ModelMap(), param);
    }

    /** 分页查询 */
    public Object query(ModelMap modelMap, Map<String, Object> param) {
        if (param.get("keyword") == null && param.get("search") != null) {
            param.put("keyword", param.get("search"));
            param.remove("search");
        }
        Object page = service.query(param);
        return setSuccessModelMap(modelMap, page);
    }

    /** 查询 */
    public Object queryList(Map<String, Object> param) {
        return queryList(new ModelMap(), param);
    }

    /** 查询 */
    public Object queryList(ModelMap modelMap, Map<String, Object> param) {
        List<?> list = service.queryList(param);
        return setSuccessModelMap(modelMap, list);
    }

    public Object get(BaseModel param) {
        return get(new ModelMap(), param);
    }

    public Object get(ModelMap modelMap, BaseModel param) {
        Object result = service.queryById(param.getId());
        return setSuccessModelMap(modelMap, result);
    }

    public Object update(T param) {
        return update(new ModelMap(), param);
    }

    public Object update(ModelMap modelMap, T param) {
        Long userId = getShiroCurrUser();
        if (param.getId() == null) {
            param.setCreateBy(userId);
            param.setCreateTime(new Date());
        }
        param.setUpdateBy(userId);
        param.setUpdateTime(new Date());
        T result = service.update(param);
        param.setId(result.getId());
        return setSuccessModelMap(modelMap);
    }

    /** 物理删除 */
    public Object delete(T param) {
        return delete(new ModelMap(), param);
    }

    /** 物理删除 */
    public Object delete(ModelMap modelMap, T param) {
        Assert.notNull(param.getId(), "ID不能为空");
        service.delete(param.getId());
        return setSuccessModelMap(modelMap);
    }

    /** 逻辑删除 */
    public Object del(HttpServletRequest request, T param) {
        return del(new ModelMap(), request, param);
    }

    /** 逻辑删除 */
    public Object del(ModelMap modelMap, HttpServletRequest request, T param) {
        if (param.getId() != null) {
            Assert.notNull(param.getId(), "ID不能为空");
            service.del(param.getId(), getCurrUser(request));
        }
        if (param.getIds() != null) {
            Assert.notNull(param.getIds(), "ID不能为空");
            service.del(param.getIds(), getCurrUser(request));
        }
        return setSuccessModelMap(modelMap);
    }
}
