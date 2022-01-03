package com.prathickya.blogApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Overridden this method
    //to configure BASIC authentication
    //with configured username and password in the properties file
    //This overridden method helps developers to configure Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {  //override authorization
        //Username and password can be from application.properties file or from InMemory or JDBC
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()  //Permit all GET without authentication
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();  //Enables Basic authentication (by default Form based Authentication)
    }

    //Instead of users configured in the application.properties file
    //Have added few inmemory users in this method
    //Now we can remove users from the application.properties file
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        final UserDetails kaarthick = User.builder().username("kaarthick").password(passwordEncoder().encode("kaarthick")).roles("USER").build();
        final UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(kaarthick, admin);
    }

    /*
    Another way of doing inMemoryAuthentication
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception { //override authentication
            auth.inMemoryAuthentication()
                    .withUser(User.builder().username("kaarthick").password(passwordEncoder().encode("kaarthick")).roles("USER").build());
            auth.inMemoryAuthentication()
                    .withUser(User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build());

        }
    */
}
