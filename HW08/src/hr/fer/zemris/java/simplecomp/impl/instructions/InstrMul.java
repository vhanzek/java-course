package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.*;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * Instrukcija koja prima 3 argumenta, točnije 3 registra i pritom nije dopušteno 
 * zadavanje indirektnim adresama. Množi vrijednosti iz potonja dva registra i 
 * stavlja dobivenu vrijednost u prvi zadani registar.
 * 
 * @author Vjeco
 */
public class InstrMul implements Instruction {
	/**
	 * Indeks prvog registra.
	 */
	private int registerIndex1;
	/**
	 * Indeks drugog registra.
	 */
	private int registerIndex2;
	/**
	 * Indeks trećeg registra.
	 */
	private int registerIndex3;

	/**
	 * Konstruktor koji učitava indekse zadanih registara.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrMul(List<InstructionArgument> arguments) {
		checkArguments(arguments, 3);
		checkRegisters(arguments, 3);
		
		this.registerIndex1 = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
		this.registerIndex3 = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(2).getValue());
	}

	public boolean execute(Computer computer) {
		Object value1 = computer.getRegisters().getRegisterValue(registerIndex2);
		Object value2 = computer.getRegisters().getRegisterValue(registerIndex3);
		computer.getRegisters().setRegisterValue(registerIndex1, Integer.valueOf((Integer) value1 * (Integer) value2));
		return false;
	}

}
