package org.website.thienan.ricewaterthienan.config.rateLimitConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final RateLimitingInterceptor rateLimitingInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addInterceptors");
        registry.addInterceptor(rateLimitingInterceptor);
    }
}