package com.impalah.JDoomK1.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.spring.JDoomK1Application;
import com.impalah.JDoomK1.util.LogUtils;

public class MyPanel extends JPanel implements ActionListener{
	
	private RenderEnvironment2D renderer2D;
	private RenderObjects renderObj;
	private Environment env;
	private Player player;
	
	private final Timer timer = new Timer(20, this);
	
	

	public MyPanel(RenderEnvironment2D renderer2d, Environment env, Player player, RenderObjects renderObj) {
		super();
		renderer2D = renderer2d;
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
		
		renderer2D.drawDebugData(g2, env, 10, 10, "Xpos: " + player.getPosX() + " - Ypos: " + player.getPosY() + " Rotation: " + player.getRotation() +" | Time: " + LocalDateTime.now());
		renderer2D.drawDebugData(g2, env, 10, 20, "Left: " + renderObj.isRotateLeft() + " Right: " + renderObj.isRotateRight()  + " Up: " + renderObj.isMoveForward()  + " Down: " + renderObj.isMoveBackward() );
		renderer2D.drawPlayer(g2, player, getWidth(), getHeight());
		renderer2D.drawMap(g2, env, getWidth(), getHeight());
		
	}

	public RenderEnvironment2D getRenderer2D() {
		return renderer2D;
	}

	public void setRenderer2D(RenderEnvironment2D renderer2d) {
		renderer2D = renderer2d;
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
		this.repaint();
		
	}
	

}
