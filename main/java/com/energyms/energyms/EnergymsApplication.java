package com.energyms.energyms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EnergymsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergymsApplication.class, args);
	}
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//working reg and login properly
	//@Repo annotation in user added 
	//device , room , appliances added
	
//  @Bean
//  public WebMvcConfigurer getCorsConfiguration(){
//      return new WebMvcConfigurer() {
//          @Override
//          public void addCorsMappings(CorsRegistry registry) {
//              registry.addMapping("/**")
//                      .allowedOrigins("*");
//              }
//      };
  
}
  
  
//	@Bean
//	public CorsWebFilter corsFilter() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		final CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("OPTIONS");
//		config.addAllowedMethod("HEAD");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("DELETE");
//		config.addAllowedMethod("PATCH");
//		source.registerCorsConfiguration("/**", config);
//		return new CorsWebFilter(source);
//	}

