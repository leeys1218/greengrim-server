package com.greengrim.green.common.config;

import com.greengrim.green.common.auth.CustomAccessDeniedHandler;
import com.greengrim.green.common.jwt.JwtAuthenticationFilter;
import com.greengrim.green.common.jwt.JwtExceptionFilter;
import com.greengrim.green.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((formLogin) ->
                        formLogin.disable())
                .httpBasic((httpBasic) ->
                        httpBasic.disable())
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                        .requestMatchers("/visitor/**").hasAnyRole("VISITOR", "MEMBER", "MANAGER")
                        .requestMatchers("/member/**").hasAnyRole("MEMBER", "MANAGER")
                        .requestMatchers("/manager/**").hasRole("MANAGER")
                        .anyRequest().permitAll()
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(new JwtExceptionFilter(),
                        JwtAuthenticationFilter.class);

        return http.build();
    }
}
