package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

/**
 * The class for storing icons, i.e. instances of class {@link ImageIcon},
 * used in {@link JNotepadPP} class. Also, it contains methods for resizing 
 * icons. Method {@link #getIcon(String)} is used for getting required icon.
 * Class can be only once instantiated, that means icons are only once loaded
 * into memory.
 * 
 * @author Vjeran
 */
public class Icons {
	
	/** The default size of icons. */
	private static final int ICON_SIZE = 20;
	
	/** The default size of tab icons. */
	private static final int TAB_ICON_SIZE = 17;
	
	/** The map with all stored icons. */
	private static Map<String, ImageIcon> icons;
	
	/** The instance of this class. */
	private static Icons instance = null;
	
	/** The saved icon. */
	public static ImageIcon BLUE_DISK;
	
	/** The unsaved icon. */
	public static ImageIcon RED_DISK;
	
	/**
	 * Gets the single instance of this class.
	 *
	 * @return single instance of this class
	 */
	public static Icons getInstance() {
	      if(instance == null) {
	         instance = new Icons();
	      }
	      return instance;
	}
	
	/**
	 * Private constructor, called only one time. Method loads
	 * and resizes all necessary icons into program memory.
	 */
	private Icons(){
		BLUE_DISK = resizeIcon("blueDisk.png", TAB_ICON_SIZE);
        RED_DISK = resizeIcon("redDisk.png", TAB_ICON_SIZE);
        
		icons = new HashMap<>();
		icons.put("close", resizeIcon("close_document.png"));
		icons.put("copy", resizeIcon("copy.png"));
		icons.put("cut", resizeIcon("cut.png"));
		icons.put("statistics", resizeIcon("document_info.png"));
		icons.put("new", resizeIcon("new_document.png"));
		icons.put("open", resizeIcon("open_document.png"));
		icons.put("paste", resizeIcon("paste.png"));
		icons.put("save", resizeIcon("save_document.png"));
		icons.put("save as", resizeIcon("save_as_document.png"));
		icons.put("exit", resizeIcon("exit.png"));
		icons.put("upper", resizeIcon("upper_case.png"));
		icons.put("lower", resizeIcon("lower_case.png"));
		icons.put("toggle", resizeIcon("invert_case.png"));
		icons.put("ascending", resizeIcon("ascending_sort.png"));
		icons.put("descending", resizeIcon("descending_sort.png"));
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
        Image newImg = image.getScaledInstance(iconSize, 
        									   iconSize,  
        									   Image.SCALE_SMOOTH); 
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
