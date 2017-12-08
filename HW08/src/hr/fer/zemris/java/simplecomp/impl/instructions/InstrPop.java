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
 * <p>Instrukcija koja uzima vrijednost sa vrha stoga (na koji pokazuje kazaljka vrha stoga
 * koja se nalazi u 15. registru opće namjene). Uzeta vrijednost se stavlja u zadani
 * registar (jedan argument, nije dopušteno indirektno adresiranje) i vrijednost kazaljke
 * koja pokazuje na vrh stoga se povećava za 1.</p>
 * 
 * <p>rx <- [r15+1]
 * r15 <- r15 + 1 </p>
 * 
 * @author Vjeco
 */
public class InstrPop implements Instruction {
	/**
	 * Indeks registra.
	 */
	private int registerIndex;
	
	/**
	 * Konstruktor koji učitava indeks zadanog registra.
	 * 
	 * @param arguments argumenti instrukcije
	 */
	public InstrPop(List<InstructionArgument> arguments) {
		checkArguments(arguments, 1);
		checkRegisters(arguments, 1);
		
		this.registerIndex = 
				RegisterUtil.getRegisterIndex((Integer) arguments.get(0).getValue());
	}
	@Override
	public boolean execute(Computer computer) {
		StackUtil.pop(computer, registerIndex);		
		return false;
	}

}
