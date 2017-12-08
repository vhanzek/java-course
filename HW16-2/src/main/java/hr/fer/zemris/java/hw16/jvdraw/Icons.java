package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

// TODO: Auto-generated Javadoc
/**
 * The class for storing icons, i.e. instances of class {@link ImageIcon},
 * used in {@link JVDraw} class. Also, it contains methods for resizing 
 * icons. Method {@link #getIcon(String)} is used for getting required icon.
 * Class can be only once instantiated, that means icons are only once loaded
 * into memory.
 * 
 * @author Vjeran
 */
public class Icons {
	
	/** The default size of icons. */
	private static final int ICON_SIZE = 15;
	
	/** The map with all stored icons. */
	private static Map<String, ImageIcon> icons;
	
	/** The instance of this class. */
	private static Icons instance = null;
	
	/**
	 * Gets the single instance of this class.
	 *
	 * @return single instance of this class
	 */
	public static Icons getInstance() {
		if (instance == null) {
			instance = new Icons();
		}
		return instance;
	}
	
	/**
	 * Private constructor, called only one time. Method loads
	 * and resizes all necessary icons into program memory.
	 */
	private Icons(){    
		icons = new HashMap<>();
		icons.put("line", resizeIcon("line.png"));
		icons.put("circle", resizeIcon("circle.png"));
		icons.put("filled_circle", resizeIcon("filled_circle.png"));
	}
	
	/**
	 * Method for getting required icon from the {@link #icons} map.
	 *
	 * @param name the name of the icon
	 * @return the icon
	 */
	public ImageIcon getIcon(String name){
		return icons.get(name);
	}
	
	/**
	 * Gets the instance of {@link ImageIcon} class from given resource.
	 *
	 * @param name the name of the image
	 * @return the image icon
	 */
	private ImageIcon getImageIcon(String name){
		List<Byte> bytes = new ArrayList<>();
		try(InputStream is = this.getClass().getResourceAsStream("icons/" + name)){
			if(is == null) {
				throw new IllegalArgumentException(
					"Image icon does not exist."
				);
			}
			int currentByte = is.read();
			while(true){
				if(currentByte == -1) break;
				bytes.add((byte) currentByte);
				currentByte = is.read();
			}
			
		} catch (IOException e){
			e.printStackTrace();
		}
		
		byte[] by = new byte[bytes.size()];
		int i = 0;
		for(Byte b : bytes){
			by[i++] = (byte) b;
		}
		return new ImageIcon(by);
	}
	
	/**
	 * Helper method for resizing given icon to given size.
	 *
	 * @param name the name of the image
	 * @param iconSize the icon size
	 * @return the image icon
	 */
	private ImageIcon resizeIcon(String name, int iconSize) {
		ImageIcon icon = getImageIcon(name);
        Image image = icon.getImage(); 
        Image newImg = image.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH); 
        return new ImageIcon(newImg);
	}
	
	/**
	 * Helper method for resizing icons. Size is set to
	 * {@link #ICON_SIZE}.
	 *
	 * @param name the name of the image
	 * @return the image icon
	 */
	private ImageIcon resizeIcon(String name){
		return resizeIcon(name, ICON_SIZE);
	}
}
