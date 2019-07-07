package com.xmyy.circle.service;

import com.xmyy.circle.model.WsAdvertContent;
import com.xmyy.circle.vo.AdApiParam;
import com.xmyy.circle.vo.AdContentParam;
import com.xmyy.circle.vo.AdManageListResult;
import com.xmyy.circle.vo.AdManageParam;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * <p>
 * 广告内容  服务类
 * </p>
 *
 * @author zlp
 * @since 2018-06-04
 */
public interface WsAdvertContentService extends BaseService<WsAdvertContent> {

    /**
     * 后台广告列表
     * @param param
     * @return
     */
    List<AdManageListResult> list(AdManageParam param);

    /**
     * 变更图片
     * @param param
     * @return
     */
    Object updateImg(AdContentParam param);

    /**
     * api 广告位列表
     * @param param
     * @return
     */
    List<WsAdvertContent> listContent(AdApiParam param);
}