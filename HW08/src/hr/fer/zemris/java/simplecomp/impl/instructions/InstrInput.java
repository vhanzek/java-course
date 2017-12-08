package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja omogućuje čitanje retka s tipkovnice koji zadaje korisnik.
 * Čita samo cijele brojeve, ako se pri čitanju ili konverziji iz {@link String}a
 * u {@link Integer} dogodi greška, zastavica ({@code flag}} se postavlja na
 * <code>false</code>, a inače se postavlja na <code>true</code>. Prima jedan 
 * argument koji predstavlja memorijsku lokaciju na koju će se spremiti učitani
 * broj.
 * 
 * @author Vjeco
 */
public class InstrInput implements Instruction {
	/**
	 * Memorijsku lokacija na koju će se spremiti učitani broj.
	 */
	private int memLocation;
	/**
	 * Čitač pomoću kojeg se čita s standardnog ulaza.
	 */
	private BufferedReader reader;
	
	/**
	 * Konstruktor koji učitava zadanu memorijsku lokaciju.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrInput(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		this.memLocation = (Integer) arguments.get(0).getValue();
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	@Override
	public boolean execute(Computer computer) {
		try {
			String num = reader.readLine();
			try {
				int number = Integer.parseInt(num);
				computer.getMemory().setLocation(memLocation, number);
				computer.getRegisters().setFlag(true);
			} catch (NumberFormatException nfe){
				computer.getRegisters().setFlag(false);
			}
		} catch (IOException e) {
			computer.getRegisters().setFlag(false);
		}
		return false;
	}

}
