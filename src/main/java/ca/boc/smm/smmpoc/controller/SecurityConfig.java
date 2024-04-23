package ca.boc.smm.smmpoc.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


  @Bean
  public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .anyRequest()
        .authenticated());
    http.oauth2ResourceServer((oauth2) -> oauth2
        .jwt(Customizer.withDefaults()));
    http.headers().frameOptions().sameOrigin();
    return http.build();
  }

  @Bean
  public JwtDecoder nimbus()
  {
    return NimbusJwtDecoder.withJwkSetUri("http://localhost:8082/realms/smm-poc-approach/protocol/openid-connect/certs").build();
  }

  //@Bean
  public SecurityFilterChain open(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .anyRequest()
        .permitAll());
    return http.build();
  }

}
