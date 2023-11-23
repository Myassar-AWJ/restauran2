package com.restaurant.restaurantdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .requestMatchers(new AntPathRequestMatcher("/", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/home", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/restaurants/**", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/about", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/css/**", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/js/**", "GET")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/images/**", "GET")).permitAll()
//                        .anyRequest().authenticated())
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll())
//                .httpBasic(withDefaults());
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().permitAll()) // Allow all requests
                .csrf(csrf -> csrf.disable()); // Disable CSRF protection

        return http.build();
    }
}
