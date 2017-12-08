package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implementacija sučelja {@link Computer} koja predstavlja računalo sa određenim 
 * brojem memorijskih lokacija i registara opće namjene.
 * 
 * @author Vjeco
 */
public class ComputerImpl implements Computer {
	/**
	 * Predstavlja registre opće namjene u računalu.
	 */
	private Registers registers;
	/**
	 * Predstavlja memoriju računala.
	 */
	private Memory memory;
	
	/**
	 * Konstruktor koji instancira nove primjerke razeda {@link RegistersImpl}
	 * i {@link MemoryImpl}.
	 * 
	 * @param memorySize broj memorijskih lokacija
	 * @param regsLen broj registara opće namjene
	 */
	public ComputerImpl(int memorySize, int regsLen) {
		this.registers = new RegistersImpl(regsLen);
		this.memory = new MemoryImpl(memorySize);
	}

	@Override
	public Registers getRegisters() {
		return registers;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

}
