package com.xmyy.pay.service;

import com.xmyy.pay.model.DgCheckAccountDetail;
import top.ibase4j.core.base.BaseService;

/**
 * 后台对账管理  服务接口
 *
 * @author AnCheng
 */
public interface ManageCheckAccountService extends BaseService<DgCheckAccountDetail> {

    /**
     * 下载解析每日对账文件
     * @param date
     * @param fileType
     * @return
     */
    Object getCheckAccountFile(String date, Long fileType);

}
