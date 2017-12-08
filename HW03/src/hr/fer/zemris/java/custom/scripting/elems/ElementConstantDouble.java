package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The Class ElementConstantDouble represents decimal number in a text.
 * 
 * @author Vjeco
 */
public class ElementConstantDouble extends Element {
	
	/** The value of the element. In this case it is decimal number (double precision). */
	private double value;

	/**
	 * Instantiates a new element constant double.
	 *
	 * @param value the value of the element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	 /* @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()*/
	@Override
	public String asText(){
		return Double.toString(value);
	}

	/**
	 * Gets the value of the element.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
}
