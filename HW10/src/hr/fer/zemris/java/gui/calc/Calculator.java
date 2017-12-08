package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.CalcLayout;

/**
 * The class represent a frame as an implementation of the simple calculator.
 * It has 30 buttons (components) and one display label. Used layout manager is
 * {@link CalcLayout}.
 * 
 * @author Vjeco
 */
@SuppressWarnings("serial")
public class Calculator extends JFrame {
	
	/** The display pattern. */
	private static final String DISPLAY_PATTERN = "%-5s";
	
	/** Color of buttons in a calculator. */
	private static final Color BUTTON_COLOR = new Color(114, 159, 207);
	
	/**
	 * The main method, program starts here.
	 *
	 * @param args the command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	/**
	 * Instantiates a new calculator.
	 */
	public Calculator(){
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setBounds(350, 350, 600, 500);
		initGUI();
	}

	/**
	 * Method for initializing GUI (Graphical User Interface) i.e.
	 * setting and processing components inside a frame.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		
		StringBuilder sb = new StringBuilder();
		Stack<Double> stack = new Stack<>();
		Stack<String> stackHelper = new Stack<>();
		Map<String, Function<Double, Double>> functions = new HashMap<>();

		//add display
		JLabel display = new JLabel("", SwingConstants.RIGHT);
		addDisplay(cp, display);
		
		//numbers and sign
		processNumber("1", "4,3", cp, display, sb, stackHelper);
		processNumber("2", "4,4", cp, display, sb, stackHelper);
		processNumber("3", "4,5", cp, display, sb, stackHelper);
		processNumber("4", "3,3", cp, display, sb, stackHelper);
		processNumber("5", "3,4", cp, display, sb, stackHelper);
		processNumber("6", "3,5", cp, display, sb, stackHelper);
		processNumber("7", "2,3", cp, display, sb, stackHelper);
		processNumber("8", "2,4", cp, display, sb, stackHelper);
		processNumber("9", "2,5", cp, display, sb, stackHelper);
		processNumber("0", "5,3", cp, display, sb, stackHelper);
		processNumber(".", "5,5", cp, display, sb, stackHelper);
		
		JButton sign = addButton("+/-", "5,4", cp); 
		sign.addActionListener(l -> {
			if(sb.toString().isEmpty()){
				return;
			} else if(sb.toString().startsWith("-")){
				sb.replace(0, 1, "");
			} else {
				sb.insert(0, "-");
			}
			setDisplay(display, sb);
		});
		
		//other buttons
		JButton calc = addButton("=", "1,6", cp);
		calc.addActionListener(l -> {
			doOperation(stackHelper, display, sb);
		});
		JButton clr = addButton("clr", "1,7", cp);
		clr.addActionListener(l -> {
			sb.setLength(0);
			setDisplay(display, sb);
		});
		JButton res = addButton("res", "2,7", cp);
		res.addActionListener(l -> {
			sb.setLength(0);
			setDisplay(display, sb);
			stackHelper.clear();
		});
		JButton push = addButton("push", "3,7", cp);
		push.addActionListener(l -> {
			stack.push(Double.parseDouble(display.getText()));
		});
		JButton pop = addButton("pop", "4,7", cp);
		pop.addActionListener(l -> {
			if(!stack.isEmpty()){
				display.setText(String.format(DISPLAY_PATTERN, stack.pop()));
			} else {
				JOptionPane.showInternalMessageDialog(
					cp, "Stack is empty!", "Error", JOptionPane.ERROR_MESSAGE
				);
			}
		});
		
		//binary operators
		JButton div = addButton("/", "2,6", cp);
		addBiOperatorAL(div, display, stackHelper, sb);	
		JButton mul = addButton("*", "3,6", cp);
		addBiOperatorAL(mul, display, stackHelper, sb);	
		JButton sub = addButton("-", "4,6", cp);		
		addBiOperatorAL(sub, display, stackHelper, sb);		
		JButton add = addButton("+", "5,6", cp);
		addBiOperatorAL(add, display, stackHelper, sb);
		JButton xn = addButton("x^n", "5,1", cp);
		addBiOperatorAL(xn, display, stackHelper, sb);
		
		//unary operators
		addFunctions(functions);
		JButton xInv = addButton("1/x", "2,1", cp);
		addUnaryOperatorAL(xInv, display, sb, functions);
		JButton sin = addButton("sin", "2,2", cp);
		addUnaryOperatorAL(sin, display, sb, functions);
		JButton log = addButton("log", "3,1", cp);
		addUnaryOperatorAL(log, display, sb, functions);
		JButton cos = addButton("cos", "3,2", cp);
		addUnaryOperatorAL(cos, display, sb, functions);
		JButton ln = addButton("ln", "4,1", cp);
		addUnaryOperatorAL(ln, display, sb, functions);
		JButton tan = addButton("tan", "4,2", cp);
		addUnaryOperatorAL(tan, display, sb, functions);
		JButton ctg = addButton("ctg", "5,2", cp);	
		addUnaryOperatorAL(ctg, display, sb, functions);
		
		//check box
		JCheckBox inv = new JCheckBox("Inv");
		setColor(inv);
		cp.add(inv, "5,7");
		inv.addItemListener(e -> {
			xn.setText(inv.isSelected() ? "n\u221Ax" : "x^n");	
			sin.setText(inv.isSelected() ? "arcsin" : "sin");		
			log.setText(inv.isSelected() ? "10^x" : "log");		
			cos.setText(inv.isSelected() ? "arccos" : "cos");		
			ln.setText(inv.isSelected() ? "e^x" : "ln");		
			tan.setText(inv.isSelected() ? "arctan" : "tan");		
			ctg.setText(inv.isSelected() ? "arcctg" : "ctg");	
			repaint();
		});
		
		setMinimumSize(cp.getMinimumSize());
	}

	/**
	 * Helper method for setting components color and border.
	 * 
	 * @param comp the component
	 */
	private void setColor(JComponent comp) {
		comp.setBackground(BUTTON_COLOR);
		comp.setBorder(BorderFactory.createLineBorder(Color.BLUE));
	}

