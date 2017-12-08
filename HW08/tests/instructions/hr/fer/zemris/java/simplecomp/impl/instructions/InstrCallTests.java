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
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
public class InstrCallTests {
	
	@Mock
	private InstructionArgument argument;
	
	@Mock
	private RegisterUtil util;

	Computer computer;
	
	@Test
	public void testReg() {

		when(argument.isNumber()).thenReturn(true);
		when(argument.getValue()).thenReturn(new Integer(8));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters();
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 100);
		
		executeInstrCall(argument); 
		
		computer.getRegisters();
		assertEquals(99, computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX));
		assertEquals(8, computer.getRegisters().getProgramCounter());
		
	}	
	
	private void executeInstrCall(InstructionArgument argument) {
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument); 
		
		Instruction call = new InstrCall(list);
		call.execute(computer);
		
	}
	
}
