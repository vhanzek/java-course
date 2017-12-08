package hr.fer.zemris.java.hw16.jvdraw.color;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * The component representing color chooser, i.e. when this component is
 * clicked on, a {@link JColorChooser} is shown to user and that way user 
 * can change foreground and background colors of geometric object to
 * be drawn.
 * 
 * @author Vjeran
 */
public class JColorArea extends JComponent implements IColorProvider{
	
	/** The preferred size of the component. */
	private static final int SIZE = 15;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8179920274416062578L;
	
	/** The currently selected color. */
	private Color selectedColor;
	
	/** The color change listeners. */
	private List<ColorChangeListener> listeners;

	/**
	 * Instantiates a new color area.
	 *
	 * @param selectedColor the selected color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		this.listeners = new ArrayList<>();
		addMouseListener(colorAreaMouseListener);		
	}
	
	/**
	 * Sets the selected color.
	 *
	 * @param selectedColor the new selected color
	 */
	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(selectedColor);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}
	
	/**
	 * Adds the color change listener.
	 *
	 * @param l the {@code ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l){
		listeners.add(l);
	}
	
	/**
	 * Removes the color change listener.
	 *
	 * @param l the {@code ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l){
		listeners.remove(l);
	}
	
	/**
	 * Method for informing all registered listeners of color
	 * change. This way, {@code JColorArea} behaves as subject
	 * in Observer design pattern.
	 *
	 * @param newColor the new color
	 */
	private void fire(Color newColor){
		for(ColorChangeListener l : listeners){
			l.newColorSelected(this, getCurrentColor(), newColor);
		}
	}

	/** 
	 * The mouse listener for this component. When this component is
	 * clicked on, a {@link JColorChooser} is shown to user and that way user 
	 * can change foreground and background colors of geometric object to
	 * be drawn.
	 */
	private static final MouseListener colorAreaMouseListener = new MouseAdapter() {
		
		public void mouseClicked(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JColorArea) {
				JColorArea ca = (JColorArea) comp;
				Color choosedColor = JColorChooser.showDialog(
					e.getComponent().getParent(), "Select color", ca.selectedColor
				);
				
				if(choosedColor == null) return;
				ca.fire(choosedColor);
				ca.selectedColor = choosedColor;
				ca.repaint();
			}
		}
		
		public void mouseEntered(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JColorArea) {
				JColorArea ca = (JColorArea) comp;
				ca.setBorder(BorderFactory.createLineBorder(Color.gray));
				ca.repaint();
			}
		}
		
		public void mouseExited(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JColorArea) {
				JColorArea ca = (JColorArea) comp;
				ca.setBorder(null);
				ca.repaint();
			}
		}
	};
}
