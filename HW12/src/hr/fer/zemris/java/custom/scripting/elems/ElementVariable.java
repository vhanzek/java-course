package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementVariable represents variable in a text, rather, 
 * inside of the observed tag.
 *  
 * @author Vjeco
 */
public class ElementVariable extends Element {
	
	/** The name of the variable. */
	private String name;

	/**
	 * Instantiates a new variable element.
	 * Name of the variable is legal if it starts with a letter followed by 
	 * letters, digits or character _.
	 *
	 * @param name the name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	 /* @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()*/
	@Override
	public String asText(){
		return name;
	}

	/**
	 * Gets the name of the variable.
	 *
	 * @return the name of the variable
	 */
	public String getName() {
		return name;
	}
}
