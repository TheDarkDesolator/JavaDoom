package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;


@Component
public class MovingWorldRenderer implements IRenderWorld{

    private CalculationValues calcVals;
    private Environment env;
    private Player player;


    public MovingWorldRenderer(){
        this.calcVals = new CalculationValues();
    }

    public MovingWorldRenderer(Environment env, Player player) {
        this.env = env;
        this.player = player;
        this.calcVals = new CalculationValues();

    }

    @Override
    public void drawMap(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, Player p) {

        Player convertedPlayer = RenderUtils.convertPlayerPositionsToScreenSize(player, env, maxWidth, maxHeight);


        int newOffsetX = convertedPlayer.getOriginX() - convertedPlayer.getPosX();
        int newOffsetY = convertedPlayer.getOriginY() - convertedPlayer.getPosY();

        RenderUtils.drawDebugData(g, 10, 30, "Offset X: " + newOffsetX + " Offset Y: " + newOffsetY);


        env.sectors.forEach(sector -> drawSector(g, env, maxWidth, maxHeight, canvasOffset, newOffsetX, newOffsetY, scale,p.getRotation(), sector, Color.RED));
    }

    @Override
    public void drawVertex(Graphics g, Environment env, Point point, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int size, Color c) {
        Point newP = RenderUtils.convertPointToScreenSize(env, point, maxWidth-canvasOffset, maxHeight-canvasOffset, scale);

        //LogUtils.logInfo("Point: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

        int newRotation = player.getRotation();

        if(newRotation > 359) newRotation = newRotation - 359;

        Player p = RenderUtils.convertPlayerPositionsToScreenSize(player,env, maxWidth, maxHeight);

        //Formulas for point rotation around origin
        //newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        //newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);

        newP.x = (int) (p.getOriginX() + ((newP.x) - p.getOriginX() + offsetX) * calcVals.cos(newRotation) - ((newP.y) - p.getOriginY() + offsetY) * calcVals.sin(newRotation));
        newP.y = (int) (p.getOriginY() + ((newP.x) - p.getOriginX() + offsetX) * calcVals.sin(newRotation) + ((newP.y)- p.getOriginY() + offsetY) * calcVals.cos(newRotation));

        //LogUtils.logInfo("Point after scaling: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

        if((newP.x >= canvasOffset && newP.x <= maxWidth) && (newP.y >= canvasOffset && newP.y <= maxHeight)) {
            g.setColor(c == null? Color.red : c);
            g.fillOval(newP.x - (int)(size/2), newP.y - (int)(size/2),size,size);
            g.drawString(point.x + ", " + point.y, newP.x - size, newP.y - size);
        }
    }

    public void drawPoint(Graphics g, Point p,Point textPoint, int size, Color c) {
        g.setColor(c == null? Color.red : c);
        g.fillOval(p.x - (int)(size/2), p.y - (int)(size/2),size,size);
        g.drawString(textPoint.x + ", " + textPoint.y, p.x - size, p.y - size);
    }

    @Override
    public void drawWall(Graphics g, Environment env, Wall wall, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int rotation, Color c) {
        Wall w = RenderUtils.convertWallToScreenSize(env, wall, maxWidth - canvasOffset, maxHeight - canvasOffset, scale);


        int newRotation = player.getRotation();

        if(newRotation > 359) newRotation = newRotation - 359;

        Player p = RenderUtils.convertPlayerPositionsToScreenSize(player,env, maxWidth, maxHeight);

        //Formulas for point rotation around origin
        //newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        //newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);

        int newStartX = (int) (p.getOriginX() + ((w.getStartX()) - p.getOriginX() + offsetX) * calcVals.cos(newRotation) - ((w.getStartY()) - p.getOriginY() + offsetY) * calcVals.sin(newRotation));
        int newStartY = (int) (p.getOriginY() + ((w.getStartX()) - p.getOriginX() + offsetX) * calcVals.sin(newRotation) + ((w.getStartY())- p.getOriginY() + offsetY) * calcVals.cos(newRotation));

        drawPoint(g, new Point(newStartX, newStartY), new Point(w.getStartX(), w.getStartY()), 6, Color.red);

        int newEndX = (int) (p.getOriginX() + ((w.getEndX()) - p.getOriginX() + offsetX) * calcVals.cos(newRotation) - ((w.getEndY()) - p.getOriginY() + offsetY) * calcVals.sin(newRotation));
        int newEndY = (int) (p.getOriginY() + ((w.getEndX()) - p.getOriginX() + offsetX) * calcVals.sin(newRotation) + ((w.getEndY()) - p.getOriginY() + offsetY) * calcVals.cos(newRotation));

        w.setStartX(newStartX);
        w.setStartY(newStartY);

        w.setEndX(newEndX);
        w.setEndY(newEndY);


        RenderUtils.drawLineBetweenVertices(g, w, c);
    }



    @Override
    public void drawPlayer(Graphics g, Environment env, Player p, int maxWidth, int maxHeight) {
        int y = maxHeight/2;
        int x = maxWidth/2;
        g.setColor(Color.white);

        g.fillOval(x-8, y-8, 16, 16);

        //45° offset for some reason
        int newRotation = player.getRotation() + 90;

        if(newRotation > 359) newRotation = newRotation - 359;


        g.setColor(Color.RED);
        /*g.drawLine(x,
                y,
                (int) Math.round(-calcVals.cos(newRotation)*15 + x),
                (int) Math.round(calcVals.sin(newRotation)*15 + y)
        );*/
        g.drawLine(x, y, x,y-15);
    }

    @Override
    public void drawSector(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale,int rotation,  Sector s, Color c) {
        s.getVertices().forEach(wall -> drawWall(g, env, wall, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale,rotation, c));
        //s.getUniquePoints().forEach(p -> drawVertex(g, env, p, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, 6, c));
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public Environment getEnv() {
        return env;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
