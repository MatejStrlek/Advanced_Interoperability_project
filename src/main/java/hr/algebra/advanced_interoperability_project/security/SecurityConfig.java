package hr.algebra.advanced_interoperability_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers ->
                        headers.defaultsDisabled()
                                .addHeaderWriter(new StaticHeadersWriter(
                                        "X-Content-Type-Options", "nosniff"
                                ))
                        )
                /*.headers(headers ->
                        headers.contentTypeOptions(contentTypeOptionsConfig -> contentTypeOptionsConfig.disable()))
                        */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/rest/mobiles",
                                "/rest/mobiles/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}