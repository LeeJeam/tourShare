package com.xmyy.pay.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xmyy.common.util.DateUtils;
import com.xmyy.pay.allinpay.ServerFactory;
import com.xmyy.pay.allinpay.YunRequest;
import com.xmyy.pay.allinpay.YunResponse;
import com.xmyy.pay.allinpay.constants.Server;
import com.xmyy.pay.allinpay.constants.YunConfig;
import com.xmyy.pay.allinpay.request.CreateMemberParams;
import com.xmyy.pay.allinpay.request.ServerHandler;
import com.xmyy.pay.allinpay.response.CreateMemberResult;
import com.xmyy.pay.core.util.HexByteUtils;
import com.xmyy.pay.core.util.RSAUtil;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class YunClient {

    private static Logger logger = Logger.getLogger(YunClient.class.getName());

    private static final String METHOD_POST = "POST";
    private static final int BUFFER_SIZE = 1024;
    public static final String SSO_SERVICE = "SSOService";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERR = "error";
    public static final String ERR_MESSAGE = "message";
    public static final String ERR_CODE = "errorCode";
    public static final String RETURN_VALUE = "returnValue";
    private String serverAddress;
    private String sessionId;
    private String ssoid = "ime_public_ssoid";
    private String _signKey;
    private static String serverUrl;
    private static String _sysid;
    private static String version;
    private static String _signMethod = "MD5";
    private Proxy proxy = null;

    static {
        try {
            version = YunConfig.VERSION;
            serverUrl = YunConfig.SERVER_URL;
            _sysid = YunConfig.SYSID;
            _signMethod = YunConfig.SIGNMETHOD;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("YunClient load key error!");
        }
    }

    /**
     * 单例模式
     */
    private static YunClient instance = new YunClient();

    private YunClient() {};

    public static YunClient getInstance() {
        return instance;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        this.serverUrl = serverAddress;
    }

    public String getSignMethod() {
        return this._signMethod;
    }

    public void setSignMethod(String signMethod) {
        this._signMethod = signMethod;
    }

    public String getSignKey() {
        return this._signKey;
    }

    public void setSignKey(String signKey) {
        this._signKey = signKey;
    }

    public static String get_sysid() {
        return _sysid;
    }

    public static void set_sysid(String _sysid) {
        YunClient._sysid = _sysid;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    private Map<String, String> parseCookie(String cookie) {
        Map<String, String> map = new HashMap();
        String[] cookies = cookie.split(";");

        for(int i = 0; i < cookies.length; ++i) {
            String[] part = cookies[i].split("=");
            if (part.length == 2) {
                map.put(part[0].trim(), part[1].trim());
            }
        }

        return map;
    }

    private void checkResult(JSONObject result) throws Exception {
        if (!"OK".equals(result.getString("status"))) {
            throw new Exception(result.getString("message"));
        }
    }

    public void login(String userName, String password, String domainName) throws Exception {
        JSONArray args = new JSONArray();
        args.add(userName);
        args.add(password);
        args.add(domainName);
        JSONObject rt = this.request("SSOService", "login", args);
        this.checkResult(rt);
        rt = rt.getJSONObject("returnValue");
        if (rt != null) {
            String token = rt.getString("ssoid");
            if (token == null || token.length() == 0) {
                token = rt.getString("token");
            }

            if (token != null && token.length() > 0) {
                this.ssoid = token;
            }
        }

    }

    public JSONObject request(String serviceAndMethod, JSONArray args) throws Exception {
        String[] parts = serviceAndMethod.split("\\.");
        if (parts.length != 2) {
            throw new Exception("服务名称错误");
        } else {
            return this.request(parts[0], parts[1], args);
        }
    }

    public JSONObject request(String service, String method, JSONArray args) throws Exception {
        JSONObject request = new JSONObject();
        request.put("service", service);
        request.put("method", method);
        request.put("args", args);
        String strRequest = request.toString();
        strRequest = strRequest.replace("\r\n", "");
        Map<String, String> req = new HashMap<>();
        req.put("req", strRequest);
        req.put("ssoid", this.ssoid);
        commonParam(strRequest, req);

        String result = this.request(req);
        if (result == null) {
            throw new Exception("返回数据错误");
        } else {
            return this.verifyReturn(JSONObject.parseObject(result));
        }
    }

    private void commonParam(String strRequest, Map<String, String> req) throws Exception {
        if (!"".equals(this._sysid)) {
            String timestamp = DateUtils.formatDateTime(new Date(),DateUtils.Pattern_Date_Time);
            StringBuilder signBuilder=new StringBuilder();
            signBuilder.append(_sysid);
            signBuilder.append(strRequest);
            signBuilder.append(timestamp);
            req.put("sign",signParam(this._signKey,signBuilder.toString()));
            req.put("sysid", this._sysid);
            req.put("timestamp", timestamp);
            req.put("v", this.version);
        }
    }

    public JSONObject request(String serviceAndMethod, JSONObject param) throws Exception {
        String[] parts = serviceAndMethod.split("\\.");
        if (parts.length != 2) {
            throw new Exception("服务名称错误");
        } else {
            return this.request(parts[0], parts[1], param);
        }
    }

    /**
     * 参数签名<br>
     */
    public static String signParam(String signKey,String params) throws Exception {
        if ("SHA1WithRSA".equals(_signMethod)) {
            return RSAUtil.sign(params);
        } else {
            MessageDigest alga;
            try {
                alga = MessageDigest.getInstance("MD5");
            } catch (Exception var7) {
                return null;
            }
            StringBuilder paramsBuilder = new StringBuilder();
            paramsBuilder.append(signKey);
            paramsBuilder.append(params);
            paramsBuilder.append(signKey);
            alga.update(paramsBuilder.toString().getBytes("utf-8"));
            return HexByteUtils.byte2hex(alga.digest());
        }
    }

    private JSONObject verifyReturn(JSONObject result) throws Exception {
        if (result.containsKey("signedValue")) {
            String value = result.getString("signedValue");
            String sign = result.getString("sign");
            if (!RSAUtil.verify(value, sign)) {
                throw new Exception("签名验证错误");
            } else {
                if ("OK".equals(result.getString("status")) && !"null".equals(value)) {
                    JSONObject ret = JSONObject.parseObject(value);
                    if (ret.containsKey("$PrimitiveReturn$")) {
                        result.put("returnValue", ret.get("$PrimitiveReturn$"));
                    } else {
                        result.put("returnValue", ret);
                    }
                }

                return result;
            }
        } else {
            return result;
        }
    }

    /**
     * 封装方法
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     * @throws UnsupportedEncodingException
     */
    public <T,R> YunResponse doRequest(ServerHandler handler, T params, Class<R> clazz)  {
        try {
            YunRequest<T> req = new YunRequest<>(handler,params);
            ServerHandler serverHandler = req.getServerHandler();
            T param = req.getParam();
            JSONObject json = (JSONObject) JSON.toJSON(param);
            logger.info("请求参数:"+json);
            JSONObject resp = request(serverHandler.getService(), serverHandler.getMethod(), json);
            YunResponse<R> yunResponse = JSON.parseObject(resp.toString(), new TypeReference<YunResponse>(){});
            if(clazz!=null) {
                yunResponse.wrap(clazz);
            }
            logger.info("返回数据：" + yunResponse);
            return yunResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取请求参数
     * @param service
     * @param method
     * @param param
     * @return
     * @throws Exception
     */
    public Map getRequest(String service, String method, Object param) throws Exception {
        JSONObject request = new JSONObject();
        request.put("service", service);
        request.put("method", method);
        request.put("param", param);
        String strRequest = request.toString();
        strRequest = strRequest.replace("\r\n", "");
        Map<String, String> req = new HashMap<>();
        req.put("req", strRequest);
        commonParam(strRequest, req);
        return req;
    }

    private JSONObject request(String service, String method, JSONObject param) throws Exception {
        JSONObject request = new JSONObject();
        request.put("service", service);
        request.put("method", method);
        request.put("param", param);
        String strRequest = request.toString();
        strRequest = strRequest.replace("\r\n", "");
        Map<String, String> req = new HashMap<>();
        req.put("req", strRequest);
        req.put("ssoid", this.ssoid);
        commonParam(strRequest, req);

        String result = this.request(req);
        if (result == null) {
            throw new Exception("返回数据错误");
        } else {
            return this.verifyReturn(JSONObject.parseObject(result));
        }
    }

    private String request(Map<String, String> args) throws Exception {
        URL url = new URL(serverUrl);
        HttpURLConnection connection = null;
        if (this.proxy == null) {
            connection = (HttpURLConnection)url.openConnection();
        } else {
            connection = (HttpURLConnection)url.openConnection(this.proxy);
        }

        connection.setRequestMethod("POST");
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(false);
        connection.setUseCaches(false);
        StringBuilder sb = new StringBuilder();

        for (Object o : args.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            sb.append((String) entry.getKey()).append("=").append(URLEncoder.encode((String) entry.getValue(), "utf-8")).append("&");
        }

        sb.append("\r\n");
        byte[] reqbody = sb.toString().getBytes();
        if (this.sessionId != null) {
            sb.setLength(0);
            sb.append("JSESSIONID=").append(this.sessionId).append(";");
            connection.addRequestProperty("Cookie", sb.toString());
        }

        connection.addRequestProperty("Content-length", Integer.toString(reqbody.length));
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();
        if (connection.getDoOutput()) {
            OutputStream outputStream = null;

            try {
                outputStream = connection.getOutputStream();
                outputStream.write(reqbody);
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }

            }
        }

        int status = connection.getResponseCode();
        int statusPrefix = status / 100;
        if (statusPrefix != 1 && statusPrefix != 3) {
            int i = 1;

            for(String key = connection.getHeaderFieldKey(i); key != null; key = connection.getHeaderFieldKey(i)) {
                String value = connection.getHeaderField(i);
                if (key.equalsIgnoreCase("Set-Cookie")) {
                    Map<String, String> map = this.parseCookie(value);
                    if (map.containsKey("JSESSIONID")) {
                        this.sessionId = map.get("JSESSIONID");
                    }
                }

                ++i;
            }

            InputStream inputStream = null;

            try {
                try {
                    inputStream = connection.getInputStream();
                } catch (Exception var21) {
                    inputStream = connection.getErrorStream();
                }

                if (inputStream == null) {
                    return null;
                } else {
                    sb.setLength(0);
                    InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
                    char[] cbuf = new char[1024];

                    int len;
                    while((len = reader.read(cbuf, 0, cbuf.length)) != -1) {
                        sb.append(new String(cbuf, 0, len));
                    }

                    reader.close();
                    return sb.toString();
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

            }
        } else {
            throw new Exception("Unexpected server response: " + status);
        }
    }

    public static void main(String[] args) {
        YunClient yunClient = new YunClient();
        CreateMemberParams params = new CreateMemberParams();
        params.setBizUserId("100");
        params.setMemberType(2L);
        params.setSource(1L);
        ServerHandler handler = ServerFactory.memberHandler(Server.Member.CREATEMEMBER);
        yunClient.doRequest(handler,params, CreateMemberResult.class);
    }
}
