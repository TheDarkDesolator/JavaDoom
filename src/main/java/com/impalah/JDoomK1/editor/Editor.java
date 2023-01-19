package com.impalah.JDoomK1.editor;

import javax.swing.JFrame;

public class Editor {

	
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
