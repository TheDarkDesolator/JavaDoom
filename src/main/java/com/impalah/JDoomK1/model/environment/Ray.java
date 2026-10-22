package com.impalah.JDoomK1.model.environment;

import java.awt.*;
import java.awt.geom.Point2D;

public class Ray {

    private Point startingPoint;
    private Point intersectionPoint;
    private double distance;
    private Wall wall;
    private boolean isEdge;

    public Ray(){

    }

    public Ray(Point s, Point i){
        this.startingPoint = s;
        this.intersectionPoint = i;
    }

    public Ray(Point s){
        this.startingPoint = s;
        this.intersectionPoint = new Point(9999,9999);
    }

    public void calculateDistance(double rotation){
        this.distance = Point2D.distance(startingPoint.x, startingPoint.y, intersectionPoint.x, intersectionPoint.y) * Math.cos(Math.toRadians(rotation));
    }

    public Point getStartingPoint() {
        return startingPoint;
    }

    public double getDistance(){
        return this.distance;
    }

    public void setStartingPoint(Point startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Point getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(Point intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public Wall getWall() {
        return wall;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public void setEdge(boolean edge) {
        isEdge = edge;
    }
}
