package com.krieger.digital.apigateway.config;

import com.krieger.digital.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))

                .route("document-service", r -> r.path("/v1/document-service/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://document-service"))

                .route("notification-service", r -> r.path("/v1/notification/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://notification-service"))

                .route("auth-service", r -> r.path("/v1/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }
}