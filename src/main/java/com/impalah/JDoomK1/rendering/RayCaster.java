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
import java.util.List;

public class RayCaster {

    private List<Ray> rays = new ArrayList<>();
    private CalculationValues calcVals = new CalculationValues();

    public RayCaster(){

    }

    public void cast(Player p, Environment e){



        Point start = new Point(p.getPosX(), p.getPosY());

        double lowerFOV = p.getRotation() + 45;
        double upperFOV = p.getRotation() + 135;
        rays = new ArrayList<>();


        for (double i = lowerFOV; i < upperFOV; i+= 0.1) {
            Ray r = new Ray(start);

            double shortestDistance = Double.MAX_VALUE;
            Point nearest = null;

            for (Wall wall : e.getAllWalls()) {

                Point hit = RenderUtils.getWallIntersectionRadial(wall, start, -i);
                if (hit == null) continue;

                double distance = hit.distance(start);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearest = hit;
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

            rays.add(r);
        }


    }

    public List<Ray> getRays() {
        return rays;
    }
}
