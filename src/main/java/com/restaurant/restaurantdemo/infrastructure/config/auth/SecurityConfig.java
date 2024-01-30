
package com.restaurant.restaurantdemo.infrastructure.config.auth;
import com.restaurant.restaurantdemo.application.service.jwt.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private UserDetailsService userDetailsService;

    // My enpdoints start from /v1 so this pattern is ok for me
    private static final String API_URL_PATTERN = "/api/auth/**";

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
//                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(API_URL_PATTERN).permitAll()
//                        .anyRequest().authenticated())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//
//        return http.build();
//    }



    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http,
                                                      HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrfConfigurer ->
                csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN),
                        PathRequest.toH2Console()));

        http.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(mvcMatcherBuilder.pattern(API_URL_PATTERN)).permitAll()
                        //This line is optional in .authenticated() case as .anyRequest().authenticated()
                        //would be applied for H2 path anyway
                        .requestMatchers(PathRequest.toH2Console()).authenticated()
                        .anyRequest().authenticated()
        );

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // Additional configurations (like CORS) can be added here
}
