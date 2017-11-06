package com.ksm.robolo.roboloapp.security;

import com.google.common.collect.ImmutableList;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(RESTAuthenticationEntryPoint authenticationEntryPoint, 
    						UserDetailsService userDetailsService, 
    						BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	 auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.cors().and().csrf().disable().authorizeRequests()
    		.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
    		.antMatchers(HttpMethod.GET, SecurityConstants.REGISTRATION_CONFIRM_URL).permitAll()
    		.antMatchers(HttpMethod.GET, SecurityConstants.PASSWORD_RETRIEVE_URL).permitAll()
    		.antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RETRIEVE_URL).permitAll()
    		.anyRequest().authenticated()
    		.and()
    		.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
    		.and()
    		.addFilter(new JWTAuthenticationFilter(authenticationManager()))
    		.addFilter(new JWTAuthorizationFilter(authenticationManager()));
    		
    	//TODO
    	// this disables session creation on Spring Security
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**")
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
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
    
}
