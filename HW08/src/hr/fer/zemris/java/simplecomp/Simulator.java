package hr.fer.zemris.java.simplecomp;

import java.util.Scanner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.impl.ExecutionUnitImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.InstructionCreator;
import hr.fer.zemris.java.simplecomp.parser.InstructionCreatorImpl;
import hr.fer.zemris.java.simplecomp.parser.ProgramParser;

/**
 * Program koji simulira obrađivanje aseblerskog koda.
 * 
 * @author Vjeco
 */
public class Simulator {

	/**
	 * Glavna metoda. Program počinje na ovom mjestu.
	 * 
	 * @param args argumenti komandne linije. 
	 * 			   Ako se zada jedan argument, on predstavlja put do
	 * 			   datoteke sa aseblerskim kodom. Ako se ne zada niti 
	 * 			   jedan argument, korisinik sam upisuje put do datoteke
	 * 			   sa aseblerskim kodom.
	 * @throws Exception ako se dogodi greška pri parsiranju koda
	 */
	public static void main(String[] args) throws Exception {
		if(args.length != 1 && args.length != 0){
			System.err.print("Expected 0 or 1 argument.");
			System.exit(-1);
		}
		
		Computer comp = new ComputerImpl(256, 16);
		InstructionCreator creator = new InstructionCreatorImpl("hr.fer.zemris.java.simplecomp.impl.instructions");
		
		Scanner sc = new Scanner(System.in);
		String path = args.length == 0 ? sc.nextLine() : args[0];
		
		ProgramParser.parse(path, comp, creator);
		ExecutionUnit exec = new ExecutionUnitImpl();
		exec.go(comp);
		
		sc.close();
	}
}