package io.catalyte.training.sportsproducts.config;

import io.catalyte.training.sportsproducts.domains.auth.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();

  /**
   * Disables the login page for Spring boot
   */
  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.httpBasic().disable();
    security.csrf().disable()
        .authorizeRequests().antMatchers(HttpMethod.PUT, "/users").authenticated().and()
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

}
