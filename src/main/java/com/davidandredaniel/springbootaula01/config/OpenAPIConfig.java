
package com.davidandredaniel.springbootaula01.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI configurarDocumentacao() {

        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestão Comercial API")
                        .description(
                                "API REST para gestão de produtos, " +
                                        "categorias, compras, vendas e stock."
                        )
                        .version("1.0.0")
                );
    }
}
