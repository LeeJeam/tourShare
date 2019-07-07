package top.ibase4j.core.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.ibase4j.core.base.provider.BaseProvider;
import top.ibase4j.core.support.dbcp.ChooseDataSource;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@ConditionalOnClass(DruidDataSource.class)
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    public static class EnableAspect implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return DataUtil.isNotEmpty(PropertiesUtil.getString("druid.reader.url"));
        }
    }

    public Object dataSourceAspect() {
        String provider = PropertiesUtil.getString("dataSourceAspect.provider");
        boolean single = isSingle();
        if (!single && DataUtil.isNotEmpty(provider)) {
            if (Boolean.valueOf(provider)) {
                return new top.ibase4j.core.support.dbcp.provider.DataSourceAspect();
            }
            return null;
        }
        return null;
    }

    @Bean
    @Conditional(EnableAspect.class)
    @ConditionalOnBean(BaseProvider.class)
    public top.ibase4j.core.support.dbcp.provider.DataSourceAspect providerAspect() {
        logger.info("top.ibase4j.core.support.dbcp.provider.DataSourceAspect");
        return new top.ibase4j.core.support.dbcp.provider.DataSourceAspect();
    }

    @Bean
    public DataSource dataSource() {
        boolean single = isSingle();
        DataSource write = getDataSource(false);
        Map<Object, Object> targetDataSources = InstanceUtil.newHashMap("write", write);
        if (!single) {
            DataSource read = getDataSource(true);
            targetDataSources.put("read", read);
        }

        ChooseDataSource dataSource = new ChooseDataSource();
        dataSource.setDefaultTargetDataSource(write);
        dataSource.setTargetDataSources(targetDataSources);
        Map<String, String> method = InstanceUtil.newHashMap();
        method.put("write", ",add,insert,create,update,delete,remove,");
        method.put("read", ",get,select,count,list,query,");
        dataSource.setMethodType(method);
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<StatViewServlet>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    private DataSource getDataSource(boolean readOnly) {
        DruidDataSource datasource = new DruidDataSource();
        if (readOnly) {
            datasource.setUrl(PropertiesUtil.getString("druid.reader.url"));
            datasource.setUsername(PropertiesUtil.getString("druid.reader.username"));
            datasource.setPassword(PropertiesUtil.getString("druid.reader.password"));
        } else if (DataUtil.isNotEmpty(PropertiesUtil.getString("druid.writer.url"))
                && DataUtil.isNotEmpty(PropertiesUtil.getString("druid.writer.username"))) {
            datasource.setUrl(PropertiesUtil.getString("druid.writer.url"));
            datasource.setUsername(PropertiesUtil.getString("druid.writer.username"));
            datasource.setPassword(PropertiesUtil.getString("druid.writer.password"));
        }
        // configuration
        Properties properties = new Properties();
        properties.putAll(PropertiesUtil.getProperties());
        datasource.configFromPropety(properties);

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);

        return datasource;
    }

    private StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);

        return statFilter;
    }

    private WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();

        // 允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }

    private boolean isSingle() {
        try {
            return DataUtil.isEmpty(PropertiesUtil.getString("druid.reader.url"));
        } catch (Exception e) {
            return false;
        }
    }
}
