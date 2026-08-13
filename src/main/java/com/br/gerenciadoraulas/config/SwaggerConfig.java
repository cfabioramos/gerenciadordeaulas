package com.br.gerenciadoraulas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Gerenciador de Aulas")
                        .version("1.0")
                        .description("Documentação completa da API do Gerenciador de Aulas.\n\n" +
                                "Esta API permite o gerenciamento de ciclos, programas de aulas, aulas, alunos, matrículas e presenças, " +
                                "facilitando o controle acadêmico.")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("contato@gerenciadoraulas.com.br")));
    }
}
