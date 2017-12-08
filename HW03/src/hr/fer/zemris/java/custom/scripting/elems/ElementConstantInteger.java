package hr.fer.zemris.java.custom.scripting.elems;

/**
* The Class ElementConstantInteger represents integer in a text.
* 
* @author Vjeco
 */
public class ElementConstantInteger extends Element {
	
	/** The value of the element. In this case it is integer' value. */
	private int value;

	/**
	 * Instantiates a new element constant integer.
	 *
	 * @param value the value of the element, integer
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}


	 /* @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()*/
	@Override
	public String asText(){
		return Integer.toString(value);
	}

	/**
	 * Gets the value of the element.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

}
