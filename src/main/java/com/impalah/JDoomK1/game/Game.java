package com.impalah.JDoomK1.game;

import java.awt.Container;

import javax.swing.JFrame;

import com.impalah.JDoomK1.rendering.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.util.Seed;
import org.springframework.beans.factory.annotation.Value;

public class Game {
	
	private final int SCREEN_X = 1000;
	private final int SCREEN_Y = 1000;
	
	@Autowired
	private RenderEnvironment2D renderer2D;

	private MovingWorldRenderer movingWorldRenderer;

	@Value("${doom.config.engine.mode}")
	private String renderMode;
	
	public void startGame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container pane = frame.getContentPane();
		
		Environment env = Seed.seedEnvironment();
		//Environment env = Seed.seedOneWall();
		
		Player player = new Player(20,50, 10);
		player.setCurrentSector(env.getSectors().get(0));
		player.centerAtCurrentSector();

		RenderObjects ro = new RenderObjects(player, env);
		IRenderWorld engine;

		movingWorldRenderer = new MovingWorldRenderer(env, player);


		if(renderMode.equals("MWR")) engine = movingWorldRenderer;
		else engine = renderer2D;

		GamePanel myRenderPanel = new GamePanel(engine, env, player, ro);
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
