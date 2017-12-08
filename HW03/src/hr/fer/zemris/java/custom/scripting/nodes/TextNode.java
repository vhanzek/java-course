package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class TextNode represents everything outside of tags in a document text.
 * 
 * @author Vjeco
 */
public class TextNode extends Node {
	
	/** The text. */
	private String text;
	
	/**
	 * Instantiates a new text node.
	 *
	 * @param text the text
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Gets the text of the node.
	 *
	 * @return the text
	 */
	public String getText(){
		return text;
	}
	
	@Override
	public String toString(){
		return getText().replaceAll("\\\\", "\\\\\\\\")
						.replaceAll("\\{", "\\\\{");
	}
}
