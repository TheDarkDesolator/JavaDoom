package com.impalah.JDoomK1.editor;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.impalah.JDoomK1.model.environment.Environment;
import com.impalah.JDoomK1.model.environment.Sector;
import com.impalah.JDoomK1.model.environment.Wall;
import com.impalah.JDoomK1.rendering.RenderEnvironment2D;
import com.impalah.JDoomK1.util.LogUtils;
import com.impalah.JDoomK1.util.Seed;

public class EditorPanel extends JPanel implements ActionListener{
	//Components
	private JSlider zoom;
	
	private Button save;
	private Button clear;
	private Button load;
	private Button newSector;
	private Button deleteSector;
	
	private Label zoomLabel;
	private TextArea zoomText;
	
	private Label xLabel;
	private TextArea xText;
	
	private Label yLabel;
	private TextArea yText;
	
	private Label frameXLabel;
	private TextArea frameXText;
	
	private Label frameYLabel;
	private TextArea frameYText;
	
	
	//Component value storage
	private int zoomVal = 0;
	private double scale = 1;
	private Point mouseLocation = new Point(0,0);
	
	//Rendering-
	private RenderEnvironment2D renderer;
	
	private final int PANELOFFSET = 30;
	
	private Environment env;
	
	private int ENV_STEPS;
	
	private int framePosX = 0;
	private int framePosY = 0;
	
	private Scrollbar scrollX;
	private Scrollbar scrollY;
	
	private Graphics2D g2;
	
	private Point nearestPoint;
	
	private Sector bufferSector = new Sector();
	
	private List<Point> pointsClicked = new ArrayList<>();
	
