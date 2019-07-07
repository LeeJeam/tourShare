package top.ibase4j.core.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.mapper.LockMapper;

import javax.sql.DataSource;
import java.util.ResourceBundle;

@Configuration
@ConditionalOnClass(value = {MapperScannerConfigurer.class, DataSourceTransactionManager.class})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableScheduling
public class MyBatisConfig {
    private static Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    private LockMapper lockMapper;

    private ResourceBundle config = ResourceBundle.getBundle("config/jdbc");

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnBean(DataSource.class)
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources(config.getString("mybatis.mapperLocations")));
        sessionFactory.setTypeAliasesPackage(config.getString("mybatis.typeAliasesPackage"));

        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(config.getString("mybatis.dialectType"));
        sessionFactory.setPlugins(new Interceptor[]{page});

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(Slf4jImpl.class);
        configuration.setCallSettersOnNulls(true);
        sessionFactory.setConfiguration(configuration);

        configuration.setMapUnderscoreToCamelCase(true);
        String idType = config.getString("mybatis.idType");
        GlobalConfiguration config = new GlobalConfiguration();
        config.setDbColumnUnderline(true);
        config.setSqlInjector(new AutoSqlInjector());
        if (DataUtil.isEmpty(idType)) {
            config.setIdType(IdType.AUTO.getKey());
        } else {
            config.setIdType(IdType.valueOf(idType).getKey());
        }
        sessionFactory.setGlobalConfig(config);
        return sessionFactory;
    }

    @Bean
    public MapperScannerConfigurer configurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage(config.getString("mybatis.mapperBasePackage"));
        return configurer;
    }

    @Bean
    @ConditionalOnBean(DataSource.class)
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Object setLockMapper(LockMapper lockMapper) {
        this.lockMapper = lockMapper;
        CacheUtil.setLockMapper(lockMapper);
        return lockMapper;
    }

    /**
     * 定时清除会话信息
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void cleanExpiredLock() {
        if (lockMapper != null) {
            logger.info("cleanExpiredLock");
            lockMapper.cleanExpiredLock();
        }
    }
}
