package hr.fer.zemris.java.webapps.gallery;

import java.util.List;

/**
 * The class {@code Picture} representing one picture in this gallery.
 * It has three properties: {@link #name}, {@link #description} and
 * {@link #tags} associated with the image.
 * 
 * @author Vjeran
 */
public class Picture {
		
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The tags. */
	private List<String> tags;

	/**
	 * Instantiates a new picture.
	 *
	 * @param name the name
	 * @param description the description
	 * @param tags the tags
	 */
	public Picture(String name, String description, List<String> tags) {
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}
}
