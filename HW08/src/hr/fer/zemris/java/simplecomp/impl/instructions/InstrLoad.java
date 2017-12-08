package hr.fer.zemris.java.simplecomp.impl.instructions;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import static hr.fer.zemris.java.simplecomp.ArgumentUtil.*;

/**
 * Instrukcija koja omogućaja učitavanje sadržaja sa zadane memorijske lokacije
 * u zadani registar opće namjene. Prvi argument je registar (ne indirektna adresa),
 * a drugi je memorijska lokacija.
 * 
 * @author Vjeco
 */
public class InstrLoad implements Instruction {
	/**
	 * Indeks registra.
	 */
	private int registerIndex;
	/**
	 * Memorijska lokacija s koje se učitava sadržaj.
	 */
	private int memoryLocation;
	
	/**
	 * Konstruktor koji učitava indeks registra i memorijsku lokaciju.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrLoad(List<InstructionArgument> arguments) {
		checkArguments(arguments, 2);
		
		this.registerIndex = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.memoryLocation =
				(Integer) arguments.get(1).getValue();
	}
	@Override
	public boolean execute(Computer computer) {
		computer.getRegisters().setRegisterValue(
				registerIndex, computer.getMemory().getLocation(memoryLocation));
		return false;
	}

}
