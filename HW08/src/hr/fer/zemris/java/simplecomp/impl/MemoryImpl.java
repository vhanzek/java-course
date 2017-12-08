package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Memory;

/**
 * Implemetacija sučelja {@link Memory} koja predstavlja memoriju računala.
 * 
 * @author Vjeco
 */
public class MemoryImpl implements Memory {
	/**
	 * Memorijske lokacije.
	 */
	private Object[] memoryLocations;
	
	/**
	 * Konstruktor koji stvara novo polje memorijskih
	 * lokacija.
	 * 
	 * @param size broj memorijskih lokacija
	 */
	public MemoryImpl(int size) {
		this.memoryLocations = new Object[size];
	}

	@Override
	public void setLocation(int location, Object value) {
		memoryLocations[location] = value;
	}

	@Override
	public Object getLocation(int location) {
		return memoryLocations[location];
	}

}
