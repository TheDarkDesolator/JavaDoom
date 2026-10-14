package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;

public interface IRenderWorld {

     void drawMap(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, Player p);

    void drawVertex(Graphics g, Environment env, Point p, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int size, Color c);
    void drawWall(Graphics g, Environment env, Wall wall, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale, int rotation, Color c);
    void drawPlayer(Graphics g, Environment env, Player p, int maxWidth, int maxHeight);
    void drawSector(Graphics g, Environment env, int maxWidth, int maxHeight, int canvasOffset, int offsetX, int offsetY, double scale,int rotation,  Sector s, Color c);




    }
