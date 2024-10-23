package com.vnc.officeManagementApp.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    /**
     * SecurityFilterChain defines the security configuration for HTTP requests.
     * It allows public access to `/api/register/` and `/api/login/`, while requiring authentication for all other endpoints.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("In securityFilterChain");
        // Disable CSRF as we are working with JWT, which handles cross-site requests.
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // CORS configuration
//        httpSecurity.cors();  // Make sure you have a CorsConfigurationSource bean defined

        // Define URL authorization rules
        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("/api/register/**", "/api/login/**")   // Publicly accessible endpoints
                .permitAll()  // Allow access without authentication
                .anyRequest()  // All other requests
                .authenticated());  // Require authentication

        // Set session management to stateless as we're using JWT tokens
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Set the custom authentication provider and JWT filter
        httpSecurity.authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Build the security configuration
        return httpSecurity.build();
    }
}
