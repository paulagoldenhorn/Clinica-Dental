package com.DH.clinicaDental.security;

import com.DH.clinicaDental.service.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder bcEncoder;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bcEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/index.html").permitAll()
            .antMatchers("/registrar_turno.html").permitAll()
            .antMatchers("/dashboard_turno.html").permitAll()
            .antMatchers("/registrar_odontologo.html").hasRole("ADMIN")
            .antMatchers("/dashboard_odontologo.html").hasRole("ADMIN")
            .antMatchers("/registrar_paciente.html").hasRole("ADMIN")
            .antMatchers("/dashboard_paciente.html").hasRole("ADMIN")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .and()
            .logout();
    }
}
