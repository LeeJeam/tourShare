package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.UcFeedback;
import com.xmyy.circle.service.UcFeedbackService;
import com.xmyy.circle.vo.UcFeedbackPageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.util.ShiroUtil;

/**
 * 意见反馈后台管理  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/manage/circle/feedback")
@Api(value = "意见反馈后台管理接口", description = "意见反馈后台管理接口")
public class ManageFeedbackController extends BaseController<UcFeedback, UcFeedbackService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "查询意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object queryAll(@Validated @RequestBody UcFeedbackPageParam param, BindingResult bindingResult) {
        return exec(() -> service.queryAll(param), bindingResult);
    }


	/*
	@PostMapping(value = "/add")
	@ApiImplicitParam(paramType = "header", name = "sessid", value = "用户sessid", required = true, dataType = "String")
	@ApiOperation(value = "新增意见反馈", produces = MediaType.APPLICATION_JSON_VALUE,response = UcFeedback.class)
	public Object addFeedBack(HttpServletRequest request, @Valid @RequestBody UcFeedbackParams param, BindingResult bindingResult) {
		return exec(() -> {
			Long uid=this.getShiroCurrUser(request);
			return service.addFeedBack(param,uid);
		},bindingResult);
	}


	@PostMapping(value = "/modify")
	@ApiImplicitParam(paramType = "header", name = "sessid", value = "用户sessid", required = true, dataType = "String")
	@ApiOperation(value = "修改意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object update(HttpServletRequest request, @Validated @RequestBody UcFeedbackParams param, BindingResult bindingResult) {
		return exec(() -> {
			Long uid=this.getShiroCurrUser(request);
			return service.updateFeedBack(param,uid);
		},bindingResult);
	}*/


    @GetMapping(value = "/delete")
    @ApiOperation(value = "删除意见反馈", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delFeedBack(Long feedBackId) {
        if (StringUtils.isEmpty(feedBackId)) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "feedBackId不能为空");
        }
        return exec(() -> service.delFeedBack(feedBackId, ShiroUtil.getCurrentUser()));
    }
}