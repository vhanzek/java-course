package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja prima jedan argument - registar ili indirektna adresa. 
 * Ako je zadan registar, instrukcija ispisuje sadržaj registra na standardni
 * izlaz, inače, ako je zadana indirektna adresa, ispisuje sadržaj memorijske
 * lokacije koja je dobivena zbrajanjem sadržaja registra i zdanog offseta.
 * 
 * @author Vjeco
 */
public class InstrEcho implements Instruction {
	/**
	 * Indeks zadanog registra.
	 */
	private int registerIndex;
	/**
	 * Odmak u indirektnoj adresi.
	 */
	private int offset;
	/**
	 * Zastavica koja određuje je li zadana indirektna adresa ili registar.
	 */
	private boolean indirect;

	/**
	 * Konstruktor koji učitava indeks zadanog registra i odmak ako je zadana 
	 * indirektna adresa.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrEcho(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		if(!arguments.get(0).isRegister()){
			throw new IllegalArgumentException(
				"Argument must be a register!"
			);
		}
		Integer registerDescriptor = (Integer) arguments.get(0).getValue();
		this.registerIndex = RegisterUtil.getRegisterIndex(registerDescriptor);
		
		if(RegisterUtil.isIndirect((Integer) arguments.get(0).getValue())){
			this.offset = RegisterUtil.getRegisterOffset(registerDescriptor);
			this.indirect = true;
		} 
	}

	@Override
	public boolean execute(Computer computer) {
		if(indirect){
			int memoryLoc = (Integer) computer.getRegisters().getRegisterValue(registerIndex) + offset;
			Object value = computer.getMemory().getLocation(memoryLoc);
			if(value != null){
				System.out.print(computer.getMemory().getLocation(memoryLoc));
			} else {
				throw new IllegalArgumentException(
					"Printing from empty memory location."
				);
			}
		} else {
			System.out.print(computer.getRegisters().getRegisterValue(registerIndex));
		}
	
		return false;
	}

}
