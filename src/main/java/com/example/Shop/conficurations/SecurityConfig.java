package com.example.Shop.conficurations;


import com.example.Shop.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

// Конфигурационный файл
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;

    /**
     * BCryptPasswordEncoder — это компонент фреймворка Spring Security, который используется в приложениях Spring Boot для безопасного хеширования и проверки паролей.
     *
     * Он использует алгоритм хеширования bcrypt, который считается высокобезопасным и специально предназначен для безопасного хранения паролей.
     *
     * Этот класс является частью модуля Spring Security и особенно полезен для обработки аутентификации пользователей и хранения паролей в приложении на основе Spring.
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // SCryptPasswordEncoder — это кодировщик, который использует алгоритм SCrypt для создания производного ключа, используемого для хранения в базе данных.
    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder(){
        return new SCryptPasswordEncoder();
    }

    /**
     * Конфигурация защиты Spring Security
     * @param http the {@Link HttpSecurity} to modify
     * @throws Exception исключение безопасности
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/products", "product/**", "/images/**", "/registration", "/user/**", "/static/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
