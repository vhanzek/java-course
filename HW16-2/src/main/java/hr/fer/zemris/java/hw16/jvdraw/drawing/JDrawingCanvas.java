package hr.fer.zemris.java.hw16.jvdraw.drawing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.ForegroundColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.components.DrawingButtonGroup;
import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractGeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * The class {@code JDrawingCanvas} is implementation of drawing area used in
 * {@link JVDraw} program. User can pick one of three geometric object to draw
 * - line, circle and filled circle. First user's click on this component triggers
 * drawing process, and the second one draws and permanently adds that object to
 * {@link GeoObjectsDrawingModel} internal list of geometric objects.
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener, ColorChangeListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4040326224937519278L;
	
	/** The Constant CANVAS_BACKGROUND_COLOR. */
	private static final Color CANVAS_BACKGROUND_COLOR = Color.WHITE;
	
	/** The model. */
	private DrawingModel model;
	
	/** The drawing buttons. */
	private DrawingButtonGroup drawingButtons;
	
	/** The foreground color. */
	private Color foregroundColor;
	
	/** The background color. */
	private Color backgroundColor;
	
	/** The starting point. */
	private Point startingPoint;
	
	/** The ending point. */
	private Point endingPoint;
	
	/**
	 * Instantiates a new drawing canvas.
	 *
	 * @param model the model
	 * @param drawingButtons the drawing buttons
	 */
	public JDrawingCanvas(DrawingModel model, DrawingButtonGroup drawingButtons) {
		this.model = model;
		this.drawingButtons = drawingButtons;
		this.foregroundColor = JVDraw.DEFAULT_FOREGROUND_COLOR;
		this.backgroundColor = JVDraw.DEFAULT_BACKGROUND_COLOR;
		
		addMouseListener(drawingMouseListener);
		addMouseMotionListener((MouseMotionListener) drawingMouseListener);
		model.addDrawingModelListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(
			RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON
		);
		g2d.setColor(CANVAS_BACKGROUND_COLOR);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		model.forEach(go -> go.draw(g2d));
		
		if(startingPoint != null){
			GeometricalObject object = 
				drawingButtons.getSelectedObject(
					startingPoint, endingPoint, 
					foregroundColor, backgroundColor
				);
			object.draw(g2d);
		}
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		if(source instanceof ForegroundColorArea){
			foregroundColor = newColor;
		} else {
			backgroundColor = newColor;
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		for(int i = index0; i <= index1; i++){
			AbstractGeometricalObject object = 
				(AbstractGeometricalObject) source.getObject(i);
			object.incrementObjectCount();
		}
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}
	
	/**
	 * Method for reseting starting and ending point.
	 */
	public void resetPoints(){
		startingPoint = null;
		endingPoint = null;
	}
	
	/** 
	 * This canvas's key listener. Its functionality is described
	 * in documentation of this {@link JDrawingCanvas} class.
	 */
	private static final MouseListener drawingMouseListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JDrawingCanvas) {
				JDrawingCanvas canvas = (JDrawingCanvas) comp;
				if(canvas.startingPoint == null){
					canvas.startingPoint = e.getPoint();
				} else {
					canvas.endingPoint = e.getPoint();
					GeometricalObject object = 
						canvas.drawingButtons.getSelectedObject(
							canvas.startingPoint, canvas.endingPoint, 
							canvas.foregroundColor, canvas.backgroundColor
						);
					canvas.model.add(object);
					canvas.resetPoints();
				}
			}
		};
		
		public void mouseMoved(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JDrawingCanvas) {
				JDrawingCanvas canvas = (JDrawingCanvas) comp;
				if(canvas.startingPoint != null){
					canvas.endingPoint = e.getPoint();
					canvas.repaint();
				}
			}
		};
	};
}
