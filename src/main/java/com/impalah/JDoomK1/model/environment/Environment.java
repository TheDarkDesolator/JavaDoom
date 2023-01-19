package com.impalah.JDoomK1.model.environment;

import java.util.ArrayList;
import java.util.List;

public class Environment {
	
	private final int MAX_X = 10000;
	
	private final int MAX_Y = 10000;
	
	public List<Sector> sectors;
	
	//parts where two sector vertecis overlap like:
	//      |-------|
	//--------------------
	//      |+++++++|
	private List<Wall> portals;
	
	public Environment() {
		this.sectors = new ArrayList<Sector>();
	}
	
	
	
	public Environment(List<Sector> sectors) {
		super();
		this.sectors = sectors;
	}
	



	public List<Sector> getSectors() {
		return sectors;
	}


	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}



	public int getMAX_X() {
		return MAX_X;
	}



	public int getMAX_Y() {
		return MAX_Y;
	}
	
	

	


	
		
	
	
	
}
