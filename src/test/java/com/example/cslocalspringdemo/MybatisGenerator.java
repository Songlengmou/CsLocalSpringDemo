package com.example.cslocalspringdemo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Song
 * @Desc: 代码生成器
 * @Data: 2023/11/8
 */
public class MybatisGenerator {
    //获取 项目绝对路径
    private static String canonicalPath = "";
    private static String DBURL = "jdbc:mysql://localhost:3306/cs_local_spring_demo?useUnicode=true&characterEncoding=utf8";
    private static String DBUSER = "root";
    private static String DBPWD = "songyp8023";
    private static String[] TABLES = new String[]{"cs_local_demo_detail"};
    private static String PACKAGE = "";

    public static void main(String[] args) {
        //获取项目路径
        try {
            canonicalPath = new File("").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("canonicalPath=" + canonicalPath);
        String filePath = canonicalPath + "/" + PACKAGE + "/src/test/";

        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("Song");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setOutputDir(filePath + "java");//代码生成路径
        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setOpen(false);//生成后打开文件夹
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                //默认会把日期类型 转为LocalDateTime ，在查询的时候会报错，这里改为Date
                String t = fieldType.toLowerCase();
                if (t.contains("date") || t.contains("time") || t.contains("year")) {
                    return DbColumnType.LOCAL_DATE_TIME;
                } else {
                    return super.processTypeConvert(gc, fieldType);
                }
            }
        });
        //数据库连接配置
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver"); //mysql8.0使用
        dsc.setUsername(DBUSER);
        dsc.setPassword(DBPWD);
        dsc.setUrl(DBURL);
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        //strategy.setTablePrefix("tb_");// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//采用驼峰映射
        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
        strategy.setInclude(TABLES); // 需要生成的表.如果需要生成所有的, 注释掉此行就可以
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.template");// 自定义包路径
        pc.setController("controller");// 这里是控制器包名，默认 web
        pc.setMapper("mapper");// 设置Mapper包名，默认mapper
        pc.setService("service");// 设置Service包名，默认service
        pc.setEntity("entity");// 设置Entity包名，默认entity,继承的父类  已序列化
        pc.setXml("mapper.xml");// 设置Mapper XML包名，默认mapper.xml
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        // 调整 xml 生成目录演示
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return filePath + "/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        // 执行生成
        mpg.execute();
        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
