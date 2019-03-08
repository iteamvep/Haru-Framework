/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.configtemplate;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * https://segmentfault.com/a/1190000012364985
 * https://stackoverflow.com/questions/31724994/spring-data-rest-and-cors
 * http://www.spring4all.com/article/177
 * https://blog.csdn.net/jianggujin/article/details/54692470
 * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf-using
 * @author x5171
 */
//@Configuration
//@EnableWebSecurity(debug = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigTemplate extends WebSecurityConfigurerAdapter {
    private static final String ALL_HEADERS = "Accept, Accept-CH, Accept-Charset, Accept-Datetime, Accept-Encoding, Accept-Ext, Accept-Features, Accept-Language, Accept-Params, Accept-Ranges, Access-Control-Allow-Credentials, Access-Control-Allow-Headers, Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Expose-Headers, Access-Control-Max-Age, Access-Control-Request-Headers, Access-Control-Request-Method, Age, Allow, Alternates, Authentication-Info, Authorization, C-Ext, C-Man, C-Opt, C-PEP, C-PEP-Info, CONNECT, Cache-Control, Compliance, Connection, Content-Base, Content-Disposition, Content-Encoding, Content-ID, Content-Language, Content-Length, Content-Location, Content-MD5, Content-Range, Content-Script-Type, Content-Security-Policy, Content-Style-Type, Content-Transfer-Encoding, Content-Type, Content-Version, Cookie, Cost, DAV, DELETE, DNT, DPR, Date, Default-Style, Delta-Base, Depth, Derived-From, Destination, Differential-ID, Digest, ETag, Expect, Expires, Ext, From, GET, GetProfile, HEAD, HTTP-date, Host, IM, If, If-Match, If-Modified-Since, If-None-Match, If-Range, If-Unmodified-Since, Keep-Alive, Label, Last-Event-ID, Last-Modified, Link, Location, Lock-Token, MIME-Version, Man, Max-Forwards, Media-Range, Message-ID, Meter, Negotiate, Non-Compliance, OPTION, OPTIONS, OWS, Opt, Optional, Ordering-Type, Origin, Overwrite, P3P, PEP, PICS-Label, POST, PUT, Pep-Info, Permanent, Position, Pragma, ProfileObject, Protocol, Protocol-Query, Protocol-Request, Proxy-Authenticate, Proxy-Authentication-Info, Proxy-Authorization, Proxy-Features, Proxy-Instruction, Public, RWS, Range, Referer, Refresh, Resolution-Hint, Resolver-Location, Retry-After, Safe, Sec-Websocket-Extensions, Sec-Websocket-Key, Sec-Websocket-Origin, Sec-Websocket-Protocol, Sec-Websocket-Version, Security-Scheme, Server, Set-Cookie, Set-Cookie2, SetProfile, SoapAction, Status, Status-URI, Strict-Transport-Security, SubOK, Subst, Surrogate-Capability, Surrogate-Control, TCN, TE, TRACE, Timeout, Title, Trailer, Transfer-Encoding, UA-Color, UA-Media, UA-Pixels, UA-Resolution, UA-Windowpixels, URI, Upgrade, User-Agent, Variant-Vary, Vary, Version, Via, Viewport-Width, WWW-Authenticate, Want-Digest, Warning, Width, X-Content-Duration, X-Content-Security-Policy, X-Content-Type-Options, X-CustomHeader, X-DNSPrefetch-Control, X-Forwarded-For, X-Forwarded-Port, X-Forwarded-Proto, X-Frame-Options, X-Modified, X-OTHER, X-PING, X-PINGOTHER, X-Powered-By, X-Requested-With";
    
    /**
     * 注入 Security 属性类配置
     */
    @Autowired
    private SecurityProperties securityProperties;
    
    /**
     * 重写PasswordEncoder  接口中的方法，实例化加密策略
     * @return 返回 BCrypt 加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .cors().and() //开启cors配置过滤器注入
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
//            .authorizeRequests().anyRequest().permitAll().and()
////                .csrf().csrfTokenRepository(new CookieCsrfTokenRepository())    //API服务器开启csrf验证会导致接口403错误
//            .csrf().disable()
//            ;
//    }
    
    
    
    /**
     * 配置全局cors信息方法一
     * @return 
     */
//    @Bean
//    public CorsFilter corsFilter() {
//        //1.添加CORS配置信息
//        CorsConfiguration config = new CorsConfiguration();
//          //放行哪些原始域
//          config.addAllowedOrigin("http://127.0.0.1");
//          //是否发送Cookie信息
//          config.setAllowCredentials(true);
//          //放行哪些原始域(请求方式)
//          config.addAllowedMethod(HttpMethod.HEAD);
//          config.addAllowedMethod(HttpMethod.GET);
//          config.addAllowedMethod(HttpMethod.POST);
//          config.addAllowedMethod(HttpMethod.OPTIONS);
//          //放行哪些原始域(头部信息)
//          config.addAllowedHeader("*");
//          //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//          config.addExposedHeader(HttpHeaders.LOCATION);
//          config.addExposedHeader("X-CSRF-TOKEN");
//          config.addExposedHeader("XSRF-TOKEN");
////          config.setMaxAge(3600L);
//        //2.添加映射路径
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//
//        //3.返回新的CorsFilter.
//        return new CorsFilter(configSource);
//    }
    
    /**
     * 配置全局cors信息方法二
     * @return 
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
//        .headers()
//        .cacheControl();
//    }
//    @Bean
//    public MyCorsFilter corsFilter() throws Exception {
//        return new MyCorsFilter();
//    }
    
    /**
     * 配置全局cors信息方法三
     * @return 
     */
//    @Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//                configuration.setAllowCredentials(true);
//		configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1"));
//                configuration.setAllowedHeaders(Arrays.asList("*"));
//		configuration.setAllowedMethods(Arrays.asList("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
//                configuration.setExposedHeaders(Arrays.asList(HttpHeaders.LOCATION, "X-CSRF-TOKEN", "XSRF-TOKEN"));
////                configuration.setMaxAge(3600L);
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//		return source;
//	}
}
