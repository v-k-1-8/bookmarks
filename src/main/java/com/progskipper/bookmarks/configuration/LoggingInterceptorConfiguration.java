package com.progskipper.bookmarks.configuration;

import com.progskipper.bookmarks.interceptor.ApiLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoggingInterceptorConfiguration implements WebMvcConfigurer {
    // Configuring Endpoint Interception

    private final ApiLoggingInterceptor apiLoggingInterceptor;

    public LoggingInterceptorConfiguration(ApiLoggingInterceptor apiLoggingInterceptor) {
        this.apiLoggingInterceptor = apiLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLoggingInterceptor);
    }
}
