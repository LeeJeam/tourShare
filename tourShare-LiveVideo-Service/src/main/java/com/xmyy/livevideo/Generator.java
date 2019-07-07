package com.xmyy.livevideo;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成 注意：不生成service接口 注意：不生成service接口 注意：不生成service接口
 *
 * @author ShenHuaJie
 */
public class Generator {

    private static String DBIP="192.168.6.231";

    private static String DB_USER="root";

    private static String DB_PASSWORD="root";

    private static String DB="tourshare";

    private static String OUTPUTDIR="D://code";

    private static String AUTHOR="simon";

    private static String PARENT_PACKAGE="com.xmyy.livevideo";
    /**
     * 测试 run 执行 注意：不生成service接口 注意：不生成service接口 注意：不生成service接口
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUTPUTDIR);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(true);// XML 二级缓存
        gc.setBaseResultMap(false);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);
        gc.setAuthor(AUTHOR);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PASSWORD);
        dsc.setUrl("jdbc:mysql://"+DBIP+":3306/"+DB+"?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&serverTimezone=PRC&useSSL=false");
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setTablePrefix("t_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"vd_live_video"}); // 需要生成的表
        //strategy.setExclude(getExcludeTable()); // 排除生成的表
        // 自定义实体父类
        strategy.setSuperEntityClass("top.ibase4j.core.base.BaseModel");
        // 自定义实体，公共字段
        strategy.setSuperEntityColumns(
            new String[]{"id_", "enable_", "remark_", "create_by", "create_time", "update_by", "update_time"});
        // 自定义 mapper 父类
        strategy.setSuperMapperClass("top.ibase4j.core.base.BaseMapper");
        // 自定义 service 父类
        strategy.setSuperServiceClass("top.ibase4j.core.base.BaseService");
        // 自定义 service 实现类父类
        strategy.setSuperServiceImplClass("top.ibase4j.core.base.BaseServiceImpl");
        // 自定义 controller 父类
        strategy.setSuperControllerClass("top.ibase4j.core.base.BaseController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuliderModel(true);
        strategy.setLogicDeleteFieldName("enable");
        mpg.setStrategy(strategy);
        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("cfg.rpcService", true);
                map.put("cfg.restControllerStyle", true);
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PARENT_PACKAGE);
        pc.setEntity("model");
        pc.setMapper("mapper");
        pc.setXml("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("web");
        mpg.setPackageInfo(pc);
        // 放置自己项目的 src/main/resources/template 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("tpl/entity.java.vm");
        tc.setMapper("tpl/mapper.java.vm");
        tc.setXml("tpl/mapper.xml.vm");
        tc.setService("tpl/service.java.vm");
        tc.setServiceImpl("tpl/serviceImpl.java.vm");
        tc.setController("tpl/controller.java.vm");
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
    }

    private static String[] getExcludeTable(){
        return new String[]{
                "QRTZ_BLOB_TRIGGERS",
                "QRTZ_CALENDARS",
                "QRTZ_CRON_TRIGGERS",
                "QRTZ_FIRED_TRIGGERS",
                "QRTZ_JOB_DETAILS",
                "QRTZ_LOCKS",
                "QRTZ_PAUSED_TRIGGER_GRPS",
                "QRTZ_SCHEDULER_STATE",
                "QRTZ_SIMPLE_TRIGGERS",
                "QRTZ_SIMPROP_TRIGGERS",
                "QRTZ_TRIGGERS",
                "sys_article",
                "sys_dept",
                "sys_dic",
                "sys_email",
                "sys_email_config",
                "sys_email_template",
                "sys_event",
                "sys_lock",
                "sys_menu",
                "sys_msg",
                "sys_msg_config",
                "sys_news",
                "sys_notice",
                "sys_param",
                "sys_role",
                "sys_role_menu",
                "sys_session",
                "sys_unit",
                "sys_user",
                "sys_user_menu",
                "sys_user_role",
                "sys_user_thirdparty",
        };
    }
}
