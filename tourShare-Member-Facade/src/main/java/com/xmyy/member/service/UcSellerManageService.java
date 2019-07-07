package com.xmyy.member.service;

import com.xmyy.member.model.UcSeller;
import com.xmyy.member.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

/**
 * @author zlp
 * @since 2018-05-17
 */
public interface UcSellerManageService extends BaseService<UcSeller> {

    /**
     * 买手列表
     * @param param
     * @return
     */
    Pagination<SellerManagePageResult>  list(SellerManagePageParam param);

    MemberManageResult getInfo(Long id);

    /**
     * 置顶
     * @param param
     * @return
     */
    Object top(SellerTopParam param);

    /**
     * 获取修改记录
     * @param id
     * @return
     */
    Object getLogList(Long id);

    /**
     * 取消“过期”的买手置顶
     */
    void cancelTopOverTime();

    /**
     * 取消置顶
     * @param param
     * @return
     */
    Object down(SellerDownParam param);
}