package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementFunction represents function in a text (inside of the observed tag). 
 *  
 * @author Vjeco
 */
public class ElementFunction extends Element {
	
	/** The name of the function. */
	private String name;
	
	/**
	 * Instantiates a new function element. 
	 * <p>In input text function can be recognized by character @. But this name of the 
	 * function is without the before mentioned character. Name of the function is legal 
	 * if it starts with a letter followed by letters, digits or character _.</p>
	 *
	 * @param name the name of the function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/* @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()*/
	@Override
	public String asText(){
		return "@" + name;
	}

	/**
	 * Gets the name of the function.
	 *
	 * @return the name of the function
	 */
	public String getName() {
		return name;
	}
}
