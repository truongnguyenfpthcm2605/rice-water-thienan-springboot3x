package org.website.thienan.ricewaterthienan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class RiceWaterThienanApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiceWaterThienanApplication.class, args);
    }
}