	/**
	 * Helper method for adding all unary operators and their actions to map.
	 *
	 * @param functions the map with functions
	 */
	private void addFunctions(Map<String, Function<Double, Double>> functions) {
		functions.put("sin", Math::sin);
		functions.put("arcsin", Math::asin);
		functions.put("cos", Math::cos);
		functions.put("arccos", Math::acos);
		functions.put("tan", Math::tan);
		functions.put("arctan", Math::atan);
		functions.put("ctg", d -> 1/Math.tan(d));
		functions.put("arcctg", d -> 1/Math.atan(d));
		functions.put("log", Math::log10);
		functions.put("10^x", d -> Math.pow(10, d));
		functions.put("e^x", Math::exp);
		functions.put("ln", Math::log);
		functions.put("1/x", d -> 1/d);
	}
	
	/**
	 * Helper method for adding action listeners to all buttons representing
	 * unary operators.
	 *
	 * @param button the button for adding action listener
	 * @param display the display label
	 * @param sb the string builder used for display
	 * @param functions the map with all functions(unary operators)
	 */
	private void addUnaryOperatorAL(JButton button, JLabel display, StringBuilder sb, Map<String, Function<Double, Double>> functions) {
		button.addActionListener(e -> {
			String name = e.getActionCommand();
			double number = Double.parseDouble(display.getText());
			double result = functions.get(name).apply(number);
			
			sb.setLength(0);
			display.setText(String.format(DISPLAY_PATTERN, 
							result % 1 == 0 ? new DecimalFormat("#").format(result) : result));
		});
	}

	/**
	 * Helper method for adding action listeners to all buttons representing
	 * binary operators.
	 *
	 * @param button the button for adding action listener
	 * @param display the display label
	 * @param stackHelper the stack with numbers and binary operations
	 * @param sb the string builder used for display
	 */
	private void addBiOperatorAL(JButton button, JLabel display, Stack<String> stackHelper, StringBuilder sb) {
		button.addActionListener(e -> {
			if(stackHelper.isEmpty()) {
				stackHelper.push(display.getText());
			} else {
				doOperation(stackHelper, display, sb);
			}
			
			stackHelper.push(e.getActionCommand());
			sb.setLength(0);
		});
	}

