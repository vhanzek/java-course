package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * The class showing currently used background and foreground color in {@link JVDraw}
 * program. Colors are shown in format <code>(R, G, B)</code> where letters represent
 * color components red, green and blue.
 * 
 * @author Vjeran
 */
public class JColorDisplay extends JLabel implements ColorChangeListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6437451093297486377L;
	
	/** The foreground color. */
	private Color foregroundColor;
	
	/** The background color. */
	private Color backgroundColor;
	
	/**
	 * Instantiates a new color display.
	 *
	 * @param foreground the foreground
	 * @param background the background
	 */
	public JColorDisplay(JColorArea foreground, JColorArea background) {
		this.foregroundColor = foreground.getCurrentColor();
		this.backgroundColor = background.getCurrentColor();
		setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
		
		setText(getLabelText());
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {		
		if(source instanceof ForegroundColorArea){
			foregroundColor = newColor;
		} else {
			backgroundColor = newColor;
		}
		
		setText(getLabelText());
		repaint();
	}
	
	/**
	 * Helper method for getting label text formatted as described in 
	 * documentation of this class.
	 *
	 * @return the label text
	 */
	private String getLabelText(){
		return String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", 
			   foregroundColor.getRed(), foregroundColor.getGreen(), foregroundColor.getBlue(), 
			   backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
	}

}
