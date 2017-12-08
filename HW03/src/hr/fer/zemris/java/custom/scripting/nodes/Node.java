package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * The Class Node used for representation of structured documents. Each node represents part of the document text.
 * 
 * @author Vjeco
 */
public class Node {
	
	/** All node's children. */
	protected ArrayIndexedCollection childrenCollection;
	
	{
		childrenCollection = new ArrayIndexedCollection();
	}

	/**
	 * Adds the given node to this node as a child.
	 * 
	 * @throws IllegalArgumentException if parameter is null
	 * @param child the child node
	 */
	public void addChildNode(Node child){
		if(Objects.isNull(child)){
			throw new IllegalArgumentException(
				"Input must not be null."
			);
		}
		
		childrenCollection.add(child);
	}
	
	/**
	 * Returns number of node's children.
	 *
	 * @return the number of node's children
	 */
	public int numberOfChildren(){
		return childrenCollection.size();	
	}
	
	/**
	 * Gets the n-th child according to given index.
	 *
	 * @param index the n-th index of the child
	 * @return the desired child
	 */
	public Node getChild(int index){
		return (Node) childrenCollection.get(index);
	}
}
