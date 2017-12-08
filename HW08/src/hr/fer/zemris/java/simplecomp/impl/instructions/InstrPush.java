package hr.fer.zemris.java.simplecomp.impl.instructions;

import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkArguments;
import static hr.fer.zemris.java.simplecomp.ArgumentUtil.checkRegisters;

import java.util.List;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.StackUtil;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

/**
 * <p>Instrukcija koja stavlja vrijednost iz zadanog registra na vrh stoga (jedan argument,
 * nije dopušteno indirektno adresiranje). Nakon obavljenje operacije vrijednost kazaljke koja
 * pokazuje na vrh stoga se samnjuje za 1. (stog se povećava prema nižim adresama!)
 * 
 * <p>[r15] <- rx
 * r15 <- r15 - 1 </p>
 * 
 * @author Vjeco
 */
public class InstrPush implements Instruction {
	private int registerIndex;
	
	/**
	 * Konstruktor koji učitava indekse zadanih registara.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrPush(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		checkRegisters(arguments, 1);
		
		this.registerIndex = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}
	@Override
	public boolean execute(Computer computer) {
		Object value = computer.getRegisters().getRegisterValue(registerIndex);
		StackUtil.push(computer, value);
		
		return false;
	}

}
