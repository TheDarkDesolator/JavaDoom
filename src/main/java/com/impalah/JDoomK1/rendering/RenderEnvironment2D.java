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
		
		
		//float b = wall.getStartY() - (wall.getStartX() * m);
		
		//205 = 555 * -0.2 + b
		//205 = 555 * -0.2 + 280
		// ? = 555 * -0.2 + 280
		
		
		Wall w = convertWallToScreenSize(env, wall, maxWidth - canvasOffset, maxHeight - canvasOffset, scale);
		
		
		
		//LogUtils.logInfo("start ({}, {}) end ({},{}), m= {} b= {}", this.getClass(), new Object[] {w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY(), m});
		
		
		w.setStartX(w.getStartX() - (int)(offsetX * scale) );
		w.setEndX(w.getEndX() - (int)(offsetX * scale));
		w.setStartY(w.getStartY() - (int)(offsetY * scale));
		w.setEndY(w.getEndY() - (int)(offsetY * scale));
		
		w.setStartX(w.getStartX() + canvasOffset);
		w.setStartY(w.getStartY() + canvasOffset);
		w.setEndX(w.getEndX() + canvasOffset);
		w.setEndY(w.getEndY() + canvasOffset);
		
		//Left side and bottom of plane intersection
		Point intersectionStartY = getIntersectionStartY(w, canvasOffset);
		Point intersectionStartX = getIntersectionStartX(w, canvasOffset);
		
		//right side and top of plane intersection
		Point intersectionEndY = getIntersectionEndY(w, canvasOffset);
		Point intersectionEndX = getIntersectionEndX(w, canvasOffset);
		
		LogUtils.logInfo("intersectionEndY: {} intersectionEndX: {}", this.getClass(), new Object[] {intersectionEndY.y, intersectionEndX.x});
	
		
		/*g.setColor(Color.pink);
		g.fillOval(canvasOffset - 6, intersectionStartY.y - 6, 12, 12);
		g.fillOval(intersectionStartX.x - 6, canvasOffset - 6, 12, 12);
		
		g.fillOval(maxWidth - 6, intersectionEndY.y - 6, 12, 12);
		g.fillOval(intersectionEndX.x - 6, maxWidth - 6, 12, 12);*/
		
		//LogUtils.logInfo("start ({}, {}) end ({},{}), m= {} bStart= {}, bEnd= {}", this.getClass(), new Object[] {w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY(), m,b});
		
		
			//Startpoint boundaries
			Wall cutOffWall = new Wall(w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY());
		
			//Endpoint
			if(w.getStartX() < canvasOffset) {
				cutOffWall.setStartY(intersectionStartY.y);
				cutOffWall.setStartX(canvasOffset);
			}
			
			if(w.getStartX() > maxWidth) {
				cutOffWall.setStartY(intersectionEndY.y);
				cutOffWall.setStartX(canvasOffset + 700);
			}
			
			if(w.getStartY() < canvasOffset) {
				cutOffWall.setStartX(intersectionStartX.x);
				cutOffWall.setStartY(canvasOffset);
			}
			if(w.getStartY() > maxHeight) {
				cutOffWall.setStartX(intersectionEndX.x);
				cutOffWall.setStartY(canvasOffset + 700);
			}
			
			if(w.getEndX() < canvasOffset) {
				cutOffWall.setEndY(intersectionStartY.y);
				cutOffWall.setEndX(canvasOffset);
			}
			
			if(w.getEndX() > maxWidth) {
				cutOffWall.setEndY(intersectionEndY.y);
				cutOffWall.setEndX(canvasOffset + 700);
			}
			
			if(w.getEndY() < canvasOffset) {
				cutOffWall.setEndX(intersectionStartX.x);
				cutOffWall.setEndY(canvasOffset);
			}
			if(w.getEndY() > maxHeight) {
				cutOffWall.setEndX(intersectionEndX.x);
				cutOffWall.setEndY(canvasOffset + 700);
			}
		
			if(!determinPointOnScreen(new Point(cutOffWall.getStartX(), cutOffWall.getStartY()), canvasOffset, maxWidth) &&
					!determinPointOnScreen(new Point(cutOffWall.getEndX(), cutOffWall.getEndY()), canvasOffset, maxWidth)) {
				
			} else drawLineBetweenVertices(g, cutOffWall, c);
			
				
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
	
	public Point getIntersectionStartY(Wall w, int canvasOffset) {
		float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
		float b = w.getStartY() - (w.getStartX() * m);
		
		//int x = (int)((b - 700) / (1 - b)) + canvasOffset;
		int x = canvasOffset;
		float y = m * x + b;
		
		return new Point(canvasOffset, (int)y);
	}
	
	
	public Point getIntersectionStartX(Wall w, int canvasOffset) {
		float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
		float b = w.getStartY() - (w.getStartX() * m);
		
		LogUtils.logInfo("X: {} Y: {}, M: {}, B: {}", this.getClass(), new Object[] {w.getEndX(), w.getEndY(), m, b});
		
		//0 = 401 * -0.19908814 + 240.83435
		
		double x = (b - 30) / (m-0);
		return new Point((int) -x, canvasOffset);
	}
	
	public Point getIntersectionEndY(Wall w, int canvasOffset) {
		float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
		float b = w.getStartY() - (w.getStartX() * m);
		
		//panelwidth  + offset;
		int x = 700+canvasOffset;
		float y = m * x + b;
		
		return new Point(canvasOffset, (int)y);
	}
	
	public Point getIntersectionEndX(Wall w, int canvasOffset) {
		float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
		float b = w.getStartY() - (w.getStartX() * m);
		
		LogUtils.logInfo("X: {} Y: {}, M: {}, B: {}", this.getClass(), new Object[] {w.getEndX(), w.getEndY(), m, b});
		
		//0 = 401 * -0.19908814 + 240.83435
		
		double x = (b - 730) / (m-0);
		return new Point((int) -x, canvasOffset);
	}
	
	public boolean determinPointOnScreen(Point p, int canvasOffset, int maxWidth) {
		if(p.x >= canvasOffset && p.x <= maxWidth && p.y >= canvasOffset && p.y <= maxWidth ) {
			return true;
		} else return false;
	}
	
	public void drawLineBetweenVertices(Graphics g, Wall v, Color c) {
		g.setColor(c);
		g.drawLine(v.startX, 
				   v.startY, 
				   v.endX, 
				   v.endY);
	}
	
	// 700 = -700*0 + 0

}
