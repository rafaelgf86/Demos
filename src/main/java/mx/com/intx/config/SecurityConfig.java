package mx.com.intx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(((csrf) -> csrf.disable())).authorizeHttpRequests((authz) -> authz.anyRequest().permitAll());
        return http.build();
    }

    
    /**
     * Este Bean permite que no se bloquee el login por CORS
     * @return
     */
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
       	configuration.setAllowedOrigins(Arrays.asList("*"));
     	configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
     	configuration.setAllowedHeaders(Arrays.asList("*"));
     	configuration.setAllowCredentials(false);
     	//configuration.setMaxAge(new Long(4800));
     	// Es muy importante! exponer el header del token  
     	//configuration.setExposedHeaders(Arrays.asList("Authorization"));
     	       
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
//	@Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//		return (web) -> web.ignoring().requestMatchers("/images/**");
//    }
	
    
}
