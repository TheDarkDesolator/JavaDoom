package com.impalah.JDoomK1.rendering;

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
		
		
		if(moveForward) {
			player.setPosX((int) Math.round(player.getPosX() + (dx * player.getSPEED())));
			player.setPosY((int) Math.round(player.getPosY() + (dy * player.getSPEED())));
			
		}
		
		if(moveBackward) {
			player.setPosX((int) Math.round(player.getPosX() - (dx * player.getSPEED())));
			player.setPosY((int) Math.round(player.getPosY() - (dy * player.getSPEED())));
			
		}
		
	
		if(strafeLeft) {
			player.setPosX((int) Math.round(player.getPosX() + (dy * player.getSPEED())));
			player.setPosY((int) Math.round(player.getPosY() - (dx * player.getSPEED())));
		}
		
		if (strafeRight) {
			player.setPosX((int) Math.round(player.getPosX() - (dy * player.getSPEED())));
			player.setPosY((int) Math.round(player.getPosY() + (dx * player.getSPEED())));
		}
		
		
		
		
	}
	
	public void calculateCurrentSector() {
		for (Sector sector : env.getSectors()) {
			
			int countBelowVertex = 0;
			
			/*for (Vertex v : sector.getVertices()) {
				
			}*/
			
		}
	}
	
	public void updateObjects() {
		updatePlayerPosition();
		
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
