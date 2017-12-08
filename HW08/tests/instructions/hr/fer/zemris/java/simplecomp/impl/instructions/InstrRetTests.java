package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class InstrRetTests {
	
	Computer computer;
	
	@Test
	public void test() {

		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters();
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 69);
		computer.getMemory().setLocation(70, 100);
		
		executeInstrRet(); 
		
		computer.getRegisters();
		assertEquals(70, computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX));
		assertEquals(100, computer.getRegisters().getProgramCounter());
		
	}	
	
	private void executeInstrRet() {
		
		List<InstructionArgument> list = new ArrayList<>();
		
		Instruction ret = new InstrRet(list);
		ret.execute(computer);
		
	}
	
}
