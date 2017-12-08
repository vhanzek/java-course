package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The utility class for reading properties. Each key and its corresponding 
 * value in the property list is a string. Property list can contain another
 * property list in it.
 * 
 * @author Vjeran
 */
public class Properties {
	
	/** The properties. */
	private Map<String, String> properties;
	
	/**
	 * Instantiates a new utility class for reading properties.
	 *
	 * @param propsPath the properties path on the disk
	 */
	public Properties(String propsPath) {
		this.properties = getPropertiesForName(propsPath);
	}
	
	/**
	 * Gets the properties list with specified name.
	 *
	 * @param pathString the path string
	 * @return the map with all read properties
	 */
	public static Map<String, String> getPropertiesForName(String pathString){
		Map<String, String> props = new HashMap<>();
		Path path = Paths.get(pathString);
		
		List<String> allLines = null;
		try {
			allLines = Files.readAllLines(path);
		} catch (IOException e) {
		}
		
		for(String line : allLines){
			line = line.trim();
			if(line.isEmpty() || line.startsWith("#")){
				continue;
			} else {
				String[] elements = line.split("=");
				props.put(elements[0].trim(), elements[1].trim());
			}
		}
		return props;
	}

	/**
	 * Gets the property with specified name.
	 *
	 * @param name the name of the property
	 * @return the property
	 */
	public String getProperty(String name){
		return properties.get(name);
	}
	
	/**
	 * Gets the property with specified name and returns it as {@link Integer}.
	 *
	  *@param name the name of the property
	 * @return the property
	 */
	public int getIntProperty(String name){
		return Integer.parseInt(properties.get(name));
	}
	
	/**
	 * Gets the property with specified name and returns it as {@link Path}.
	 *
	  *@param name the name of the property
	 * @return the property
	 */
	public Path getPathProperty(String name){
		return Paths.get((properties.get(name)));
	}
	
	/**
	 * Gets properties map.
	 *
	 * @return the properties map
	 */
	public Map<String, String> getProperties(){
		return properties;
	}
}
