package com.impalah.JDoomK1.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;

public class Seed {

	public static Environment seedEnvironment(int panelWidth, int panelHeight) {

		Environment env = new Environment(panelWidth, panelHeight);

		List<Sector> sectors = new ArrayList<Sector>();

		// Main hall: doorways in the west and east walls (y 4800..5200)
		List<Wall> hallVertices = new ArrayList<Wall>();

		hallVertices.add(new Wall(2500, 2500, 2500, 4800));
		hallVertices.add(new Wall(2500, 5200, 2500, 7500));
		hallVertices.add(new Wall(2500, 7500, 7500, 7500));
		hallVertices.add(new Wall(7500, 7500, 7500, 5200));
		hallVertices.add(new Wall(7500, 4800, 7500, 2500));
		hallVertices.add(new Wall(2500, 2500, 7500, 2500));

		hallVertices.forEach(w -> w.setColor(Color.red));

		Sector hall = new Sector(hallVertices, Color.red);

		// West room: three walls, the hall's west wall is its fourth side
		List<Wall> westRoomVertices = new ArrayList<Wall>();

		westRoomVertices.add(new Wall(1200, 3800, 1200, 6200));
		westRoomVertices.add(new Wall(1200, 6200, 2500, 6200));
		westRoomVertices.add(new Wall(2500, 3800, 1200, 3800));

		westRoomVertices.forEach(w -> w.setColor(Color.green));

		Sector westRoom = new Sector(westRoomVertices, Color.green);

		// East room: same idea
		List<Wall> eastRoomVertices = new ArrayList<Wall>();

		eastRoomVertices.add(new Wall(7500, 6300, 8700, 6300));
		eastRoomVertices.add(new Wall(8700, 6300, 8700, 3800));
		eastRoomVertices.add(new Wall(8700, 3800, 7500, 3800));

		eastRoomVertices.forEach(w -> w.setColor(Color.yellow));

		Sector eastRoom = new Sector(eastRoomVertices, Color.yellow);

		// Central room: doorway in the south wall (x 4800..5200)
		List<Wall> centralRoomVertices = new ArrayList<Wall>();

		centralRoomVertices.add(new Wall(4300, 4300, 5700, 4300));
		centralRoomVertices.add(new Wall(4300, 4300, 4300, 5700));
		centralRoomVertices.add(new Wall(5700, 4300, 5700, 5700));
		centralRoomVertices.add(new Wall(4300, 5700, 4800, 5700));
		centralRoomVertices.add(new Wall(5200, 5700, 5700, 5700));

		centralRoomVertices.forEach(w -> w.setColor(Color.blue));

		Sector centralRoom = new Sector(centralRoomVertices, Color.blue);

		// Pillar, north-west
		List<Wall> pillar1Vertices = new ArrayList<Wall>();

		pillar1Vertices.add(new Wall(3300, 3300, 3300, 3700));
		pillar1Vertices.add(new Wall(3300, 3700, 3700, 3700));
		pillar1Vertices.add(new Wall(3700, 3700, 3700, 3300));
		pillar1Vertices.add(new Wall(3300, 3300, 3700, 3300));

		pillar1Vertices.forEach(w -> w.setColor(Color.orange));

		Sector pillar1 = new Sector(pillar1Vertices, Color.orange);

		// Pillar, north-east
		List<Wall> pillar2Vertices = new ArrayList<Wall>();

		pillar2Vertices.add(new Wall(6300, 3300, 6300, 3700));
		pillar2Vertices.add(new Wall(6300, 3700, 6700, 3700));
		pillar2Vertices.add(new Wall(6700, 3700, 6700, 3300));
		pillar2Vertices.add(new Wall(6300, 3300, 6700, 3300));

		pillar2Vertices.forEach(w -> w.setColor(Color.orange));

		Sector pillar2 = new Sector(pillar2Vertices, Color.orange);

		// Pillar, south-west
		List<Wall> pillar3Vertices = new ArrayList<Wall>();

		pillar3Vertices.add(new Wall(3300, 6300, 3300, 6700));
		pillar3Vertices.add(new Wall(3300, 6700, 3700, 6700));
		pillar3Vertices.add(new Wall(3700, 6700, 3700, 6300));
		pillar3Vertices.add(new Wall(3300, 6300, 3700, 6300));

		pillar3Vertices.forEach(w -> w.setColor(Color.orange));

		Sector pillar3 = new Sector(pillar3Vertices, Color.orange);

		// Pillar, south-east
		List<Wall> pillar4Vertices = new ArrayList<Wall>();

		pillar4Vertices.add(new Wall(6300, 6300, 6300, 6700));
		pillar4Vertices.add(new Wall(6300, 6700, 6700, 6700));
		pillar4Vertices.add(new Wall(6700, 6700, 6700, 6300));
		pillar4Vertices.add(new Wall(6300, 6300, 6700, 6300));

		pillar4Vertices.forEach(w -> w.setColor(Color.orange));

		Sector pillar4 = new Sector(pillar4Vertices, Color.orange);

		// Triangular pillar: angled walls to exercise the raycaster
		List<Wall> trianglePillarVertices = new ArrayList<Wall>();

		trianglePillarVertices.add(new Wall(4700, 2900, 5300, 2900));
		trianglePillarVertices.add(new Wall(5300, 2900, 5000, 3400));
		trianglePillarVertices.add(new Wall(5000, 3400, 4700, 2900));

		trianglePillarVertices.forEach(w -> w.setColor(Color.magenta));

		Sector trianglePillar = new Sector(trianglePillarVertices, Color.magenta);

		sectors.add(hall);
		sectors.add(westRoom);
		sectors.add(eastRoom);
		sectors.add(centralRoom);
		sectors.add(pillar1);
		sectors.add(pillar2);
		sectors.add(pillar3);
		sectors.add(pillar4);
		sectors.add(trianglePillar);

		env.setSectors(sectors);

		return env;
	}

	public static Environment seedOneWall(){

		Environment env = new Environment(700,700);

		List<Sector> sectors = new ArrayList<Sector>();

		Sector sector1 = new Sector();

		List<Wall> sector1Vertices = new ArrayList<Wall>();

		sector1Vertices.add(new Wall(2500,2500,2500,7500));
		sector1Vertices.add(new Wall(2500,7500,7500,7500));
		sector1Vertices.add(new Wall(7500,7500,7500,2500));
		sector1Vertices.add(new Wall(2500,2500, 7500,2500));

		sector1.setColor(Color.red);
		sector1.setWalls(sector1Vertices);

		sectors.add(sector1);
		env.setSectors(sectors);

		return env;


	}

}
