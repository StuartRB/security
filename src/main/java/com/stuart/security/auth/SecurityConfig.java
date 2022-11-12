package com.stuart.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

    public SecurityConfig(ApiKeyAuthenticationProvider apiKeyAuthenticationProvider) {
        this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .addFilter(filter())
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
//                .authorizeRequests(auth -> {
//                    auth.antMatchers("/").permitAll();
//                    auth.antMatchers("/user").hasRole("USER");
//                    auth.antMatchers("/admin").hasRole("ADMIN");
//                })
//                .antMatchers("/user").hasAnyRole()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .anyRequest().authenticated()

                .and()
                .build();
    }

    private GenericFilterBean filter() {
        var filter = new ApiKeyFilter();
        filter.setAuthenticationManager(new ProviderManager(apiKeyAuthenticationProvider));
        return filter;
    }
}
