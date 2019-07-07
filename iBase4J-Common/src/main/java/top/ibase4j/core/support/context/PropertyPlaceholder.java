/**
 *
 */
package top.ibase4j.core.support.context;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import top.ibase4j.core.Constants;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.SecurityUtil;

/**
 *
 * @author ShenHuaJie
 * @version 2017年12月1日 上午10:48:48
 */
public class PropertyPlaceholder extends PropertyPlaceholderConfigurer implements ApplicationContextAware {
    private List<String> decryptProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            if (decryptProperties != null && decryptProperties.contains(keyStr)) {
                String dkey = props.getProperty("druid.key");
                dkey = DataUtil.isEmpty(dkey) ? Constants.DB_KEY : dkey;
                value = SecurityUtil.decryptDes(value, dkey.getBytes());
                props.setProperty(keyStr, value);
            }
            PropertiesUtil.getProperties().put(keyStr, value);
        }
    }

    /**
     * @param decryptProperties
     *            the decryptPropertiesMap to set
     */
    public void setDecryptProperties(List<String> decryptProperties) {
        this.decryptProperties = decryptProperties;
    }

    public static void main(String[] args) {
        String encrypt = SecurityUtil.encryptDes("buzhidao", Constants.DB_KEY.getBytes());
        System.out.println(encrypt);
        System.out.println(SecurityUtil.decryptDes(encrypt, Constants.DB_KEY.getBytes()));
    }
}
