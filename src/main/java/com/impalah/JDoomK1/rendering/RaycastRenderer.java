package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Ray;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;

import java.awt.*;
import java.util.List;

public class RaycastRenderer extends AbstractRenderEngine{

    private final int WALL_HEIGHT = 240;

    public RaycastRenderer(int rpX, int rpY, Environment e, Player p){
        super(rpX, rpY, e, p);
    }

    public RaycastRenderer(int rpX, int rpY){
        super(rpX, rpY);
    }


    @Override
    public void drawMap(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, Player p) {

    }

    @Override
    public void drawVertex(Graphics g, Environment env, Point p, Point canvasOffset, Point worldOffset, double scale, int size, Color c) {

    }

    @Override
    public void drawWall(Graphics g, Environment env, Wall wall, Point canvasOffset, Point worldOffset, double scale, int rotation, Color c) {

    }

    @Override
    public void drawPlayer(Graphics g, Environment env, Player p, Point canvasOffset) {

    }

    @Override
    public void drawSector(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, int rotation, Sector s, Color c) {

    }

    public void drawMapByRays(Graphics g, List<Ray> rays){

        int totalRayHeight = super.getEnv().getPanelHeight();
        for (int i = 0; i < rays.size(); i++) {
            //ray drawn exists of floor, wall and ceiling so need 3 lines
            Ray currentRay = rays.get(i);

            double projectedSLiceHeight = WALL_HEIGHT / currentRay.getDistance() * 1000;

            double wallTop    = (super.getEnv().getPanelHeight() - projectedSLiceHeight) / 2;
            double wallBottom = (super.getEnv().getPanelHeight() + projectedSLiceHeight) / 2;

            Wall floorToWall = new Wall(
                                        new Point(i, super.getEnv().getPanelHeight()),
                                        new Point(i, (int) wallBottom)
            );
            Wall wallSlice = new Wall(
                    new Point(i, (int) wallTop),
                    new Point(i, (int) wallBottom)
            );
            Wall ceilingToWall = new Wall(
                    new Point(i, 0),
                    new Point(i, (int) wallTop)
            );

            RenderUtils.drawColumn(g, floorToWall, Color.white);
            if(currentRay.isEdge()){

                RenderUtils.drawColumn(g, wallSlice, Color.black);
            } else {

                RenderUtils.drawColumn(g, wallSlice, currentRay.getWall().getColor());
            }
            RenderUtils.drawColumn(g, ceilingToWall, Color.darkGray);
            //RenderUtils.drawLineBetweenPoints(g, new Point(i,0), new Point(i, super.getEnv().getPanelHeight()), currentRay.getWall().getColor());
        }
    }


}
