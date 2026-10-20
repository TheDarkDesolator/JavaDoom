package com.impalah.JDoomK1.rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;

public class RenderEnvironment2D extends AbstractRenderEngine {
	
	private CalculationValues calcVals = new CalculationValues();
	
	public RenderEnvironment2D(int rpX, int rpY, Environment e, Player p) {
		super(rpX, rpY, e, p);
	}

	public RenderEnvironment2D(int rpX, int rpY) {
		super(rpX, rpY);
	}
	

	@Override
	public void drawMap(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, Player p) {
		for (Sector sector : env.getSectors()) {
			if(p != null) {
				if(sector.getId().equals(p.getCurrentSector().getId())) {
					drawSector(g, env,canvasOffset, worldOffset, scale,0, sector, Color.blue);
					
				}else drawSector(g, env, canvasOffset, worldOffset, scale,0, sector, sector.getColor());
				}else drawSector(g, env, canvasOffset, worldOffset, scale,0, sector, null);
			
		}
	}

	@Override
	public void drawSector(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, int rotation, Sector s, Color c) {

		//maxWidth = maxWidth + canvasOffset;
		//maxHeight = maxHeight + canvasOffset;

		for (Wall w : s.getWalls()) {

			drawWall(g, env, w, canvasOffset, worldOffset, scale,0, c == null? s.getColor() : c);
		}

	   for(Point p : s.getUniquePoints()) {

			drawVertex(g, env, p, canvasOffset, worldOffset, scale,6, c);
		}

	}

	@Override
	public void drawWall(Graphics g, Environment env, Wall wall, Point canvasOffset, Point worldOffset, double scale, int rotation, Color c) {

		int rpWidth = super.getRenderPaneWidth() + canvasOffset.x;
		int rpHeight = super.getRenderPaneHeight() + canvasOffset.y;
		//float b = wall.getStartY() - (wall.getStartX() * m);

		//205 = 555 * -0.2 + b
		//205 = 555 * -0.2 + 280
		// ? = 555 * -0.2 + 280


		Wall w = RenderUtils.convertWallToScreenSize(env, wall, rpWidth - canvasOffset.x, rpHeight - canvasOffset.y, scale);

		//LogUtils.logInfo("start ({}, {}) end ({},{}), m= {} b= {}", this.getClass(), new Object[] {w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY(), m});


		w.setStartX(w.getStartX() - (int)(worldOffset.x * scale) );
		w.setEndX(w.getEndX() - (int)(worldOffset.x * scale));
		w.setStartY(w.getStartY() - (int)(worldOffset.y * scale));
		w.setEndY(w.getEndY() - (int)(worldOffset.y * scale));

		w.setStartX(w.getStartX() + canvasOffset.x);
		w.setStartY(w.getStartY() + canvasOffset.y);
		w.setEndX(w.getEndX() + canvasOffset.x);
		w.setEndY(w.getEndY() + canvasOffset.y);

		//Left side and bottom of plane intersection
		Point intersectionStartY = RenderUtils.getIntersectionStartY(w, canvasOffset.y);
		Point intersectionStartX = RenderUtils.getIntersectionStartX(w, canvasOffset.x);

		//right side and top of plane intersection
		Point intersectionEndY = RenderUtils.getIntersectionEndY(w, canvasOffset.y);
		Point intersectionEndX = RenderUtils.getIntersectionEndX(w, canvasOffset.x);

		//LogUtils.logInfo("intersectionEndY: {} intersectionEndX: {}", this.getClass(), new Object[] {intersectionEndY.y, intersectionEndX.x});


		/*g.setColor(Color.pink);
		g.fillOval(canvasOffset - 6, intersectionStartY.y - 6, 12, 12);
		g.fillOval(intersectionStartX.x - 6, canvasOffset - 6, 12, 12);

		g.fillOval(maxWidth - 6, intersectionEndY.y - 6, 12, 12);
		g.fillOval(intersectionEndX.x - 6, maxWidth - 6, 12, 12);*/




			//Startpoint boundaries
			Wall cutOffWall = new Wall(w.getStartX(), w.getStartY(), w.getEndX(), w.getEndY());

			//Endpoint
			if(w.getStartX() < canvasOffset.x) {
				cutOffWall.setStartY(intersectionStartY.y);
				cutOffWall.setStartX(canvasOffset.x);
			}

			if(w.getStartX() > rpWidth) {
				cutOffWall.setStartY(intersectionEndY.y);
				cutOffWall.setStartX(canvasOffset.x + rpWidth);
			}

			if(w.getStartY() < canvasOffset.y) {
				cutOffWall.setStartX(intersectionStartX.x);
				cutOffWall.setStartY(canvasOffset.y);
			}
			if(w.getStartY() > rpHeight) {
				cutOffWall.setStartX(intersectionEndX.x);
				cutOffWall.setStartY(canvasOffset.y + rpHeight);
			}

			if(w.getEndX() < canvasOffset.x) {
				cutOffWall.setEndY(intersectionStartY.y);
				cutOffWall.setEndX(canvasOffset.x);
			}

			if(w.getEndX() > rpWidth) {
				cutOffWall.setEndY(intersectionEndY.y);
				cutOffWall.setEndX(canvasOffset.x + rpWidth);
			}

			if(w.getEndY() < canvasOffset.y) {
				cutOffWall.setEndX(intersectionStartX.x);
				cutOffWall.setEndY(canvasOffset.y);
			}
			if(w.getEndY() > rpHeight) {
				cutOffWall.setEndX(intersectionEndX.x);
				cutOffWall.setEndY(canvasOffset.y + rpHeight);
			}

			//LogUtils.logInfo("start ({}, {}) end ({},{})", this.getClass(), new Object[] {cutOffWall.getStartX(), cutOffWall.getStartY(), cutOffWall.getEndX(), cutOffWall.getEndY()});

			if(!RenderUtils.determinPointOnScreen(new Point(cutOffWall.getStartX(), cutOffWall.getStartY()), canvasOffset.x, rpWidth) &&
					!RenderUtils.determinPointOnScreen(new Point(cutOffWall.getEndX(), cutOffWall.getEndY()), canvasOffset.y, rpHeight)) {

			} else RenderUtils.drawLineBetweenPoints(g, cutOffWall, c);


	}

