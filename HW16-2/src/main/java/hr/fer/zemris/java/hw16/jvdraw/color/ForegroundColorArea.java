package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class derived from {@link JColorArea} representing foreground 
 * color chooser in {@link JVDraw} class.
 * 
 * @author Vjeran
 */
public class ForegroundColorArea extends JColorArea {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3650626884059319823L;

	/**
	 * Instantiates a new foreground color area.
	 *
	 * @param selectedColor the selected color
	 */
	public ForegroundColorArea(Color selectedColor) {
		super(selectedColor);
		setToolTipText("Click to change foreground color.");
	}

}
