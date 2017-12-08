package hr.fer.zemris.java.simplecomp.impl.instructions;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.RegisterUtil;
import hr.fer.zemris.java.simplecomp.impl.ComputerImpl;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class InstrMoveTests {
	
	@Mock
	private InstructionArgument argument1;
	
	@Mock
	private InstructionArgument argument2;
	
	@Mock
	private RegisterUtil util;

	Computer computer;
	
	@Test
	public void testRegReg() {
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8));
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getRegisters().setRegisterValue(10, 40);
		
		executeInstrMove(argument1, argument2); 
		
		assertEquals(40, computer.getRegisters().getRegisterValue(8));
		
	}
	
	@Test
	public void testIndRegReg() {
		
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8 + (5 << 8) + (1 << 24) ));
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getRegisters().setRegisterValue(10, 40);
		
		executeInstrMove(argument1, argument2); 

		assertEquals(40, computer.getMemory().getLocation(55));
		
	}
	
	@Test
	public void testIndRegNum() {
		
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8 + (5 << 8) + (1 << 24) ));
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		
		executeInstrMove(argument1, argument2); 

		assertEquals(10, computer.getMemory().getLocation(55));
	}
	
	@Test
	public void testRegNum() {
		
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8));
		when(argument2.isNumber()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		
		executeInstrMove(argument1, argument2); 

		assertEquals(10, computer.getRegisters().getRegisterValue(8));
	}
	
	@Test
	public void testRegIndReg() {
		
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8));
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10 + (5 << 8) + (1 << 24) ));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getRegisters().setRegisterValue(10, 40);
		computer.getMemory().setLocation(45, 13);
		
		executeInstrMove(argument1, argument2); 

		assertEquals(13, computer.getRegisters().getRegisterValue(8));
	}
	
	@Test
	public void testIndRegIndReg() {
		
		when(argument1.isRegister()).thenReturn(true);
		when(argument1.getValue()).thenReturn(new Integer(8 + (5 << 8) + (1 << 24) ));
		when(argument2.isRegister()).thenReturn(true);
		when(argument2.getValue()).thenReturn(new Integer(10 + (5 << 8) + (1 << 24) ));
		
		computer = new ComputerImpl(256, 16);
		computer.getRegisters().setRegisterValue(8, 50);
		computer.getRegisters().setRegisterValue(10, 40);
		computer.getMemory().setLocation(45, 13);
		
		executeInstrMove(argument1, argument2); 

		assertEquals(13, computer.getMemory().getLocation(55));
	}

	private void executeInstrMove(InstructionArgument argument1, InstructionArgument argument2) {
		
		List<InstructionArgument> list = new ArrayList<>();
		list.add(argument1); 
		list.add(argument2);
		
		Instruction move = new InstrMove(list);
		move.execute(computer);
		
	}
	
}
