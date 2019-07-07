package com.xmyy.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import top.ibase4j.core.exception.BizException;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 网络访问工具类
 *
 * @author  wangzejun
 * @since 2018年05月21日16:07:05
 */
public class HttpClientUtil {

    //默认编码UTF8
    private static String CHARSET = "UTF-8";

    // 连接上一个url，获取response的返回等待时间
    public static final int SOCKET_TIMEOUT = 20 * 1000;

    // 连接一个url的连接等待时间
    public static final int CONNECT_TIMEOUT = 10 * 1000;


    /**
     * post请求，并使用body传输数据
     *
     * @see org.apache.http.entity.ContentType
     */
    public static String doPost(String url, String body) {
        return doPost(url, null, body, CHARSET, null);
    }

    /**
     * post请求，并使用body传输数据
     *
     * @see org.apache.http.entity.ContentType
     */
    public static String doPost(String url, String mimeType, String body) {
        return doPost(url, mimeType, body, CHARSET, null);
    }

    /**
     * post请求，并使用body传输数据
     *
     * @see org.apache.http.entity.ContentType
     */
    public static String doPost(String url, String mimeType, String body, String charset, SSLContext sslcontext) {
        FileInputStream instream = null;
        CloseableHttpClient httpClient = null;
        try {
            HttpClientBuilder builder = HttpClientBuilder.create();
            //SSL 证书
            if (sslcontext != null) {
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);
                builder.setSSLSocketFactory(sslsf);
            }
            httpClient = builder.build();

            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);

            ContentType contentType = ContentType.create(mimeType, charset);
            StringEntity entity = new StringEntity(body, contentType);
            entity.setContentType(mimeType);
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                //网络异常处理
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() != 200) {
                    String failError=String.format("请求异常，POST >>>> url:%s,原因是%s",
                            url,statusLine.getReasonPhrase());
                    throw new BizException( statusLine.getStatusCode(),failError);
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    return EntityUtils.toString(resEntity, charset);
                }
            }
            return null;
        } catch (IOException | RuntimeException e) {
            String failError=String.format("请求异常，POST >>>> url:%s,原因是%s",
                    url,e.getMessage());
            throw new BizException(failError);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException | RuntimeException e) {
                String failError=String.format("关闭IO异常原因，POST >>>> url:%s,原因是%s",
                        url,e.getMessage());
                throw new BizException(failError);
            }
        }
    }

    /**
     * post请求
     *
     * @return String
     *
     * @since 1.0.0
     */
    public static String doPost(String url, Map<String, String> map, String charset) {
        String logId = "";
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            HttpClientBuilder builder = HttpClientBuilder.create();
            httpClient = builder.build();
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(String.format("网络请求异常,url:%s,charset:%s,message:%s", url, charset, e.getMessage()));
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(String.format("关闭失败io原因是：", e.getMessage()));
            }
        }
        return result;
    }

    /**
     * UTF8编码
     *
     * @return String
     *
     * @since 1.0.0
     */
    public static String doPost(String url, Map<String, String> map) {
        return doPost(url, map, CHARSET);
    }


    /**
     * get请求
     * @param url
     * @param charset
     * @return
     */
    public static String doGet(String url, String charset) {
        String logId = "";
        CloseableHttpClient httpClient = null;
        String result = null;
        try {
            RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
            httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }

            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(String.format("网络请求异常,url:%s,charset:%s,message:%s", url, charset, e.getMessage()));
        }catch (Exception e){
            throw new RuntimeException(String.format("网络请求异常,url:%s,charset:%s,message:%s", url, charset, e.getMessage()));
        }finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException | RuntimeException e) {
                throw new RuntimeException(String.format("关闭失败的原因是：%s",e.getMessage()));
            }
        }
        return result;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, CHARSET);
    }


    /**
     * ssl 请求
     * @param certPath
     * @param password
     * @return
     */
    public static SSLContext getPkcs12SSLContext(String certPath, char[] password) {
        String logId = "SSLContext";
        FileInputStream instream = null;
        try {
            //读取证书
            instream = new FileInputStream(new File(certPath));
            //加载证书
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(instream, password);
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, password).build();
            return sslcontext;
        } catch (KeyStoreException | IOException | KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(String.format("%s SSL证书加载失败原因是%s",logId,e.getMessage()));
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("流关闭失败");
            }
        }
    }
}