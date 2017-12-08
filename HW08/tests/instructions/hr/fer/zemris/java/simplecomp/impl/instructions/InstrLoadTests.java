package hr.fer.zemris.java.simplecomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

@RunWith(MockitoJUnitRunner.class)
public class InstrLoadTests {

	@Mock
	private InstructionArgument argument1;
	
	@Mock
	private InstructionArgument argument2;
	
	@Mock
	private RegisterUtil util;

	Computer computer;
	
	@Test
	public void testReg() {

		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8));
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getMemory().setLocation(10, 69);
		
		executeInstrLoad(argument1, argument2); 
		
		assertEquals(69, computer.getRegisters().getRegisterValue(8));
		
	}	
	
	private void executeInstrLoad(InstructionArgument argument1, InstructionArgument argument2) {
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1); 
		list.add(argument2);
		
		Instruction load = new InstrLoad(list);
		load.execute(computer);
		
	}
	
}
