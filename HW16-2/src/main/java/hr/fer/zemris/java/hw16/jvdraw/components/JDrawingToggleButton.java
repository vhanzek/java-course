package hr.fer.zemris.java.hw16.jvdraw.components;

import javax.swing.Action;
import javax.swing.JToggleButton;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;


/**
 * The class representing drawing button used in {@link JVDraw}
 * tool bar. Unlike the {@link JToggleButton} class, it stores
 * button name so that buttons are able to differentiate.
 * 
 * @author Vjeran
 */
public class JDrawingToggleButton extends JToggleButton{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 331161377853833534L;
	
	/** The drawing object name. */
	private String drawingObjectName;
	
	/**
	 * Instantiates a new drawing button.
	 *
	 * @param action the action
	 * @param drawingObjectName the drawing object name
	 */
	public JDrawingToggleButton(Action action, String drawingObjectName) {
		super(action);
		this.drawingObjectName = drawingObjectName;
	}

	/**
	 * Gets the drawing object name.
	 *
	 * @return the drawing object name
	 */
	public String getDrawingObjectName() {
		return drawingObjectName;
	}
}
