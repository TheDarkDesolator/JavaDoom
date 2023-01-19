package com.impalah.JDoomK1.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;

public class Seed {
	
	public static Environment seedEnvironment() {
		
		Environment env = new Environment();
		
		List<Sector> sectors = new ArrayList<Sector>();
		
		Sector sector1 = new Sector();
		
		List<Wall> sector1Vertices = new ArrayList<Wall>();
		
		sector1Vertices.add(new Wall(2500,2500,2500,7500));
		sector1Vertices.add(new Wall(2500,7500,7500,7500));
		sector1Vertices.add(new Wall(7500,7500,7500,2500));
		sector1Vertices.add(new Wall(7500,2500,2500,2500));
		
		sector1.setColor(Color.red);
		sector1.setVertices(sector1Vertices);
		
		Sector sector2 = new Sector();
		
		List<Wall> sector2Vertices = new ArrayList<Wall>();
		
		sector2Vertices.add(new Wall(1200,3800,1200,6200));
		sector2Vertices.add(new Wall(1200,6200,2500,6200));
		sector2Vertices.add(new Wall(2500,6200,2500,3800));
		sector2Vertices.add(new Wall(2500,3800,1200,3800));
		
		sector2.setColor(Color.green);
		sector2.setVertices(sector2Vertices);
		
		Sector sector3 = new Sector();
		
		List<Wall> sector3Vertices = new ArrayList<Wall>();
		
		sector3Vertices.add(new Wall(7500,3800,7500,6300));
		sector3Vertices.add(new Wall(7500,6300,8700,6300));
		sector3Vertices.add(new Wall(8700,6300,8700,3800));
		sector3Vertices.add(new Wall(8700,3800,7500,3800));
		
		sector3.setColor(Color.yellow);
		sector3.setVertices(sector3Vertices);
		
		
		sectors.add(sector1);
		sectors.add(sector2);
		sectors.add(sector3);
		env.setSectors(sectors);
		
		
		
		return env;
	}

}
