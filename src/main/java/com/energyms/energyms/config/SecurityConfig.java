package com.energyms.energyms.config;

//import com.energyms.energyms.exception.CORSFilter;
//import com.energyms.energyms.exception.CorsFilter;
import com.energyms.energyms.jwt.JwtAuthenticationEntryPoint;
import com.energyms.energyms.jwt.JwtAuthenticationFilter;
//import com.energyms.energyms.oauth2.CustomOAuth2UserService;
import com.energyms.energyms.oauth2.CustomOAuth2UserService;
import com.energyms.energyms.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    
   
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
         
                .csrf()
                .disable()
                .authorizeRequests()
               // .antMatchers(HttpMethod.GET,"/****").permitAll()
                .antMatchers("/register","/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .oauth2Login()
                    .loginPage("/login")
                     .userInfoEndpoint()
                      .userService(oAuth2UserService)
                       ;
//                .oauth2Login()
//                .authorizationEndpoint()
//                    .baseUri("/oauth2/authorize")
//                  //  .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//                    .and()
//                .redirectionEndpoint()
//                    .baseUri("/oauth2/callback/*")
//                    .and()
//                .userInfoEndpoint()
//                    .userService(oAuth2UserService);
//                //    .and()
//               // .successHandler(oAuth2AuthenticationSuccessHandler)
//                //.failureHandler(oAuth2AuthenticationFailureHandler);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    
    
//    @Bean  
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean(){
//	    FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter());
//	    registrationBean.setName("CORS FIlter");
//	    registrationBean.addUrlPatterns("/*");
//	    registrationBean.setOrder(1);
//	    return registrationBean;
//	}
//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*");
//    }

}
