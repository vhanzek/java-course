package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;
import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkRegisters;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja prima 1 arugment - registar čija se vrijednost povećava
 * za 1. Nije dozvoljeno zadavanje pomoćnu indirektne adrese.
 * 
 * @author Vjeco
 */
public class InstrIncrement implements Instruction {
	/**
	 * Indeks registra čija se vrijednost treba povećati.
	 */
	private int registerIndex;

	/**
	 * Konstruktor koji učitava indeks zadanog registra.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrIncrement(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		checkRegisters(arguments, 1);
		this.registerIndex = RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}
	
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		computer.getRegisters().setRegisterValue(registerIndex, Integer.valueOf((Integer) value + 1)); 
		return false;
	}

}
