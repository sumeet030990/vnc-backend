package com.vnc.officeManagementApp.Config;

import com.vnc.officeManagementApp.Jwt.AuthEntryPointJwt;
import com.vnc.officeManagementApp.Jwt.AuthTokenFilter;
import com.vnc.officeManagementApp.Services.UserAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        DataSource dataSource;

        @Autowired
        private AuthEntryPointJwt unauthorizedHandler;

        @Autowired
        UserAuthService userAuthService;

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
                return new AuthTokenFilter();
        }

        @Bean
        public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/login", "/register")
                                .permitAll() // Allow access to login and register
                                .anyRequest().authenticated()) // All other requests need authentication
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use
                                                                                                         // stateless
                                                                                                         // sessions
                                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
                                .addFilterBefore(authenticationJwtTokenFilter(),
                                                UsernamePasswordAuthenticationFilter.class); // Add JWT filter

                http.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
                daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
                daoAuthenticationProvider.setUserDetailsService(userAuthService);

                return daoAuthenticationProvider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(); // Use BCrypt for encoding
        }
}