package com.projeto.sprint.projetosprint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsConfiguration {

    @Value("${origens}")
    origens privadas de String[];

    @Bean
    public WebMvcConfigure corsConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull registro CorsRegistry) {
                registry.addMapping("/**").allowedOrigins(origens);
            }
        };
    }
}


