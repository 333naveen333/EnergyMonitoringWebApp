package com.energyms.energyms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@EnableJpaRepositories("my.package.base.*")
//@ComponentScan(basePackages = { "com.energyms.energyms.*" })
//@EntityScan("com.energyms.energyms.*") 
public class EnergymsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergymsApplication.class, args);
	}
    
		
}
	
//appliance status default set to false


    //.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
	//.\bin\windows\kafka-server-start.bat .\config\server.properties
	//.\bin\windows\kafka-console-consumer.bat --topic device_topic --from-beginning --bootstrap-server localhost:9092
	
	
	
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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

