/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.configtemplate;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author iTeamVEP
 */
//@Configuration
//@EnableWebMvc
public class GlobalCorsConfigTemplate implements WebMvcConfigurer {
    
//    @Override
//            //重写父类提供的跨域请求处理的接口
//            public void addCorsMappings(CorsRegistry registry) {
//                System.out.print("addCorsMappings - aff");
////                添加映射路径
//                registry.addMapping("/admin/**")
//                        .allowCredentials(true)
//                        .allowedOrigins("http://127.0.0.1")
//                        .allowedHeaders("*")
//                        .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
//                        .exposedHeaders(HttpHeaders.LOCATION, "X-CSRF-TOKEN", "XSRF-TOKEN");
//                registry.addMapping("/Admin/**")
//                        .allowCredentials(true)
//                        .allowedOrigins("http://127.0.0.1")
//                        .allowedHeaders("*")
//                        .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
//                        .exposedHeaders(HttpHeaders.LOCATION, "X-CSRF-TOKEN", "XSRF-TOKEN");
//                registry.addMapping("/**")
//                        //放行哪些原始域
//                        .allowedOrigins("http://127.0.0.1")
//                        //是否发送Cookie信息
//                        .allowCredentials(true)
//                        //放行哪些原始域(请求方式)
//                        .allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
//                        //放行哪些原始域(头部信息)
//                        .allowedHeaders("*")
//                        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//                        .exposedHeaders(HttpHeaders.LOCATION, "X-CSRF-TOKEN", "XSRF-TOKEN");
//                        ;
//            }
}
