package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * The class representing panel used to show object information to user 
 * when object is double clicked in {@link JDrawingList}. Also, those 
 * object properties can be modified by changing its values. This class
 * processes those changes.
 * 
 * @author Vjeran
 */
public class JMessagePanel extends JPanel{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5665559736054784655L;
	
	/** The selected object. */
	private GeometricalObject object;
	
	/** The object properties. */
	private JTextField[] objectProperties;
	
	/**
	 * Instantiates a new information panel.
	 *
	 * @param object the selected object in {@link JDrawingList}
	 */
	public JMessagePanel(GeometricalObject object) {
		this.object = object;
		this.objectProperties = new JTextField[6];
		initPanel();
	}

	/**
	 * Method for initializing this panel's GUI.
	 */
	private void initPanel() {
		setLayout(new GridLayout(0, 2, 2, 2));
		if(object instanceof Line){
			processLine((Line) object);
		} else if (object instanceof Circle){
			processCircle((Circle) object);
		} else if (object instanceof FilledCircle){
			processFilledCircle((FilledCircle) object);
		}
	}
	
	/**
	 * Method for processing object properties change. This method is called
	 * if properties are changed and user has chosen to confirm that change.
	 */
	public void processChange() {
		if(object instanceof Line){
			modifyLine((Line) object);
		} else if (object instanceof Circle){
			modifyCircle((Circle) object);
		} else if (object instanceof FilledCircle){
			modifyFilledCircle((FilledCircle) object);
		}
	}
	
	
	/*--------------------------------- OBJECT PROCESSING ---------------------------------*/
	
	/**
	 * Helper method for initializing panel's GUI if selected object is 
	 * instance of class {@link Line}.
	 *
	 * @param line the instance of class {@link Line}
	 */
	private void processLine(Line line) {
		initPanelLook(line.getStartingPoint(), line.getEndingPoint(), 
					  null, -1, line.getForegroundColor(), null);
	}
	
	/**
	 * Helper method for modifying object properties, i.e. this method 
	 * is called when selected object is instance of {@link Line} class
	 * and user wants to modify its properties.
	 *
	 * @param line the instance of class {@link Line}
	 */
	private void modifyLine(Line line) {
		int[] start = parseTextInput(objectProperties[0].getText());
		if(start == null || checkPoint(start)) return;
		
		int[] end = parseTextInput(objectProperties[1].getText());
		if(end == null || checkPoint(end)) return;
		
		int[] color = parseTextInput(objectProperties[4].getText());
		if(color == null || checkColor(color)) return;
		
		line.setStartingPoint(new Point(start[0], start[1]));
		line.setEndingPoint(new Point(end[0], end[1]));
		line.setForegroundColor(new Color(color[0], color[1], color[2]));
	}

	/**
	 * Helper method for initializing panel's GUI if selected object is 
	 * instance of class {@link Circle}.
	 *
	 * @param circle the instance of class {@link Circle}
	 */
	private void processCircle(Circle circle) {
		initPanelLook(null, null, circle.getCenterPoint(), circle.getRadius(), 
					  circle.getForegroundColor(), null);
	}
	
	/**
	 * Helper method for modifying object properties, i.e. this method 
	 * is called when selected object is instance of {@link Circle} class
	 * and user wants to modify its properties.
	 *
	 * @param circle the instance of class {@link AbstractCircle}
	 */
	private void modifyCircle(AbstractCircle circle) {
		int[] center = parseTextInput(objectProperties[2].getText());
		if(center == null || checkPoint(center)) return;
		
		int[] radius = parseTextInput(objectProperties[3].getText());
		if(circle == null || checkRadius(radius)) return;
		
		int[] color = parseTextInput(objectProperties[4].getText());
		if(color == null || checkColor(color)) return;
		
		circle.setCenterPoint(new Point(center[0], center[1]));
		circle.setRadius(radius[0]);
		circle.setForegroundColor(new Color(color[0], color[1], color[2]));
		
	}

	/**
	 * Helper method for initializing panel's GUI if selected object is 
	 * instance of class {@link FilledCircle}.
	 *
	 * @param filledCircle the instance of class {@link FilledCircle}
	 */
	private void processFilledCircle(FilledCircle filledCircle) {
		initPanelLook(null, null, filledCircle.getCenterPoint(), filledCircle.getRadius(), 
					  filledCircle.getForegroundColor(), filledCircle.getBackgroundColor());
	}
	
