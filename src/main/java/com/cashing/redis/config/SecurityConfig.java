package com.cashing.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{




    /**
     * Method for enabling / disabling specific application routes
     *
     * @param http - HTTP request
     * @throws Exception - Exception
     */
    @Override
    @SuppressWarnings("squid:S4502")
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()

                .headers().frameOptions().disable().and()

                .headers().cacheControl().disable().and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()

                // allow anonymous resource requests on login and reset password
                .antMatchers("/examples/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/*", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**").permitAll()
                // any other request needs authentication
                .anyRequest().authenticated();
    }
}
