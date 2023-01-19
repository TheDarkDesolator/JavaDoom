package com.impalah.JDoomK1.model;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.impalah.JDoomK1.model.environment.Sector;

public class Player {
	private int posX;
	private int posY;
	private int rotation;
	private Sector currentSector;
	//player hitbox
	private final int RADIUS = 10;
	
	private final double SPEED = 5;
	private final int ROTATIONSPEED = 5;
	
	
	public Player() {
		this.posX = 0;
		this.posY = 0;
		this.rotation = 0;
	}
	
	
	public Player(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.rotation = 0;
	}



	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}


	public Sector getCurrentSector() {
		return currentSector;
	}
	
	public void centerAtCurrentSector() {
		
		setPosX(currentSector.getSectorCenter().x);
		setPosY(currentSector.getSectorCenter().y);
		
	}


	public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}


	public int getRADIUS() {
		return RADIUS;
	}


	public double getSPEED() {
		return SPEED;
	}


	public int getRotation() {
		return rotation;
	}


	public void setRotation(int rotation) {
		
		this.rotation = rotation;
	}
	
	public void addRotation(int delta) {
		
		int newRotation = getRotation() + delta;
		
		if(newRotation > 359) {
			setRotation(0);
		} else if (newRotation < 0) {
			setRotation(359);
		} else setRotation(newRotation);
	}


	public int getROTATIONSPEED() {
		return ROTATIONSPEED;
	}
	
	
	
	
	
	
	
	
	
	
	
}
