package com.impalah.JDoomK1.model.environment;

import java.awt.Point;
import java.util.Map;

public class Wall implements Comparable<Wall> {
	
	public int startX;
	public int startY;
	
	public int endX;
	public int endY;
	
	
	public Wall() {
		this.startX = 0;
		this.startY = 0;
		this.endX = 0;
		this.endY = 0;
	}
	
	
	public Wall(int startX, int startY, int endX, int endY) {
		super();
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}
	
	public Wall(Point start, Point end) {
		this.startX = start.x;
		this.startY = start.y;
		this.endX = end.x;
		this.endY = end.y;
	}




	public int getStartX() {
		return startX;
	}




	public void setStartX(int startX) {
		this.startX = startX;
	}




	public int getStartY() {
		return startY;
	}




	public void setStartY(int startY) {
		this.startY = startY;
	}




	public int getEndX() {
		return endX;
	}




	public void setEndX(int endX) {
		this.endX = endX;
	}




	public int getEndY() {
		return endY;
	}




	public void setEndY(int endY) {
		this.endY = endY;
	}




	@Override
	public int compareTo(Wall o) {
		if(startX == o.startX && startY == o.startY && endX == o.endX && endY == o.endY) return 0;
		
		return 1;
	}
	
	

}
