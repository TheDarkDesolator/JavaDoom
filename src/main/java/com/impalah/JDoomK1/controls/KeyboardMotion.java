package com.impalah.JDoomK1.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.impalah.JDoomK1.rendering.RenderObjects;
import com.impalah.JDoomK1.util.LogUtils;

public class KeyboardMotion {
	
	private RenderObjects ro;
	private JComponent component;
	
	public KeyboardMotion(RenderObjects ro, JComponent component) {
		this.ro = ro;
		this.component = component;
	}

	private class MotionAction extends AbstractAction implements ActionListener{
		
		private String direction;
		private String type;
		
		public MotionAction(String name, String type) {
			super(name);
			this.direction = name;
			this.type = type;
			
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(type.equals("PRESSED")) {
				if(direction.equals("LEFT")) {
					ro.setRotateLeft(true);
				} 
				else if(direction.equals("RIGHT")) {
					ro.setRotateRight(true);
				}
				else if(direction.equals("UP")) {
					ro.setMoveForward(true);
				}
				else if(direction.equals("DOWN")) {
					ro.setMoveBackward(true);
				}
				else if(direction.equals("STRAFELEFT")) {
					ro.setStrafeLeft(true);
				}
				else if(direction.equals("STRAFERIGHT")) {
					ro.setStrafeRight(true);
				}
			}
			
			if(type.equals("RELEASED")) {
				if(direction.equals("LEFT")) {
					ro.setRotateLeft(false);
				} 
				else if(direction.equals("RIGHT")) {
					ro.setRotateRight(false);
				}
				else if(direction.equals("UP")) {
					ro.setMoveForward(false);
				}
				else if(direction.equals("DOWN")) {
					ro.setMoveBackward(false);
				}
				else if(direction.equals("STRAFELEFT")) {
					ro.setStrafeLeft(false);
				}
				else if(direction.equals("STRAFERIGHT")) {
					ro.setStrafeRight(false);
				}
			}
				
			
		}
		
		
	}
	
	public void addAction(String name) {
		
		InputMap im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = component.getActionMap();

		int keyEvent = 0;
		
		switch (name){
		case "LEFT": keyEvent = KeyEvent.VK_Q; break;
		case "RIGHT": keyEvent = KeyEvent.VK_D; break;
		case "UP": keyEvent = KeyEvent.VK_Z; break;
		case "DOWN": keyEvent = KeyEvent.VK_S; break;
		case "STRAFELEFT" : keyEvent = KeyEvent.VK_A; break;
		case "STRAFERIGHT" : keyEvent = KeyEvent.VK_E; break;
		}

		KeyStroke pk = KeyStroke.getKeyStroke(keyEvent, 0 ,false);
		MotionAction paction = new MotionAction(name, "PRESSED");
		
		KeyStroke rk = KeyStroke.getKeyStroke(keyEvent, 0, true);
		MotionAction raction = new MotionAction(name, "RELEASED");	
	
		im.put(pk, "PRESSED" + name);
		am.put("PRESSED" + name, paction);
		
		im.put(rk, "RELEASED"+ name);
		am.put("RELEASED" + name, raction);
		
	}
}
