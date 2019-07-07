package com.xmyy.manage.web.search;

import com.xmyy.common.util.TimeCounter;
import com.xmyy.search.service.PackerImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AbstractController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 背包客ES导入  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/packer/import")
@Api(value = "背包客ES索引库导入", description = "背包客ES索引库导入")
public class ManagePackerImportController extends AbstractController {

    @Resource
    private PackerImportService packerImportService;

    @PostMapping(value = "/full")
    @ApiOperation(value = "全新导入", produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<Object> fullImportDatas() {
        return () -> {
            TimeCounter t = new TimeCounter("fullImportDatas");
            t.start();
            int importCount = packerImportService.fullImportPackersToIndex(-1);
            t.stop();
            long costTimeInseconds = TimeUnit.MILLISECONDS.toSeconds(t.getCostTime());

            return setSuccessModelMap("导入成功，共导入[" + importCount + "]条数据，耗时" + costTimeInseconds + "秒！");
        };
    }


    @ApiOperation(value = "增量索引", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "increment")
    public Callable<Object> incrementImportDatas() {
        return () -> {
            int importCount = packerImportService.incrementImportPackersToIndex(-1);

            return setSuccessModelMap("导入成功，共导入[" + importCount + "]条数据！");
        };
    }


    @ApiOperation(value = "重建并索引", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "rebuild")
    public Object rebuild() {
        String msg = "";
        if (packerImportService.rebuildIndex()) {
            int importCount = packerImportService.fullImportPackersToIndex(3000);
            msg += "导入背包客库索引成功！共导入[" + importCount + "]条买手数据！";
        } else {
            msg += "导入背包客库索引失败！";
        }
        return setSuccessModelMap(msg);
    }

}