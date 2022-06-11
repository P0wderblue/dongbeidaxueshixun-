package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CrossConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对于任意路径(/**)都要配置跨域
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置跨域
        corsConfiguration.addAllowedHeader("*"); // Access-Control-Expose-Headers
        corsConfiguration.addAllowedMethod("*"); // Access-Control-Allow-Methods
        corsConfiguration.addAllowedOriginPattern("*"); // Access-Control-Allow-origin
        corsConfiguration.setAllowCredentials(true); // Access-Control-Allow-Credentials

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
