package com.xmyy.circle.web;

import com.xmyy.circle.model.UcFeedQuestion;
import com.xmyy.circle.service.UcFeedQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

/**
 * 反馈问题答疑  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/api/circle/feedquestion")
@Api(value = "APP反馈问题答疑接口", description = "APP反馈问题答疑接口")
public class ApiUcFeedQuestionController extends AppBaseController<UcFeedQuestion, UcFeedQuestionService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "查询反馈问题答疑", produces = MediaType.APPLICATION_JSON_VALUE, response = UcFeedQuestion.class)
    public Object queryFeedQuestionList(String question) {
        return exec(() -> service.queryFeedQuestionList(question));
    }
}