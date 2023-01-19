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
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;

public class RenderEnvironment2D {
	
	private CalculationValues calcVals = new CalculationValues();
	
	public RenderEnvironment2D() {
		
	}
	
	public void drawDebugData(Graphics g, int posX, int posY, String text) {
		g.setColor(Color.white);
		g.drawString(text, posX, posY);
	}
	
	public void drawMap(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, Player p) {
		for (Sector sector : env.getSectors()) {
			if(p != null) {
				if(sector.getId().equals(p.getCurrentSector().getId())) {
					drawSector(g, env, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, sector, Color.blue);
					
				}else drawSector(g, env, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, sector, sector.getColor());
			}else drawSector(g, env, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, sector, null);
			
		}
	}
	
	public void drawMapAroundPlayer() {
		
	}
	
	public void drawSector(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale,  Sector s, Color c) {
		
		maxWidth = maxWidth + canvasOffset;
		maxHeight = maxHeight + canvasOffset;
		
		for (Wall w : s.getVertices()) {
			
			drawWall(g, env, w, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, c == null? s.getColor() : c);
		}
		
	   for(Point p : s.getUniquePoints()) {
			
			drawVertex(g, env, p, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale,6, c);				
		}	
		
	}
	
	public void drawWall(Graphics g, Environment env, Wall wall, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, Color c) {
		Wall w = convertWallToScreenSize(env, wall, maxWidth - canvasOffset, maxHeight - canvasOffset, scale);
		
		w.setStartX(w.getStartX() - (int)(offsetX * scale) );
		w.setEndX(w.getEndX() - (int)(offsetX * scale));
		w.setStartY(w.getStartY() - (int)(offsetY * scale));
		w.setEndY(w.getEndY() - (int)(offsetY * scale));
		
		w.setStartX(w.getStartX() + canvasOffset);
		w.setStartY(w.getStartY() + canvasOffset);
		w.setEndX(w.getEndX() + canvasOffset);
		w.setEndY(w.getEndY() + canvasOffset);
		
		//LogUtils.logInfo("StartX: {} StartY: {} EndX: {} EndY: {}", this.getClass(), new Object[] {w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY()});
			
			if(w.getStartX() < canvasOffset) {
				w.setStartX(canvasOffset-1);
			} else if(w.getStartX() > maxWidth) {
				w.setStartX(maxWidth+1);
			}
			
			if(w.getEndX() < canvasOffset) {
				w.setEndX(canvasOffset-1);
			} else if(w.getEndX() > maxWidth) {
				w.setEndX(maxWidth+1);
			}
			
			if(w.getStartY() < canvasOffset) {
				w.setStartY(canvasOffset-1);
			} else if(w.getStartY() > maxHeight) {
				w.setStartY(maxHeight+1);
			}
			
			if(w.getEndY() < canvasOffset) {
				w.setEndY(canvasOffset-1);
			} else if(w.getEndY() > maxHeight) {
				w.setEndY(maxHeight+1);
			}
			
			
			if((w.getStartX() < canvasOffset && w.getStartY() < canvasOffset) && (w.getEndX() > maxWidth && w.getEndY() > maxHeight)) {
				
			} else drawLineBetweenVertices(g, w, c);
	}
	
	public void drawVertex(Graphics g, Environment env, Point p, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int size, Color c) {
		Point newP = convertPointToScreenSize(env, p, maxWidth-canvasOffset, maxHeight-canvasOffset, scale);
		
		//LogUtils.logInfo("Point: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});
		
		newP.x -= (int) (offsetX * scale);
		newP.y -= (int) (offsetY * scale);
		
		newP.x += canvasOffset;
		newP.y += canvasOffset;
		
		//LogUtils.logInfo("Point after scaling: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});
		
		if((newP.x >= canvasOffset && newP.x <= maxWidth) && (newP.y >= canvasOffset && newP.y <= maxHeight)) {
			g.setColor(c == null? Color.red : c);
			g.fillOval(newP.x - (int)(size/2), newP.y - (int)(size/2),size,size);
			g.drawString(p.x + ", " + p.y, newP.x - size, newP.y - size);
		}
	}
	
	public void drawPlayer(Graphics g, Environment env,  Player p, int maxWidth, int maxHeight) {
		
		Map<Integer, Integer> convertedCoords = convertPlayerPositionToScreenSize(p, env, maxWidth, maxHeight);
		
		int posX = convertedCoords.entrySet().iterator().next().getKey();
		int posY = convertedCoords.entrySet().iterator().next().getValue();
		
		g.setColor(Color.white);
		
		int x = posX - (15/2);
		int y = posY - (15/2);
		
		g.fillOval(x, y, 15, 15);
		
		//45° offset for some reason
		int newRotation = p.getRotation() + 90;
		
		if(newRotation > 359) newRotation = newRotation - 359;
		
		
		g.setColor(Color.RED);
		g.drawLine(posX,
				posY,
					(int) Math.round(-calcVals.cos(newRotation)*15 + posX),
					(int) Math.round(calcVals.sin(newRotation)*15 + posY)
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
	
	public Wall convertWallToScreenSize(Environment env, Wall v, int maxWidth, int maxHeight, double scale) {
		Wall newVertex = new Wall();
		
		double multiplierX = (double) maxWidth / env.getMAX_X();
		double multiplierY = (double) maxHeight / env.getMAX_Y();
		
		newVertex.setStartX((int) ((v.getStartX() * scale) * multiplierX));
		newVertex.setStartY((int) ((v.getStartY() * scale) * multiplierY));
		newVertex.setEndX((int) ((v.getEndX() * scale) * multiplierX));
		newVertex.setEndY((int) ((v.getEndY() * scale) * multiplierY));
		
		return newVertex;
		
	}
	
	public Point convertPointToScreenSize(Environment env, Point p, int maxWidth, int maxHeight, double scale) {
		
		double multiplierX = (double) maxWidth / env.getMAX_X();
		double multiplierY = (double) maxHeight / env.getMAX_Y();
		
		int newX = (int) (p.x * multiplierX * scale);
		int newY = (int) (p.y * multiplierY * scale);
		
		return new Point(newX, newY );
	}
	
public Point convertPointToEnvironmentSize(Environment env, Point p, int maxWidth, int maxHeight) {
		
		double multiplierX = (double)  env.getMAX_X() / maxWidth;
		double multiplierY = (double) env.getMAX_Y() / maxHeight;
		
		return new Point((int) Math.round(p.x * multiplierX), (int) Math.round(p.y * multiplierY));
	}
	public void drawLineBetweenVertices(Graphics g, Wall v, Color c) {
		g.setColor(c);
		g.drawLine(v.startX, 
				   v.startY, 
				   v.endX, 
				   v.endY);
	}

}
