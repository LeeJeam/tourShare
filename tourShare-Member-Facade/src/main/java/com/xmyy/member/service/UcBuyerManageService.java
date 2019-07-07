package com.xmyy.member.service;

import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.vo.BuyerManagePageParam;
import com.xmyy.member.vo.BuyerManagePageResult;
import com.xmyy.member.vo.MemberManageResult;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * 买家后台管理  服务接口
 *
 * @author zlp
 */
public interface UcBuyerManageService extends BaseService<UcBuyer> {

    /**
     * 买家/背包客列表
     * @param param
     * @return
     */
    Pagination<BuyerManagePageResult>  list(BuyerManagePageParam param);

    /**
     * 买家/背包客基本信息
     * @param id
     * @return
     */
    MemberManageResult getInfo(Long id);

    /**
     * 获取修改记录
     * @param id
     * @return
     */
    Object getLogList(Long id);

    /**
     * 新增虚拟买家
     * @param data
     * @return
     */
    Object addVirtual(List<String[]> data);

    /**
     * 查询随机虚拟买家
     * @return
     */
    UcBuyer getRandomVirtual();
}