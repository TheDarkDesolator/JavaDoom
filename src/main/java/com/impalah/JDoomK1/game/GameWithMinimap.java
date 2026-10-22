package com.impalah.JDoomK1.game;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.rendering.*;
import com.impalah.JDoomK1.util.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.*;
import java.awt.*;

public class GameWithMinimap {
	
	private final int SCREEN_X = 1200;
	private final int SCREEN_Y = 900;

	private final Point mainGameSize = new Point(900,900);
	private final Point minimapSize = new Point(70,70);

	@Autowired
	private RenderEnvironment2D renderer2D;

	private MovingWorldRenderer movingWorldRenderer;

	private RaycastRenderer rayCastRenderer;

	@Value("${doom.config.engine.mode}")
	private String renderMode;
	
	public void startGame() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container pane = frame.getContentPane();
		
		Environment env = Seed.seedEnvironment(mainGameSize.x, mainGameSize.y);
		//Environment env = Seed.seedOneWall();
		
		Player player = new Player(20,50, 10);
		player.setCurrentSector(env.getSectors().get(0));
		player.centerAtCurrentSector();

		RenderObjects ro = new RenderObjects(player, env);
		IRenderWorld engine;

		movingWorldRenderer = new MovingWorldRenderer(mainGameSize.x, mainGameSize.y, env, player);
		rayCastRenderer = new RaycastRenderer(mainGameSize.x, mainGameSize.y, env, player);

		if(renderMode.equals("MWR")) engine = movingWorldRenderer;
		else if(renderMode.equals("RCR")) engine = rayCastRenderer;
		else engine = renderer2D;

		GamePanelMiniMap myRenderPanel = new GamePanelMiniMap(engine, env, player, ro);
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
