package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.mapper.DgReportMapper;
import com.xmyy.circle.model.DgReport;
import com.xmyy.circle.service.DgReportService;
import com.xmyy.circle.vo.ReportAddParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

import java.util.List;

/**
 * 举报  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = DgReportService.class)
@CacheConfig(cacheNames = "DgReport")
public class DgReportServiceImpl extends BaseServiceImpl<DgReport, DgReportMapper> implements DgReportService {

    @Override
    public Object add(ReportAddParam params, Long userId) {
        DgReport dgReport = new DgReport();
        dgReport.setTargetId(params.getTargetId());
        dgReport.setTargetType(params.getTargetType());
        dgReport.setReportType(params.getTargetType());
        dgReport.setContent(params.getContent());
        dgReport.setMemberId(userId);
        dgReport.setMemberType(params.getMemberType());
        dgReport.setCreateBy(userId);
        String images = "";
        List<String> list = params.getImages();
        if (list != null && list.size() > 0) {
            images = StringUtils.join(list, ",");
        }
        dgReport.setImages(images);
        return update(dgReport);
    }
}
