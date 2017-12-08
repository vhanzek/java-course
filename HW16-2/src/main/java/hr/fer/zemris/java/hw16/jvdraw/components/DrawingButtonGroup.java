package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Point;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.actions.JVDrawActions;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * The class derived from {@link ButtonGroup} used for displaying drawing
 * buttons in tool bar of {@link JVDraw} program. Buttons added to this
 * class are mutually exclusive.
 * 
 * @author Vjeran
 */
public class DrawingButtonGroup extends ButtonGroup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7583335048997412872L;
	
	/** The Constant LINE. */
	private static final String LINE = "LINE";
	
	/** The Constant CIRCLE. */
	private static final String CIRCLE = "CIRCLE";
	
	/** The Constant FCIRCLE. */
	private static final String FCIRCLE = "FCIRCLE";
	
	/**
	 * Instantiates a new drawing button group.
	 */
	public DrawingButtonGroup() {
		initButtonGroup();
	}
	
	/**
	 * Method for initializing this drawing button group.
	 */
	private void initButtonGroup() {
		JToggleButton lineButton = 
			new JDrawingToggleButton(JVDrawActions.drawLineAction, LINE);
		JToggleButton circleButton = 
			new JDrawingToggleButton(JVDrawActions.drawCircleAction, CIRCLE);
		JToggleButton filledCircleButton = 
			new JDrawingToggleButton(JVDrawActions.drawFilledCircleAction, FCIRCLE);

		add(lineButton);
		add(circleButton);
		add(filledCircleButton);

		setSelected(lineButton.getModel(), true);
	}

	/**
	 * Method for getting currently selected geometric object for drawing.
	 * There are three different geometric objects - line, circle and filled
	 * circle.
	 *
	 * @param start the starting point
	 * @param end the ending point
	 * @param fc the foreground color
	 * @param bc the background color
	 * @return the selected geometric object
	 */
	public GeometricalObject getSelectedObject(Point start, Point end, Color fc, Color bc) {
		for (Enumeration<AbstractButton> buttons = getElements(); buttons.hasMoreElements();) {
			JDrawingToggleButton button = (JDrawingToggleButton) buttons.nextElement();
            if (button.isSelected()){
            	switch(button.getDrawingObjectName()){
            	case LINE:
            		return new Line(start, end, fc);
            	case CIRCLE:
            		return new Circle(start, end, fc);
            	case FCIRCLE:
            		return new FilledCircle(start, end, fc, bc);
				default :		
            		throw new RuntimeException(
                		"Unknown drawing object."
                	);
                }
            }
        }
		return null;
	}
}
