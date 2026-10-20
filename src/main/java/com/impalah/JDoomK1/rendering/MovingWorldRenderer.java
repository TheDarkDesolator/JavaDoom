package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Ray;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;


@Component
public class MovingWorldRenderer extends AbstractRenderEngine{

    public MovingWorldRenderer(int rpX, int rpY){
        super(rpX, rpY);
    }

    public MovingWorldRenderer(int rpX, int rpY, Environment e, Player p) {
        super(rpX, rpY, e, p);
    }


    @Override
    public void drawMap(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, Player p) {

        Player convertedPlayer = RenderUtils.convertPlayerPositionsToScreenSize(super.getPlayer(), env, super.getRenderPaneWidth(), super.getRenderPaneHeight());


        int newOffsetX = convertedPlayer.getOriginX() - convertedPlayer.getPosX();
        int newOffsetY = convertedPlayer.getOriginY() - convertedPlayer.getPosY();

        RenderUtils.drawDebugData(g, 10, 30, "Offset X: " + newOffsetX + " Offset Y: " + newOffsetY);


        env.sectors.forEach(sector -> drawSector(g, env, canvasOffset, new Point(newOffsetX, newOffsetY), scale,p.getRotation(), sector, sector.getColor()));
    }

    private Point calculatePlayerOffsetToScreen(Environment env){
        Player convertedPlayer = RenderUtils.convertPlayerPositionsToScreenSize(super.getPlayer(), env, super.getRenderPaneWidth(), super.getRenderPaneHeight());

        int newOffsetX = convertedPlayer.getOriginX() - convertedPlayer.getPosX();
        int newOffsetY = convertedPlayer.getOriginY() - convertedPlayer.getPosY();

        return new Point(newOffsetX, newOffsetY);
    }

    @Override
    public void drawVertex(Graphics g, Environment env, Point point, Point canvasOffset, Point worldOffset, double scale, int size, Color c) {
        Point newP = RenderUtils.convertPointToScreenSize(env, point, super.getRenderPaneWidth()-canvasOffset.x, super.getRenderPaneHeight()-canvasOffset.y, scale);

        //LogUtils.logInfo("Point: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

        int newRotation = super.getPlayer().getRotation();

        if(newRotation > 359) newRotation = newRotation - 359;

        Player p = RenderUtils.convertPlayerPositionsToScreenSize(super.getPlayer(),env, super.getRenderPaneWidth(), super.getRenderPaneHeight());

        //Formulas for point rotation around origin
        //newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        //newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);

        newP.x = (int) (p.getOriginX() + ((newP.x) - p.getOriginX() + worldOffset.x) * super.getCalcVals().cos(newRotation) - ((newP.y) - p.getOriginY() + worldOffset.y) * super.getCalcVals().sin(newRotation));
        newP.y = (int) (p.getOriginY() + ((newP.x) - p.getOriginX() + worldOffset.x) * super.getCalcVals().sin(newRotation) + ((newP.y)- p.getOriginY() + worldOffset.y) * super.getCalcVals().cos(newRotation));

        //LogUtils.logInfo("Point after scaling: {} - {}", this.getClass(), new Object[] {newP.x, newP.y});

        /*
        if((newP.x >= canvasOffset.x && newP.x <= super.getRenderPaneWidth()) && (newP.y >= canvasOffset.y && newP.y <= super.getRenderPaneHeight())) {
            g.setColor(c == null? Color.red : c);
            g.fillOval(newP.x - (int)(size/2), newP.y - (int)(size/2),size,size);
            g.drawString(point.x + ", " + point.y, newP.x - size, newP.y - size);
        }*/
    }

    public void drawPoint(Graphics g, Point p,Point textPoint, int size, Color c) {
        g.setColor(c == null? Color.red : c);
        g.fillOval(p.x - (int)(size/2), p.y - (int)(size/2),size,size);
        g.drawString(textPoint.x + ", " + textPoint.y, p.x - size, p.y - size);
    }

