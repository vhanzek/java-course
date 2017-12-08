package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The Class EndNode represents end tag expression. End tag is an empty tag, meaning, there are no element in it.
 * 
 * @author Vjeco
 */
public class EndNode extends Node {
	
	/**
	 * Returns string "{$END$}".
	 * 
	 * @return result string
	 */
	@Override
	public String toString(){
		return "{$END$}";
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEndNode(this);
		
	}
}
