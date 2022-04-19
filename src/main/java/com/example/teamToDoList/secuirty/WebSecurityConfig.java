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

        @Autowired // to access to repository methode without create instance
        userDetalesServiseimp userDetailsService;

        @Bean// to access to any class methods without create instance
        public PasswordEncoder getPasswordEncoder(){ // auto created
            PasswordEncoder encoder = new BCryptPasswordEncoder();// to select the encode

            return encoder;
        }

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.cors().disable().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login", "/signup", "/", "/forgot_password", "/reset_password").permitAll() // allow access to login page without Authentication
                    .antMatchers("/image/**", "/css/**").permitAll()
                    .anyRequest().authenticated() // any other page need  login
                    .and()
                    .formLogin() // in login form
                    .and().rememberMe().and().formLogin()
                    .loginPage("/login").permitAll() // the GitMapping rout in controller of login
                    .loginProcessingUrl("/login") // the action of login form
                    .defaultSuccessUrl("/dashboard",true)
                    .failureUrl("/login?error=true")
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID"); //that we must use
        }
    }

