package hr.fer.zemris.java.custom.scripting.exec.operations;

import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * <p>The interface representing operation used in {@link SmartScriptEngine}.
 * All defined operations are stored in {@link Operations} class.</p>
 * 
 * <p>All required parameters are retrieved from the temporary stack.
 * For example, if required operation i multiplication of two
 * parameters. First, two parameters are retrieved from the stack by
 * popping its values. After operation has been executed, the result 
 * is pushed to temporary stack for further operations.</p>
 * 
 * @author Vjeran
 */
public interface Operation {
	
	/**
	 * Method for executing specified operation.
	 *
	 * @param stack the temporary stack
	 * @param rc the request context
	 */
	void execute(Stack<Object> stack, RequestContext rc);
}
