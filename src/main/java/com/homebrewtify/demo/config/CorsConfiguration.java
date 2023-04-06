//package com.homebrewtify.demo.config;
//
//
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class CorsConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 모든 url 적용
//                .allowedOrigins("http://localhost:3000","http://localhost:8080")
//                .allowedMethods("GET","POST","PUT","DELETE")
//                .allowedHeaders("*");
//    }
//}
