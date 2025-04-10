package com.example.taskmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    // LocaleResolver määrittelee, miten sovellus käsittelee kieltä.
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("fi-FI")); // Oletuskieli on suomi
        return localeResolver;
    }

    // LocaleChangeInterceptor kuuntelee URL-parametrejä, 
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // Kieliparametri on 'lang'
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Lisää interceptori, joka kuuntelee kielenvaihtoparametria
        registry.addInterceptor(localeChangeInterceptor());
    }
}
