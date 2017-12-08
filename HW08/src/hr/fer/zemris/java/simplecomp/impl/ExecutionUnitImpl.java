package hr.fer.zemris.java.simplecomp.impl;

import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.ExecutionUnit;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

/**
 * * <p>Upravljački sklop računala. Ovaj razred "izvodi"
 * program odnosno predstavlja impulse takta za sam
 * procesor.</p>
 * 
 * @author Vjeco
 */
public class ExecutionUnitImpl implements ExecutionUnit {

	@Override
	public boolean go(Computer computer) {
		Registers regs = computer.getRegisters();
		Memory mem = computer.getMemory();
		regs.setProgramCounter(0);
		
		while(true){
			
			Instruction instruction = 
					(Instruction) mem.getLocation(regs.getProgramCounter());
			regs.incrementProgramCounter();
			if(instruction.execute(computer)) break;
		}
		
		return true;
	}

}
