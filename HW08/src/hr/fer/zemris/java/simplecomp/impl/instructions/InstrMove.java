package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;

import java.util.List;

import static hr.fer.zemris.java.simplecomp.RegisterUtil.*;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * <p>Instrukcija koja omogućuje mijenjanje sadržaja registara i memorijskih
 * lokacija ovisno o tome koji su argumenti zadani.</p>
 * 
 * <p><ul>U ovoj instrukciji vrijede pravila:
 * 		<li>prvi argument može biti registar ili indirektno adresiranje</li>
 * 		<li>drugi argument može biti registar, indirektno adresiranje ili broj</li>
 * </ul>
 * Moguće se sve kombinacije ovih pravila.</p>
 * 
 * @author Vjeco
 *
 */
public class InstrMove implements Instruction {
	/**
	 * Indeks odredišnog registra.
	 */
	private int destRegisterIndex;
	/**
	 * Vrijednost zadanog broja (ako je broj zadan) ili indeks 
	 * zadanog  izvornog registra.
	 */
	private int srcValue;
	/**
	 * Ako je {@code destIndirect = true}, onda ova članska varijabla
	 * predstavlja odmak u indirektnog adresiranju.
	 */
	private int destOffset;
	/**
	 * Ako je {@code srcIndirect = true}, onda ova članska varijabla
	 * predstavlja odmak u indirektnog adresiranju.
	 */
	private int srcOffset;
	/**
	 * Zastavica koja određuje korišteno idirektno adresiranje u 
	 * odredištnom registru.
	 */
	private boolean destIndirect;
	/**
	 * Zastavica koja određuje korišteno idirektno adresiranje u 
	 * izvornom registru.
	 */
	private boolean srcIndirect;
	/**
	 * Zastavica koja određuje je li drugi argument broj ili registar.
	 */
	private boolean srcRegister;
	
	/**
	 * Konstruktor koji određuje kakvi su zadani argumenti -
	 * je li prvi argument registar ili indirektno adresiranje i
	 * je li drugi argument registar, indirektno adresiranje ili
	 * broj.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrMove(List<InstructionArgument> arguments) {
		checkArguments(arguments, 2);
		
		if (!arguments.get(0).isRegister()){
			throw new IllegalArgumentException(
				"Type mismatch for first argument"
			);
		}
		
		Integer destArg = (Integer) arguments.get(0).getValue();
		Integer srcArg = (Integer) arguments.get(1).getValue();
		
		destRegisterIndex = getRegisterIndex(destArg);
		if(destIndirect = isIndirect(destArg)){
			destOffset = getRegisterOffset(destArg);
		}

		if(srcRegister = arguments.get(1).isRegister()){
			srcValue = getRegisterIndex(srcArg);
			if(srcIndirect = isIndirect(srcArg)){
				srcOffset = getRegisterOffset(srcArg);
			}
		} else {
			srcValue = srcArg;
		}
	}
	
	@Override
	public boolean execute(Computer computer) {
		Memory mem = computer.getMemory();
		Registers regs = computer.getRegisters();
		Object value = null;
		
		if(srcRegister){
			if(srcIndirect){
				int srcMemLocation = (Integer) regs.getRegisterValue(srcValue) + srcOffset;
				value = computer.getMemory().getLocation(srcMemLocation);
			} else {
				value = regs.getRegisterValue(srcValue); 
			}
		} else {
			value = (Integer) srcValue;
		}
		
		if(destIndirect){
			int destMemLocation = (Integer) regs.getRegisterValue(destRegisterIndex) + destOffset;
			mem.setLocation(destMemLocation, value);
		} else {
			regs.setRegisterValue(destRegisterIndex, value);
		}
			
		return false;
	}

}
