package com.xmyy.manage.web.order;

import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandOrderManageService;
import com.xmyy.demand.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 需求后台管理  前端控制器
 *
 * @author AnCheng
 */
@RestController
@RequestMapping("/manage/demand")
@Api(value = "需求后台管理", description = "需求后台管理")
public class ManageDemandController extends BaseController<DgDemandOrder, DgDemandOrderManageService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "分页查询需求列表", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandListManageResult.class)
    public Object listByPage(@Valid @RequestBody DemandListManageParam params, BindingResult bindingResult) {
        return exec(() -> service.listByPage(params), bindingResult);
    }


    @GetMapping(value = "/detail")
    @ApiOperation(value = "需求详情", produces = MediaType.APPLICATION_JSON_VALUE, response = DemBackDetailResult.class)
    public Object queryDemand(@RequestParam Long id) {
        return exec(() -> service.getDetailByBack(id));
    }


    @PostMapping(value = "/listLibrary")
    @ApiOperation(value = "需求库列表", produces = MediaType.APPLICATION_JSON_VALUE, response = DemandLibListManageResult.class)
    public Object listLibrary(@Valid @RequestBody DemandLibListManageParam param, BindingResult bindingResult) {
        return exec(() -> service.listLibrary(param), bindingResult);
    }


    @GetMapping(value = "/statistic")
    @ApiOperation(value = "统计", produces = MediaType.APPLICATION_JSON_VALUE, response = StatisticResult.class)
    public Object statistic() {
        return exec(() -> service.getStatisticByTag());
    }

}