	/**
	 * Helper method for modifying object properties, i.e. this method 
	 * is called when selected object is instance of {@link FilledCircle} 
	 * class and user wants to modify its properties.
	 *
	 * @param filledCircle the instance of class {@link FilledCircle}
	 */
	private void modifyFilledCircle(FilledCircle filledCircle) {
		modifyCircle(filledCircle);
		
		int[] bc = parseTextInput(objectProperties[5].getText());
		if(bc == null || checkColor(bc)) return;
		filledCircle.setBackgroundColor(new Color(bc[0], bc[1], bc[2]));
	}
	

	/*--------------------------------- HELPER METHODS ---------------------------------*/
	
	/**
	 * Helper method for initializing panel's GUI. It is generic method used
	 * whenever the object in a {@link JDrawingList} has been double clicked.
	 *
	 * @param start the starting point
	 * @param end the ending point
	 * @param center the center point
	 * @param radius the radius
	 * @param fc the foreground color
	 * @param bc the background color
	 */
	private void initPanelLook(Point start, Point end, Point center, int radius, Color fc, Color bc){
		if(start != null){
			add(getLabel("Starting coordinate: "));
			objectProperties[0] = new JTextField(getPointAsString(start));
			add(objectProperties[0]);
		}
		if(end != null){
			add(getLabel("Ending coordinate: "));
			objectProperties[1] = new JTextField(getPointAsString(end));
			add(objectProperties[1]);
		}
		if(center != null){
			add(getLabel("Center coordinate: "));
			objectProperties[2] = new JTextField(getPointAsString(center));
			add(objectProperties[2]);
		}
		if(radius != -1){
			add(getLabel("Radius: "));
			objectProperties[3] = new JTextField(String.valueOf(radius));
			add(objectProperties[3]);
		}
		if(fc != null){
			add(getLabel("Foreground color: "));
			objectProperties[4] = new JTextField(getColorAsString(fc));
			add(objectProperties[4]);
		}
		if(bc != null){
			add(getLabel("Background color: "));
			objectProperties[5] = new JTextField(getColorAsString(bc));
			add(objectProperties[5]);
		}
	}
	
	/**
	 * Helper method for getting new label with given text aligned to right.
	 *
	 * @param text the text
	 * @return the label
	 */
	private JLabel getLabel(String text){
		return new JLabel(text, SwingConstants.RIGHT);
	}
	
	/**
	 * Gets the point as string.
	 *
	 * @param p the point
	 * @return the point as string
	 */
	private String getPointAsString(Point p){
		return String.format("%d, %d", (int) p.getX(), (int) p.getY());
	}
	
	/**
	 * Gets the color as string.
	 *
	 * @param c the color
	 * @return the color as string
	 */
	private String getColorAsString(Color c){
		return String.format("%d, %d, %d", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	/**
	 * Helper method for getting array of parsed integers from
	 * the offered text field. This method is used when user
	 * wants to change objects properties
	 *
	 * @param text the text in {@link JTextField}
	 * @return the array of parsed integers
	 */
	private int[] parseTextInput(String text) {
		if(text.isEmpty()) {
			showError("Field must not be empty!");
			return null;
		} 
		String[] parts = text.split(",");
		int[] numbers = new int[parts.length];
		for(int i = 0; i < parts.length; i++){
			numbers[i] = Integer.parseInt(parts[i].trim());
		}
		return numbers;
	}
	
	
	/*--------------------------------- ERROR PROCESSING ---------------------------------*/
	
	/**
	 * Method for checking if there was an error modifying any object
	 * property representing point.
	 *
	 * @param point the point
	 * @return <code>true</code>, if there was an error
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkPoint(int[] point) {
		if(point.length != 2){
			showError("Point must have 2 coordinates!");
			return true;
		}
		return false;
	}
	
	/**
	 * Method for checking if there was an error modifying radius of
	 * some object extending {@link AbstractCircle}.
	 *
	 * @param radius the radius of the circle
	 * @return <code>true</code>, if there was an error
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkRadius(int[] radius) {
		if(radius.length != 1) {
			showError("Radius must not contain ','!");
			return true;
		}
		if(radius[0] < 0) {
			showError("Radius must not be negative!");
			return true;
		}
		return false;
	}
	
	/**
	 * Method for checking if there was an error modifying any object's
	 * property representing color - foreground color or background color
	 *
	 * @param color the color
	 * @return <code>true</code>, if there was an error
	 * 		   <code>false</code> otherwise
	 */
	private boolean checkColor(int[] color) {
		if(color.length != 3){
			showError("Invalid number of color components!");
			return true;
		}
		for(int c : color){
			if(c < 0 || c > 255){
				showError("Invalid color provided!");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper method for showing error message to user.
	 *
	 * @param msg the error message.
	 */
	private void showError(String msg) {
		JOptionPane.showMessageDialog(
			this, msg, "Error", JOptionPane.ERROR_MESSAGE
		);
	}
}
