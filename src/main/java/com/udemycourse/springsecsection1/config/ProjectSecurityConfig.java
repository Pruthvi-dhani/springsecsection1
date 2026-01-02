package com.udemycourse.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests -> requests.requestMatchers(
                "/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/myNotices", "/contact", "/error", "/ping").permitAll()
        );
        httpSecurity.formLogin(flc -> flc.disable());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
