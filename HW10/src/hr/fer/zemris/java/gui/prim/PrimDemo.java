package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The demonstration of the {@link ListModel} that generates prime numbers sequentially 
 * and puts them into all registered observers (lists).
 * 
 * @author Vjeco
 */
public class PrimDemo extends JFrame {
	
	/** The constant serialVersionUID. */
	private static final long serialVersionUID = -1305147476023758530L;
	
	/**
	 * The main method, program starts here.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			new PrimDemo().setVisible(true);
		});
	}

	/**
	 * Instantiates a new simple GUI demonstration with 2 lists 
	 * and one button for generating next prime number.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setBounds(300, 300, 500, 500);
		initGUI();
	}
	
	/**
	 * Method for initializing GUI (Graphical User Interface) i.e.
	 * setting and processing components inside a frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JPanel lists = new JPanel();
		lists.setLayout(new GridLayout(1, 2));
		
		PrimListModel primListModel = new PrimListModel();
		
		JButton button = new JButton("Sljedeći");
		button.addActionListener(e -> {
			primListModel.next();
		});

		JList<Integer> left = new JList<>(primListModel);
		left.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JList<Integer> right = new JList<>(primListModel);
		right.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		cp.add(new JScrollPane(lists), BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
		
		lists.add(left);
		lists.add(right);		
	}
	
	/**
	 * The class representing list model that has {@linkplain #next()} method
	 * for generating prime numbers sequentially and adding them to all registered
	 * lists.
	 * 
	 * @author Vjeco
	 */
	private static class PrimListModel implements ListModel<Integer>{
		
		/** The all registered listeners. */
		private List<ListDataListener> listeners;
		
		/** The list of all generated prime numbers. */
		private List<Integer> primNumbers;
		
		/**
		 * Instantiates a new prime number list model.
		 */
		public PrimListModel() {
			this.listeners = new ArrayList<>();
			this.primNumbers = new ArrayList<>();
			primNumbers.add(1);
		}

		@Override
		public int getSize() {
			return primNumbers.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return primNumbers.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);	
		}
		
		/**
		 * Method for getting next prime number and putting it into
		 * prime numbers list - {@linkplain #primNumbers}.
		 */
		public void next(){
			add(generateNextPrim());
		}
		
		/**
		 * Method for generating next prime number.
		 *
		 * @return the next prime number
		 */
		private Integer generateNextPrim() {
			int lastPrim = primNumbers.get(getSize() - 1);
			for(int i = lastPrim + 1;;i++){
				if(isPrime(i)) return i;
			}
		}

		/**
		 * Checks if given number is prime.
		 *
		 * @param num the observed number
		 * @return <code>true</code>, if is prime
		 * 		   <code>false</code> otherwise
		 */
		private boolean isPrime(int num) {
			for(int i = 2; i*i <= num; i++){
				if(num%i == 0){
					return false;
				}
			}
			return true;
		}

		/**
		 * Adds given prime number in the list {@linkplain #primNumbers}.
		 *
		 * @param num the number to be added
		 */
		public void add(Integer num){
			int pos = primNumbers.size();
			primNumbers.add(num);
			
			ListDataEvent event = new ListDataEvent(
				this, ListDataEvent.INTERVAL_ADDED, pos, pos
			);
			
			for(ListDataListener l : listeners){
				l.intervalAdded(event);
			}
		}
	}
}
