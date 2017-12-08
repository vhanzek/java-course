package hr.fer.zemris.java.graphics;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.shapes.Triangle;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * The Class Demo for working with instances of class {@link GeometricShape}.
 * 
 * @author Vjeco
 */
public class Demo {

	/**
	 * The main method. Starts with the start of the program.
	 * Method for working with geometric shapes and drawing them to raster.
	 *
	 * @param args the arguments. Expected one or two arguments. 
	 * If one argument is provided, it represents both raster's width and height.
	 * If two arguments are provided, they represent  raster's width and height.
	 */
	public static void main(String[] args) {
		int rasterWidth = 0;
		int rasterHeight = 0;
		
		if(args.length == 1){
			try{
				rasterHeight = rasterWidth = Integer.parseInt(args[0]);
			} catch (NumberFormatException nfe){
				System.err.println(nfe.getMessage());
				System.exit(-1);
			}
		} else if(args.length == 2){
			try{
				rasterWidth = Integer.parseInt(args[0]);
				rasterHeight = Integer.parseInt(args[1]);
			} catch (NumberFormatException nfe){
				System.err.println(nfe.getMessage());
				System.exit(-1);
			}
		} else {
			System.err.println("Illegal number of arguments!");
			System.exit(-1);
		}
		
		BWRaster raster = new BWRasterMem(rasterWidth, rasterHeight);
		
		BufferedReader reader = new BufferedReader(
									new InputStreamReader(
										new BufferedInputStream(System.in)));
		GeometricShape[] shapes = null;
		
		try {
			String line = reader.readLine();
			int noShapes = 0;
			try{
				noShapes = Integer.parseInt(line);
			} catch (NumberFormatException nfe){
				System.err.println(nfe.getMessage());
				System.exit(-1);
			}
			
			shapes = new GeometricShape[noShapes];
			
			for(int i = 0; i < noShapes; i++){
				line = reader.readLine();
				if(line.isEmpty()){
					i--;
				} else {
					shapes[i] = processLine(line);
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		
		processShapes(shapes, raster);
	}

	/**
	 * Method for processing shapes. If during the iteration program encounters <code>null</code> value, raster enabled/disabled flip mode.
	 * Otherwise, shape is drawn to the raster.
	 *
	 * @param shapes the shapes the be drawn
	 * @param raster the raster
	 */
	private static void processShapes(GeometricShape[] shapes, BWRaster raster) {
		RasterView view = new SimpleRasterView();
		for(GeometricShape shape : shapes){
			if(Objects.isNull(shape)){
				if(raster.isFlipModeEnabled()){
					raster.disableFlipMode();
				} else {
					raster.enableFlipMode();
				}
			} else {
				shape.draw(raster);
				view.produce(raster);
			}
			System.out.println();
		}
		
	}

	/**
	 * Method for parsing one line from the standard input.
	 *
	 * @param line the line which needs to be parsed
	 * @return the geometric shape got from the parsing
	 */
	private static GeometricShape processLine(String line) {
		line = line.toUpperCase();
		
		try{
			if(line.equals("FLIP")){
				return null;
			} else {
				String[] elements = line.split(" ");
				if(elements[0].equals("RECTANGLE")){
					return new Rectangle(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), 
							 			 Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
				} else if (elements[0].equals("SQUARE")){
					return new Square(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), 
									  Integer.parseInt(elements[3]));
				} else if (elements[0].equals("ELLIPSE")){
					return new Ellipse(Integer.parseInt(elements[1]), Integer.parseInt(elements[3]), 
									   Integer.parseInt(elements[3]), Integer.parseInt(elements[4]));
				} else if (elements[0].equals("CIRCLE")){
					return new Circle(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), 
							  		  Integer.parseInt(elements[3]));
				} else if(elements[0].equals("TRIANGLE")){
					return new Triangle(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), 
					  		  			Integer.parseInt(elements[3]), Integer.parseInt(elements[4]),
					  		  			Integer.parseInt(elements[5]), Integer.parseInt(elements[6]));
				} else {
					System.err.println("Unknown shape.");
					System.exit(-1);
				}
			}
		} catch(NumberFormatException nfe){
			System.err.println("Unable to parse elements.");
			System.exit(-1);
		}
		
		return null;
			
	}

}
