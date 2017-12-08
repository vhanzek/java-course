package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.StackUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja omogućava poziv potprograma. Prima 1 argument, adresu potprograma.
 * Trenutni sadržaj registra PC (program counter) pohranjuje na stog; potom u taj registar 
 * upisuje predanu adresu čime definira sljedeću instrukciju koja će biti izvedena.
 * 
 * @author Vjeco
 */
public class InstrCall implements Instruction {
	/**
	 * Adresa potprograma.
	 */
	private int address;
	
	/**
	 * Konstruktor koji učitava adresu potprograma.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrCall(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		address = (Integer) arguments.get(0).getValue();
	}
	
	@Override
	public boolean execute(Computer computer) {
		StackUtil.push(computer, computer.getRegisters().getProgramCounter());
		computer.getRegisters().setProgramCounter(address);
		return false;
	}
}
