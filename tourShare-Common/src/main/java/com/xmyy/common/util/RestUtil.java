/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xmyy.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureAdapter;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import top.ibase4j.core.util.PropertiesUtil;

import javax.net.ssl.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author zlp
 */
public class RestUtil {

    private static final int DEFAULT_CONNECT_TIMEOUT_MILLISECONDS = PropertiesUtil.getInt("rest.default_connect_timeout_milliseconds", 3 * 1000);
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = PropertiesUtil.getInt("rest.default_read_timeout_milliseconds", 20 * 1000);
    private static final boolean DEFAULT_ENABLE_JAXB_ANNOTATION = PropertiesUtil.getBoolean("rest.default_enable_jaxb_annotation", true);
    private static final String DEFAULT_PROPERTY_NAMING_STRATEGY = PropertiesUtil.getString("rest.default_property_naming_strategy");
    private static final int DEFAULT_ASYNC_CONNECT_TIMEOUT_MILLISECONDS = PropertiesUtil.getInt("rest.default_async_connect_timeout_milliseconds", 3 * 1000);
    private static final int DEFAULT_ASYNC_READ_TIMEOUT_MILLISECONDS = PropertiesUtil.getInt("rest.default_async_read_timeout_milliseconds", 20 * 1000);
    private static final boolean DEFAULT_ENABLE_ASYNC_THREAD_POOL = PropertiesUtil.getBoolean("rest.default_enable_async_thread_pool", false);
    private static final int DEFAULT_ASYNC_QUEUE_CAPACITY = PropertiesUtil.getInt("rest.default_async_queue_capacity", 0);
    private static final int DEFAULT_ASYNC_CORE_POOL_SIZE = PropertiesUtil.getInt("rest.default_async_core_pool_size", 1);
    private static final int DEFAULT_ASYNC_MAX_POOL_SIZE = PropertiesUtil.getInt("rest.default_async_max_pool_size", Integer.MAX_VALUE);
    private static final int DEFAULT_ASYNC_KEEP_ALIVE_SECONDS = PropertiesUtil.getInt("rest.default_async_keep_alive_seconds", 60);

