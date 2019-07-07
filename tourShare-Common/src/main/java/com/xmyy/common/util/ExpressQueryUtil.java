package com.xmyy.common.util;

import com.alibaba.fastjson.JSON;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.crypto.MD5;
import com.xmyy.common.vo.ExpressCompany;
import com.xmyy.common.vo.ExpressConfig;
import com.xmyy.common.vo.ExpressInfo;
import com.xmyy.common.vo.ExpressInputParam;
import org.apache.commons.lang3.StringUtils;
import top.ibase4j.core.exception.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 快递100物流查询
 *
 * @author wangzejun
 */
public final class ExpressQueryUtil {

    private static ExpressConfig expressConfig = ExpressConfig.getInstance();

    /**
     * 根据物流公司编码和运单号获取物流信息
     */
    public static ExpressInfo getExpressInfoByCompanyCodeAndNum(ExpressInputParam expressInputParam) {
        ExpressInfo expressInfo;
        if (StringUtils.isNotBlank(expressConfig.getFreeSwitch()) && Boolean.parseBoolean(expressConfig.getFreeSwitch())) {
            expressInfo = getExpressInfo4Free(expressInputParam);
        } else {
            expressInfo = getExpressInfo4Firm(expressInputParam);
        }
        return expressInfo;
    }

    /**
     * 企业版快递100查询接口
     */
    private static ExpressInfo getExpressInfo4Firm(ExpressInputParam expressInputParam) {
        if (StringUtils.isBlank(expressConfig.getFreeKey()) || expressConfig.getFreeKey().equals(expressConfig.getFirmKey())) {
            throw new BizException(400, "快递100公司没有给服务器没授权KEY，请联系系统管理员");
        }

        StringBuilder unEncryptSign = new StringBuilder();
        String tansportParam = JSON.toJSONString(expressInputParam);
        unEncryptSign.append(tansportParam);
        unEncryptSign.append(expressConfig.getFreeKey());
        unEncryptSign.append(expressConfig.getCustomer());

        String sign = MD5.hashToBase64String(unEncryptSign.toString());
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("param", tansportParam);
        paramsMap.put("sign", sign);
        paramsMap.put("customer", expressConfig.getCustomer());
        return JSON.parseObject(HttpClientUtil.doPost(expressConfig.getFirmUrl(), paramsMap), ExpressInfo.class);
    }

    /**
     * 免费版快递100查询接口
     */
    private static ExpressInfo getExpressInfo4Free(ExpressInputParam expressInputParam) {
        String queryUrl = getQueryUrlByExpressInputParam(expressInputParam, expressConfig.getFreeUrl());
        String responseResult = HttpClientUtil.doGet(queryUrl, "UTF-8");
        if (StringUtils.isNotBlank(responseResult)) {
            return JSON.parseObject(responseResult, ExpressInfo.class);
        }
        return null;
    }

    /**
     * 根据物流入参获取请求的Url
     */
    private static String getQueryUrlByExpressInputParam(ExpressInputParam expressInputParam, String queryUrl) {
        List<String> paramList = new ArrayList<>();
        if (StringUtils.isNotBlank(expressConfig.getFreeKey())) {
            paramList.add(String.format("id=%s", expressConfig.getFreeKey()));
        }
        paramList.add(String.format("com=%s", expressInputParam.getCom()));
        paramList.add(String.format("nu=%s", expressInputParam.getNu()));
        paramList.add(String.format("show=%s", expressInputParam.getShow()));
        paramList.add(String.format("muti=%s", expressInputParam.getMuti()));
        paramList.add(String.format("order=%s", expressInputParam.getOrder()));
        return queryUrl + "?" + StringUtils.join(paramList, "&");
    }

    /**
     * 根据物流公司名称和运输单号获取物流信息
     */
    public static ExpressInfo getExpressInfoByCompanyNameAndNum(ExpressInputParam expressInputParam ) {
        ExpressInfo expressInfo;
        expressInputParam.setCom(getConpanyCodeByCompanyName(expressInputParam.getComName()));
        if (StringUtils.isBlank(expressInputParam.getCom())) {
            String notFindError=String.format("快递公司%s找不到对应的编码", expressInputParam.getComName());
            throw new BizException(notFindError);
        }

        try {
            if (StringUtils.isNotBlank(expressConfig.getFreeSwitch()) && Boolean.parseBoolean(expressConfig.getFreeSwitch())) {
                expressInfo=getExpressInfo4Free(expressInputParam);
            } else {
                expressInfo=getExpressInfo4Firm(expressInputParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String failError = String.format("调用快递100接口查询运单的信息失败原因是%s",e.getMessage());
            throw new BizException(failError);
        }

        if (expressInfo != null && !expressInfo.getResult()) {
            throw new BizException(Integer.parseInt(expressInfo.getReturnCode()), expressInfo.getMessage());
        }
        return expressInfo;
    }

    /**
     * 根据公司名字获取对应的快递编码
     */
    private static String getConpanyCodeByCompanyName(String companyName){
        String companyCode = null;
        switch (companyName){
            case "顺丰速运":
                companyCode= EnumConstants.ChinaExpressCompany.SHUNFENG.getValue();
                break;
            case "圆通快递":
                companyCode= EnumConstants.ChinaExpressCompany.YUANTONG.getValue();
                break;
            case "申通快递":
                companyCode= EnumConstants.ChinaExpressCompany.YUANTONG.getValue();
                break;
            case "韵达快递":
                companyCode= EnumConstants.ChinaExpressCompany.YUNDA.getValue();
                break;
            case "国通快递":
                companyCode= EnumConstants.ChinaExpressCompany.GUOTONG.getValue();
                break;
            case "百世快递":
                companyCode= EnumConstants.ChinaExpressCompany.BAISHI.getValue();
                break;
            case "天天快递":
                companyCode= EnumConstants.ChinaExpressCompany.TIANTIAN.getValue();
                break;
            case "EMS":
                companyCode= EnumConstants.ChinaExpressCompany.EMS.getValue();
                break;
            case"宅急送":
                companyCode= EnumConstants.ChinaExpressCompany.ZHAIJISONG.getValue();
                break;
            default:
                break;
        }
        return companyCode;
    }

    /**
     * 根据运输单号获取当前公司
     */
    public static List<ExpressCompany> getExpressCompanyByNum(ExpressInputParam expressInputParam) {
        List<ExpressCompany> companies=null;
        String queryParam=String.format("num=%s&key=%s", expressInputParam.getNu(), expressConfig.getFreeKey());

        try {
            String responseResult = HttpClientUtil.doGet(expressConfig.getAutoUrl() + "?" + queryParam, "UTF-8");
            if (StringUtils.isNotBlank(responseResult)) {
                companies = JSON.parseArray(responseResult, ExpressCompany.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String failError=String.format("调用快递100接口查询运单的物流公司信息失败原因是%s", e.getMessage());
            throw new BizException(failError);
        }
        return companies;
    }

    public static void main(String[] args) {
        ExpressInputParam inputParam = new ExpressInputParam();
        inputParam.setCom("huitongkuaidi");
        inputParam.setNu("70529208441378");
        System.out.println(JSON.toJSONString(getExpressInfoByCompanyCodeAndNum(inputParam)));
    }

}