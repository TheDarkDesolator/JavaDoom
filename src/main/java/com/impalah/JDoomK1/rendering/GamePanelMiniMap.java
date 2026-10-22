package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;
import com.impalah.JDoomK1.util.Seed;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GamePanelMiniMap extends JPanel implements ActionListener{


	//Rendering-
	private final int PANELOFFSET = 0;

	private IRenderWorld mainEngine;
	private MovingWorldRenderer minimapRenderer;
	private RenderObjects renderObj;
	private Environment env;
	private Environment miniMapEnv;
	private Player player;
	private RayCaster rayCaster = new RayCaster(900);

	private final Timer timer = new Timer(20, this);




	public GamePanelMiniMap(IRenderWorld mainEngine, Environment env, Player player, RenderObjects renderObj) {
		super();
		env = Seed.seedEnvironment(env.getPanelWidth(),env.getPanelHeight());
		this.renderObj = renderObj;
		this.env = env;
		this.player = player;
		timer.start();
		setFocusable(true);
		requestFocus();
		this.mainEngine = mainEngine;

		this.miniMapEnv = new Environment(env, 210,210);
		this.minimapRenderer = new MovingWorldRenderer(210,210, miniMapEnv, player);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Main view
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, env.getPanelWidth(), env.getPanelHeight());

		//Minimap view
		g2.setColor(Color.BLACK);
		g2.fillRect(env.getPanelWidth() + 30, 0 + 30, 210, 210);

		//RenderUtils.drawDebugData(g2, 10, 10, "Xpos: " + player.getPosX() + " - Ypos: " + player.getPosY() + " Rotation: " + player.getRotation() +" | Time: " + LocalDateTime.now());
		//RenderUtils.drawDebugData(g2, 10, 20, "Left: " + renderObj.isRotateLeft() + " Right: " + renderObj.isRotateRight()  + " Up: " + renderObj.isMoveForward()  + " Down: " + renderObj.isMoveBackward() );
		//RenderUtils.drawDebugData(g2, 10, 40, "Current Sector: " + player.getCurrentSector().getId().toString());

		if(mainEngine instanceof RaycastRenderer){
			((RaycastRenderer)mainEngine).drawMapByRays(g2,rayCaster.getRays());
		} else {
			mainEngine.drawPlayer(g2, env, player, new Point(0,0));
			mainEngine.drawMap(g2, env,new Point(0,0),new Point(0,0),1, player);
			if(mainEngine instanceof MovingWorldRenderer){
				((MovingWorldRenderer)mainEngine).drawRays(g, rayCaster.getRays(), new Point(0,0));
			}
		}






		minimapRenderer.drawPlayer(g2, miniMapEnv, player, new Point(env.getPanelWidth() + 30, 30));
		minimapRenderer.drawMap(g2, miniMapEnv,new Point(env.getPanelWidth() + 30,30),new Point(0,0),1, player);
		minimapRenderer.drawRays(g, rayCaster.getRays(), new Point(env.getPanelWidth() + 30, 30));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		renderObj.updateObjects();
		rayCaster.cast(player, env);
		this.repaint();
		//LogUtils.logInfo("ActionPerformed {}", this.getClass(), new Object[]{e.toString()});
	}

	
}
