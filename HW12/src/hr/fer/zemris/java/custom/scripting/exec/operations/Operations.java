package hr.fer.zemris.java.custom.scripting.exec.operations;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class with all defined operations in .smscr scripts. All operations
 * implement {@link Operation} interface which defines one method -
 * {@linkplain Operation#execute(Stack, RequestContext)}.
 * 
 * @author Vjeran
 */
public class Operations {
	
	/** The map of all defined operations. */
	private static Map<String, Operation> operations = new HashMap<>();
	
	/** 
	 * The operation for addition of two parameters. 
	 */
	private static Operation add = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			doOperation(stack, (v1, v2) -> v1 + v2);
		}	
	};
	
	/** 
	 * The operation for subtraction of two parameters. 
	 */
	private static Operation substract = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			doOperation(stack, (v1, v2) -> v1 - v2);
		}	
	};
	
	/** 
	 * The operation for division of two parameters. 
	 */
	private static Operation divide = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			doOperation(stack, (v1, v2) -> v1/v2);
		}	
	};
	
	/** 
	 * The operation for multiplication of two parameters. 
	 */
	private static Operation multiply = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			doOperation(stack, (v1, v2) -> v1*v2);
		}	
	};
	
	/** 
	 * The operation for calculating sinus of the retrieved parameter.
	 */
	private static Operation sin = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			double value = (double) getPoppedValue(stack);
			stack.push(Math.sin(value));
		}	
	};
	
	/** 
	 * The operation for formating decimal number using given format 
	 * which is compatible with {@link DecimalFormat}; produces a string.
	 * First parameter is decimal number and the second is desired format.
	 */
	private static Operation decimalFormat = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			String format = (String) stack.pop();
			double x = (double) getPoppedValue(stack);

			stack.push(new DecimalFormat(format).format(x));
		}	
	};
	
	/** 
	 * The operation that duplicates current top value from stack.
	 */
	private static Operation duplicate = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			Object obj = stack.pop();
			stack.push(obj);
			stack.push(obj);
		}	
	};
	
	/** 
	 * The operation that replaces the order of two topmost items on stack.
	 */
	private static Operation swap = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			Object obj1 = stack.pop();
			Object obj2 = stack.pop();
			stack.push(obj1);
			stack.push(obj2);
		}	
	};
	
	/** 
	 * The operation for setting mime type of {@link RequestContext} by
	 * calling method {@link RequestContext#setMimeType(String)} where
	 * the parameter is retrieved from the stack.
	 */
	private static Operation setMimeType = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			String mimeType = (String) stack.pop();
			rc.setMimeType(mimeType);
		}	
	};
	
	/** 
	 * The operation that obtains from {@link RequestContext} parameters map a value 
	 * mapped for name (first parameter) by calling {@link RequestContext#getParameter(String)}
	 * and pushes it onto stack. If there is no such mapping, it pushes instead default 
	 * value (second parameter) onto stack.
	 */
	private static Operation paramGet = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			getParameter(stack, rc, (reqC, name) -> reqC.getParameter(name));
		}	
	};
	
	/** 
	 * The operation that obtains from {@link RequestContext} persistent parameters map a value mapped 
	 * for name (first parameter) by calling {@link RequestContext#getPersistentParameter(String)}
	 * and pushes it onto stack. If there is no such mapping, it pushes instead default 
	 * value (second parameter) onto stack.
	 */
	private static Operation pparamGet = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			getParameter(stack, rc, (reqC, name) -> reqC.getPersistentParameter(name));
		}	
	};
	
	/** 
	 * The operation that obtains from {@link RequestContext} temporary parameters map a value mapped 
	 * for name (first parameter) by calling {@link RequestContext#getTemporaryParameter(String)}
	 * and pushes it onto stack. If there is no such mapping, it pushes instead default 
	 * value (second parameter) onto stack.
	 */
	private static Operation tparamGet = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			getParameter(stack, rc, (reqC, name) -> reqC.getTemporaryParameter(name));
		}	
	};
	
	/** 
	 * The operation for storing a value (first parameter) into {@link RequestContext}
	 * persistent parameters map under the given name (second parameter) by calling
	 * {@link RequestContext#setPersistentParameter(String, String)}.
	 */
	private static Operation pparamSet = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			setParameter(stack, rc, (reqC, n, v) -> reqC.setPersistentParameter(n, v));
		}	
	};
	
	/** 
	 * The operation for storing a value (first parameter) into {@link RequestContext}
	 * temporary parameters map under the given name (second parameter) by calling
	 * {@link RequestContext#setTemporaryParameter(String, String)}.
	 */
	private static Operation tparamSet = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			setParameter(stack, rc, (reqC, n, v) -> reqC.setTemporaryParameter(n,v));
		}	
	};
	
	/** 
	 * The operation which removes association for name (only parameter) 
	 * from request context {@code persistentParameters} map by calling
	 * {@link RequestContext#removePersistentParameter(String)}.
	 */
	private static Operation pparamDel = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			String name = (String) stack.pop();
			rc.removePersistentParameter(name);
		}	
	};
	
	/** 
	 * The operation which removes association for name (only parameter) 
	 * from request context {@code temporaryParameters} map by calling
	 * {@link RequestContext#removeTemporaryParameter(String)}.
	 */
	private static Operation tparamDel = new Operation(){

		@Override
		public void execute(Stack<Object> stack, RequestContext rc) {
			String name = (String) stack.pop();
			rc.removeTemporaryParameter(name);
		}	
	};
	
	/**
	 * Static block for initializing all defined operations.
	 */
	static {
		operations.put("+", add);
		operations.put("-", substract);
		operations.put("/", divide);
		operations.put("*", multiply);
		operations.put("sin", sin);
		operations.put("decfmt", decimalFormat);
		operations.put("dup", duplicate);
		operations.put("swap", swap);
		operations.put("setMimeType", setMimeType);
		operations.put("paramGet", paramGet);
		operations.put("pparamGet", pparamGet);
		operations.put("pparamSet", pparamSet);
		operations.put("pparamDel", pparamDel);
		operations.put("tparamSet", tparamSet);
		operations.put("tparamGet", tparamGet);
		operations.put("tparamDel", tparamDel);
	}
	
	/**
	 * Static method for getting certain operation under specified {@code name}.
	 *
	 * @param name the name of the operation
	 * @return the operation
	 */
	public static Operation getOperation(String name){
		return operations.get(name);
	}
	

	/*----------------------------------Helper methods----------------------------------*/

	/**
	 * Method for getting value from the stack. It returns object that can be 
	 * cast to {@link Double} or {@link Integer}.
	 *
	 * @param stack the temporary stack
	 * @return the popped value
	 */
	private static Object getPoppedValue(Stack<Object> stack) {
		Object objValue = stack.pop();
		double value = 0;
		
		if(objValue instanceof Integer){
			value = (Integer) objValue;
		} else if (objValue instanceof Double){
			value = (Double) objValue;
		} else if (objValue instanceof String){
			value = parseNumber((String) objValue);
		} else {
			throw new RuntimeException(
				"Unkown value " + objValue + "."
			);
		}
		
		return value;
	}
	
	/**
	 * Method for getting specified parameter from {@code rc} class. Which parameter
	 * will be retrieved is defined by {@link ParameterGetter} strategy.
	 *
	 * @param stack the stack
	 * @param rc the request context
	 * @param getter the strategy for getting parameter
	 * @return the certain parameter from request context
	 */
	private static void getParameter(Stack<Object> stack, RequestContext rc, ParameterGetter getter) {
		Object defValue = stack.pop();
		String name = (String) stack.pop();
		
		String value = getter.apply(rc, name);
		stack.push(value == null ? defValue : value);
	}
	
	/**
	 * Method for setting specified parameter in {@code rc} class. Which parameter
	 * will be set is defined by {@link ParameterSetter} strategy.
	 *
	 * @param stack the stack
	 * @param rc the request context
	 * @param setter the setter
	 */
	private static void setParameter(Stack<Object> stack, RequestContext rc, ParameterSetter setter) {
		Object name = stack.pop();
		Object value = stack.pop();
		
		setter.apply(rc, name.toString(), value.toString());
	}
	
	/**
	 * Method for executing binary operation. Which operation will be executed is
	 * defined by {@link BiFunction} {@code f}.
	 *
	 * @param stack the stack
	 * @param f the binary function
	 */
	private static void doOperation(Stack<Object> stack, BiFunction<Double, Double, Double>  f){
		double op2 = (double) getPoppedValue(stack);
		double op1 = (double) getPoppedValue(stack);
		
		stack.push(new DecimalFormat().format(f.apply(op1, op2)));
	}

	/**
	 * Helper method for parsing string. It parses string to {@link Double} or
	 * {@link Integer}, depending on given string, but it always returns double
	 * representation of the number.
	 *
	 * @param number the string to be parsed
	 * @return the double representation of number
	 * @throws RuntimeException if string can not be parsed to number
	 */
	private static double parseNumber(String number){
		try{
			if(number.contains(".") || number.contains("E")){
				return Double.parseDouble(number);
			} else {
				return Integer.parseInt(number);
			}
		} catch(NumberFormatException nfe){
			throw new RuntimeException(
				"Unable to parse string " + number + "."
			);
		}
	}
	
	/**
	 * The strategy for getting certain parameter from {@link RequestContext} class.
	 * 
	 * @author Vjeran
	 */
	private interface ParameterGetter {
		
		/**
		 * Method for getting certain parameter.
		 *
		 * @param rc the request context
		 * @param name the name
		 * @return the parameter
		 */
		String apply(RequestContext rc, String name);
	}
	
	/**
	 * The strategy for setting certain parameter in {@link RequestContext} class.
	 * 
	 * @author Vjeran
	 */
	private interface ParameterSetter{
		
		/**
		 * Method for setting certain parameter.
		 *
		 * @param rc the request context
		 * @param name the name
		 * @param value the value
		 */
		void apply(RequestContext rc, String name, String value);
	}
}
