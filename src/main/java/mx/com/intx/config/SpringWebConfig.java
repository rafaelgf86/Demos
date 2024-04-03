package mx.com.intx.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc                                    // Activar MVC
@Configuration 	                                 // Indicar que la clasees de configuración 
@ComponentScan({ "mx.com.intx.web" })   // Indicar paquete de escaneo de controladores
public class SpringWebConfig implements WebMvcConfigurer {

	// Lista permitida para CORS
	/*
	private String[] listaPermitida = new String[] {
			"/usuario","/usuario/{username}",
			"/proveedor","/proveedor/1","/proveedor/{id}"
			};*/ 
	 
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	 @Bean 
	public MessageSource messageSource() {
	  	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasenames("messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry            
			// Enable cross-origin request handling for the specified path pattern. 
	        // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**"). 
	        .addMapping("/**")
	        .allowedOrigins("*")
	        // .allowedOriginPatterns("")
	        .allowCredentials(false)
	        .allowedHeaders("*")
	        .exposedHeaders("*")
	        .allowedMethods("*")
	        ;
	}
	

	
	 
}