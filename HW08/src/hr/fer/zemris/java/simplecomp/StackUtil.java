package hr.fer.zemris.java.simplecomp;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * <p>Pomoćni razred sa dvije statičke metode za obavljenje operacija
 * push i pop na stogu.</p> 
 * 
 * <p>Stoga počinje na višim memorijskim lokacija i povećava se prema nižima.</p>
 * 
 * @author Vjeco
 */
public class StackUtil {
	
	/**
	 * Metoda koja stavlja {@code value} na stog i smanjuje adresu trenutnog
	 * pokazivača na vrh stoga za 1.
	 * 
	 * @param computer primjerak rzreda {@link Computer} pomoću kojeg se obavljaju
	 * 		           transkacije između memorije i registara
	 * @param value	   vrijednost koja se treba staviti na vrh stoga
	 */
	public static void push(Computer computer, Object value){
		Registers regs = computer.getRegisters();
		
		int stackMemLocation = (Integer) regs.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		computer.getMemory().setLocation(stackMemLocation, value);
		regs.setRegisterValue(Registers.STACK_REGISTER_INDEX, stackMemLocation - 1);
	}
	
	/**
	 * Metoda koja uzima vrijednost sa stoga i stavlja ju u zadani registar ako je on
	 * veći od 0. Nakon obavljene operacije se vrijednost kazaljke vrha stoga poveća za 1.
	 * 
	 * @param computer primjerak rzreda {@link Computer} pomoću kojeg se obavljaju
	 * 		           transkacije između memorije i registara
	 * @param registerIndex	indeks registra u koji se stavlja uzeta vrijednost sa stoga.
	 * 						Ako je indeks manji od 0, stvaljanje u registar se preskače.
	 * @return vrijednost uzeta sa stoga
	 */
	public static Object pop(Computer computer, int registerIndex){
		Registers regs = computer.getRegisters();
		Memory mem = computer.getMemory();
		
		int stackMemLocation = (Integer) regs.getRegisterValue(Registers.STACK_REGISTER_INDEX);
		Object value = mem.getLocation(stackMemLocation + 1);
		
		if(registerIndex >= 0){
			regs.setRegisterValue(registerIndex, value);
		}
		regs.setRegisterValue(Registers.STACK_REGISTER_INDEX, stackMemLocation + 1);
		
		return value;
	}
}
