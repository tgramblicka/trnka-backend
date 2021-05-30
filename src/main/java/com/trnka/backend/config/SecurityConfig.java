package com.trnka.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trnka.backend.controller.LoginController;
import com.trnka.backend.controller.RestApiPaths;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        configureWrapper(http);
    }

    private void configureWrapper(final HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/vst/ui/teacher/**", "/vst/ui/course/**", "/vst/ui/examination/**", "/vst/ui/student/**", "/vst/ui/sync-management/**")
                .hasAnyAuthority("TEACHER", "ADMIN")
                .antMatchers("/vst/ui/admin/**", "/vst/sync/**")
                .hasAnyAuthority("ADMIN")
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage(RestApiPaths.PATH_UI + LoginController.LOGIN)
                .defaultSuccessUrl("/vst/ui/teacher/home")
                .failureUrl(RestApiPaths.PATH_UI + LoginController.LOGIN_ERROR)
                .and()
                .logout()
                .logoutSuccessUrl(RestApiPaths.PATH_UI + LoginController.LOGOUT);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder code = new BCryptPasswordEncoder();
        System.out.println("PWD:" + code.encode("123456"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().withUser("a").password("{noop}a").roles(UserRole.TEACHER.name());
        // auth.inMemoryAuthentication().withUser("user").password("{noop}0000").roles(UserRole.TEACHER.name());
        // auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles(UserRole.ADMIN.name());

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT username, password, enabled from user where username = ?")
                .authoritiesByUsernameQuery("SELECT u.username, a.authority " + //
                        "FROM authorities a, user u " + //
                        "WHERE u.username = ? AND u.id = a.user_id");
    }

}
