package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

/**
 * The interface {@code IColorProvider} with single method
 * {@link #getCurrentColor()} which returns currently used color
 * in classes implementing this interface.
 */
public interface IColorProvider {
	
	/**
	 * Gets the current color.
	 *
	 * @return the current color
	 */
	public Color getCurrentColor();
}
