package com.impalah.JDoomK1.model.environment;

public class EnvironmentPoint implements Comparable<EnvironmentPoint>{
	
	public int valX;
	public int valY;
	
	public EnvironmentPoint(int valX, int valY) {
		super();
		this.valX = valX;
		this.valY = valY;
	}

	public int getValX() {
		return valX;
	}

	public void setValX(int valX) {
		this.valX = valX;
	}

	public int getValY() {
		return valY;
	}

	public void setValY(int valY) {
		this.valY = valY;
	}

	@Override
	public int compareTo(EnvironmentPoint o) {
		if(this.valX == o.getValX() && this.valY == o.getValY()) {
			return 0;
		}
		
		return 1;
	}
	
	

}
