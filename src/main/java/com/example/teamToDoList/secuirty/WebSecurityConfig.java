package com.example.teamToDoList.secuirty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




    @Configuration //to configration the  HttpSecurity
    @EnableWebSecurity//to enable the  HttpSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  //

        @Autowired // to acces to rebo methode without create anstans
        userDetalesServiseimp userDetailsService;

        @Bean// to accec to any class methods without create instans
        public PasswordEncoder getPasswordEncoder(){ // auto created
            PasswordEncoder encoder = new BCryptPasswordEncoder();// to select the encode

            return encoder;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().disable().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll() // allow to go to in without login
                    .antMatchers("/signup").permitAll()// allow to go to in without login
                    .antMatchers("/").permitAll()// allow to go to in without login
                    .anyRequest().authenticated() // any ather page need login
                    .and()
                    .formLogin() // in login form
                    .loginPage("/login") // the GitMapping rout in controller of login
                    .loginProcessingUrl("/perform_login") // the action of login form
                    .defaultSuccessUrl("/myprofile")
                    .failureUrl("/login")
                    .and()
                    .logout()
                    .logoutUrl("/perform_logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID"); //that we must use
        }
    }

