package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * Implemetacija sučelja {@link Registers} koja predstavlja
 * registre opće namjene u računalu. Također sadrži dva "posebna"
 * registra: zastavicu i programsko brojilo.
 * 
 * @author Vjeco
 */
public class RegistersImpl implements Registers {
	/**
	 * Zastavica.
	 */
	private boolean flag;
	/**
	 * Programsko brojilo.
	 */
	private int programCounter;
	/**
	 * Registri opće namjene.
	 */
	private Object[] registers;
	
	/**
	 * Konstruktor koji stvara nove registre opće namjene.
	 * 
	 * @param regsLen broj registara opće namjene
	 */
	public RegistersImpl(int regsLen) {
		this.registers = new Object[regsLen];
	}

	@Override
	public Object getRegisterValue(int index) {
		return registers[index];
	}

	@Override
	public void setRegisterValue(int index, Object value) {
		registers[index] = value;
	}

	@Override
	public int getProgramCounter() {
		return programCounter;
	}

	@Override
	public void setProgramCounter(int value) {
		programCounter = value;
	}

	@Override
	public void incrementProgramCounter() {
		programCounter++;
	}

	@Override
	public boolean getFlag() {
		return flag;
	}

	@Override
	public void setFlag(boolean value) {
		this.flag = value;
	}

}
