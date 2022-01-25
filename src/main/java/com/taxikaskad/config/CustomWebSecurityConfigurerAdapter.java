package com.taxikaskad.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Value("${qiwi.user}")
    private String userQiwi;

    @Value("${qiwi.password}")
    private String passwordQiwi;

    @Value("${sber.user}")
    private String userSber;

    @Value("${sber.password}")
    private String passwordSber;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userQiwi).password(passwordEncoder().encode(passwordQiwi))
                .authorities("ROLE_QIWI_USER")
                .and()
                .withUser(userSber).password(passwordEncoder().encode(passwordSber))
                .authorities("ROLE_SBER_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/sber-service/**").hasRole("SBER_USER")
                .antMatchers("/qiwi-service/**").hasRole("QIWI_USER")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
