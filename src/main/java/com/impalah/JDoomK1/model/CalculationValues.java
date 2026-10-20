package com.impalah.JDoomK1.model;

import java.util.ArrayList;
import java.util.List;

import com.impalah.JDoomK1.util.LogUtils;

public class CalculationValues {
	
	
	private double[] allCos = new double[360];
	private double[] allSin = new double[360];
	
	public CalculationValues() {
		
		for (int i = 0; i < 360; i++) {
			this.allCos[i] = Math.cos(Math.toRadians(i));
			this.allSin[i] = Math.sin(Math.toRadians(i));
		}
	}

	public double cos(int rotation) {
		return allCos[rotation];
	}


	public double sin(int rotation) {
		return allSin[rotation];
	}

	
	
	

}
