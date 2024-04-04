package com.example.cslocalspringdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Slf4j
@RestController
@SpringBootApplication
@EnableSwagger2
public class CsLocalSpringDemoApplication {

    public static void main(String[] args) throws UnknownHostException {
        // 忽略非法反射警告
        disableAccessWarnings();
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CsLocalSpringDemoApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        log.info("------------------------------------------------------------------------");
        log.info("启动成功!");
        log.info("Server-Url:{}://{}:{}/", "http", InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));
        log.info("------------------------------------------------------------------------");

        //测试数据库是否连接成功
        isSuccessYunSql();
    }

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.profiles.active}")
    private String profilesActive;

    /**
     * 健康检查
     */
    @RequestMapping("/health")
    @ResponseBody
    public String get() {
        return applicationName + " is ok, environment is " + profilesActive;
    }

    private static void isSuccessYunSql() {
        String serverAddress = "120.26.13.162";
        int serverPort = 3306;
        try (Socket socket = new Socket(serverAddress, serverPort)) {
            log.info("连接云服务器成功！" + socket);
        } catch (Exception e) {
            log.info("连接云服务器失败：" + e.getMessage());
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
