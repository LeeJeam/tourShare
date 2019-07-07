package com.xmyy.member.web;

import com.xmyy.common.EnumConstants;
import com.xmyy.member.model.UcMemberAddress;
import com.xmyy.member.service.UcMemberAddressService;
import com.xmyy.member.vo.MemberAddressParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.util.BindingUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 我的收货地址  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/buyer/address")
@Api(value = "买家收货地址接口", description = "买家收货地址接口")
public class ApiBuyerAddressController extends AppBaseController<UcMemberAddress, UcMemberAddressService> {

    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @GetMapping(value = "/list")
    @ApiOperation(value = "我的收货地址列表", response = UcMemberAddress.class)
    public Object list(HttpServletRequest request) {
        return exec(() -> service.list(super.getCurrUser(request)));
    }


    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, dataType = "Long")
    })
    @ApiOperation(value = "我的收货地址详情", response = UcMemberAddress.class)
    public Object get(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "地址ID不能为空");
        }
        UcMemberAddress address = service.queryById(id);
        return setSuccessModelMap(new ModelMap(), address);
    }


    @PostMapping(value = "/save")
    @ApiOperation(value = "新增/修改 收货地址", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberAddress.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    public Object update(@Valid @RequestBody MemberAddressParam addressParams, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), BindingUtil.errorMessage(bindingResult));
        }

        if (addressParams.getIsDefault() != null && addressParams.getIsDefault().intValue() == EnumConstants.YesOrNo.YES.getValue()) {
            updateBatchDefault(request);
        }

        UcMemberAddress address = InstanceUtil.to(addressParams, UcMemberAddress.class);
        address.setBuyerId(this.getCurrUser(request));
        service.update(address);

        return setSuccessModelMap(new ModelMap(), address.getId());
    }


    @GetMapping(value = "/getDefault")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "获取默认地址", response = UcMemberAddress.class)
    public Object getDetail(HttpServletRequest request) {
        UcMemberAddress address = new UcMemberAddress();
        address.setIsDefault(EnumConstants.YesOrNo.YES.getValue());
        address.setBuyerId(getCurrUser(request));
        return setSuccessModelMap(new ModelMap(), service.selectOne(address));
    }


    @GetMapping(value = "/setDefault")
    @ApiOperation(value = "设置默认地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, dataType = "Long")
    })
    public Object setDefault(Long id, HttpServletRequest request) {

        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }

        updateBatchDefault(request);

        UcMemberAddress address = new UcMemberAddress();
        address.setBuyerId(this.getCurrUser(request));
        address.setIsDefault(EnumConstants.YesOrNo.YES.getValue());
        address.setId(id);
        service.update(address);

        return setSuccessModelMap(new ModelMap());
    }


    @PostMapping(value = "/del")
    @ApiOperation(value = "删除我的收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "地址id", required = true, dataType = "Long")
    })
    public Object del(Long id, HttpServletRequest request) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        service.del(id, this.getCurrUser(request));
        return setSuccessModelMap(new ModelMap());
    }

    //将原默认地址，修改为非默认
    private void updateBatchDefault(HttpServletRequest request) {
        Map<String, Object> param = InstanceUtil.newHashMap("ucBuyerId", this.getCurrUser(request));
        param.put("isDefault", EnumConstants.YesOrNo.YES.getValue());
        List<UcMemberAddress> list = service.queryList(param);
        if (DataUtil.isNotEmpty(list)) {
            list.parallelStream().forEach(d -> {
                d.setIsDefault(EnumConstants.YesOrNo.NO.getValue());
            });
            service.updateBatch(list);
        }
    }

}