package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * The Class ForLoopNode represents for loop tag expression. 
 * There are three or four elements in for loop node. Fourth expression can be dropped, and in that case it is <code>null</code>.
 * First expression is instance of {@link ElementVariable}, and others can be anything but function and operator.
 * <p>For example :
 * {$FOR i 0 10 1 $} means initialize variable i, <code>i = 0</code></p>, and increase it by 1 until <code>i = 10</code>.
 * </p>
 * 
 * @author Vjeco
 */
public class ForLoopNode extends Node {
	
	/** The variable. */
	private ElementVariable variable;
	
	/** The start expression. */
	private Element startExpression;
	
	/** The end expression. */
	private Element endExpression;
	
	/** The step expression.*/
	private Element stepExpression;
	
	/**
	 * Instantiates a new for loop node and initialize it with gives elements.
	 *
	 * @param variable the variable
	 * @param startExpression the start expression
	 * @param endExpression the end expression
	 * @param stepExpression the step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
						Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Gets the variable, first element in for loop.
	 *
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Gets the start expression, second element in for loop.
	 *
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Gets the end expression, third element in for loop.
	 *
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Gets the step expression, fourth element in for loop.
	 * 
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Returns string that looks like "{$FOR ...$}" where ... represents node's elements (expressions).
	 * 
	 * @return result string of the for loop node
	 */
	@Override
	public String toString(){
		String step;
		if(Objects.isNull(stepExpression)){
			step = "";
		} else {
			step = stepExpression.asText();
		}
		String forLoopnode = "{$FOR " + variable.asText() + " " + startExpression.asText() + 
							 " " + endExpression.asText() + " " + step + "$}";
		
		return forLoopnode;		
	}
	
	
	
}
