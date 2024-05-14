package com.example.cslocalspringdemo;

import com.example.cslocalspringdemo.common.annotation.UniqueNameGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;

//1.方法一
@SpringBootApplication
//2.方法二
//@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
//1.扫描的mapper包路径 方法一 （待测）
// @MapperScan(value = "com.example.cslocalspringdemo.**.mapper", nameGenerator = UniqueNameGenerator.class)
//2.扫描的mapper包路径 方法二
//@Import(MyBatisConfig.class)
//3.在 application.properties 配置
@ComponentScan(nameGenerator = UniqueNameGenerator.class)
//启用缓存
//@EnableCaching
//使用log
@Slf4j
//定时任务
@EnableScheduling
//开启swagger
@EnableSwagger2
public class CsLocalSpringDemoApplication {

    public static void main(String[] args) {
        // 忽略非法反射警告
        disableAccessWarnings();
        SpringApplication.run(CsLocalSpringDemoApplication.class, args);
        log.info("本地测试系统服务启动成功...");

        //测试本地数据库是否连接成功
        isSuccessLocalSql();
    }

    private static void isSuccessLocalSql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.info("Unable to load driver class.");
            e.printStackTrace();
            return;
        }
        // 建立数据库连接
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs_local_spring_demo", "root", "songyp8023");
            log.info("<===== Connected to the database.");

            Statement statement = connection.createStatement();
            String sql = "select * from cs_local_demo";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("user_name");
                String address = resultSet.getString("user_age");
                log.info("cs_local_demo表的id=" + id + " userName=" + name + " userAge=" + address);
            }
        } catch (SQLException e) {
            log.info("<===== Unable to connect to the database.");
            e.printStackTrace();
        } finally {
            // 关闭数据库连接
            if (connection != null) {
                log.info("Connected to the database finally. =====>");
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("Failed to connect to the database finally. =====>");
            }
        }
    }

    /**
     * 忽略非法反射警告 适用于jdk11
     */
    @SuppressWarnings("unchecked")
    public static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile =
                    unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }

}
