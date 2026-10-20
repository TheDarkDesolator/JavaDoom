package com.impalah.JDoomK1.rendering;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;

public class GamePanel extends JPanel implements ActionListener{

	private IRenderWorld engine;
	private RenderObjects renderObj;
	private Environment env;
	private Player player;
	private RayCaster rayCaster = new RayCaster();

	private final Timer timer = new Timer(20, this);

	public GamePanel(IRenderWorld renderer2d, Environment env, Player player, RenderObjects renderObj) {
		super();
		engine = renderer2d;
		this.renderObj = renderObj;
		this.env = env;
		this.player = player;
		timer.start();
		setFocusable(true);
		requestFocus();
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, getWidth(), getHeight());

		RenderUtils.drawDebugData(g2, 10, 10, "Xpos: " + player.getPosX() + " - Ypos: " + player.getPosY() + " Rotation: " + player.getRotation() +" | Time: " + LocalDateTime.now());
		RenderUtils.drawDebugData(g2, 10, 20, "Left: " + renderObj.isRotateLeft() + " Right: " + renderObj.isRotateRight()  + " Up: " + renderObj.isMoveForward()  + " Down: " + renderObj.isMoveBackward() );
		RenderUtils.drawDebugData(g2, 10, 40, "Current Sector: " + player.getCurrentSector().getId().toString());

		engine.drawPlayer(g2, env, player,new Point(0,0));
		engine.drawMap(g2, env,new Point(0,0),new Point(0,0),1, player);
		if(engine instanceof MovingWorldRenderer){
			((MovingWorldRenderer) engine).drawRays(g, rayCaster.getRays(), new Point(0,0));
		}
		//renderer2D.drawMapAroundPlayer(g2, env, getWidth(), getHeight(), 0,0,0,1,player);

	}

	public IRenderWorld getEngine() {
		return engine;
	}

	public void setEngine(RenderEnvironment2D renderer2d) {
		engine = renderer2d;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//UpdateLoop
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		renderObj.updateObjects();
		rayCaster.cast(player,env);
		this.repaint();

	}


}
