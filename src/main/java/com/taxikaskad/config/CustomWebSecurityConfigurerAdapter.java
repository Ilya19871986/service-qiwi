package com.taxikaskad.config;

import lombok.extern.slf4j.Slf4j;

//@Configuration
//@EnableWebSecurity
@Slf4j
public class CustomWebSecurityConfigurerAdapter /*extends WebSecurityConfigurerAdapter*/ {

//    @Value("${qiwi.user}")
//    private String user;
//
//    @Value("${qiwi.password}")
//    private String password;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(user).password(passwordEncoder().encode(password))
//                .authorities("ROLE_QIWI_USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
