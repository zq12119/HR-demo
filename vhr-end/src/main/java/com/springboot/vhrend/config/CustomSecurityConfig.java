package com.springboot.vhrend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.vhrend.model.Hr;
import com.springboot.vhrend.model.RespBean;
import com.springboot.vhrend.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HrService hrService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/doLogin")
                .loginPage("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        Authentication authentication)
                            throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        Hr hr = (Hr) authentication.getPrincipal();
                        hr.setPassword("123");
                        RespBean ok = RespBean.ok("登录成功", hr);
                        out.write(new ObjectMapper().writeValueAsString(ok));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        AuthenticationException e) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        RespBean respBean = RespBean.error("登录失败");
                        if (e instanceof LockedException) {
                            respBean.setMsg("账户被锁定，请联系管理员");
                        } else if(e instanceof CredentialsExpiredException) {
                            respBean.setMsg("密码过期，请联系管理员");
                        } else if(e instanceof AccountExpiredException) {
                            respBean.setMsg("账户过期，请联系管理员");
                        } else if(e instanceof DisabledException) {
                            respBean.setMsg("账户被禁用，请联系管理员");
                        } else if(e instanceof BadCredentialsException) {
                            respBean.setMsg("用户名或密码输入错误，请重新登录");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功")));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable();
    }
}