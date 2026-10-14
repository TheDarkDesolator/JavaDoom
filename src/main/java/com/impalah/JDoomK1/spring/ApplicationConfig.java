package com.impalah.JDoomK1.spring;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.rendering.MovingWorldRenderer;
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
	public MovingWorldRenderer movingWorldRenderer(){
		return new MovingWorldRenderer();
	}
	
	@Bean
	public com.impalah.JDoomK1.game.Game Game() {
		return new com.impalah.JDoomK1.game.Game();
	}
	
	@Bean 
	public Editor Editor() {
		return new Editor();
	}

	@Bean
	public CalculationValues calculationValues(){
		return new CalculationValues();
	}

	
	
}
