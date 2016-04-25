package br.com.concrete.conf;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.concrete.identity.auth.AuthenticationInterceptor;

@Configuration 
@EnableWebMvc 
@EntityScan(basePackages={"br.com.concrete"})
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(getAuthInterceptor()).excludePathPatterns("/error");
	}
	
	@Bean
	public AuthenticationInterceptor getAuthInterceptor() {
		return new AuthenticationInterceptor();
	}
	
}
