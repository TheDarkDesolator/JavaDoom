package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;

import java.awt.*;

public class DimensionalRenderer implements IRenderWorld{

    private CalculationValues calcVals;
    private Environment env;
    private Player player;

    public DimensionalRenderer(){
        this.calcVals = new CalculationValues();
    }

    public DimensionalRenderer(Environment env, Player player) {
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
    public void drawVertex(Graphics g, Environment env, Point p, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int size, Color c) {

    }

    @Override
    public void drawWall(Graphics g, Environment env, Wall wall, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int rotation, Color c) {
        Wall w = RenderUtils.convertWallToScreenSize(env, wall, maxWidth, maxHeight, scale);


    }

    @Override
    public void drawPlayer(Graphics g, Environment env, Player p, int maxWidth, int maxHeight) {

    }

    @Override
    public void drawSector(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int rotation, Sector s, Color c) {
        s.getVertices().forEach(wall -> drawWall(g, env, wall, maxWidth, maxHeight, canvasOffset, offsetX, offsetY, scale,rotation, c));

    }
}
