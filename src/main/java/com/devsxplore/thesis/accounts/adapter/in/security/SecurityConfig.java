package com.devsxplore.thesis.accounts.adapter.in.security;

import com.devsxplore.thesis.accounts.application.config.AccountSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableConfigurationProperties(AccountSecurityProperties.class)
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthService oAuthService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity chainBuilder) {
        return chainBuilder.authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/", "/error", "/css/**", "/img/**", "/.well-known/**", "/js/**").permitAll()
                        .requestMatchers("/supervisor/register", "/student/register").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/supervisor/**").hasRole("SUPERVISOR")
                        .requestMatchers("/topic/**").hasRole("SUPERVISOR")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .anyRequest().authenticated()
                )
                .logout(l -> l.
                        logoutSuccessUrl("/"))
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuthService)))
                .build();
    }
}
