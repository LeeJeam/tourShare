package com.xmyy.manage.web.seller;

import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.ExportExcel;
import com.xmyy.member.model.UcInviteCode;
import com.xmyy.member.service.UcInviteCodeService;
import com.xmyy.member.vo.InviteCodeGenerateParams;
import com.xmyy.member.vo.InviteCodePageParam;
import com.xmyy.member.vo.InviteCodePageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.ibase4j.core.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

/**
 * 邀请码后台管理  前端控制器
 *
 * @author zlp
 */
@Controller
@RequestMapping("/manage/seller/inviteCode")
@Api(value = "邀请码后台管理接口", description = "邀请码后台管理接口")
public class ManageInviteCodeController extends BaseController<UcInviteCode, UcInviteCodeService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询邀请码", produces = MediaType.APPLICATION_JSON_VALUE, response = InviteCodePageResult.class)
    public Object list(@RequestBody @Valid InviteCodePageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(param), bindingResult);
    }


    @RequestMapping(value = "/batchSend", method = RequestMethod.POST)
    @ApiOperation(value = "生成单个邀请码", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object batchSend(@Valid @RequestBody InviteCodeGenerateParams param, BindingResult bindingResult) {
        return exec(() -> service.batchSend(param), bindingResult);
    }


    @RequestMapping(value = "/exportExcel", method = {RequestMethod.GET})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "count", value = "生成数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "days", value = "时效(1天/3天/10天)", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "channelType", value = "渠道(1运营推广，2线下推广，3用户邀请)", required = true, dataType = "Integer")
    })
    @ApiOperation(value = "批量生成邀请码并导出Excel")
    public void exportExcel(Integer count,
                            Integer days,
                            Integer channelType,
                            HttpServletResponse response,
                            HttpServletRequest request) throws IOException {
        Long userId = super.getShiroCurrUser();

        UcInviteCode code = new UcInviteCode();
        code.setChannelType(channelType);
        code.setDays(days);
        code.setCreateBy(userId);
        code.setUpdateBy(userId);

        List<UcInviteCode> list = service.exportExcel(code, count);

        LinkedHashMap<String, Function<UcInviteCode, ?>> maps = new LinkedHashMap<>();
        maps.put("邀请码号", UcInviteCode::getInviteNo);
        maps.put("渠道", (t) -> EnumConstants.ChannelType.valueOf(t.getChannelType()).getName());
        maps.put("截止时间", (t) -> DateUtils.addDay(t.getSendTime(), t.getDays()));

        ExportExcel.exportXlsx(response, "邀请码列表_" + DateUtils.formatDate(new Date()), "邀请码列表", list, maps);
    }

}