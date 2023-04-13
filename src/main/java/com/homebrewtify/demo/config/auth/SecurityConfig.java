package com.homebrewtify.demo.config.auth;

import com.homebrewtify.demo.config.jwt.*;
import com.homebrewtify.demo.service.CustomOAuth2UserService;
import com.homebrewtify.demo.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;
    @Autowired
    OAuthSuccessHandler oAuthSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)


                .and()
                .headers().frameOptions().disable()

                .and()
                .authorizeRequests() // URL별 권한 관리를 설정하는 옵션
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .antMatchers("/users/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/noAuthOk").permitAll()
                .antMatchers("/swagger-ui/**","/swagger-ui","/swagger-resources/**","/swagger-resources",
                        "/swagger-ui","/swagger-ui.html","/v3/api-docs").permitAll()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler).userInfoEndpoint().userService(customOAuth2UserService)
                .and().and()
                .apply(new JwtSecurityConfig(tokenProvider))
        ;
    }
}
