package hr.fer.zemris.java.custom.collections;

/**
 * Example for working with ObjectStack class.
 * 
 * @author Vjeco
 */
public class StackDemo {

	/**
	 * Method that starts with the beginning of the program.
	 * 
	 * @param args command line arguments. One argument is expected and it represents expression for evaluating.
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException(
				"Illegal number of command line arguments."
			);
		}
		String expressions[] = args[0].split(" ");
		
		ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection();
		ObjectStack stack = new ObjectStack(arrayIndexedCollection);
		
		for(String element : expressions){
			if(element.matches("-?\\d+")){
				stack.push(Integer.parseInt(element));
			} else if (element.matches("\\+|\\-|\\*|\\/|\\+|\\%")){
				Integer second = (Integer) stack.pop();
				Integer first = (Integer) stack.pop();
				
				switch (element){
					case "+": stack.push((Integer) (first + second)); 
							  break;
					case "-": stack.push((Integer) (first - second)); 
							  break;
					case "*": stack.push((Integer) (first * second)); 
						 	  break;
					case "/": stack.push((Integer) (first / second)); 
							  break;
					case "%": stack.push((Integer) (first % second)); 
				}
			} else {
				throw new IllegalArgumentException(
					"Unknown expression."
				);
			}
		}
		
		if(stack.size() != 1){
			System.err.println("Stack size is not 1.");
		} else {
			System.out.println("Expression evaluates to " + (Integer) stack.pop() + ".");
		}
	}
}
