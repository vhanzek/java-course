package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja omogućuje "skok" unutar aseblerskog koda. Točnije,
 * argument koji instrukcija prima predstavlja memorijsku lokaciju na 
 * koju program treba "skočiti". Skok se ostvaruje stavljanjem zadane
 * memorijske lokacije u registar programskog brojila te se program 
 * nastavlja na tom lokaciji.
 * 
 * @author Vjeco
 */
public class InstrJump implements Instruction {
	/**
	 * Memorijska lokacija na koju program treba "skočiti".
	 */
	private int jumpLocation;

	/**
	 * Konstruktor koji učitava zadanu memorijsku lokaciju.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrJump(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		this.jumpLocation = (Integer) arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter(jumpLocation);
		return false;
	}
}
