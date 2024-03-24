package com.example.Shop.conficurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Интерфейс WebMvcConfigurer реализуется классом,
// который имеет аннотацию @EnableWebMvc.
// Этот класс определяет методы обратного вызова
// для настройки конфигурации Spring MVC на основе Java.
// Для всех не реализованных методов используются значения по умолчанию.
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
