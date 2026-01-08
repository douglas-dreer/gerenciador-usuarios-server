package io.github.gabitxt.gerenciamentousuario.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:Gerenciamento de Usuários}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gerenciamento de Usuários")
                        .version("1.0.0")
                        .description("""
                                API RESTful para gerenciamento de usuários e endereços.
                                
                                ## Funcionalidades
                                
                                - **Usuários**: CRUD completo de usuários
                                - **Endereços**: Cadastro de endereços com integração ViaCEP
                                
                                ## Integração ViaCEP
                                
                                Ao cadastrar um endereço com CEP válido, os dados são automaticamente 
                                preenchidos via API ViaCEP (logradouro, bairro, cidade, estado).
                                """)
                        .contact(new Contact()
                                .name("gabitxt")
                                .url("https://github.com/Gabxt28")
                                .email("gabitxt@github.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento")))
                .tags(List.of(
                        new Tag()
                                .name("Usuários")
                                .description("Operações relacionadas a usuários"),
                        new Tag()
                                .name("Endereços")
                                .description("Operações relacionadas a endereços")));
    }
}

