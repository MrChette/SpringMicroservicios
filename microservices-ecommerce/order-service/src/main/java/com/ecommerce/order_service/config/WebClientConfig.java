package com.ecommerce.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // Creamos un bean para optimizar la creación de instancias de WebClient y poder inyectarlas en los servicios que lo necesiten.
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


}
