package com.pp.api.configuration.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.api.filter.MDCLoggingFilter;
import com.pp.api.filter.PersistenceLoggingFilter;
import com.pp.api.service.RequestResponseLoggingService;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.REQUEST;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> mdcLoggingFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setOrder(MDCLoggingFilter.ORDER);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(new MDCLoggingFilter());
        filterRegistrationBean.setDispatcherTypes(
                REQUEST,
                ERROR
        );

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> persistenceLoggingFilter(
            RequestResponseLoggingService requestResponseLoggingService,
            ObjectMapper objectMapper
    ) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setOrder(PersistenceLoggingFilter.ORDER);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setFilter(
                new PersistenceLoggingFilter(
                        requestResponseLoggingService,
                        objectMapper
                )
        );

        return filterRegistrationBean;
    }

}
