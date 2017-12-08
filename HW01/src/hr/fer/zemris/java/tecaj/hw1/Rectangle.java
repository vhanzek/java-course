package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Class Rectangle used for calculating rectangle's properties.
 * @author Vjeco
 */
public class Rectangle {
	
	/**
	 * Method that starts the program.
	 *
	 * @param args if there are no arguments, program asks for necessary information. 
	 * 				If there are two arguments, program uses that information for width and height of the rectangle to calculate recatngle's area and perimeter.
	 * 				Otherwise, program reports an error.
	 */
	public static void main(String[] args){
		int length = args.length;
		double width = 0;
		double height = 0;
		
		if (length == 0){
			BufferedReader reader = new BufferedReader(
										new InputStreamReader(
												new BufferedInputStream(System.in)));
			
			width = scanData("width", reader);
			height = scanData("height", reader);
		} else if (length == 2){
			 width = Double.parseDouble(args[0]);
			 height = Double.parseDouble(args[1]);
		} else {
			System.err.println("Invalid number of arguments was provided.");
			System.exit(1);
		}
		
		System.out.printf("You have specified a rectangle with width %.1f and height %.1f. "
							+ "Its area is %.1f and its perimeter is %.1f.%n", width, height, width * height, 2 * (width + height));
	}
	
	/**
	 * Method for scanning and processing data from standard input.
	 *
	 * @param name name of the variable
	 * @param reader used reader for scanning data
	 * @return scanned value 
	 */
	private static double scanData(String name, BufferedReader reader){
		String input = null;
		double number;
		
		while(true){
			System.out.format("Please provide %s:", name);
			try {
				input = reader.readLine();
			} catch (IOException e) {
				e.getMessage();
			}
			if(input.trim().isEmpty()){
				System.out.println("Nothing was given.");
			} else {
				number = Double.parseDouble(input);
				if (number < 0.0){
					System.out.format("%s is negative.%n", name);
				} else break;
			}
		}
		
		return number;
	}

}
