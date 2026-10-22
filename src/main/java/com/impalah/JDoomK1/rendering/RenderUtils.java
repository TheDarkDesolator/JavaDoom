package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Ray;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class RenderUtils {

    public static Player convertPlayerPositionsToScreenSize(Player p, Environment env, int renderPaneWidth, int renderPaneHeight){

        Player newP = new Player();


        double multiplierX = (double) renderPaneWidth / env.getMAX_X();
        double multiplierY = (double) renderPaneHeight / env.getMAX_Y();

        newP.setPosX ((int) Math.round(p.getPosX() * multiplierX));
        newP.setPosY((int) Math.round(p.getPosY() * multiplierY));
        newP.setOriginX ((int) Math.round(p.getOriginX() * multiplierX));
        newP.setOriginY((int) Math.round(p.getOriginY() * multiplierY));


        return newP;

    }

    public Wall convertWallAroundPlayer(Wall w){
        return null;
    }
    

    public static Wall convertWallToScreenSize(Environment env, Wall v, int renderPaneWidth, int renderPaneHeight, double scale) {
        Wall newVertex = new Wall();

        double multiplierX = (double) renderPaneWidth / env.getMAX_X();
        double multiplierY = (double) renderPaneHeight / env.getMAX_Y();

        newVertex.setStartX((int) ((v.getStartX() * scale) * multiplierX));
        newVertex.setStartY((int) ((v.getStartY() * scale) * multiplierY));
        newVertex.setEndX((int) ((v.getEndX() * scale) * multiplierX));
        newVertex.setEndY((int) ((v.getEndY() * scale) * multiplierY));

        return newVertex;

    }

    public static Point convertPointToScreenSize(Environment env, Point p, int renderPaneWidth, int renderPaneHeight, double scale) {

        double multiplierX = (double) renderPaneWidth / env.getMAX_X();
        double multiplierY = (double) renderPaneHeight / env.getMAX_Y();

        int newX = (int) (p.x * multiplierX * scale);
        int newY = (int) (p.y * multiplierY * scale);

        return new Point(newX, newY );
    }

    public static Point convertPointToEnvironmentSize(Environment env, Point p, int renderPaneWidth, int renderPaneHeight) {

        double multiplierX = (double)  env.getMAX_X() / renderPaneWidth;
        double multiplierY = (double) env.getMAX_Y() / renderPaneHeight;

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


    public static Point getIntersectionStartX(Wall  w, int canvasOffset) {

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

    public static boolean determinPointOnScreen(Point p, int canvasOffset, int renderPaneWidth) {
        if(p.x > canvasOffset && p.x < renderPaneWidth && p.y > canvasOffset && p.y < renderPaneWidth ) {
            return true;
        } else return false;
    }

    public static void drawLineBetweenPoints(Graphics g, Wall v, Color c) {
        g.setColor(c);
        g.drawLine(v.startX,
                v.startY,
                v.endX,
                v.endY);
    }

    public static void drawColumn(Graphics g, Wall v, Color c) {
        int top = Math.min(v.startY, v.endY);
        int height = Math.abs(v.endY - v.startY);

        g.setColor(c);
        g.fillRect(v.startX, top, 1, height);
    }

    public static void drawColumnImage(Graphics g, Ray r, Wall slice, Image texture) {
        Wall world = r.getWall();
        int texW = texture.getWidth(null);
        int texH = texture.getHeight(null);

        // how far along the world wall the ray landed -> which texture column
        int along = (int) Point.distance(world.getStartX(), world.getStartY(),
                r.getIntersectionPoint().x, r.getIntersectionPoint().y);
        int texX = along % texW;

        // where to draw on screen, taken from the slice
        int top = Math.min(slice.startY, slice.endY);
        int bottom = Math.max(slice.startY, slice.endY);

        g.drawImage(texture,
                slice.startX, top, slice.startX + 1, bottom,   // destination corners
                texX, 0, texX + 1, texH,                       // source: one column, full height
                null);
    }

    public static void drawLineBetweenPoints(Graphics g, Point p1, Point p2, Color c){
        drawLineBetweenPoints(g, new Wall(p1, p2), c);
    }

    public static Point getWallsIntersectionPoint(Wall w1, Wall w2){
        float m1 = ((float)w1.getStartY() - w1.getEndY()) / ((float)(w1.getStartX() - w1.getEndX() == 0 ? 1 : ((float)w1.getStartX() - w1.getEndX())));
        float b1 = w1.getStartY() - (w1.getStartX() * m1);

        float m2 = ((float)w2.getStartY() - w2.getEndY()) / ((float)(w2.getStartX() - w2.getEndX() == 0 ? 1 : ((float)w2.getStartX() - w2.getEndX())));
        float b2 = w2.getStartY() - (w2.getStartX() * m2);

        if (Math.abs(m1 - m2) < 1e-6f) return null;

        float x = (b2 - b1) / (m1 - m2);
        float y = m1 * x + b1;


        return new Point(Math.round(x),Math.round(y));
    }

    public static Point getWallIntersectionRadial(Wall wall, Point startingPoint, double rotation){
        double rad = Math.toRadians(rotation);
        double dx = Math.cos(rad);                       // ray direction
        double dy = Math.sin(rad);

        double ex = wall.getEndX() - wall.getStartX();   // wall direction
        double ey = wall.getEndY() - wall.getStartY();

        double denom = dx * ey - dy * ex;
        if (Math.abs(denom) < 1e-9) return null;         // ray parallel to wall

        double wx = wall.getStartX() - startingPoint.x;
        double wy = wall.getStartY() - startingPoint.y;

        double t = (wx * ey - wy * ex) / denom;          // distance along the ray
        double u = (wx * dy - wy * dx) / denom;          // position along the wall, 0..1

        if (t < 0) return null;                          // wall is behind the ray
        if (u < 0 || u > 1) return null;                 // crossing is beyond the wall's ends

        return new Point(
                (int) Math.round(startingPoint.x + dx * t),
                (int) Math.round(startingPoint.y + dy * t));
    }

    public static void drawDebugData(Graphics g, int posX, int posY, String text) {
        g.setColor(Color.white);
        g.drawString(text, posX, posY);
    }

    //Calculate wether point is on a line
    private static boolean isOnSegment(Wall w, float x, float y) {
        float eps = 0.5f;
        return x >= Math.min(w.getStartX(), w.getEndX()) - eps
                && x <= Math.max(w.getStartX(), w.getEndX()) + eps
                && y >= Math.min(w.getStartY(), w.getEndY()) - eps
                && y <= Math.max(w.getStartY(), w.getEndY()) + eps;
    }
}
