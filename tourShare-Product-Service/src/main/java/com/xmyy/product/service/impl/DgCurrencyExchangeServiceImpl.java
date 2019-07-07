package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.product.mapper.DgCurrencyExchangeMapper;
import com.xmyy.product.model.DgCurrencyExchange;
import com.xmyy.product.service.DgCurrencyExchangeService;
import com.xmyy.product.vo.ApiExchangeListResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 汇率  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = DgCurrencyExchangeService.class)
@CacheConfig(cacheNames = "DgCurrencyExchange")
public class DgCurrencyExchangeServiceImpl extends BaseServiceImpl<DgCurrencyExchange, DgCurrencyExchangeMapper> implements DgCurrencyExchangeService {
    private static Logger logger = LoggerFactory.getLogger(DgCurrencyExchangeServiceImpl.class);

    @Resource
    private DgCurrencyExchangeMapper dgCurrencyExchangeMapper;

    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    private static final String APPKEY = "7166603c82fee0b0af46aa561cdb8619";
    private static final String ERRORCODEKEY = "error_code";

    @Override
    public Object getList() {
        List<DgCurrencyExchange> exchangeList = getExchangeList();
        return exchangeList.stream().map(e -> InstanceUtil.to(e, ApiExchangeListResult.class)).collect(Collectors.toList());
    }

    public List<DgCurrencyExchange> getExchangeList() {
        EntityWrapper<DgCurrencyExchange> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        return dgCurrencyExchangeMapper.selectList(ew);
    }

    public Double getExchangeResult(String from, String to) {
        JSONObject respResult = getExchangeCurrency(from, to);
        if (respResult.getInteger(ERRORCODEKEY) == 0) {
            JSONArray array = JSONArray.parseArray(respResult.get("result").toString());
            JSONObject job = JSONObject.parseObject(array.get(0).toString());
            return Double.valueOf((String) job.get("result"));
        } else {
            return null;
        }

    }

    //3.实时汇率查询换算
    private static JSONObject getExchangeCurrency(String from, String to) {
        JSONObject object = new JSONObject();

        String result = null;
        String url = "http://op.juhe.cn/onebox/exchange/currency"; //请求接口地址
        Map<String, Object> params = new HashMap<>(); //请求参数
        params.put("from", from); //转换汇率前的货币代码
        params.put("to", to); //转换汇率成的货币代码
        params.put("key", APPKEY); //应用APPKEY(应用详细页查询)

        try {
            result = net(url, params, "GET");
            if (result == null) {
                object.put(ERRORCODEKEY, -2);
                return object;
            } else {
                object = JSONObject.parseObject(result);
                return object;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            object.put(ERRORCODEKEY, -1);
            return object;
        }
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map<String, Object> params, String method) throws IOException {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs;
        try {
            StringBuilder sb = new StringBuilder();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", USERAGENT);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method != null && method.equals("POST")) {
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(urlencode(params));
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
            return rs;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return "调用汇率接口失败";
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", DEF_CHATSET)).append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
            }
        }
        return sb.toString();
    }

}
