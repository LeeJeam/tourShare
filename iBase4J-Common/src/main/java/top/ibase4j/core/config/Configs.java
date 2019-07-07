package top.ibase4j.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import top.ibase4j.core.Constants;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;
import top.ibase4j.core.util.SecurityUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置，本项目暂时用不到
 *
 * @author ShenHuaJie
 * @since 2017年8月15日 上午9:03:17
 */
@Configuration
public class Configs implements EnvironmentPostProcessor, Ordered {
    private static Logger logger = LoggerFactory.getLogger(Configs.class);

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        String[] profiles = environment.getActiveProfiles();
        Properties props = getConfig(profiles);
        propertySources.addLast(new PropertiesPropertySource("thirdEnv", props));
        for (PropertySource<?> propertySource : propertySources) {
            if (propertySource.getSource() instanceof Map) {
                Map map = (Map) propertySource.getSource();
                for (Object key : map.keySet()) {
                    String keyStr = key.toString();
                    Object value = map.get(key);
                    if ("druid.password,druid.writer.password,druid.reader.password".contains(keyStr)) {
                        String dkey = (String) map.get("druid.key");
                        dkey = DataUtil.isEmpty(dkey) ? Constants.DB_KEY : dkey;
                        value = SecurityUtil.decryptDes(value.toString(), dkey.getBytes());
                        map.put(key, value);
                    }
                    PropertiesUtil.getProperties().put(keyStr, value.toString());
                }
            }
        }
        System.out.println("* iBase4J Read configuration file finished.");
        logger.info("* iBase4J Read configuration file finished.");
    }

    @Override
    public int getOrder() {
        return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
    }

    // 加载配置文件
    private Properties getConfig(String[] profiles) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resouceList = InstanceUtil.newArrayList();
        addResources(resolver, resouceList, "classpath*:config/*.properties");
        if (profiles != null) {
            for (String p : profiles) {
                if (DataUtil.isNotEmpty(p)) {
                    p += "/";
                }
                addResources(resolver, resouceList, "classpath*:config/" + p + "*.properties");
            }
        }
        try {
            PropertiesFactoryBean config = new PropertiesFactoryBean();
            config.setLocations(resouceList.toArray(new Resource[]{}));
            config.afterPropertiesSet();
            return config.getObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 加载配置文件
    private void addResources(PathMatchingResourcePatternResolver resolver, List<Resource> resouceList, String path) {
        try {
            Resource[] resources = resolver.getResources(path);
            Collections.addAll(resouceList, resources);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static void main(String[] args) {
        String encrypt = SecurityUtil.encryptDes("buzhidao", Constants.DB_KEY.getBytes());
        System.out.println(encrypt);
        System.out.println(SecurityUtil.decryptDes(encrypt, Constants.DB_KEY.getBytes()));
    }
}
