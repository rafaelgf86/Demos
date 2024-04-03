package mx.com.intx.servlet;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import mx.com.intx.config.AppConfig;
import mx.com.intx.config.SecurityConfig;
import mx.com.intx.config.SpringWebConfig;

public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	

	@Override 
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{ AppConfig.class , SecurityConfig.class };
	}


	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	
	
}