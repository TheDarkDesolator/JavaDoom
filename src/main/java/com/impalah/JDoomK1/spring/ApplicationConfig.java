package com.impalah.JDoomK1.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.impalah.JDoomK1.editor.Editor;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;

@Configuration
@Component
public class ApplicationConfig {
	
	@Bean
	public RenderEnvironment2D RenderEnvironment2D() {
		return new RenderEnvironment2D();
	}
	
	@Bean
	public com.impalah.JDoomK1.game.Game Game() {
		return new com.impalah.JDoomK1.game.Game();
	}
	
	@Bean 
	public Editor Editor() {
		return new Editor();
	}
	
	
}
