package com.impalah.JDoomK1.model.environment;

import java.util.ArrayList;
import java.util.List;

public class Environment {

	//environment size in system not in graphics
	private final int MAX_X = 10000;
	
	private final int MAX_Y = 10000;


	private int panelWidth;
	private int panelHeight;
	
	public List<Sector> sectors;
	
	//parts where two sector vertecis overlap like:
	//      |-------|
	//--------------------
	//      |+++++++|
	private List<Wall> portals;

	private List<Wall> allWalls;
	
	public Environment(int panelWidth, int panelHeight) {
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		this.sectors = new ArrayList<Sector>();
	}

	public Environment(Environment e, int panelHeight, int panelWidth){
		this.panelHeight = panelHeight;
		this.panelWidth = panelWidth;
		this.sectors = e.getSectors();
	}
	
	public Environment(int panelWidth, int panelHeight, List<Sector> sectors) {
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

	public int getPanelWidth() {
		return panelWidth;
	}

	public int getPanelHeight() {
		return panelHeight;
	}

	public void setPanelWidth(int panelWidth) {
		this.panelWidth = panelWidth;
	}

	public void setPanelHeight(int panelHeight) {
		this.panelHeight = panelHeight;
	}

	public void setPortals(List<Wall> portals) {
		this.portals = portals;
	}

	public void setAllWalls(List<Wall> allWalls) {
		this.allWalls = allWalls;
	}

	public List<Wall> getAllWalls(){
		List<Wall> buffer = new ArrayList<>();
		this.getSectors().forEach(s -> buffer.addAll(s.getWalls()));
		return buffer;
	}
}
