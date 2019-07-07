package com.xmyy.manage.web.admin;

import com.xmyy.manage.model.AdminDic;
import com.xmyy.manage.service.AdminDicService;
import com.xmyy.manage.vo.ManageAdminDicPageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 字典管理
 *
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:14:34
 */
@RestController
@Api(value = "字典管理", description = "字典管理")
@RequestMapping(value = "/manage/dic")
public class AdminDicController extends BaseController<AdminDic, AdminDicService> {

    @ApiOperation(value = "查询字典项", produces = MediaType.APPLICATION_JSON_VALUE, response = AdminDic.class)
    @PostMapping(value = "/list")
    public Object query(@RequestBody ManageAdminDicPageParam param) {
        return exec(() -> service.list(param), null);
    }

    @ApiOperation(value = "字典项详情", produces = MediaType.APPLICATION_JSON_VALUE, response = AdminDic.class)
    @GetMapping(value = "/detail")
    @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "Long")
    public Object get(ModelMap modelMap, @RequestParam(required = true) Long id) {
        Object result = this.service.queryById(id);
        return this.setSuccessModelMap(modelMap, result);
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "修改字典项")
    public Object update(@RequestBody AdminDic param) {
        Long userId = this.getShiroCurrUser();
        param.setUpdateBy(userId);
        param.setCreateBy(userId);

        return exec(() -> service.save(param), null);
    }


    @PostMapping(value = "/detele")
    @ApiOperation(value = "删除字典项")
    public Object delete(ModelMap modelMap, @RequestBody AdminDic param) {
        return super.delete(modelMap, param);
    }
    
}
