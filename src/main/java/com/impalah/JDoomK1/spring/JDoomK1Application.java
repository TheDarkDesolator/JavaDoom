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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Vertex;
import com.impalah.JDoomK1.rendering.MyPanel;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;
import com.impalah.JDoomK1.rendering.RenderObjects;
import com.impalah.JDoomK1.util.LogUtils;
import com.impalah.JDoomK1.util.Seed;

@SpringBootApplication
@ComponentScan
public class JDoomK1Application implements CommandLineRunner{
	
	private final int SCREEN_X = 1000;
	private final int SCREEN_Y = 1000;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(JDoomK1Application.class);
		
		builder.headless(false);
		
		ConfigurableApplicationContext ctx = builder.run(args);
	}
	
	@Autowired
	private RenderEnvironment2D renderer2D;
	

	@Override
	public void run(String... args) throws Exception {
		

		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		
		Environment env = Seed.seedEnvironment();
		
		Player player = new Player(50,50);
		player.setCurrentSector(env.getSectors().get(0));

		RenderObjects ro = new RenderObjects(player, env);
		
		MyPanel myRenderPanel = new MyPanel(renderer2D, env, player, ro);
		myRenderPanel.setSize(SCREEN_X, SCREEN_Y);
		
		KeyboardMotion km = new KeyboardMotion(ro, myRenderPanel);
		km.addAction("LEFT");
		km.addAction("RIGHT");
		km.addAction("UP");
		km.addAction("DOWN");
		km.addAction("STRAFELEFT");
		km.addAction("STRAFERIGHT");

	    //pane.add(renderPanel, BorderLayout.CENTER);
	    frame.add(myRenderPanel);
	    frame.setSize(SCREEN_X,SCREEN_Y);
	    frame.setVisible(true);
	    
	    
	    
	}
}
