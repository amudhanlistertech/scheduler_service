package com.amudhan.schedulerservice.config;

import com.amudhan.schedulerservice.security.AuthoritiesConstants;
import com.amudhan.schedulerservice.security.jwt.JWTConfigurer;
import com.amudhan.schedulerservice.security.jwt.TokenProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MicroserviceSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Inject
    private TokenProvider tokenProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers(HttpMethod.GET, "/**")
            .antMatchers(HttpMethod.POST, "/**")
            .antMatchers(HttpMethod.PUT, "/**")
            .antMatchers(HttpMethod.DELETE, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**")
            .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf()
//            .disable()
//            .headers()
//            .frameOptions()
//            .disable()
//        .and()
//            .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//            .authorizeRequests()
//            .antMatchers("/api/**").authenticated()
//            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
//            .antMatchers("/swagger-resources/configuration/ui").permitAll()
//        .and()
//            .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
}
