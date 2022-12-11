package com.example.bookManagement.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.bookManagement.repository.UserRepository;
import com.example.bookManagement.service.UserServiceImpl;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;

/*
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserServiceImpl userService = new UserServiceImpl();
	
	
	@Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/user/**").permitAll();
////            .anyRequest()
//            .permitAll();
//                .antMatchers("/user/register/**").permitAll()
//                .antMatchers("/user/signin").permitAll()
//            .anyRequest()
//            .authenticated();
    	
//    	http
//        .cors()
//        .and()
//        .csrf()
//        .disable()
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//        .httpBasic()
//        .and()
//        .authorizeRequests()
//        .antMatchers(HttpMethod.POST, "/user/signin")
//        .permitAll()
//        .anyRequest()
//        .authenticated();
    	
    	http
        .httpBasic()
      .and()
        .authorizeRequests()
          .antMatchers("/user/signin").permitAll()
          .anyRequest().authenticated();
    	
    	
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);
        return provider;
    }
    
    @Bean
  	public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
  	}
}*/


@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

  private final UserRepository userRepo;

  
  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(
            username ->
                userRepo
                    .findByEmail(username)
                    .orElseThrow(
                        () ->
                        new UsernameNotFoundException("User not found with username or email:" + username)))
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF
    http.cors().and().csrf().disable();

    // Set session management to stateless
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    // Set unauthorized requests exception handler
//    http.exceptionHandling(
//        (exceptions) ->
//            exceptions
//                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//                .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));

    // Set permissions on endpoints
    http.authorizeRequests()
      
        // Our public endpoints
    
     
        .antMatchers(HttpMethod.POST, "/user/register")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/user/signin")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/api/book/**")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/api/book/search")
        .permitAll()
        // Our private endpoints
        .anyRequest()
        .authenticated();

    return http.build();
  }

 

 
  // Set password encoding schema
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Used by spring security if CORS is enabled.
//  @Bean
//  public CorsFilter corsFilter() {
//    var source = new UrlBasedCorsConfigurationSource();
//    var config = new CorsConfiguration();
//    config.setAllowCredentials(true);
//    config.addAllowedOrigin("*");
//    config.addAllowedHeader("*");
//    config.addAllowedMethod("*");
//    source.registerCorsConfiguration("/**", config);
//    return new CorsFilter(source);
//  }

  // Expose authentication manager bean
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}