package com.xmyy.manage.web.admin;

import com.xmyy.manage.model.WsSensitive;
import com.xmyy.manage.service.WsSensitiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.ibase4j.core.base.BaseController;

/**
 * 敏感词  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/sensitive")
@Api(value = "敏感词接口", description = "敏感词接口")
public class WsSensitiveController extends BaseController<WsSensitive, WsSensitiveService> {

    @PostMapping(value = "/importWord")
    @ApiOperation(value = "导入敏感词；文件格式为：txt，excel；一行一个敏感词；excel第一行为标题不导入", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateWord(@ApiParam(name = "file", value = "txt/Excel文件") @RequestParam("file") MultipartFile file) {
        Long currUser = this.getShiroCurrUser();
        WsSensitive sensitive = new WsSensitive();
        sensitive.setCreateBy(currUser);
        sensitive.setUpdateBy(currUser);
        return exec(() -> service.importWord(file.getOriginalFilename().toLowerCase(), sensitive, file.getBytes()));
    }

}