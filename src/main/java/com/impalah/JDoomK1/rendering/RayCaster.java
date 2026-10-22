package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Ray;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.util.LogUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RayCaster {

    private List<Ray> rays = new ArrayList<>();
    private CalculationValues calcVals = new CalculationValues();
    private int amountOfRays = 900;

    public RayCaster(int amountOfRays){
        this.amountOfRays = amountOfRays;
    }

    public void cast(Player p, Environment e){

        Point start = new Point(p.getPosX(), p.getPosY());

        double lowerFOV = p.getRotation() + 45;
        double upperFOV = p.getRotation() + 135;
        double raySteps = (double) 90 /amountOfRays;
        rays = new ArrayList<>();


        for (double i = lowerFOV; i < upperFOV; i+= raySteps) {
            Ray r = new Ray(start);

            /*
             * Flat-screen ray angle (corrects the distortion from equally spaced angles)
             *
             * The loop walks i in equal angle steps from (rotation + 45) to (rotation + 135).
             * Screen columns, however, sit at equal distances on a flat projection plane, and
             * equal angles do not map to equal distances on a plane (the relationship is a
             * tangent). Left alone, straight walls bend. This line re-maps each equal-step
             * angle to the angle at which that column really sits on a flat screen.
             *
             * facing    = p.getRotation() + 90            centre of the view, in degrees
             * offset    = i - facing                      how far this ray is from the centre,
             *                                             -45 (left edge) to +45 (right edge)
             * position  = offset / 45                     the same thing as a fraction of the half-view,
             *                                             -1 (left edge) to +1 (right edge)
             * screenPos = tan(45 degrees) * position      where that column lies on the flat plane,
             *                                             in units of the plane's half-width. Edges stay
             *                                             at +/-1 because tan(45 degrees) = 1, so the
             *                                             edge rays do not move.
             * angle     = facing + atan(screenPos)        the angle the ray must point at, converted
             *                                             from radians back to degrees
             *
             * The 45 appears twice and means "half the field of view in degrees". If the FOV
             * changes, change both occurrences.
             *
             * The same angle must be used for the fisheye correction:
             *     beta = angle - facing
             * That is, the ray's real angle from the view centre, not the equal-step one.
             */
            double angle = (p.getRotation() + 90) + Math.toDegrees(Math.atan(Math.tan(Math.toRadians(45)) * (i - (p.getRotation() + 90)) / 45));

            double shortestDistance = Double.MAX_VALUE;
            Point nearest = null;
            Wall nearestWall = null;

            for (Wall wall : e.getAllWalls()) {

                Point hit = RenderUtils.getWallIntersectionRadial(wall, start, -angle);
                if (hit == null) continue;

                double distance = hit.distance(start);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearest = hit;
                    nearestWall = wall;
                }
            }

            if (nearest == null) {
                // Nothing hit: extend the ray far in the facing direction so it can still be drawn
                double rad = Math.toRadians(p.getRotation());
                int maxLength = 2000;
                nearest = new Point(
                        (int) Math.round(start.x + Math.cos(rad) * maxLength),
                        (int) Math.round(start.y + Math.sin(rad) * maxLength));
            }

            if(nearestWall != null){
                if(nearest.equals(new Point(nearestWall.getStartX(), nearestWall.getStartY())) || nearest.equals(new Point(nearestWall.getEndX(), nearestWall.getEndY()))){
                    r.setEdge(true);
                }
            }

            r.setIntersectionPoint(nearest);
            r.setWall(nearestWall);
            //Beta is how far the ray sits from the centre of the 'fan of rays' (-45 to +45)
            double beta = angle - (p.getRotation() + 90);
            r.calculateDistance(beta);

            rays.add(r);
        }

        Collections.reverse(rays);


    }

    public List<Ray> getRays() {
        return rays;
    }
}
