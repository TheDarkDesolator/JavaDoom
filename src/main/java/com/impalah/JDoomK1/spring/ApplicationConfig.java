package com.impalah.JDoomK1.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.impalah.JDoomK1.rendering.RenderEnvironment2D;

@Configuration
@Component
public class ApplicationConfig {
	
	@Bean
	public RenderEnvironment2D RenderEnvironment2D() {
		return new RenderEnvironment2D();
	}
	
	
}
