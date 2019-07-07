package com.xmyy.manage.web.seller;

import com.xmyy.member.model.UcMemberLog;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcSellerManageService;
import com.xmyy.member.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

import javax.validation.Valid;

/**
 * 买手后台管理  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/seller")
@Api(value = "买手后台管理接口", description = "买手后台管理接口")
public class ManageSellerController extends BaseController<UcSeller, UcSellerManageService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "买手/自营买手列表", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerManagePageResult.class)
    public Object list(@RequestBody @Valid SellerManagePageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(param), bindingResult);
    }


    @ApiOperation(value = "买手基本信息", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberManageResult.class)
    @RequestMapping(value = "getInfo", method = {RequestMethod.GET})
    @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "Long")
    public Object getInfo(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.getInfo(id), null);
    }


    @RequestMapping(value = "/top", method = RequestMethod.POST)
    @ApiOperation(value = "置顶", produces = MediaType.APPLICATION_JSON_VALUE, response = UcSeller.class)
    public Object top(@Valid @RequestBody SellerTopParam param, BindingResult bindingResult) {
        param.setUpdateBy(this.getShiroCurrUser());
        return exec(() -> service.top(param), bindingResult);
    }


    @RequestMapping(value = "/down", method = RequestMethod.POST)
    @ApiOperation(value = "取消置顶", produces = MediaType.APPLICATION_JSON_VALUE, response = UcSeller.class)
    public Object down(@Valid @RequestBody SellerDownParam param, BindingResult bindingResult) {
        param.setUpdateBy(this.getShiroCurrUser());
        return exec(() -> service.down(param), bindingResult);
    }


    @ApiOperation(value = "获取修改记录", response = UcMemberLog.class)
    @RequestMapping(value = "getLogList", method = {RequestMethod.GET})
    @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "Long")
    public Object getLogList(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.getLogList(id), null);
    }

}