package io.catalyte.training.sportsproducts.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * Disables the login page for Spring boot
   */
  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.httpBasic().disable();
    security.cors().and().csrf().disable();
  }

}
