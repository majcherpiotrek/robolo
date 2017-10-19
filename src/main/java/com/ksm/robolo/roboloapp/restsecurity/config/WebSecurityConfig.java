package com.ksm.robolo.roboloapp.restsecurity.config;

import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationEntryPoint;
import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationFailureHandler;
import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;

    private final RESTAuthenticationFailureHandler authenticationFailureHandler;

    private final RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(RESTAuthenticationEntryPoint authenticationEntryPoint, RESTAuthenticationFailureHandler authenticationFailureHandler, RESTAuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .formLogin()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                    .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers("/login")
                .and()
                .ignoring().antMatchers("/register")
                .and()
                .ignoring().antMatchers("/register/confirm")
                .and()
                .ignoring().antMatchers("/h2-console/**");
    }
}
