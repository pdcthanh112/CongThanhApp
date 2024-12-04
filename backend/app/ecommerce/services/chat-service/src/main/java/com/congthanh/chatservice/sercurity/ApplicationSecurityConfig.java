package com.congthanh.chatservice.sercurity;

//import com.congthanh.project.auth.ApplicationUserService;
//import com.congthanh.project.jwt.JwtConfig;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {

  private static final String[] SWAGGER_LIST = {
          "/v3/api-docs",
          "/swagger-ui/**",
          "/swagger-resources/**",
          "/swagger-resources",
          "/*", "/**"
  };


  //    private final ApplicationUserService applicationUserService;
//    private JwtConfig jwtConfig;
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeHttpRequests()
            .requestMatchers(SWAGGER_LIST).permitAll()
            .anyRequest().authenticated()/*.and().httpBasic()*/;

    return http.build();

//    "/api/v1/auth/**", "/v1/api-docs", "/v1/api-docs", "/v1/api-docs/**", "/swagger-resources", "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security", "/swagger-ui/**",
//            "/webjars/**",
//            "/swagger-ui.html",
//            "/management/**", "/management/category/**",
//            "/ecommerce/**", "/management/product/**",
//            "/graphql"

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(applicationUserService);
//        provider.setPasswordEncoder(encoder());
//        return provider;
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public javax.crypto.SecretKey getSecretKey() {
//        return Keys.hmacShaKeyFor(jwtConfig.getSecretKey().getBytes());
//    }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
      }
    };
  }

}
