package com.impalah.JDoomK1.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Vertex;

public class RenderEnvironment2D {
	
	private CalculationValues calcVals = new CalculationValues();
	
	public RenderEnvironment2D() {
		
	}
	
	public void drawDebugData(Graphics2D g, Environment env, int posX, int posY, String text) {
		g.setColor(Color.white);
		g.drawString(text, posX, posY);
	}
	
	public void drawMap(Graphics2D g, Environment env, int maxWidth, int maxHeight) {
		for (Sector sector : env.getSectors()) {
			for (Vertex v : sector.getVertices()) {
				drawVertex(g, convertVertexToScreenSize(env, v, maxWidth, maxHeight), sector.getColor());
			}
		}
		
	}
	
	public void drawMapAroundPlayer() {
		
	}
	
	public void drawPlayer(Graphics2D g, Player p, int maxWidth, int maxHeight) {
		g.setColor(Color.white);
		
		int x = p.getPosX() - (15/2);
		int y = p.getPosY() - (15/2);
		
		g.fillOval(x, y, 15, 15);
		
		//45° offset for some reason
		int newRotation = p.getRotation() + 90;
		
		if(newRotation > 359) newRotation = newRotation - 359;
		
		
		g.setColor(Color.RED);
		g.drawLine(p.getPosX(),
				   p.getPosY(),
					(int) Math.round(-calcVals.cos(newRotation)*15 + p.getPosX()),
					(int) Math.round(calcVals.sin(newRotation)*15 + p.getPosY())
				);
				  
		
	}
	
	public Map<Integer, Integer> convertPlayerPositionToScreenSize(Player p, Environment env, int maxWidth, int maxHeight){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		double multiplierX = (double) maxWidth / env.getMAX_X();
		double multiplierY = (double) maxHeight / env.getMAX_Y();
		
		int newX = (int) Math.round(p.getPosX() * multiplierX);
		int newY = (int) Math.round(p.getPosY() * multiplierY);
		
		map.put(newX, newY);
		
		return map;
		
	}
	
	public Vertex convertVertexToScreenSize(Environment env, Vertex v, int maxWidth, int maxHeight) {
		Vertex newVertex = new Vertex();
		
		double multiplierX = (double) maxWidth / env.getMAX_X();
		double multiplierY = (double) maxHeight / env.getMAX_Y();
		
		newVertex.setStartX((int) Math.round(v.getStartX() * multiplierX));
		newVertex.setStartY((int) Math.round(v.getStartY() * multiplierY));
		newVertex.setEndX((int) Math.round(v.getEndX() * multiplierX));
		newVertex.setEndY((int) Math.round(v.getEndY() * multiplierY));
		
		return newVertex;
		
	}
	public void drawVertex(Graphics2D g, Vertex v, Color c) {
		g.setColor(c);
		g.drawLine(v.startX, 
				   v.startY, 
				   v.endX, 
				   v.endY);
	}

}
