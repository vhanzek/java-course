package hr.fer.zemris.java.custom.scripting;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.exec.ObjectMultistack;
import hr.fer.zemris.java.custom.scripting.exec.ValueWrapper;

public class OmsTests {

	@Test
	public void testPush(){
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		ValueWrapper day = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper price = new ValueWrapper(Double.valueOf(333.33));
		
		multistack.push("year", year);
		assertEquals("Expected 2000.", Integer.valueOf(2000), multistack.peek("year").getValue());
		multistack.push("year", day);
		assertEquals("Expected 2000.", Integer.valueOf(1), multistack.peek("year").getValue());
		multistack.push("price", price);
		assertEquals("Expected 2000.", Double.valueOf(333.33), multistack.peek("price").getValue());
	}
	
	@Test
	public void testPeek(){
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		
		multistack.push("year", year);
		assertEquals("Expected 2000.", Integer.valueOf(2000), multistack.peek("year").getValue());
	}
	
	@Test
	public void testPop(){
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		ValueWrapper day = new ValueWrapper(Integer.valueOf(1));
		
		multistack.push("year", year);
		multistack.push("year", day);
		multistack.pop("year");
		assertEquals("Expected 2000.", Integer.valueOf(2000), multistack.peek("year").getValue());
	}
	
	@Test(expected = EmptyStackException.class)
	public void testEmptyStackPop(){
		ObjectMultistack multistack = new ObjectMultistack();		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		
		multistack.push("year", year);
		multistack.pop("year");
		multistack.pop("year");
	}
	
	@Test(expected = EmptyStackException.class)
	public void testEmptyStackPeek(){
		ObjectMultistack multistack = new ObjectMultistack();		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		
		multistack.push("year", year);
		multistack.pop("year");
		multistack.peek("year");
	}
	
	@Test
	public void testIsEmpty(){
		ObjectMultistack multistack = new ObjectMultistack();		
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		
		multistack.push("year", year);
		multistack.pop("year");
		assertTrue("Expected true.", multistack.isEmpty("year"));
	}
	
	@Test
	public void testValueInteger(){
		ValueWrapper marijo = new ValueWrapper(Integer.valueOf(5000));
		assertTrue("Expected integer.", marijo.getValue() instanceof Integer);
	}
	
	@Test
	public void testValueDouble(){
		ValueWrapper marijo = new ValueWrapper(Double.valueOf(5000.34));
		assertTrue("Expected integer.", marijo.getValue() instanceof Double);
	}
	
	@Test
	public void testValueString(){
		ValueWrapper decimal = new ValueWrapper("3.4");
		ValueWrapper sci1 = new ValueWrapper("2.4E+7");
		ValueWrapper sci2 = new ValueWrapper("3.7e+11");
		ValueWrapper integer = new ValueWrapper("3");
		
		assertTrue("Expected integer.", integer.getValue() instanceof Integer);
		assertTrue("Expected double.", decimal.getValue() instanceof Double);
		assertTrue("Expected double.", sci1.getValue() instanceof Double);
		assertTrue("Expected double.", sci2.getValue() instanceof Double);
	}
	
	@Test
	public void testValueNull(){
		ValueWrapper marijo = new ValueWrapper(null);
		assertTrue("Expected integer.", marijo.getValue() instanceof Integer);
		assertEquals("Expected 0.", Integer.valueOf(0),marijo.getValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testIllegalValueString1(){
		new ValueWrapper("3.4ZOO");
	}
	
	@Test(expected = RuntimeException.class)
	public void testIllegalValueString2(){
		new ValueWrapper("34ZOO");
	}
	
	@Test(expected = RuntimeException.class)
	public void testIllegalValue(){
		new ValueWrapper(new ValueWrapper("1"));
	}
	
	@Test
	public void testValueIncrement(){
		ValueWrapper temp = new ValueWrapper("3");
		
		temp.increment(Integer.valueOf(4));
		assertEquals(Integer.valueOf(7), temp.getValue());
		temp.increment(Double.valueOf(3.4));
		assertEquals(Double.valueOf(10.4), temp.getValue());
		temp.increment(Integer.valueOf(4));
		assertEquals(Double.valueOf(14.4), temp.getValue());
	}
	
	@Test
	public void testValueDecrement(){
		ValueWrapper temp = new ValueWrapper("21");
		
		temp.decrement(Integer.valueOf(4));
		assertEquals( Integer.valueOf(17), temp.getValue());
		temp.decrement(Double.valueOf(3.4));
		assertEquals(Double.valueOf(13.6), temp.getValue());
		temp.decrement(Integer.valueOf(4));
		assertEquals(Double.valueOf(9.6), temp.getValue());
	}
	
	@Test
	public void testValueMultiply(){
		ValueWrapper temp = new ValueWrapper("20");
		
		temp.multiply(Integer.valueOf(4));
		assertEquals( Integer.valueOf(80), temp.getValue());
		temp.multiply(Double.valueOf(1.5));
		assertEquals(Double.valueOf(120), temp.getValue());
		temp.multiply(Integer.valueOf(4));
		assertEquals(Double.valueOf(480.0), temp.getValue());
	}
	
	@Test
	public void testValueDividing(){
		ValueWrapper temp = new ValueWrapper("100");
		
		temp.divide(Integer.valueOf(3));
		assertEquals(Integer.valueOf(33), temp.getValue());
		temp.divide(Double.valueOf(3.3));
		assertEquals(Double.valueOf(10), temp.getValue());
		temp.divide(Integer.valueOf(4));
		assertEquals(Double.valueOf(2.5), temp.getValue());
	}
	
	@Test
	public void testNumCompare(){
		ValueWrapper temp = new ValueWrapper("100");
		
		assertEquals(0, temp.numCompare(Integer.valueOf(100)));
		assertTrue("Expected int bigger than 0", temp.numCompare(Integer.valueOf(50)) > 0);
		assertTrue("Expected int lesser than 0", temp.numCompare(Integer.valueOf(120)) < 0);
	}

}