    private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);
    private static volatile ObjectMapper mapper;
    private static volatile RestTemplate rest;
    private static volatile AsyncRestTemplate asyncRest;
    private static volatile ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private interface ClientHttpRequestCallback {

        void preExecute(ClientHttpRequest req);

        void postExecute(ClientHttpRequest req, ClientHttpResponse resp);
    }

    private static ClientHttpResponse extractGZIPResponse(final ClientHttpResponse response) throws IOException {
        if (response != null && response.getRawStatusCode() == 200) {
            HttpHeaders headers = response.getHeaders();
            if (headers != null) {
                List<String> list = headers.get("Content-Encoding");
                if (list != null && !list.isEmpty()) {
                    String contentEncoding = list.get(0);
                    if (contentEncoding != null) {
                        if (contentEncoding.equalsIgnoreCase("gzip")) {
                            headers.remove("Content-Encoding");
                            return new ClientHttpResponse() {
                                @Override
                                public HttpStatus getStatusCode() throws IOException {
                                    return response.getStatusCode();
                                }

                                @Override
                                public int getRawStatusCode() throws IOException {
                                    return response.getRawStatusCode();
                                }

                                @Override
                                public String getStatusText() throws IOException {
                                    return response.getStatusText();
                                }

                                @Override
                                public void close() {
                                    response.close();
                                }

                                @Override
                                public InputStream getBody() throws IOException {
                                    return new GZIPInputStream(response.getBody());
                                }

                                @Override
                                public HttpHeaders getHeaders() {
                                    return response.getHeaders();
                                }
                            };
                        }
                    }
                }
            }
        }
        return response;
    }

    private static class ClientHttpRequestImpl implements ClientHttpRequest {

        private final ClientHttpRequest request;
        private final ClientHttpRequestCallback callback;

        public ClientHttpRequestImpl(ClientHttpRequest request, ClientHttpRequestCallback callback) {
            this.request = request;
            this.callback = callback;
        }

        public ClientHttpResponse execute() throws IOException {
            callback.preExecute(request);
            ClientHttpResponse response = extractGZIPResponse(request.execute());
            callback.postExecute(request, response);
            return response;
        }

        public HttpMethod getMethod() {
            return request.getMethod();
        }

        @Override
        public String getMethodValue() {
            return request.getMethodValue();
        }

        public URI getURI() {
            return request.getURI();
        }

        public HttpHeaders getHeaders() {
            return request.getHeaders();
        }

        public OutputStream getBody() throws IOException {
            return request.getBody();
        }
    }

    private interface AsyncClientHttpRequestCallback {

        void preExecute(AsyncClientHttpRequest req);

        void postExecute(AsyncClientHttpRequest req, ClientHttpResponse resp);
    }

    private static class AsyncClientHttpRequestImpl implements AsyncClientHttpRequest {

        private final AsyncClientHttpRequest request;
        private final AsyncClientHttpRequestCallback callback;
        private boolean executed = false;

        public AsyncClientHttpRequestImpl(AsyncClientHttpRequest request, AsyncClientHttpRequestCallback callback) {
            this.request = request;
            this.callback = callback;
        }

        public ListenableFuture<ClientHttpResponse> executeAsync() throws IOException {
            Assert.state(!executed, "ClientHttpRequest already executed");
            callback.preExecute(request);

            ListenableFuture<ClientHttpResponse> result = new ListenableFutureAdapter<ClientHttpResponse, ClientHttpResponse>(request.executeAsync()) {
                @Override
                protected ClientHttpResponse adapt(ClientHttpResponse adapteeResult) throws ExecutionException {
                    try {
                        return extractGZIPResponse(adapteeResult);
                    } catch (IOException ex) {
                        logger.error(ex.getMessage(), ex);
                        return adapteeResult;
                    }
                }
            };

            result.addCallback(new ListenableFutureCallback<ClientHttpResponse>() {
                @Override
                public void onSuccess(ClientHttpResponse result) {
                    callback.postExecute(request, result);
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error(ex.getMessage(), ex);
                }
            });

            executed = true;
            return result;
        }

        public HttpMethod getMethod() {
            return request.getMethod();
        }

        @Override
        public String getMethodValue() {
            return request.getMethodValue();
        }

        public URI getURI() {
            return request.getURI();
        }

        public HttpHeaders getHeaders() {
            return request.getHeaders();
        }

        public OutputStream getBody() throws IOException {
            return request.getBody();
        }
    }

    public static class ClientHttpRequestFactoryImpl extends SimpleClientHttpRequestFactory implements ClientHttpRequestCallback, AsyncClientHttpRequestCallback {

        private AsyncListenableTaskExecutor taskExecutor;
        private CookieManager cookieManager;
        private boolean cookieReadOnly;
        private HttpHeaders headers;

        public ClientHttpRequestFactoryImpl() {
            headers = new HttpHeaders();
        }

        @Override
        public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
            ClientHttpRequest req = super.createRequest(uri, httpMethod);
            if (req != null) {
                return new ClientHttpRequestImpl(req, this);
            }
            return null;
        }

        @Override
        protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
            if (url.getProtocol().equals("https")) {
                url = new URL(url, "", new sun.net.www.protocol.https.Handler());
            }
            return super.openConnection(url, proxy);
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
                };
                SSLContext ctx;
                try {
                    ctx = SSLContext.getInstance("TLS");
                    ctx.init(null, trustAllCerts, new java.security.SecureRandom());
                    ((HttpsURLConnection) connection).setSSLSocketFactory(ctx.getSocketFactory());
                } catch (KeyManagementException ex) {
                } catch (NoSuchAlgorithmException ex) {
                }
                ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String paramString, SSLSession paramSSLSession) {
                        return true;
                    }
                });
            }
            super.prepareConnection(connection, httpMethod);
        }

        public void preExecute(ClientHttpRequest req) {
            if (headers != null && !headers.isEmpty()) {
                req.getHeaders().putAll(headers);
            }
            if (cookieManager != null) {
                try {
                    Map<String, List<String>> cookieMap = cookieManager.get(req.getURI(), new HashMap<String, List<String>>());
                    if (cookieMap != null && !cookieMap.isEmpty()) {
                        List<String> cookies = cookieMap.get("Cookie");
                        if (cookies != null && !cookies.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            for (String s : cookies) {
                                sb.append("; ").append(s);
                            }
                            if (sb.length() > 2) {
                                req.getHeaders().set("Cookie", sb.substring(2));
                            }
                        }
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        public void postExecute(ClientHttpRequest req, ClientHttpResponse resp) {
            if (!cookieReadOnly && cookieManager != null) {
                try {
                    cookieManager.put(req.getURI(), resp.getHeaders());
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        public HttpHeaders getHeaders() {
            return headers;
        }

        public void setHeaders(HttpHeaders headers) {
            this.headers = headers;
        }

        public CookieManager getCookieManager() {
            return cookieManager;
        }

        public void setCookieManager(CookieManager cookieManager) {
            this.cookieManager = cookieManager;
        }

        public void setCookieManager(CookieManager cookieManager, boolean readOnly) {
            this.cookieManager = cookieManager;
            this.cookieReadOnly = readOnly;
        }

        public boolean isCookieReadOnly() {
            return cookieReadOnly;
        }

        public void setCookieReadOnly(boolean readOnly) {
            this.cookieReadOnly = readOnly;
        }

        public void setHeader(String headerName, String headerValue) {
            headers.set(headerName, headerValue);
        }

        @Override
        public AsyncClientHttpRequest createAsyncRequest(URI uri, HttpMethod httpMethod) throws IOException {
            Assert.state(this.taskExecutor != null, "Asynchronous execution requires an AsyncTaskExecutor to be set");
            AsyncClientHttpRequest req = super.createAsyncRequest(uri, httpMethod);
            if (req != null) {
                return new AsyncClientHttpRequestImpl(req, this);
            }
            return null;
        }

        @Override
        public void setTaskExecutor(AsyncListenableTaskExecutor taskExecutor) {
            this.taskExecutor = taskExecutor;
            super.setTaskExecutor(taskExecutor);
        }

        public void preExecute(AsyncClientHttpRequest req) {
            if (headers != null && !headers.isEmpty()) {
                req.getHeaders().putAll(headers);
            }
            if (cookieManager != null) {
                try {
                    Map<String, List<String>> cookieMap = cookieManager.get(req.getURI(), new HashMap<String, List<String>>());
                    if (cookieMap != null && !cookieMap.isEmpty()) {
                        List<String> cookies = cookieMap.get("Cookie");
                        if (cookies != null && !cookies.isEmpty()) {
                            StringBuilder sb = new StringBuilder();
                            for (String s : cookies) {
                                sb.append("; ").append(s);
                            }
                            if (sb.length() > 2) {
                                req.getHeaders().set("Cookie", sb.substring(2));
                            }
                        }
                    }
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }

        @Override
        public void postExecute(AsyncClientHttpRequest req, ClientHttpResponse resp) {
            if (!cookieReadOnly && cookieManager != null) {
                try {
                    cookieManager.put(req.getURI(), resp.getHeaders());
                } catch (IOException ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
    }

    public static ObjectMapper createObjectMapper() {
        return createObjectMapper(null, null);
    }

    public static ObjectMapper createObjectMapper(Boolean registerJaxb) {
        return createObjectMapper(registerJaxb, null);
    }

    public static ObjectMapper createObjectMapper(Boolean registerJaxb, String propertyNamingStrategy) {
        registerJaxb = registerJaxb == null ? DEFAULT_ENABLE_JAXB_ANNOTATION : registerJaxb;
        propertyNamingStrategy = propertyNamingStrategy == null ? DEFAULT_PROPERTY_NAMING_STRATEGY : propertyNamingStrategy;

        ObjectMapper objectMapper = new ObjectMapper();
        if (registerJaxb) {
            objectMapper.registerModule(new JaxbAnnotationModule());
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        if (propertyNamingStrategy != null) {
            if (propertyNamingStrategy.equalsIgnoreCase("SNAKE_CASE")) {
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            } else if (propertyNamingStrategy.equalsIgnoreCase("UPPER_CAMEL_CASE")) {
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
            } else if (propertyNamingStrategy.equalsIgnoreCase("LOWER_CAMEL_CASE")) {
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
            } else if (propertyNamingStrategy.equalsIgnoreCase("LOWER_CASE")) {
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
            } else if (propertyNamingStrategy.equalsIgnoreCase("KEBAB_CASE")) {
                objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
            }
        }
        return objectMapper;
    }

    public static ObjectMapper
            getObjectMapper() {
        if (mapper == null) {
            synchronized (RestUtil.class) {
                if (mapper == null) {
                    mapper = createObjectMapper();
                }
            }
        }
        return mapper;
    }

    public static RestTemplate createRestTemplate() {
        return createRestTemplate(null, null);
    }

    public static RestTemplate createRestTemplate(Boolean registerJaxb) {
        return createRestTemplate(registerJaxb, null);
    }

    public static RestTemplate createRestTemplate(Boolean registerJaxb, String propertyNamingStrategy) {
        registerJaxb = registerJaxb == null ? DEFAULT_ENABLE_JAXB_ANNOTATION : registerJaxb;
        propertyNamingStrategy = propertyNamingStrategy == null ? DEFAULT_PROPERTY_NAMING_STRATEGY : propertyNamingStrategy;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new ClientHttpRequestFactoryImpl());
        setTimeout(restTemplate, DEFAULT_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_READ_TIMEOUT_MILLISECONDS);
        setMessageConverter(restTemplate, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        configureFormHttpMessageConverter(restTemplate);
        configureJackson2(restTemplate, createObjectMapper(registerJaxb, propertyNamingStrategy), MediaType.TEXT_PLAIN, MediaType.TEXT_HTML);
        configureJaxb2(restTemplate, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML);
        return restTemplate;
    }

    public static RestTemplate getRestTemplate() {
        if (rest == null) {
            synchronized (RestUtil.class) {
                if (rest == null) {
                    rest = createRestTemplate();
                }
            }
        }
        return rest;
    }

    public static AsyncRestTemplate createAsyncRestTemplate() {
        return createAsyncRestTemplate(null, DEFAULT_ENABLE_JAXB_ANNOTATION);
    }

    public static AsyncRestTemplate createAsyncRestTemplate(boolean registerJaxb) {
        return createAsyncRestTemplate(null, DEFAULT_ENABLE_JAXB_ANNOTATION);
    }

    public static AsyncRestTemplate createAsyncRestTemplate(AsyncListenableTaskExecutor taskExecutor) {
        return createAsyncRestTemplate(taskExecutor, DEFAULT_ENABLE_JAXB_ANNOTATION);
    }

    public static AsyncRestTemplate createAsyncRestTemplate(AsyncListenableTaskExecutor taskExecutor, boolean registerJaxb) {
        return createAsyncRestTemplate(taskExecutor, registerJaxb, null);
    }

    public static AsyncRestTemplate createAsyncRestTemplate(AsyncListenableTaskExecutor taskExecutor, boolean registerJaxb, String propertyNamingStrategy) {
        ClientHttpRequestFactoryImpl clientHttpRequestFactory = new ClientHttpRequestFactoryImpl();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        setTimeout(restTemplate, DEFAULT_ASYNC_CONNECT_TIMEOUT_MILLISECONDS, DEFAULT_ASYNC_READ_TIMEOUT_MILLISECONDS);
        setMessageConverter(restTemplate, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        configureFormHttpMessageConverter(restTemplate);
        configureJackson2(restTemplate, createObjectMapper(registerJaxb, propertyNamingStrategy), MediaType.TEXT_PLAIN, MediaType.TEXT_HTML);
        configureJaxb2(restTemplate, MediaType.TEXT_PLAIN, MediaType.TEXT_HTML);

        if (taskExecutor == null) {
            if (DEFAULT_ENABLE_ASYNC_THREAD_POOL) {
                if (threadPoolTaskExecutor == null) {
                    synchronized (RestUtil.class) {
                        if (threadPoolTaskExecutor == null) {

                            Runtime.getRuntime().addShutdownHook(new Thread() {

                                @Override
                                public void run() {
                                    destory();
                                }

                            });

                            threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
                            threadPoolTaskExecutor.setQueueCapacity(DEFAULT_ASYNC_QUEUE_CAPACITY);
                            threadPoolTaskExecutor.setCorePoolSize(DEFAULT_ASYNC_CORE_POOL_SIZE);
                            threadPoolTaskExecutor.setMaxPoolSize(DEFAULT_ASYNC_MAX_POOL_SIZE);
                            threadPoolTaskExecutor.setKeepAliveSeconds(DEFAULT_ASYNC_KEEP_ALIVE_SECONDS);
                            threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false);
                            threadPoolTaskExecutor.initialize();
                        }
                    }
                }
                taskExecutor = threadPoolTaskExecutor;
            } else {
                taskExecutor = new SimpleAsyncTaskExecutor();
            }
        }
        clientHttpRequestFactory.setTaskExecutor(taskExecutor);
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(clientHttpRequestFactory, restTemplate);
        return asyncRestTemplate;
    }

    public static AsyncRestTemplate getAsyncRestTemplate() {
        if (asyncRest == null) {
            synchronized (RestUtil.class) {
                if (asyncRest == null) {
                    asyncRest = createAsyncRestTemplate();
                }
            }
        }
        return asyncRest;
    }

    public static void setTimeout(RestTemplate rest, int connectTimeout, int readTimeout) {
        ClientHttpRequestFactory factory = rest.getRequestFactory();
        Reflection.setField(factory, "connectTimeout", connectTimeout);
        Reflection.setField(factory, "readTimeout", readTimeout);
    }

    public static void setCookieManager(RestTemplate rest, CookieManager cookieManager) {
        ClientHttpRequestFactory factory = rest.getRequestFactory();
        if (factory instanceof ClientHttpRequestFactoryImpl) {
            ((ClientHttpRequestFactoryImpl) factory).setCookieManager(cookieManager);
        }
    }

    public static void setCookieManager(RestTemplate rest, CookieManager cookieManager, boolean readOnly) {
        ClientHttpRequestFactory factory = rest.getRequestFactory();
        if (factory instanceof ClientHttpRequestFactoryImpl) {
            ((ClientHttpRequestFactoryImpl) factory).setCookieManager(cookieManager, readOnly);
        }
    }

    public static void setCookieReadOnly(RestTemplate rest, boolean readOnly) {
        ClientHttpRequestFactory factory = rest.getRequestFactory();
        if (factory instanceof ClientHttpRequestFactoryImpl) {
            ((ClientHttpRequestFactoryImpl) factory).setCookieReadOnly(readOnly);
        }
    }

    public static void setHeader(RestTemplate rest, String headerName, String headerValue) {
        ClientHttpRequestFactory factory = rest.getRequestFactory();
        if (factory instanceof ClientHttpRequestFactoryImpl) {
            ((ClientHttpRequestFactoryImpl) factory).setHeader(headerName, headerValue);
        }
    }

    public static String urlEncode(String s) {
        return urlEncode(s, "UTF-8");
    }

    public static String urlEncode(String s, String enc) {
        try {
            return URLEncoder.encode(s, enc).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException ex) {
            return "";
        }
    }

    public static String urlEncode(Map<String, ?> map) {
        return urlEncode(map, "UTF-8");
    }

    public static String urlEncode(Map<String, ?> map, String enc) {
        StringBuilder sb = new StringBuilder();
        url_encode_map(sb, null, map, enc);
        return sb.toString().replaceAll("\\+", "%20");
    }

    @SuppressWarnings("unchecked")
    private static void url_encode_map(StringBuilder sb, String prefix, Map map, String enc) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
            String key = entry.getKey();
            if (prefix != null) {
                key = prefix + "." + key;
            }
            url_encode_do(sb, key, entry.getValue(), enc);
        }
    }

    private static void url_encode_do(StringBuilder sb, String name, Object value, String enc) {
        if (value != null) {
            if (value instanceof Map) {
                url_encode_map(sb, name, (Map) value, enc);
            } else if (value.getClass().isArray()) {
                List values = Arrays.asList(value);
                for (Object v : values) {
                    url_encode_do(sb, name, v, enc);
                }
            } else if (value instanceof List) {
                List values = (List) value;
                for (Object v : values) {
                    url_encode_do(sb, name, v, enc);
                }
            } else {
                try {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(URLEncoder.encode(name, enc));
                    sb.append("=");
                    sb.append(URLEncoder.encode(value.toString(), enc));
                } catch (UnsupportedEncodingException ex) {
                }
            }
        }
    }

    private static void setMessageConverter(RestTemplate restTemplate, HttpMessageConverter<?> newMessageConverter) {
        setMessageConverter(restTemplate.getMessageConverters(), newMessageConverter);
    }

    private static void setMessageConverter(List<HttpMessageConverter<?>> messageConverters, HttpMessageConverter<?> newMessageConverter) {
        if (messageConverters != null) {
            for (int i = 0, size = messageConverters.size(); i < size; i++) {
                HttpMessageConverter<?> messageConverter = messageConverters.get(i);
                if (messageConverter.getClass().isInstance(newMessageConverter)) {
                    messageConverters.remove(i);
                    messageConverters.add(i, newMessageConverter);
                    return;
                }
            }
            messageConverters.add(newMessageConverter);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T getMessageConverter(RestTemplate restTemplate, Class<T> clazz) {
        return getMessageConverter(restTemplate.getMessageConverters(), clazz);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getMessageConverter(List<HttpMessageConverter<?>> messageConverters, Class<T> clazz) {
        if (messageConverters != null) {
            for (HttpMessageConverter<?> messageConverter : messageConverters) {
                if (clazz.isInstance(messageConverter)) {
                    return (T) messageConverter;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static void configureFormHttpMessageConverter(RestTemplate restTemplate) {
        FormHttpMessageConverter messageConverter = getMessageConverter(restTemplate, FormHttpMessageConverter.class
        );
        if (messageConverter != null) {
            List<HttpMessageConverter<?>> partConverters = (List<HttpMessageConverter<?>>) Reflection.getField(messageConverter, "partConverters");
            setMessageConverter(partConverters, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        }
    }

    public static void configureJackson2(RestTemplate restTemplate, ObjectMapper objectMapper, MediaType... mediaTypes) {
        configureJackson2(getMessageConverter(restTemplate, MappingJackson2HttpMessageConverter.class
        ), objectMapper, mediaTypes);

    }

    private static void configureJackson2(List<HttpMessageConverter<?>> messageConverters, ObjectMapper objectMapper, MediaType... mediaTypes) {
        configureJackson2(getMessageConverter(messageConverters, MappingJackson2HttpMessageConverter.class
        ), objectMapper, mediaTypes);
    }

    private static void configureJackson2(MappingJackson2HttpMessageConverter messageConverter, ObjectMapper objectMapper, MediaType... mediaTypes) {
        if (messageConverter != null) {
            if (objectMapper != null) {
                messageConverter.setObjectMapper(objectMapper);
            }
            if (mediaTypes != null && mediaTypes.length > 0) {
                ArrayList<MediaType> types = new ArrayList<MediaType>();
                types.addAll(messageConverter.getSupportedMediaTypes());
                types.addAll(Arrays.asList(mediaTypes));
                messageConverter.setSupportedMediaTypes(types);
            }
        }
    }

    public static void configureJaxb2(RestTemplate restTemplate, MediaType... mediaTypes) {
        configureJaxb2(getMessageConverter(restTemplate, Jaxb2RootElementHttpMessageConverter.class
        ), mediaTypes);

    }

    private static void configureJaxb2(List<HttpMessageConverter<?>> messageConverters, MediaType... mediaTypes) {
        configureJaxb2(getMessageConverter(messageConverters, Jaxb2RootElementHttpMessageConverter.class
        ), mediaTypes);
    }

    private static void configureJaxb2(Jaxb2RootElementHttpMessageConverter messageConverter, MediaType... mediaTypes) {
        if (messageConverter != null) {
            if (mediaTypes != null && mediaTypes.length > 0) {
                ArrayList<MediaType> types = new ArrayList<MediaType>();
                types.addAll(messageConverter.getSupportedMediaTypes());
                types.addAll(Arrays.asList(mediaTypes));
                messageConverter.setSupportedMediaTypes(types);
            }
        }
    }

    public static MultiValueMap<String, ?> buildMultiValueMap(Object... params) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        if (params.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < params.length; i += 2) {
            map.add(params[i].toString(), params[i + 1]);
        }
        return map;
    }

    public static Map<String, ?> buildMap(Object... params) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (params.length % 2 == 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < params.length; i += 2) {
            map.put(params[i].toString(), params[i + 1]);
        }
        return map;
    }

    public static String toJson(Object o) {
        return toJson(o, getObjectMapper());
    }

    public static String toJson(Object o, ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "";
    }

    public static String toXml(Object o) {
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = context.createMarshaller();
            StringWriter sw = new StringWriter();
            marshaller.marshal(o, sw);
            return sw.toString();
        } catch (JAXBException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "";
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, getObjectMapper());
    }

    public static <T> T fromJson(String json, Class<T> clazz, ObjectMapper mapper) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonParseException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (JsonMappingException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference valueTypeRef) {
        return fromJson(json, valueTypeRef, getObjectMapper());
    }

    public static <T> T fromJson(String json, TypeReference valueTypeRef, ObjectMapper mapper) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (JsonParseException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (JsonMappingException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static <T> T fromJson(String json, JavaType valueType) {
        return fromJson(json, valueType, getObjectMapper());
    }

    public static <T> T fromJson(String json, JavaType valueType, ObjectMapper mapper) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonParseException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (JsonMappingException ex) {
            logger.error(ex.getMessage() + "\n" + json + "\n", ex);
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            return (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException ex) {
            logger.error(ex.getMessage() + "\n" + xml + "\n", ex);
        }
        return null;
    }

    public static MultiValueMap<String, Object> toMultiValueMap(Object object) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        toMultiValueMap(map, "", object);
        return map;
    }

    public static MultiValueMap<String, Object> toMultiValueMap(String objectName, Object object) {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        toMultiValueMap(map, objectName, object);
        return map;
    }

    public static void toMultiValueMap(MultiValueMap<String, Object> map, String objectName, Object object) {
        map.setAll(toMap(objectName, object));
    }

    public static Map<String, Object> toMap(Object object) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        toMap(map, "", object);
        return map;
    }

    public static Map<String, Object> toMap(String objectName, Object object) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        toMap(map, objectName, object);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static void toMap(Map<String, Object> map, String objectName, Object object) {
        if (object == null) {
            if (!(object instanceof Resource)) {
                if (objectName != null && objectName.length() != 0) {
                    map.put(objectName, "");
                }
            }
            return;
        }
        Class objectClass = object.getClass();

        if (object instanceof Number
                || byte.class
                == objectClass
                || short.class
                == objectClass
                || int.class
                == objectClass
                || long.class
                == objectClass
                || float.class
                == objectClass
                || double.class
                == objectClass
                || object instanceof Boolean
                || boolean.class
                == objectClass
                || object instanceof Character
                || char.class
                == objectClass
                || object instanceof String
                || object instanceof Date) {
            if (objectName != null && objectName.length() != 0) {
                map.put(objectName, object.toString());
            }
        } else if (object instanceof Map) {
            String prefix = (objectName == null || objectName.length() == 0) ? "" : objectName + ".";
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) object).entrySet()) {
                toMap(map, prefix + entry.getKey().toString(), entry.getValue());
            }
        } else if (object instanceof Collection) {
            if (objectName != null && objectName.length() != 0) {
                Object[] array = ((Collection) object).toArray();
                for (int index = 0, count = array.length; index < count; index++) {
                    toMap(map, objectName + "[" + index + "]", array[index]);
                    index++;
                }
            }
        } else if (objectClass.isArray()) {
            if (objectName != null && objectName.length() != 0) {
                List list = Arrays.asList(object);
                for (int index = 0, count = list.size(); index < count; index++) {
                    toMap(map, objectName + "[" + index + "]", list.get(index));
                    index++;

                }
            }
        } else {
            try {
                PropertyDescriptor[] props = Introspector.getBeanInfo(objectClass, Object.class
                ).getPropertyDescriptors();
                for (PropertyDescriptor prop : props) {
                    try {
                        Method readMethod = prop.getReadMethod();
                        if (readMethod != null) {
                            if (!readMethod.isAccessible()) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(object);
                            if (value != null) {
                                Class<?> valueClass = value.getClass();
                                Annotation annotation = null;

                                if (value instanceof Date || value instanceof Calendar) {
                                    annotation = readMethod.getAnnotation(DateTimeFormat.class
                                    );
                                    if (annotation == null) {
                                        Field field = Reflection.findField(objectClass, prop.getName());

                                        if (field != null) {
                                            annotation = field.getAnnotation(DateTimeFormat.class
                                            );

                                        }
                                    }
                                } else if (value instanceof Long || long.class
                                        == valueClass) {
                                    annotation = readMethod.getAnnotation(NumberFormat.class
                                    );

                                    if (annotation == null) {
                                        annotation = readMethod.getAnnotation(DateTimeFormat.class
                                        );
                                        if (annotation == null) {
                                            Field field = Reflection.findField(objectClass, prop.getName());

                                            if (field != null) {
                                                annotation = field.getAnnotation(NumberFormat.class
                                                );

                                                if (annotation == null) {
                                                    annotation = field.getAnnotation(DateTimeFormat.class
                                                    );

                                                }
                                            }
                                        }
                                    }
                                } else if (value instanceof Number
                                        || byte.class
                                        == valueClass
                                        || short.class
                                        == valueClass
                                        || int.class
                                        == valueClass
                                        || long.class
                                        == valueClass
                                        || float.class
                                        == valueClass
                                        || double.class
                                        == valueClass) {
                                    annotation = readMethod.getAnnotation(NumberFormat.class
                                    );
                                    if (annotation == null) {
                                        Field field = Reflection.findField(objectClass, prop.getName());

                                        if (field != null) {
                                            annotation = field.getAnnotation(NumberFormat.class
                                            );
                                        }
                                    }
                                } else if (value instanceof Resource) {
                                    if (objectName == null || objectName.length() == 0) {
                                        map.put(prop.getName(), value);
                                    } else {
                                        map.put(objectName + "." + prop.getName(), value);
                                    }
                                    continue;
                                }

                                String text;
                                if (annotation != null) {
                                    if (annotation instanceof NumberFormat) {
                                        NumberFormatAnnotationFormatterFactory factory = new NumberFormatAnnotationFormatterFactory();
                                        Printer<Number> printer = factory.getPrinter((NumberFormat) annotation, prop.getPropertyType());
                                        text = printer.print((Number) value, LocaleContextHolder.getLocale());
                                    } else {
                                        DateTimeFormatAnnotationFormatterFactory factory = new DateTimeFormatAnnotationFormatterFactory();
                                        Printer<?> printer = factory.getPrinter((DateTimeFormat) annotation, prop.getPropertyType());
                                        if (value instanceof Date) {
                                            text = ((Printer<Date>) printer).print((Date) value, LocaleContextHolder.getLocale());
                                        } else if (value instanceof Calendar) {
                                            text = ((Printer<Calendar>) printer).print((Calendar) value, LocaleContextHolder.getLocale());
                                        } else {
                                            text = ((Printer<Long>) printer).print((Long) value, LocaleContextHolder.getLocale());
                                        }
                                    }
                                } else {
                                    text = value.toString();
                                }

                                if (objectName == null || objectName.length() == 0) {
                                    map.put(prop.getName(), text);
                                } else {
                                    map.put(objectName + "." + prop.getName(), text);
                                }
                            }
                        }
                    } catch (IllegalAccessException ex) {
                        logger.error(ex.getMessage(), ex);
                    } catch (IllegalArgumentException ex) {
                        logger.error(ex.getMessage(), ex);
                    } catch (InvocationTargetException ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            } catch (IntrospectionException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public static void destory() {
        if (threadPoolTaskExecutor != null) {
            synchronized (RestUtil.class) {
                if (threadPoolTaskExecutor != null) {
                    threadPoolTaskExecutor.destroy();
                }
            }
        }
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = createRestTemplate();
        setHeader(restTemplate, "Accept-Encoding", "gzip");
        ResponseEntity<String> rr = restTemplate.getForEntity("https://www.yooyo.com/test.json", String.class
        );
        String s = rr.getBody();
        System.out.println("sync: " + s);

        final AsyncRestTemplate async = createAsyncRestTemplate();
        RestTemplate r = (RestTemplate) Reflection.getField(async, "syncTemplate");
        setHeader(r, "Accept-Encoding", "gzip");

        ListenableFuture<ResponseEntity<String>> lt = async.getForEntity("https://www.yooyo.com/test.json", String.class, (Object) null);

        lt.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {

            @Override
            public void onSuccess(ResponseEntity<String> result) {
                System.out.println("first: " + result.getBody());
                ListenableFuture<ResponseEntity<String>> lt1 = async.getForEntity("https://www.yooyo.com/test.json", String.class, (Object) null);
                lt1.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {

                    @Override
                    public void onSuccess(ResponseEntity<String> result) {
                        System.out.println("second: " + result.getBody());
                        destory();
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        System.out.println(ex.getMessage());
                        destory();
                    }
                });
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex.getMessage());
                destory();
            }
        });

    }
}