	@Override
	public void drawVertex(Graphics g, Environment env, Point p, Point canvasOffset, Point worldOffset, double scale, int size, Color c) {


		Point newP = RenderUtils.convertPointToScreenSize(env, p, super.getRenderPaneWidth(), super.getRenderPaneHeight(), scale);

		//LogUtils.logInfo("Point: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

		newP.x -= (int) (worldOffset.x * scale);
		newP.y -= (int) (worldOffset.y * scale);

		newP.x += canvasOffset.x;
		newP.y += canvasOffset.y;

		//LogUtils.logInfo("Point after scaling: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

		if((newP.x >= canvasOffset.x && newP.x <= super.getRenderPaneWidth() + canvasOffset.x) && (newP.y >= canvasOffset.y && newP.y <= super.getRenderPaneHeight() + canvasOffset.y)) {
			g.setColor(c == null? Color.red : c);
			g.fillOval(newP.x - (int)(size/2), newP.y - (int)(size/2),size,size);
			g.drawString(p.x + ", " + p.y, newP.x - size, newP.y - size);
		}
	}


	public void drawPlayer(Graphics g, Environment env,  Player p, Point canvasOffset) {

		p = RenderUtils.convertPlayerPositionsToScreenSize(p, env, super.getRenderPaneWidth(), super.getRenderPaneHeight());

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
					(int) Math.round(-calcVals.cos(newRotation)*15 + p.getPosX()), //Position X, length of 15
					(int) Math.round(calcVals.sin(newRotation)*15 + p.getPosY())
				);


	}



	// 700 = -700*0 + 0

}
