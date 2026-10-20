package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;

public interface IRenderWorld {

     void drawMap(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale, Player p);

    void drawVertex(Graphics g, Environment env, Point p, Point canvasOffset, Point worldOffset, double scale, int size, Color c);
    void drawWall(Graphics g, Environment env, Wall wall, Point canvasOffset, Point worldOffset, double scale, int rotation, Color c);
    void drawPlayer(Graphics g, Environment env, Player p, Point canvasOffset);
    void drawSector(Graphics g, Environment env, Point canvasOffset, Point worldOffset, double scale,int rotation,  Sector s, Color c);




    }
