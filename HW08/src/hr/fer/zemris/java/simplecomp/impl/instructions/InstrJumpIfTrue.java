package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja je slična instrukciji {@link InstrJump}, jedina razlika
 * je u tome što ova instrukcija provjerava je li zastavica trenutno postavljenja
 * na <code>true</code> ili <code>false</code>. Ako je postavljenja na <code>true</code>,
 * skok će se obaviti, ako nije, ne događa se ništa i program se najnormalije nastavlja.
 * 
 * @author Vjeco
 */
public class InstrJumpIfTrue implements Instruction {
	/**
	 * Memorijska lokacija na koju progam treba "skočiti".
	 */
	private int jumpLocation;

	/**
	 * Konstruktor koji učitava zadanu memorijsku lokaciju.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrJumpIfTrue(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		this.jumpLocation = (Integer) arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		if(computer.getRegisters().getFlag()){
			computer.getRegisters().setProgramCounter(jumpLocation);
		}
		return false;
	}

}
