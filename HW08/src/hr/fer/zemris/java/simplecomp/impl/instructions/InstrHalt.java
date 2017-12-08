package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja zaustavlja rad procesora.
 * 
 * @author Vjeco
 */
public class InstrHalt implements Instruction{
	
	/**
	 * Konstruktor koji provjerava broj argumenata koji
	 * mora biti jedank nuli.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrHalt(List<InstructionArgument> arguments) {
		checkArguments(arguments, 0);
	}

	@Override
	public boolean execute(Computer computer) {
		return true;
	}

}
