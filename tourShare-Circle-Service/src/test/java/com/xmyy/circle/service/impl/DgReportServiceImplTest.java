package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.DgReportService;
import com.xmyy.circle.vo.ReportAddParam;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Simon on 2018/7/19.
 */
public class DgReportServiceImplTest extends BaseJUnitTest {

    @Resource
    DgReportService dgReportService;

    @Test
    public void add() {
        ReportAddParam params = new ReportAddParam();
        params.setTargetId(15L);
        params.setMemberType(2);
        params.setReportType(1);
        params.setContent("举报内容");
        List<String> images = Arrays.asList("http://image.jpg");
        params.setImages(images);
        Object result = dgReportService.add(params, 1L);
        Assert.assertNotNull(result);
    }
}