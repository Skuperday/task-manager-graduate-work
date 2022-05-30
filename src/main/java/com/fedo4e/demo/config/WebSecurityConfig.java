package com.fedo4e.demo.config;

import com.fedo4e.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.spring5.SpringTemplateEngine;


    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        UserService userService;


        public WebSecurityConfig(UserService userService) {
            this.userService = userService;
        }

        @Bean
        public static BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**","tickets/all").hasRole("ADMIN")
                    //Доступ только для авторизованных пользователей
                    .antMatchers("/main").hasRole("USER")
                    //Доступ только для сотрудников отдела удержания
                    .antMatchers("/retention").hasRole("RET")
                    //Доступ только для отдела улучшения качества
                    .antMatchers("/quality").hasRole("QUAL")
                    //Доступ только для тех.отдела
                    .antMatchers("/tech").hasRole("TECH")
                    .antMatchers("/", "/resources/**","/tickets/add").permitAll()
                    //Все остальные страницы требуют аутентификации
                    .anyRequest().authenticated()
                    .and()
                    //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                    //Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/")
                    .usernameParameter("email")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .permitAll()
                    .logoutSuccessUrl("/");
        }

        @Autowired
        protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
        }

    }
