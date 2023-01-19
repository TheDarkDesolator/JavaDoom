package com.impalah.JDoomK1.spring;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.editor.Editor;
import com.impalah.JDoomK1.game.Game;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.rendering.GamePanel;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;
import com.impalah.JDoomK1.rendering.RenderObjects;
import com.impalah.JDoomK1.util.LogUtils;
import com.impalah.JDoomK1.util.Seed;

@SpringBootApplication
@ComponentScan
public class JDoomK1Application implements CommandLineRunner{
	
	@Value("${doom.mode}")
	private String mode;
	

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(JDoomK1Application.class);
		
		builder.headless(false);
		
		ConfigurableApplicationContext ctx = builder.run(args);
	}
	
	@Autowired
	private Game game;
	
	@Autowired
	private Editor editor;
	

	@Override
	public void run(String... args) throws Exception {
		
		
		if(mode.equals("GAME")) {
			game.startGame();
		}
		else if(mode.equals("EDITOR")) {
			editor.startEditor();
		}
		
	    
	    
	    
	}
}