    @Override
    public void drawWall(Graphics g, Environment env, Wall wall, Point canvasOffset, Point worldOffset, double scale, int rotation, Color c) {
        Wall w = RenderUtils.convertWallToScreenSize(env, wall, super.getRenderPaneWidth(), super.getRenderPaneHeight(), scale);


        int newRotation = super.getPlayer().getRotation();

        if(newRotation > 359) newRotation = newRotation - 359;

        Player p = RenderUtils.convertPlayerPositionsToScreenSize(super.getPlayer(),env, super.getRenderPaneWidth(), super.getRenderPaneHeight());

        //Formulas for point rotation around origin
        //newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        //newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);

        int newStartX = (int) (p.getOriginX() + ((w.getStartX()) - p.getOriginX() + worldOffset.x) * super.getCalcVals().cos(newRotation) - ((w.getStartY()) - p.getOriginY() + worldOffset.y) * super.getCalcVals().sin(newRotation));
        int newStartY = (int) (p.getOriginY() + ((w.getStartX()) - p.getOriginX() + worldOffset.x) * super.getCalcVals().sin(newRotation) + ((w.getStartY())- p.getOriginY() + worldOffset.y) * super.getCalcVals().cos(newRotation));

        //drawPoint(g, new Point(newStartX + canvasOffset.x, newStartY + canvasOffset.y), new Point(w.getStartX() + canvasOffset.x, w.getStartY() + canvasOffset.y), 2, Color.red);

        int newEndX = (int) (p.getOriginX() + ((w.getEndX()) - p.getOriginX() + worldOffset.x) * super.getCalcVals().cos(newRotation) - ((w.getEndY()) - p.getOriginY() + worldOffset.y) * super.getCalcVals().sin(newRotation));
        int newEndY = (int) (p.getOriginY() + ((w.getEndX()) - p.getOriginX() + worldOffset.x) * super.getCalcVals().sin(newRotation) + ((w.getEndY()) - p.getOriginY() + worldOffset.y) * super.getCalcVals().cos(newRotation));

        w.setStartX(newStartX + canvasOffset.x);
        w.setStartY(newStartY + canvasOffset.y);

        w.setEndX(newEndX + canvasOffset.x);
        w.setEndY(newEndY + canvasOffset.y);


        RenderUtils.drawLineBetweenPoints(g, w, c);
    }



    @Override
    public void drawPlayer(Graphics g, Environment env, Player p, Point canvasOffset) {
        int y = (super.getRenderPaneHeight()/2) + canvasOffset.y;
        int x = (super.getRenderPaneWidth()/2) + canvasOffset.x;
        g.setColor(Color.white);

        g.fillOval(x-3, y-3, 6, 6);

        //45° offset for some reason
        int newRotation = super.getPlayer().getRotation() + 90;

        if(newRotation > 359) newRotation = newRotation - 359;


        g.setColor(Color.RED);
        /*g.drawLine(x,
                y,
                (int) Math.round(-super.getCalcVals().cos(newRotation)*15 + x),
                (int) Math.round(super.getCalcVals().sin(newRotation)*15 + y)
        );*/

        //LogUtils.logInfo("Player drawn at: ({}, {})", this.getClass(), new Object[]{x, y});
        g.drawLine(x, y, x,y-15);
    }

    @Override
    public void drawSector(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale,int rotation,  Sector s, Color c) {
        s.getWalls().forEach(wall -> drawWall(g, env, wall, canvasOffset, worldOffset, scale,rotation, c));
        //s.getUniquePoints().forEach(p -> drawVertex(g, env, p, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale, 6, c));
    }

    public void drawRays(Graphics g, List<Ray> rays, Point canvasOffset){
            Point playerMovementOffset = calculatePlayerOffsetToScreen(super.getEnv());
            rays.forEach(r -> drawWall(g, super.getEnv(), new Wall(r.getStartingPoint(), r.getIntersectionPoint()) , canvasOffset, new Point(playerMovementOffset.x, playerMovementOffset.y), 1, super.getPlayer().getRotation(), Color.YELLOW));
    }
}
