package com.impalah.JDoomK1.model.environment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Sector {
	private List<Vertex> vertices;
	private Color color;
	
	public Sector() {
		this.vertices = new ArrayList<Vertex>();
		this.color = Color.red;
	}

	public Sector(List<Vertex> vertices, Color color) {
		super();
		this.vertices = vertices;
		this.color = color;
	}
	
	public void addVertex(Vertex v) {
		if(vertices.size() > 0) {
			boolean foundV = false;
			for (Vertex vertex : vertices) {
				//vertex exists
				if (v.compareTo(vertex) == 0) {
					foundV = true;
				}
			}
			
			if(!foundV) {
				vertices.add(v);
			}
			
		} else {
			vertices.add(v);
		}
	}

	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	
	
}
