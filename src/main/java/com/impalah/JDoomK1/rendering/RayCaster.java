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

            double shortestDistance = Double.MAX_VALUE;
            Point nearest = null;
            Wall nearestWall = null;

            for (Wall wall : e.getAllWalls()) {

                Point hit = RenderUtils.getWallIntersectionRadial(wall, start, -i);
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

            r.setIntersectionPoint(nearest);
            r.setWall(nearestWall);
            //Beta is how far the ray sits from the centre of the 'fan of rays' (-45 to +45)
            double beta = i - (p.getRotation() + 90);
            r.calculateDistance(beta);

            rays.add(r);
        }

        Collections.reverse(rays);


    }

    public List<Ray> getRays() {
        return rays;
    }
}
