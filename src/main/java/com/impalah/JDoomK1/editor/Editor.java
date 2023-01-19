package com.impalah.JDoomK1.editor;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.plaf.SliderUI;

import org.springframework.beans.factory.annotation.Autowired;

import com.impalah.JDoomK1.controls.KeyboardMotion;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.rendering.GamePanel;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;
import com.impalah.JDoomK1.rendering.RenderObjects;
import com.impalah.JDoomK1.util.Seed;

public class Editor {
	
	@Autowired
	private RenderEnvironment2D renderer2D;
	
	public void startEditor() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		EditorPanel editorPanel = new EditorPanel();
		editorPanel.setLayout(null);
		
		editorPanel.setSize(1000, 850);
	

	
	    frame.add(editorPanel);
	    frame.setSize(1000,850);
	    frame.setVisible(true);
	}

}
