package com.xmyy.circle.service;

import com.xmyy.circle.model.DgReport;
import com.xmyy.circle.vo.ReportAddParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 举报  服务类
 * </p>
 *
 * @author simon
 * @since 2018-06-11
 */
public interface DgReportService extends BaseService<DgReport> {

    Object add(ReportAddParam params, Long userId);
	
}