package com.medilabo.gateway.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/"));

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/", "/login", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .pathMatchers("/actuator/health").permitAll()

                        .pathMatchers("/actuator/**").hasRole("ADMIN")

                        .pathMatchers("/patients/**").hasRole("USER")
                        .pathMatchers("/notes/**").hasRole("USER")
                        .pathMatchers("/risk/**").hasRole("USER")

                        .anyExchange().authenticated()
                )

                .formLogin(Customizer.withDefaults())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
                )

                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)

                .build();
    }

    @Bean
    public MapReactiveUserDetailsService users() {
        var user = User.withUsername("user").password("{noop}root").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}root").roles("ADMIN").build();
        return new MapReactiveUserDetailsService(user, admin);
    }
}
