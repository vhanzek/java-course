package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;
import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkRegisters;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Instrukcija koja postavlja zastavicu na temelju usporedbe sadržaja
 * dva zadana registra (dva argumenta) i pritom nije dopušteno indirektno 
 * adresiranje. Ako su sadržaji registara isti, zastavica se postavlja na
 * <code>true</code>, inače na <code>false</code>.
 * 
 * @author Vjeco
 */
public class InstrTestEquals implements Instruction {
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
	
	/**
	 * Konstruktor koji učitava indekse zadanih registara.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrTestEquals(List<InstructionArgument> arguments) {
		checkArguments(arguments, 2);
		checkRegisters(arguments, 2);
		
		this.registerIndex1 = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
		this.registerIndex2 = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(1).getValue());
	}
	@Override
	public boolean execute(Computer computer) {
		Registers regs = computer.getRegisters();
		Object value1 = regs.getRegisterValue(registerIndex1);
		Object value2 = regs.getRegisterValue(registerIndex2);
		computer.getRegisters().setFlag(areEqual(value1, value2));
		
		return false;
	}
	
	/**
	 * Pomoćna metoda koja provjerava jesu li sadržaji u registrima isti.
	 * 
	 * @param value1 sadržaj prvog registra
	 * @param value2 sadržaj drugog registra
	 * @return <code>true</code> ako su isti
	 * 		   <code>false</code> inače
	 */
	private boolean areEqual(Object value1, Object value2) {
		if(value1 instanceof String){
			if(value2 instanceof String){
				value1 = (String) value1;
				value2 = (String) value2;
				return value1.equals(value2);
			} else return false;
		} else if (value1 instanceof Integer){
			if(value2 instanceof Integer){
				value1 = (Integer) value1;
				value2 = (Integer) value2;
				return value1.equals(value2);
			} else return false;
		} else {
			return false;
		}
	}

}
