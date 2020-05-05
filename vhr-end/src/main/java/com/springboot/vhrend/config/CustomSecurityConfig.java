package com.springboot.vhrend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.vhrend.model.Hr;
import com.springboot.vhrend.model.RespBean;
import com.springboot.vhrend.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private HrService hrService;
    @Autowired
    CustomSecurityMetadataFilter customSecurityMetadataFilter;
    @Autowired
    CusotmUrlDecisionManager cusotmUrlDecisionManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

//     不拦截 login
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/v2/*")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/webjars/**")
                .antMatchers("/index.html")
                .antMatchers("/verifyCode")
                .mvcMatchers("/login");
    }
//     登陆验证
    @Bean
    LoginFilter login() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication)
                    throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                Hr hr = (Hr) authentication.getPrincipal();
                hr.setPassword(null);
                RespBean ok = RespBean.ok("登录成功", hr);
                out.write(new ObjectMapper().writeValueAsString(ok));
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException exception)
                    throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                RespBean respBean = RespBean.error("登录失败");
                if (exception instanceof LockedException) {
                    respBean.setMsg("账户被锁定，请联系管理员");
                } else if (exception instanceof CredentialsExpiredException) {
                    respBean.setMsg("密码过期，请联系管理员");
                } else if (exception instanceof AccountExpiredException) {
                    respBean.setMsg("账户过期，请联系管理员");
                } else if (exception instanceof DisabledException) {
                    respBean.setMsg("账户被禁用，请联系管理员");
                } else if (exception instanceof BadCredentialsException) {
                    respBean.setMsg("用户名或密码输入错误，请重新登录");
                } else if (exception != null && !StringUtils.isEmpty(exception.getMessage())) {
                    respBean.setMsg(exception.getMessage());
                }
                out.write(new ObjectMapper().writeValueAsString(respBean));
                out.flush();
                out.close();
            }
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(login(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
//                .anyRequest().authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(cusotmUrlDecisionManager);
                        object.setSecurityMetadataSource(customSecurityMetadataFilter);
                        return object;
                    }
                })
//                .and()
//                .formLogin()
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/doLogin")
//                .loginPage("/login")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request,
//                                                        HttpServletResponse response,
//                                                        Authentication authentication)
//                            throws IOException, ServletException {
//                        response.setContentType("application/json;charset=utf-8");
//                        PrintWriter out = response.getWriter();
//                        Hr hr = (Hr) authentication.getPrincipal();
//                        hr.setPassword(null);
//                        RespBean ok = RespBean.ok("登录成功", hr);
//                        out.write(new ObjectMapper().writeValueAsString(ok));
//                        out.flush();
//                        out.close();
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request,
//                                                        HttpServletResponse response,
//                                                        AuthenticationException e)
//                            throws IOException, ServletException {
//                        response.setContentType("application/json;charset=utf-8");
//                        PrintWriter out = response.getWriter();
//                        RespBean respBean = RespBean.error("登录失败");
//                        if (e instanceof LockedException) {
//                            respBean.setMsg("账户被锁定，请联系管理员");
//                        } else if (e instanceof CredentialsExpiredException) {
//                            respBean.setMsg("密码过期，请联系管理员");
//                        } else if (e instanceof AccountExpiredException) {
//                            respBean.setMsg("账户过期，请联系管理员");
//                        } else if (e instanceof DisabledException) {
//                            respBean.setMsg("账户被禁用，请联系管理员");
//                        } else if (e instanceof BadCredentialsException) {
//                            respBean.setMsg("用户名或密码输入错误，请重新登录");
//                        }
//                        out.write(new ObjectMapper().writeValueAsString(respBean));
//                        out.flush();
//                        out.close();
//                    }
//                })
//                .permitAll()

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
                .csrf().disable()
//                 没有认证时不要重定向，在这里处理结果
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request,
                                         HttpServletResponse response,
                                         AuthenticationException e)
                            throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        response.setStatus(401);
                        PrintWriter out = response.getWriter();
                        RespBean respBean = RespBean.error("访问失败");
                        if (e instanceof InsufficientAuthenticationException) {
                            respBean.setMsg("请求失败，请联系管理员");
                        }
                        out.write(new ObjectMapper().writeValueAsString(respBean));
                        out.flush();
                        out.close();
                    }
                });
    }
}