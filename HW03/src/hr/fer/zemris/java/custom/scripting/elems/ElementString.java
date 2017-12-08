package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementString represents string inside of the tag. Strings begin and end with ".
 * 
 * @author Vjeco
 */
public class ElementString extends Element {
	
	/** The value of the string. */
	private String value;

	/**
	 * Instantiates a new string element with given value.
	 *
	 * @param value the value of the element (string)
	 */
	public ElementString(String value) {
		this.value = value;
	}


	 /* @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()*/
	@Override
	public String asText(){
		value = value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
		return "\"" + value + "\"";
	}

	/**
	 * Gets the value of the string.
	 *
	 * @return the value of the string
	 */
	public String getValue() {
		return value;
	}
}
