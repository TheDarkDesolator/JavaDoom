package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class RenderUtils {

    public static Player convertPlayerPositionsToScreenSize(Player p, Environment env, int maxWidth, int maxHeight){

        Player newP = new Player();


        double multiplierX = (double) maxWidth / env.getMAX_X();
        double multiplierY = (double) maxHeight / env.getMAX_Y();

        newP.setPosX ((int) Math.round(p.getPosX() * multiplierX));
        newP.setPosY((int) Math.round(p.getPosY() * multiplierY));
        newP.setOriginX ((int) Math.round(p.getOriginX() * multiplierX));
        newP.setOriginY((int) Math.round(p.getOriginY() * multiplierY));


        return newP;

    }

    public Wall convertWallAroundPlayer(Wall w){
        return null;
    }

    public static Wall convertWallToScreenSize(Environment env, Wall v, int maxWidth, int maxHeight, double scale) {
        Wall newVertex = new Wall();

        double multiplierX = (double) maxWidth / env.getMAX_X();
        double multiplierY = (double) maxHeight / env.getMAX_Y();

        newVertex.setStartX((int) ((v.getStartX() * scale) * multiplierX));
        newVertex.setStartY((int) ((v.getStartY() * scale) * multiplierY));
        newVertex.setEndX((int) ((v.getEndX() * scale) * multiplierX));
        newVertex.setEndY((int) ((v.getEndY() * scale) * multiplierY));

        return newVertex;

    }

    public static Point convertPointToScreenSize(Environment env, Point p, int maxWidth, int maxHeight, double scale) {

        double multiplierX = (double) maxWidth / env.getMAX_X();
        double multiplierY = (double) maxHeight / env.getMAX_Y();

        int newX = (int) (p.x * multiplierX * scale);
        int newY = (int) (p.y * multiplierY * scale);

        return new Point(newX, newY );
    }

    public static Point convertPointToEnvironmentSize(Environment env, Point p, int maxWidth, int maxHeight) {

        double multiplierX = (double)  env.getMAX_X() / maxWidth;
        double multiplierY = (double) env.getMAX_Y() / maxHeight;

        return new Point((int) Math.round(p.x * multiplierX), (int) Math.round(p.y * multiplierY));
    }

    public static Point getIntersectionStartY(Wall w, int canvasOffset) {
        float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
        float b = w.getStartY() - (w.getStartX() * m);

        //int x = (int)((b - 700) / (1 - b)) + canvasOffset;
        int x = canvasOffset;
        float y = m * x + b;

        return new Point(canvasOffset, (int)y);
    }


    public static Point getIntersectionStartX(Wall w, int canvasOffset) {

        if(w.getStartX() == w.getEndX()) {
            return new Point(w.getEndX(), canvasOffset);
        } else {
            float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
            float b = w.getStartY() - (w.getStartX() * m);

            //LogUtils.logInfo("X: {} Y: {}, M: {}, B: {}", this.getClass(), new Object[] {w.getEndX(), w.getEndY(), m, b});

            //0 = 401 * -0.19908814 + 240.83435

            double x = Math.round((b - 30) / (m-0));
            return new Point((int) -x, canvasOffset);
        }

    }

    public static Point getIntersectionEndY(Wall w, int canvasOffset) {
        float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
        float b = w.getStartY() - (w.getStartX() * m);

        //panelwidth  + offset;
        int x = 700+canvasOffset;
        float y = m * x + b;

        return new Point(canvasOffset, (int)y);
    }

    public static Point getIntersectionEndX(Wall w, int canvasOffset) {

        if(w.getStartX() == w.getEndX()) {
            return new Point(w.getStartX(), canvasOffset);
        } else {
            float m = ((float)w.getStartY() - w.getEndY()) / ((float)(w.getStartX() - w.getEndX() == 0 ? 1 : ((float)w.getStartX() - w.getEndX())));
            float b = w.getStartY() - (w.getStartX() * m);

            LogUtils.logInfo("X: {} Y: {}, M: {}, B: {}", RenderUtils.class, new Object[] {w.getEndX(), w.getEndY(), m, b});

            //0 = 401 * -0.19908814 + 240.83435

            double x = Math.round((b - 730) / (m-0));
            return new Point((int) -x, canvasOffset);
        }

    }

    public static boolean determinPointOnScreen(Point p, int canvasOffset, int maxWidth) {
        if(p.x > canvasOffset && p.x < maxWidth && p.y > canvasOffset && p.y < maxWidth ) {
            return true;
        } else return false;
    }

    public static void drawLineBetweenVertices(Graphics g, Wall v, Color c) {
        g.setColor(c);
        g.drawLine(v.startX,
                v.startY,
                v.endX,
                v.endY);
    }

    public static void drawDebugData(Graphics g, int posX, int posY, String text) {
        g.setColor(Color.white);
        g.drawString(text, posX, posY);
    }
}
