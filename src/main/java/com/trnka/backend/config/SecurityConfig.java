package com.trnka.backend.config;

import com.trnka.backend.dto.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.trnka.backend.controller.LoginController;
import com.trnka.backend.controller.RestApiPaths;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/vst/ui/teacher/**", "/vst/ui/admin/**")
                .hasAnyRole("TEACHER", "ADMIN")
                .and()
                .formLogin()
                .loginPage(RestApiPaths.PATH_UI + LoginController.LOGIN)
                .defaultSuccessUrl("/vst/ui/teacher/home")
                .failureUrl(RestApiPaths.PATH_UI + LoginController.LOGIN_ERROR)
                .and()
                .logout()
                .logoutSuccessUrl(RestApiPaths.PATH_UI + LoginController.LOGOUT);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("a").password("{noop}a").roles(UserRole.TEACHER.name());
        auth.inMemoryAuthentication().withUser("user").password("{noop}aaaa").roles(UserRole.TEACHER.name());
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles(UserRole.ADMIN.name());
    }




}
