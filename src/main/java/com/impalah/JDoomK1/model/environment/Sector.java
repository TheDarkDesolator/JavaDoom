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
	private List<Wall> vertices;
	private Color color;
	
	public Sector() {
		this.vertices = new ArrayList<Wall>();
		this.color = Color.red;
		this.id = UUID.randomUUID();
	}

	public Sector(List<Wall> vertices, Color color) {
		super();
		this.vertices = vertices;
		this.color = color;
		this.id = UUID.randomUUID();
	}
	
	public void addVertex(Wall v) {
		if(vertices.size() > 0) {
			boolean foundV = false;
			for (Wall vertex : vertices) {
				//vertex exists
				if (v.compareTo(vertex) == 0) {
					foundV = true;
				}
			}
			
			if(!foundV) {
				vertices.add(v);
			}
			
		} else {
			vertices.add(v);
		}
	}
	
	public List<Point> getUniquePoints() {
		List<Point> points = new ArrayList<>();
		for (Wall wall : vertices) {
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

	public List<Wall> getVertices() {
		return vertices;
	}

	public void setVertices(List<Wall> vertices) {
		this.vertices = vertices;
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
