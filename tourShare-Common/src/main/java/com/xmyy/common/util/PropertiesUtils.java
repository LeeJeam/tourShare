package com.xmyy.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 配置文件读取工具<br>
 *
 * @author wangzejun
 * @time 2018年 09月02日 15:42:54
 * @since 1.0.0
 */
public final class PropertiesUtils {

    public static Properties getPoperties(InputStream in) throws IOException {
        Properties p =new Properties();
        p.load(in);
        return p;
    }
    public static Properties getPropertiesByPath(String path) throws IOException{
        InputStream resourceAsStream = PropertiesUtils.class.getResourceAsStream(path);
        Properties p =new Properties();
        p.load(resourceAsStream);
        return p;
    }

    public static Map<String,String> getMapByPath(String path) throws IOException{
        Map<String,String> propMap=new LinkedHashMap<String,String>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream resourceAsStream = loader.getResourceAsStream(path);
        Properties prop =new Properties();
        prop.load(resourceAsStream);
        Iterator<Map.Entry<Object, Object>> it=prop.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Object, Object> entry=it.next();
            String key = (String) entry.getKey();
            String value = (String)entry.getValue();
            propMap.put(key, value);
        }
        return propMap;
    }

}