package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;

import java.awt.*;

public class RaycastRenderer extends AbstractRenderEngine{

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
}
