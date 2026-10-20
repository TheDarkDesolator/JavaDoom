package com.impalah.JDoomK1.model.environment;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Sector {
	private UUID id;
	private List<Wall> walls;
	private Color color;
	
	public Sector() {
		this.walls = new ArrayList<Wall>();
		this.color = Color.red;
		this.id = UUID.randomUUID();
	}

	public Sector(List<Wall> walls, Color color) {
		this.walls = walls;
		this.color = color;
		this.id = UUID.randomUUID();
	}
	
	public static Sector createFromPoints(List<Point> points, Color color) {
		List<Wall> walls = new ArrayList<>();
		for (int i = 0; i < points.size(); i++) {
			Point currentPoint = points.get(i);
			Point nextPoint = new Point();
			if(i +1 == points.size()) {
				nextPoint = points.get(0);
				 
			} else nextPoint = points.get(i+1);
			
			walls.add(new Wall(currentPoint, nextPoint));
		}
		
		return new Sector(walls, color);
	}
	
	public void addVertex(Wall v) {
		if(walls.size() > 0) {
			boolean foundV = false;
			for (Wall vertex : walls) {
				//vertex exists
				if (v.compareTo(vertex) == 0) {
					foundV = true;
				}
			}
			
			if(!foundV) {
				walls.add(v);
			}
			
		} else {
			walls.add(v);
		}
	}
	
	public List<Point> getUniquePoints() {
		List<Point> points = new ArrayList<>();
		for (Wall wall : walls) {
			Point startPoint = new Point(wall.getStartX(), wall.getStartY());
			Point endPoint = new Point(wall.getEndX(), wall.getEndY());
			
			if(!points.contains(startPoint)) {
				points.add(startPoint);
			}
			
			if(!points.contains(endPoint)) {
				points.add(endPoint);
			}
		}
		
		return points;
	}
	
	public Point getSectorCenter() {
		List<Double> xVals = this.getUniquePoints().stream()
				 .map(p -> p.getX())
				 .collect(Collectors.toList());
		List<Double> yVals = this.getUniquePoints().stream()
		.map(p -> p.getY())
		.collect(Collectors.toList());
		
		return new Point( (int) Math.round(Collections.max(xVals) - Collections.min(xVals)),
		(int) Math.round(Collections.max(yVals) - Collections.min(yVals)));
	}
	
	public Point getSectorNW() {
		List<Double> xVals = this.getUniquePoints().stream()
				 .map(p -> p.getX())
				 .collect(Collectors.toList());
		List<Double> yVals = this.getUniquePoints().stream()
		.map(p -> p.getY())
		.collect(Collectors.toList());
		
		return new Point((int) Math.round(Collections.min(xVals)), (int) Math.round( Collections.max(yVals)));
	}
	
	public Point getSectorNE() {
		List<Double> xVals = this.getUniquePoints().stream()
				 .map(p -> p.getX())
				 .collect(Collectors.toList());
		List<Double> yVals = this.getUniquePoints().stream()
		.map(p -> p.getY())
		.collect(Collectors.toList());
		
		return new Point((int) Math.round(Collections.max(xVals)), (int) Math.round( Collections.max(yVals)));
	}
	
	public Point getSectorSE() {
		List<Double> xVals = this.getUniquePoints().stream()
				 .map(p -> p.getX())
				 .collect(Collectors.toList());
		List<Double> yVals = this.getUniquePoints().stream()
		.map(p -> p.getY())
		.collect(Collectors.toList());
		
		return new Point((int) Math.round(Collections.max(xVals)), (int) Math.round( Collections.min(yVals)));
	}
	
	public Point getSectorSW() {
		List<Double> xVals = this.getUniquePoints().stream()
				 .map(p -> p.getX())
				 .collect(Collectors.toList());
		List<Double> yVals = this.getUniquePoints().stream()
		.map(p -> p.getY())
		.collect(Collectors.toList());
		
		return new Point((int) Math.round(Collections.min(xVals)), (int) Math.round( Collections.min(yVals)));
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public UUID getId() {
		return id;
	}
	
	
	
	
	
	
}
