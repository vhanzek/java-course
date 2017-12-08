package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class derived from {@link JColorArea} representing background 
 * color chooser in {@link JVDraw} class.
 * 
 * @author Vjeran
 */
public class BackgroundColorArea extends JColorArea {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6152757989387294246L;

	/**
	 * Instantiates a new background color area.
	 *
	 * @param selectedColor the selected color
	 */
	public BackgroundColorArea(Color selectedColor) {
		super(selectedColor);
		setToolTipText("Click to change background color.");
	}


}
