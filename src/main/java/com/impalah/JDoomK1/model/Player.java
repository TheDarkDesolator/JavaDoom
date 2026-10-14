package com.impalah.JDoomK1.model;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.impalah.JDoomK1.model.environment.Sector;

public class Player {
	private int posX;
	private int posY;
	private int posZ;
	private int rotation;
	private Sector currentSector;
	//player hitbox
	private final int RADIUS = 10;
	
	private final double SPEED = 5;
	private final int ROTATIONSPEED = 5;

	private int originX;
	private int originY;
	
	
	public Player() {
		this.posX = 0;
		this.posY = 0;
		this.rotation = 0;
		this.originX = 0;
		this.originY = 0;
	}
	
	
	public Player(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.rotation = 0;
		this.originX = posX;
		this.originY = posY;
	}

	public Player(int posX, int posY, int posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.rotation = 0;
		this.originX = posX;
		this.originY = posY;
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
		this.originX = currentSector.getSectorCenter().x;
		this.originY = currentSector.getSectorCenter().y;
		
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

	public int getOriginX() {
		return originX;
	}

	public int getOriginY() {
		return originY;
	}

	public void setOriginX(int originX) {
		this.originX = originX;
	}

	public void setOriginY(int originY) {
		this.originY = originY;
	}

	public int getPosZ() {
		return posZ;
	}

	public void setPosZ(int posZ) {
		this.posZ = posZ;
	}
}
