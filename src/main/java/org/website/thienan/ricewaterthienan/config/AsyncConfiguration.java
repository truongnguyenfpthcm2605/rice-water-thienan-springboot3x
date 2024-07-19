package org.website.thienan.ricewaterthienan.config;

import java.util.concurrent.Executor;
import java.util.logging.Logger;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Executor getAsyncExecutor() {
        logger.info("getAsyncExecutor");
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(4);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setThreadNamePrefix("Thread Async-: ");
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        logger.info("getAsyncUncaughtExceptionHandler");
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
