package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The listener interface for receiving color change events.
 * The class that is interested in processing a color change
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addColorChangeListener<code> method. When
 * color is changed, that object's appropriate method is invoked.
 *
 * @author Vjeran
 */
public interface ColorChangeListener {
	
	/**
	 * Method called when color in {@link JColorArea} changes.
	 *
	 * @param source the source
	 * @param oldColor the old color
	 * @param newColor the new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
