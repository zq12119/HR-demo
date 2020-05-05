package com.springboot.vhrend.config;

import com.springboot.vhrend.model.Menu;
import com.springboot.vhrend.model.Role;
import com.springboot.vhrend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataFilter implements FilterInvocationSecurityMetadataSource {
   @Autowired
   MenuService menuService;

   AntPathMatcher antPathMatcher = new AntPathMatcher();

   @Override
   public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
      String requestUrl = ((FilterInvocation) object).getRequestUrl();
      List<Menu> menus = menuService.getAllMenusWithRole();
      for (Menu menu : menus) {
         if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
            List<Role> roles = menu.getRoles();
            String[] names = new String[roles.size()];
            for (int i = 0; i < roles.size(); i++) {
               names[i] = roles.get(i).getName();
            }
            return SecurityConfig.createList(names);
         }
      }
//       没有匹配上的所有 url登录之后就能访问
      return SecurityConfig.createList("ROLE_login");
   }

   @Override
   public Collection<ConfigAttribute> getAllConfigAttributes() {
      return null;
   }

   @Override
   public boolean supports(Class<?> aClass) {
      return true;
   }
}