	/**
	 * Helper method for executing binary operation. 
	 * Method takes 3 objects (first operand, operation, second operand)
	 * from stack and executes it.
	 *
	 * @param stackHelper the stack with numbers and binary operations
	 * @param display the display label
	 * @param sb the string builder used for display
	 */
	private void doOperation(Stack<String> stackHelper, JLabel display, StringBuilder sb) {
		if(stackHelper.size() == 2){
			if(sb.toString().isEmpty()){
				stackHelper.pop();
			} else {
				stackHelper.add(sb.toString());
				String resultString = String.valueOf(getBiOperationResult(stackHelper));
				stackHelper.clear();
				stackHelper.push(resultString);
				
				sb.setLength(0);
				double result = Double.parseDouble(resultString);
				display.setText(String.format(DISPLAY_PATTERN, 
								result % 1 == 0 ? new DecimalFormat("#").format(result) : result));
			}
		}
	}
	
	/**
	 * Helper method for getting binary operation result.
	 *
	 * @param stackHelper the stack with numbers and binary operations
	 * @return the binary operation result
	 */
	private double getBiOperationResult(Stack<String> stackHelper) {
		double firstOperand = Double.parseDouble(stackHelper.get(0));
		double secondOperand = Double.parseDouble(stackHelper.get(2));
		double result = 0;
		
		switch (stackHelper.get(1)) {
		case "+":
			result = firstOperand + secondOperand;
			break;
		case "-":
			result = firstOperand - secondOperand;
			break;
		case "*":
			result = firstOperand * secondOperand;
			break;
		case "/":
			result = firstOperand / secondOperand;
			break;
		case "x^n":
			result = Math.pow(firstOperand, secondOperand);
			break;
		case "n\u221Ax":
			result = Math.pow(firstOperand, 1/secondOperand);
			break;
		default:
			break;
		}

		return result;
	}

	/**
	 * Helper method for adding button in content pane. It also set its background 
	 * and border.
	 *
	 * @param name the name of the button
	 * @param pos the position in a frame
	 * @param cp the content pane
	 * @return the added button
	 */
	private JButton addButton(String name, String pos, Container cp) {
		JButton button = new JButton(name);
		setColor(button);
		cp.add(button, pos);
		return button;
	}

	/**
	 * Helper method for processing button representing number or '.'.
	 * Method adds button to content pane and set its action listener.
	 *
	 * @param num the number as a string
	 * @param pos the position of the number in a frame
	 * @param cp the content pane
	 * @param display the display label
	 * @param sb the string builder
	 * @param stackHelper the stack with numbers and binary operations
	 * @return the added button
	 */
	private JButton processNumber(String num, String pos, Container cp, JLabel display, StringBuilder sb, Stack<String> stackHelper) {
		JButton number = new JButton(num);
		setColor(number);
		cp.add(number, pos);
		number.addActionListener(l -> {
			if(sb.length() == 0 && stackHelper.size() == 1){
				stackHelper.clear();
			}
			if(!num.equals(".") || !sb.toString().contains(".")){
				sb.append(num);
				setDisplay(display, sb);
			}
		});
		return number;
	}

	/**
	 * Helper method for setting display text.
	 *
	 * @param display the display label
	 * @param sb the string builder used for displaying current number
	 */
	private void setDisplay(JLabel display, StringBuilder sb) {
		String displayText = String.format(DISPLAY_PATTERN, sb.toString().isEmpty() ? "0" : sb.toString());
		display.setText(displayText);
	}

	/**
	 * Helper method for adding display label to content pane and setting its 
	 * color and border.
	 *
	 * @param parent the parent container, content pane
	 * @param display the display label
	 */
	private void addDisplay(Container parent, JLabel display) {
		String displayText = String.format(DISPLAY_PATTERN, "0");
		display.setText(displayText);
		display.setBackground(Color.ORANGE);
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		parent.add(display, "1,1");
	}
}