	private boolean newSectorMode = false;
	
	
	
	
	public EditorPanel() {
		nearestPoint = new Point(0,0);
		
		env = new Environment();
		env = Seed.seedEnvironment();
		
		ENV_STEPS = env.getMAX_X()/100;
		
		scrollX = new Scrollbar();
		scrollX.setBounds(PANELOFFSET, 700 + PANELOFFSET, 700, 20);
		scrollX.setOrientation(Scrollbar.HORIZONTAL);
		scrollX.setMaximum(700);
		scrollX.setMinimum(0);
		scrollX.setUnitIncrement((int) Math.round(700 * scale));
		scrollX.setVisibleAmount((int) Math.round(700 * scale));
		scrollX.setBackground(Color.white);
		scrollX.addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				framePosX = e.getValue();
				frameXText.setText(String.valueOf(framePosX));
				repaint();
			}
		});
		
		this.add(scrollX);
		
		scrollY = new Scrollbar();
		scrollY.setBounds(700 + PANELOFFSET, PANELOFFSET, 20, 700);
		scrollY.setOrientation(Scrollbar.VERTICAL);
		scrollY.setMaximum(700);
		scrollY.setMinimum(0);
		scrollY.setUnitIncrement((int) Math.round(700 * scale));
		scrollY.setVisibleAmount((int) Math.round(700 * scale));
		scrollY.setBackground(Color.white);
		scrollY.addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				framePosY = e.getValue();
				frameYText.setText(String.valueOf(framePosY));
				repaint();
			}
		});
		
		this.add(scrollY);
		
		//Initiat components
		zoom = new JSlider(100,400, 100);
		zoom.setValue(100);
		zoom.setMajorTickSpacing(25);
		zoom.setMinorTickSpacing(1);
		zoom.setPaintTicks(true);
		zoom.setPaintLabels(true);
		zoom.setSnapToTicks(true);
		
		zoom.setBounds(PANELOFFSET, 720 + PANELOFFSET, 700, 50);
		
		zoom.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				zoomVal = zoom.getValue();
				scale = (double) zoomVal / 100;
				zoomText.setText(String.valueOf(zoomVal));
				//change scrollbar increment
				double newIncrementX = 700 / scale;
				scrollX.setVisibleAmount((int) newIncrementX);
				framePosX = scrollX.getValue();
				frameXText.setText(String.valueOf(framePosX));
				
				double newIncrementY = 700 / scale;
				scrollY.setVisibleAmount((int) newIncrementY);
				framePosY = scrollX.getValue();
				frameYText.setText(String.valueOf(framePosY));
				repaint();
				
			}
		});
		zoomVal = zoom.getValue();
		this.add(zoom);
		
		//Save
		
		save = new Button("Save");
		save.setBounds(740 + PANELOFFSET, PANELOFFSET, 100, 35);
		this.add(save);
		
		
		//Load
		load = new Button("Load");
		load.setBounds(855 + PANELOFFSET, PANELOFFSET, 100, 35);
		this.add(load);
		
		//Clear
		clear = new Button("Clear");
		clear.setBounds(740 + PANELOFFSET, PANELOFFSET + 40, 215, 35);
		this.add(clear);
		//New Sector
		
		newSector = new Button("New sector");
		newSector.setBounds(740 + PANELOFFSET, PANELOFFSET + 75, 215, 35);
		newSector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogUtils.logInfo("New Sector clicked", this.getClass(), null);
				if(!newSectorMode) {
					
					newSector.setLabel("Cancel");
					newSectorMode = true;
				} else {
					newSector.setLabel("New sector");
					newSectorMode = false;
					
					pointsClicked = new ArrayList<Point>();
					bufferSector = new Sector();
				}
				
			}
		});
		this.add(newSector);
		
		//Delete Sector
		deleteSector = new Button("Delete sector");
		deleteSector.setBounds(740 + PANELOFFSET, PANELOFFSET + 135, 215, 35);
		this.add(deleteSector);
		
		zoomLabel = new Label("Zoom:");
		zoomLabel.setBounds(740 + PANELOFFSET, PANELOFFSET + 185, 100, 15);
		this.add(zoomLabel);
		zoomText = new TextArea(String.valueOf(zoomVal));
		zoomText.setBounds(855 + PANELOFFSET, PANELOFFSET + 185, 100, 15);
		this.add(zoomText);
		
		
		xLabel = new Label("Environment X:");
		xLabel.setBounds(740 + PANELOFFSET, 200 + PANELOFFSET, 100, 15);
		this.add(xLabel);
		xText = new TextArea(String.valueOf(mouseLocation.x));
		xText.setBounds(855 + PANELOFFSET, 200 + PANELOFFSET, 100, 15);
		this.add(xText);
		
		yLabel = new Label("Environment Y:");
		yLabel.setBounds(740 + PANELOFFSET, 215 + PANELOFFSET, 100, 15);
		this.add(yLabel);
		yText = new TextArea(String.valueOf(mouseLocation.y));
		yText.setBounds(855 + PANELOFFSET, 215 + PANELOFFSET, 100, 15);
		this.add(yText);
		
		
		frameXLabel = new Label("Frame X:");
		frameXLabel.setBounds(740 + PANELOFFSET, 230 + PANELOFFSET, 100, 15);
		this.add(frameXLabel);
		frameXText = new TextArea(String.valueOf(framePosX));
		frameXText.setBounds(855 + PANELOFFSET, 230 + PANELOFFSET, 100, 15);
		this.add(frameXText);
		
		frameYLabel = new Label("Frame Y:");
		frameYLabel.setBounds(740 + PANELOFFSET, 245 + PANELOFFSET, 100, 15);
		this.add(frameYLabel);
		frameYText = new TextArea(String.valueOf(framePosY));
		frameYText.setBounds(855 + PANELOFFSET, 245 + PANELOFFSET, 100, 15);
		this.add(frameYText);
		
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
				mouseLocation = e.getPoint();
				
				//LogUtils.logInfo("Mouse: {} - {}", this.getClass(), new Object[] {mouseLocation.x, mouseLocation.y});
				int posX = mouseLocation.x - PANELOFFSET;
				int posY = mouseLocation.y - PANELOFFSET;
				
				if(posX >= 700) {
					posX = 700;
				} else if(posX <= 0) posX = 0;
				
				if(posY >= 700) {
					posY = 700;
				} else if(posY <=0) posY = 0;
				
				Point newPosition = new Point((int) ((posX / scale) + framePosX), (int) ((posY/scale) + framePosY));
				//LogUtils.logInfo("newPosition: {} - {}", this.getClass(), new Object[] {newPosition.x, newPosition.y});
				mouseLocation = renderer.convertPointToEnvironmentSize(env, 
																	   newPosition, 
																	   700, 
																	   700) ;
				
				xText.setText(String.valueOf(mouseLocation.x));
				yText.setText(String.valueOf(mouseLocation.y));
				
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(newSectorMode) {
					if(pointsClicked.size() > 0 && nearestPoint.equals(pointsClicked.get(0))) {
						
							if(pointsClicked.size()> 2) {
								
								env.sectors.add(Sector.createFromPoints(pointsClicked, Color.red));
								pointsClicked = new ArrayList<Point>();
								newSector.setLabel("New sector");
								newSectorMode = false;
								
							} else LogUtils.logError("Can't create a sector with only 1 wall!", this.getClass(), null);
						
					}
					else {
						pointsClicked.add(nearestPoint);
					}
					
				}
			}
		});
		
		
		
		renderer = new RenderEnvironment2D();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//g2 = (Graphics2D) g;
		g.setColor(Color.BLACK);
		//g2.fillRect(0, 0, PANELOFFSET, PANELOFFSET);
		g.fillRect(PANELOFFSET	, PANELOFFSET, 700, 700);
		
		paintGrid(g);
		
		
		//renderer.drawDebugData(g2, 10 + PANELOFFSET, 10 + PANELOFFSET, "Zoom:" + zoomVal + " X: " + mouseLocation.x + " Y: " + mouseLocation.y);
		renderer.drawMap(g, env, 700, 700, PANELOFFSET, framePosX,framePosY, scale, null);
		findNearestPoint(g);
		
		if(newSectorMode) {
			for (Point point : pointsClicked) {
				renderer.drawVertex(g, env, point, 700 + PANELOFFSET, 700 + PANELOFFSET, PANELOFFSET, framePosX, framePosY, scale, 6, Color.yellow);
			}
			
			if(pointsClicked.size() > 0)
			renderer.drawWall(g, env, new Wall(pointsClicked.get(pointsClicked.size()-1), nearestPoint), 700 + PANELOFFSET, 700 + PANELOFFSET ,PANELOFFSET, framePosX, framePosY, scale,Color.yellow);
			
			
			if(pointsClicked.size() > 1) {
				for (int i = 0; i < pointsClicked.size() - 1; i++) {
					Point currentPoint = pointsClicked.get(i);
					Point nextPoint = new Point();
					if(i +1 == pointsClicked.size()) {
						nextPoint = pointsClicked.get(0);
						 
					} else nextPoint = pointsClicked.get(i+1);
					renderer.drawWall(g, env, new Wall(currentPoint, nextPoint), 700 + PANELOFFSET, 700 + PANELOFFSET, PANELOFFSET, framePosX, framePosY, scale, Color.yellow);
				}
				
				}
		}
		
		
		//renderer.drawMap(g2, env, 700, 700, PANELOFFSET, 0,0, 1, null);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.repaint();
		LogUtils.logInfo("ActionPerformed", this.getClass(), null);
	}
	
	public void paintGrid(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		
		
		 
		for (int i = 0; i < env.getMAX_X() + ENV_STEPS; i+=ENV_STEPS) {
			
			int xPoint = renderer.convertPointToScreenSize(env, new Point(i, 0),700, 700, scale).x;
			xPoint = xPoint - (int) (framePosX * scale);
			
				if(xPoint >= 0 && xPoint <=700) {
					g.drawLine(xPoint + PANELOFFSET , PANELOFFSET, xPoint + PANELOFFSET, 700+PANELOFFSET);
					g.drawString(String.valueOf(i), xPoint + PANELOFFSET - 10, PANELOFFSET/2);
				}
			}
		
		for (int i = 0; i < env.getMAX_Y() + ENV_STEPS; i+=ENV_STEPS) {
			
			int yPoint = renderer.convertPointToScreenSize(env, new Point(0, i),700, 700, scale).y;
			yPoint = yPoint - (int) (framePosY * scale);
			
				if(yPoint >= 0 && yPoint <=700) {
					g.drawLine(PANELOFFSET, yPoint + PANELOFFSET , 700+PANELOFFSET, yPoint + PANELOFFSET);
					//g.drawString(String.valueOf(i),PANELOFFSET/2, yPoint + PANELOFFSET + 10);
				}
			}
		
			
		
		
	}
	
	public void findNearestPoint(Graphics g) {
		//LogUtils.logInfo("MouseLocation: {} - {}", this.getClass(), new Object[] {mouseLocation.x, mouseLocation.y});
		float pointX = Math.round(mouseLocation.x / 100.0f) * 100;
		float pointY = Math.round(mouseLocation.y / 100.0f) * 100;
		LogUtils.logInfo("Nearest point: {} - {}", this.getClass(), new Object[] {pointX, pointY});
		this.nearestPoint = new Point(  (int) pointX, (int) pointY);
		renderer.drawVertex(g, env, nearestPoint, 700+PANELOFFSET, 700+PANELOFFSET, PANELOFFSET, framePosX, framePosY, scale,6, Color.yellow);	
		
	}
	
	public void loadEnvironment() {
		
	}
	
	public void saveEnvironment() {
		
	}
	
	public void drawNewSector() {
		newSectorMode = true;
	}
	
}
