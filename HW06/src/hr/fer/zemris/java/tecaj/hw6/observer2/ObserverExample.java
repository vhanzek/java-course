package hr.fer.zemris.java.tecaj.hw6.observer2;

import hr.fer.zemris.java.tecaj.hw6.observer2.ChangeCounter;
import hr.fer.zemris.java.tecaj.hw6.observer2.DoubleValue;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorage;
import hr.fer.zemris.java.tecaj.hw6.observer2.IntegerStorageObserver;
import hr.fer.zemris.java.tecaj.hw6.observer2.SquareValue;

/**
 * The class for working with instances of class {@link IntegerStorage} and interface {@link IntegerStorageObserver}.
 * 
 * @author Vjeco
 */
public class ObserverExample {

	/**
	 * The main method. Program starts here.
	 *
	 * @param args the arguments. Command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		istorage.removeObserver(observer);
		
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}

}
