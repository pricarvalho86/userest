package br.com.concrete.conf;

import java.util.Locale;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import br.com.concrete.identity.auth.AuthenticationInterceptor;

@Configuration 
@EnableWebMvc 
@EntityScan(basePackages={"br.com.concrete"})
public class AppConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(getAuthInterceptor()).excludePathPatterns("/error","/user/","/login");
	}
	
	@Bean
	public AuthenticationInterceptor getAuthInterceptor() {
		return new AuthenticationInterceptor();
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(new Locale("pt", "BR"));
		return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	
}
