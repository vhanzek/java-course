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
public class InstrPushTests {
	
	@Mock
	private InstructionArgument argument;
	
	@Mock
	private RegisterUtil util;

	Computer computer;
	
	@Test
	public void testReg() {

		when(argument.isRegister()).thenReturn(true);
		when(argument.getValue()).thenReturn(new Integer(8));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getRegisters();
		computer.getRegisters().setRegisterValue(Registers.STACK_REGISTER_INDEX, 100);
		
		executeInstrPush(argument); 
		
		computer.getRegisters();
		int top = (int) computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX);
		top++; 
		
		computer.getRegisters();
		assertEquals(99, computer.getRegisters().getRegisterValue(Registers.STACK_REGISTER_INDEX));
		assertEquals(50, computer.getMemory().getLocation(top));
		
	}	
	
	private void executeInstrPush(InstructionArgument argument) {
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument); 
		
		Instruction push = new InstrPush(list);
		push.execute(computer);
		
	}
	
}
