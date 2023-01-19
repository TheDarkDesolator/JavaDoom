package com.impalah.JDoomK1.rendering;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.util.LogUtils;

public class RenderObjects {
	
	private Player player;
	private Environment env;
	
	private CalculationValues calcVals = new CalculationValues();
	
	private boolean moveForward = false;
	private boolean moveBackward = false;
	private boolean rotateLeft = false;
	private boolean rotateRight = false;
	private boolean strafeLeft = false;
	private boolean strafeRight = false;
	
	
	
	public RenderObjects(Player player, Environment env) {
		super();
		this.player = player;
		this.env = env;
	}
	
	public void updatePlayerPosition() {
		
		if(rotateLeft) {
			player.addRotation(-5);
			
		}
		
		if(rotateRight) {
			player.addRotation(5);
			
		}
		
		double dx = calcVals.sin(player.getRotation()) * 10;
		double dy = calcVals.cos(player.getRotation()) * 10;
		
		int newPlayerPosX = player.getPosX();
		int newPlayerPosY = player.getPosY();
		
		
		if(moveForward) {
			newPlayerPosX = (int) Math.round(player.getPosX() + (dx * player.getSPEED()));
			newPlayerPosY = (int) Math.round(player.getPosY() + (dy * player.getSPEED()));
			
		}
		
		if(moveBackward) {
			newPlayerPosX = (int) Math.round(player.getPosX() - (dx * player.getSPEED()));
			newPlayerPosY = (int) Math.round(player.getPosY() - (dy * player.getSPEED()));
			
		}
		
	
		if(strafeLeft) {
			newPlayerPosX = (int) Math.round(player.getPosX() + (dy * player.getSPEED()));
			newPlayerPosY = (int) Math.round(player.getPosY() - (dx * player.getSPEED()));
		}
		
		if (strafeRight) {
			newPlayerPosX = (int) Math.round(player.getPosX() - (dy * player.getSPEED()));
			newPlayerPosY = (int) Math.round(player.getPosY() + (dx * player.getSPEED()));
		}
		
		player.setPosX(newPlayerPosX);
		player.setPosY(newPlayerPosY);
		
		
		
	}
	
	public void calculateCurrentSector() {
		
		for (Sector sector : env.getSectors()) {
			
			List<Double> xVals = sector.getUniquePoints().stream()
					 .map(p -> p.getX())
					 .collect(Collectors.toList());
			List<Double> yVals = sector.getUniquePoints().stream()
			.map(p -> p.getY())
			.collect(Collectors.toList());
			
			int maxPosX = (int) Math.round(Collections.max(xVals));
			int minPosX =  (int) Math.round(Collections.min(xVals));
			
			int maxPosY = (int) Math.round(Collections.max(yVals));
			int minPosY = (int) Math.round(Collections.min(yVals));
			
			if(player.getPosX() <= maxPosX && player.getPosX() >= minPosX && player.getPosY() <= maxPosY && player.getPosY() >= minPosY && sector.getId() != player.getCurrentSector().getId()) {
				player.setCurrentSector(sector);
			}
						
		}
	}
	
	public void updateObjects() {
		updatePlayerPosition();
		calculateCurrentSector();
		
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Environment getEnv() {
		return env;
	}
	public void setEnv(Environment env) {
		this.env = env;
	}

	public boolean isMoveForward() {
		return moveForward;
	}

	public void setMoveForward(boolean moveForward) {
		this.moveForward = moveForward;
	}

	public boolean isMoveBackward() {
		return moveBackward;
	}

	public void setMoveBackward(boolean moveBackward) {
		this.moveBackward = moveBackward;
	}

	public boolean isStrafeLeft() {
		return strafeLeft;
	}

	public void setStrafeLeft(boolean strafeLeft) {
		this.strafeLeft = strafeLeft;
	}

	public boolean isStrafeRight() {
		return strafeRight;
	}

	public void setStrafeRight(boolean strafeRight) {
		this.strafeRight = strafeRight;
	}

	public boolean isRotateLeft() {
		return rotateLeft;
	}

	public void setRotateLeft(boolean rotateLeft) {
		this.rotateLeft = rotateLeft;
	}

	public boolean isRotateRight() {
		return rotateRight;
	}

	public void setRotateRight(boolean rotateRight) {
		this.rotateRight = rotateRight;
	}
	
	
	
	
	
	

}
