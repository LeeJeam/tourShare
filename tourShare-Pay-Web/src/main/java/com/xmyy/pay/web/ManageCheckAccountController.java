package com.xmyy.pay.web;

import com.xmyy.pay.model.DgCheckAccountDetail;
import com.xmyy.pay.service.ManageCheckAccountService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.ibase4j.core.base.AppBaseController;

/**
 * 后台对账  前端控制器
 *
 * @author AnCheng
 */
@ApiIgnore
@RestController
@RequestMapping("/manage/checkAccount")
@Api(value = "后台对账接口", description = "后台对账接口")
public class ManageCheckAccountController extends AppBaseController<DgCheckAccountDetail, ManageCheckAccountService> {

}