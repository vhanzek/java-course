package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.StackUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja ne prima niti jedan argument te omogućuje povratak iz 
 * potprograma u glavni program. To se ostvaruje na način da se s vrha stoga 
 * skine adresa i postavi se kao vrijednost registra PC (program counter).
 * 
 * @author Vjeco
 */
public class InstrRet implements Instruction {
	
	/**
	 * Konstruktor koji provjerava broj argumenata instrukcije 
	 * koji mora biti jednak nuli.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrRet(List<InstructionArgument> arguments) {
		checkArguments(arguments, 0);
	}
	
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setProgramCounter((Integer) StackUtil.pop(computer, -1));
		return false;
	}
}
