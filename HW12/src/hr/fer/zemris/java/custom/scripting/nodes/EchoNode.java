package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.List;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * The Class EchoNode represents echo tag expression. 
 * There are infinitely many elements in this node, that's why 
 * {@link ArrayIndexedCollection} is used as storage of elements.
 * 
 * @author Vjeco
 */
public class EchoNode extends Node {
	
	/** The elements of the node (tag). */
	private List<Element> elements;

	/**
	 * Instantiates a new echo node and initialize it with gives elements.
	 *
	 * @param elements node's elements
	 */
	public EchoNode(List<Element> elements) {
		this.elements = elements;
	}

	/**
	 * Gets the elements of the node.
	 *
	 * @return the elements  of the node
	 */
	public List<Element> getElements() {
		return elements;
	}
	
	/**
	 * Returns string that looks like "{$= ...$}" where ... represents node's elements.
	 * 
	 * @return result string of the echo node
	 */
	@Override
	public String toString(){
		StringBuilder nodeElements = new StringBuilder();
		
		for(int i = 0, len = elements.size(); i < len; i++){
			Element element = (Element) elements.get(i);	
			nodeElements.append(element.asText() + " ");
		}
		String echoNode = "{$= " + nodeElements.toString() +"$}";
		
		return echoNode;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
		
	}
}
