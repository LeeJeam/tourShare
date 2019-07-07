package com.xmyy.cert.service;

import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.vo.MemberCertAddParam;
import com.xmyy.cert.vo.MemberCertCountParam;
import com.xmyy.cert.vo.MemberCertParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * 用户认证  服务接口
 *
 * @author James
 */
public interface UcMemberCertService extends BaseService<UcMemberCert> {

    /**
     * 查询认证列表信息
     *
     * @param param
     * @return
     */
    Object queryMemberCertList(MemberCertParam param);

    /**
     * APP新增认证信息
     * @param memberId
     * @param memberCertAddParams
     * @return
     * @throws BizException
     */
    Object saveIdentityInfo( Long memberId,MemberCertAddParam memberCertAddParams);

    /**
     * 审批认证数据
     * @param paramCert
     * @return
     * @throws BizException
     */
    Object verifyCertInfo(UcMemberCert paramCert) throws BizException;

    /**
     * 买手或背包客用户认证详情
     * @param memberId
     * @param memberType
     * @return
     */
    Object queryUserCertDetail(Long memberId,Integer memberType);

    /**
     * 获取审批统计结果
     *
     * @return
     */
    List<Map<String, String>> getResultCount(MemberCertCountParam param);

    /**
     * 查询下一个未认证的用户信息
     * @return
     */
    Object queryNextUnaudit();

}