package fr.sboivin.springdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String encodePassword(String clearPassword) {
        return passwordEncoder().encode(clearPassword);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        http.authorizeRequests().antMatchers("/login", "/css/**", "/img/**").permitAll();
        http.authorizeRequests().antMatchers("/","/**/list","/profils/**").access("hasRole('ADMIN') or hasRole('USER')");
        http.authorizeRequests().anyRequest().hasRole("ADMIN");
        http.csrf().disable();
    }

}
