package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.UcFeedQuestion;
import com.xmyy.circle.service.UcFeedQuestionService;
import com.xmyy.circle.vo.UcFeedQuestionParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.util.ShiroUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 反馈问题答疑后台管理  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/manage/circle/feedquestion")
@Api(value = "反馈问题答疑后台管理接口", description = "反馈问题答疑后台管理接口")
public class ManageFeedQuestionController extends BaseController<UcFeedQuestion, UcFeedQuestionService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询反馈问题答疑", produces = MediaType.APPLICATION_JSON_VALUE, response = UcFeedQuestion.class)
    public Object queryFeedQuestionList(String question) {
        return exec(() -> service.queryFeedQuestionList(question));
    }


    @PostMapping(value = "/add")
    @ApiOperation(value = "反馈问题答疑详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addFeedQuestion(HttpServletRequest request, @Valid @RequestBody UcFeedQuestionParam params, BindingResult bindingResult) {
        return exec(() -> service.addFeedQuestion(params, this.getShiroCurrUser()), bindingResult);
    }


    @PostMapping(value = "/modify")
    @ApiOperation(value = "修改反馈问题答疑", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateFeedQuestion(HttpServletRequest request, @Valid @RequestBody UcFeedQuestionParam params, BindingResult bindingResult) {
        return exec(() -> {
            Long userId = this.getShiroCurrUser();
            return service.updateFeedQuestion(params, userId);
        }, bindingResult);
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除反馈问题答疑", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteFeedQuestion(Long Id) {
        if (StringUtils.isEmpty(Id)) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "Id不能为空");
        }
        return exec(() -> service.deleteFeedQuestion(Id, ShiroUtil.getCurrentUser()), null);
    }
}