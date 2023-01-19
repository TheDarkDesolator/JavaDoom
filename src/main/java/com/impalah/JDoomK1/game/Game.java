package com.impalah.JDoomK1.game;

import java.awt.Container;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.rendering.GamePanel;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;
import com.impalah.JDoomK1.rendering.RenderObjects;
import com.impalah.JDoomK1.util.Seed;

public class Game {
	
	private final int SCREEN_X = 1000;
	private final int SCREEN_Y = 1000;
	
	@Autowired
	private RenderEnvironment2D renderer2D;
	
	public void startGame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container pane = frame.getContentPane();
		
		Environment env = Seed.seedEnvironment();
		
		Player player = new Player(50,50);
		player.setCurrentSector(env.getSectors().get(0));
		player.centerAtCurrentSector();

		RenderObjects ro = new RenderObjects(player, env);
		
		GamePanel myRenderPanel = new GamePanel(renderer2D, env, player, ro);
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
