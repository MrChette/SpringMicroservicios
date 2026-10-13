package com.ecommerce.order_service.config;

import com.ecommerce.order_service.service.client.InventoryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {

    // Creamos un bean para optimizar la creación de instancias de WebClient y poder inyectarlas en los servicios que lo necesiten.
    // Configuramos la URL base del WebClient para que apunte al servicio de inventario.
    @Bean
    public WebClient webClientBuilder() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }


    // Creamos un bean para el cliente de inventario, utilizando el WebClient previamente configurado.
    @Bean
    public InventoryClient inventoryClient(WebClient webClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();

        return factory.createClient(InventoryClient.class);
    }


}
