package com.iPhoneMarket.iPhoneMarket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/about", "/profile/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_processing")
                .failureUrl("/login?error")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();



        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());

    }
}
