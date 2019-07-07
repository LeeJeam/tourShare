package com.xmyy.pay.core.util;

import com.alibaba.fastjson.JSON;
import com.xmyy.common.util.HttpConnectionUtil;
import com.xmyy.pay.allinpay.constants.YunConfig;

import java.util.Map;
import java.util.TreeMap;

/**
 * 通联收银宝支付接口，对接云商通不需要使用
 *
 * @author wangzejun
 * @time 2018年 09月25日 17:00:07
 * @since 1.0.0
 */
public class SybPayUtils {

    /**
     * 收银宝商户号
     */
    public static final String SYB_CUSID = "55158104816MTRD";

    /**
     *收银宝APPID
     */
    public static final String SYB_APPID = "00142296";

    /**
     * APP授权key
     */
    public static final String SYB_APPKEY = "Ow92awdfsewlwqp821DU935D";

    /**
     * 支付调用地址
     */
    public static final String SYB_APIURL = "https://vsp.allinpay.com/apiweb/unitorder";//生产环境

    /**
     * 支付接口<br>
     * @param trxamt
     * @param reqsn
     * @param paytype
     * @param body
     * @param remark
     * @param acct
     * @param authcode
     * @param notify_url
     * @param limit_pay
     * @param idno
     * @param truename
     * @param asinfo
     * @return
     * @throws Exception
     */
    public static Map<String,String> pay(long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,String notify_url,String limit_pay,String idno,String truename,String asinfo) throws Exception{
        HttpConnectionUtil http = new HttpConnectionUtil(SYB_APIURL+"/pay");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("cusid", SYB_CUSID);
        params.put("appid", SYB_APPID);
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("paytype", paytype);
        params.put("randomstr", SybHelpUtils.getValidatecode(8));
        params.put("body", body);
        params.put("remark", remark);
        params.put("acct", acct);
        params.put("authcode", authcode);
        params.put("notify_url", notify_url);
        params.put("limit_pay", limit_pay);
        params.put("idno", idno);
        params.put("truename", truename);
        params.put("asinfo", asinfo);
        params.put("sign", SybHelpUtils.sign(params,SYB_APPKEY));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result);
        return map;

    }

    /**
     * 取消支付<br>
     * @param trxamt
     * @param reqsn
     * @param oldtrxid
     * @param oldreqsn
     * @return
     * @throws Exception
     */
    public static Map<String,String> cancel(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
        HttpConnectionUtil http = new HttpConnectionUtil(SYB_APIURL+"/cancel");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("cusid", SYB_CUSID);
        params.put("appid", SYB_APPID);
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("oldtrxid", oldtrxid);
        params.put("oldreqsn", oldreqsn);
        params.put("randomstr", SybHelpUtils.getValidatecode(8));
        params.put("sign", SybHelpUtils.sign(params,SYB_APPKEY));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result);
        return map;
    }

    /**
     * 退款接口<br>
     * @param trxamt
     * @param reqsn
     * @param oldtrxid
     * @param oldreqsn
     * @return
     * @throws Exception
     */
    public static Map<String,String> refund(long trxamt,String reqsn,String oldtrxid,String oldreqsn) throws Exception{
        HttpConnectionUtil http = new HttpConnectionUtil(SYB_APIURL+"/refund");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("cusid", SYB_CUSID);
        params.put("appid", SYB_APPID);
        params.put("version", "11");
        params.put("trxamt", String.valueOf(trxamt));
        params.put("reqsn", reqsn);
        params.put("oldreqsn", oldreqsn);
        params.put("oldtrxid", oldtrxid);
        params.put("randomstr", SybHelpUtils.getValidatecode(8));
        params.put("sign", SybHelpUtils.sign(params,SYB_APPKEY));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result);
        return map;
    }


    /**
     * 查询<br>
     * @param reqsn
     * @param trxid
     * @return
     * @throws Exception
     */
    public static Map<String,String> query(String reqsn,String trxid) throws Exception{
        HttpConnectionUtil http = new HttpConnectionUtil(SYB_APIURL+"/query");
        http.init();
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("cusid", SYB_CUSID);
        params.put("appid", SYB_APPID);
        params.put("version", "11");
        params.put("reqsn", reqsn);
        params.put("trxid", trxid);
        params.put("randomstr", SybHelpUtils.getValidatecode(8));
        params.put("sign", SybHelpUtils.sign(params,SYB_APPKEY));
        byte[] bys = http.postParams(params, true);
        String result = new String(bys,"UTF-8");
        Map<String,String> map = handleResult(result);
        return map;
    }


    /**
     * 处理请求，获取结果<br>
     * @param result
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String,String> handleResult(String result) throws Exception{
        Map map = JSON.parseObject(result, Map.class);
        if(map == null){
            throw new Exception("返回数据错误");
        }
        if("SUCCESS".equals(map.get("retcode"))){
            TreeMap tmap = new TreeMap();
            tmap.putAll(map);
            String sign = tmap.remove("sign").toString();
            String sign1 = SybHelpUtils.sign(tmap,SYB_APPKEY);
            if(sign1.toLowerCase().equals(sign.toLowerCase())){
                return map;
            }else{
                throw new Exception("验证签名失败");
            }

        }else{
            throw new Exception(map.get("retmsg").toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> stringMap = com.xmyy.pay.core.util.SybPayUtils.pay(1L, "PL924669488646", "A01", "测试商品",
                "", "", "", YunConfig.BACK_URL, "no_credit",
                "", "", "");
        for (String s : stringMap.keySet()) {
            System.out.println(s + "=" + stringMap.get(s));
        }
    }

}
