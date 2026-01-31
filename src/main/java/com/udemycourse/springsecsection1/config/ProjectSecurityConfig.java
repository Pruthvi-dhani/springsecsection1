package com.udemycourse.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(requests -> requests.requestMatchers(
                "/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/myNotices", "/contact", "/error", "/ping").permitAll()
        );
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$svkCQYDC8sdtJaCuoiSMXOJfWrgt1tmwnfJ0yg5vk2/eGYcrpJFGa").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$k9N6.VShRRYAAuiia9/Rieus8QwL0gNWlZbLzCf8x2ma/zWoXbRdO").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoderService() {
        // uses bcrypt password encoder by default
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
