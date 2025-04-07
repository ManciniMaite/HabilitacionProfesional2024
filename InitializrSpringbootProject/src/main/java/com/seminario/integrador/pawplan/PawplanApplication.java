package com.seminario.integrador.pawplan;


import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@SpringBootApplication
public class PawplanApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawplanApplication.class, args);
	}
        
        @Bean
        public CorsFilter corsFilter() {            
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("http://localhost:4200/","http://localhost:4200")); // frontend origin
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // headers permitidos
            configuration.setAllowCredentials(true); // si usás cookies/session
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            configuration.setExposedHeaders(List.of("Authorization"));
            configuration.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return new CorsFilter(source);

        }
}
