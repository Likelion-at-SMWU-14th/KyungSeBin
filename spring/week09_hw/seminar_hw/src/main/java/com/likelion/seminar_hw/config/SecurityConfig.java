package com.likelion.seminar_hw.config;

import com.likelion.seminar_hw.jwt.JwtAuthenticationFilter;
import com.likelion.seminar_hw.jwt.JwtTokenProvider;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTokenProvider jwtTokenProvider,
            UserDetailsService userDetailsService
    ) throws Exception {

        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(
                        jwtTokenProvider,
                        userDetailsService
                );

        http
                // Postman + 헤더/JSON 토큰 방식
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(
                                DispatcherType.ERROR
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/signup",
                                "/api/auth/login",
                                "/api/auth/refresh"
                        ).permitAll()

                        .requestMatchers(
                                "/h2-console",
                                "/h2-console/**"
                        ).permitAll()

                        .anyRequest().authenticated()
                )


                .headers(headers ->
                        headers.frameOptions(frame ->
                                frame.sameOrigin()
                        )
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                (request, response, e) -> {
                                    response.setStatus(401);
                                    response.setContentType(
                                            "application/json;charset=UTF-8"
                                    );
                                    response.getWriter().write(
                                            "{\"message\":\"인증이 필요합니다.\"}"
                                    );
                                }
                        )
                        .accessDeniedHandler(
                                (request, response, e) -> {
                                    response.setStatus(403);
                                    response.setContentType(
                                            "application/json;charset=UTF-8"
                                    );
                                    response.getWriter().write(
                                            "{\"message\":\"접근 권한이 없습니다.\"}"
                                    );
                                }
                        )
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}