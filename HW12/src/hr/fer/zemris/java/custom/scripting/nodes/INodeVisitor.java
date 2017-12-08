package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * The interface that provides visitation of all nodes in
 * document node constructed by {@link SmartScriptParser}.
 * 
 * @author Vjeran
 */
public interface INodeVisitor {
	
	/**
	 * Method that visits text node in document tree structure 
	 * and does certain action .
	 *
	 * @param node the text node
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Method that visits for loop node in document tree structure 
	 * and does certain action. Also, it visit all children nodes 
	 * that are contained in this node.
	 *
	 * @param node the for loop node
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Method that visits echo node in document tree structure 
	 * and does certain action.
	 *
	 * @param node the echo node
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Method that visits document node in document tree structure 
	 * and does certain action. Usually, this method starts visitation
	 * of all nodes in structure.
	 *
	 * @param node the document node
	 */
	public void visitDocumentNode(DocumentNode node);
	
	/**
	 * Method that visits end node in document tree structure 
	 * and does certain action .
	 *
	 * @param node the end node
	 */
	public void visitEndNode(EndNode node);
}
