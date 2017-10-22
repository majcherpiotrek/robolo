package com.ksm.robolo.roboloapp.restsecurity.config;

import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationEntryPoint;
import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationFailureHandler;
import com.ksm.robolo.roboloapp.restsecurity.RESTAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;

    private final RESTAuthenticationFailureHandler authenticationFailureHandler;

    private final RESTAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(RESTAuthenticationEntryPoint authenticationEntryPoint, RESTAuthenticationFailureHandler authenticationFailureHandler, RESTAuthenticationSuccessHandler authenticationSuccessHandler, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider());
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
                //TODO only for development!!!
                .csrf().disable();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers("/login")
                .and()
                .ignoring().antMatchers("/register")
                .and()
                .ignoring().antMatchers("/confirm/**")
                .and()
                .ignoring().antMatchers("/password/retrieve/**")
                .and()
                //TODO only for development!!!!
                .ignoring().antMatchers("/h2-console/**")
                .and()
                .ignoring().antMatchers("/swagger-ui.html/**")
                .and()
                .ignoring().antMatchers("/swagger-resources/**")
                .and()
                .ignoring().antMatchers("/v2/api-docs/**")
                .and()
                .ignoring().antMatchers("/**/favicon.ico")
                .and()
                .ignoring().antMatchers("/webjars/**");
    }
}
