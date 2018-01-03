package com.yangxvhao.proxy;

import com.yangxvhao.proxy.product.AbstractProduct;
import com.yangxvhao.proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yangxvhao
 * @date 18-1-3.
 */
@SpringBootApplication
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application implements CommandLineRunner {

    @Autowired
    AbstractProduct abstractProduct;

    @Autowired
    ProxyService proxyService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Override
    public void run(String... strings) throws Exception {
        abstractProduct.produce();
    }
}
