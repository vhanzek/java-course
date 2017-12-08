package hr.fer.zemris.java.tecaj.hw.collection;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

public class ShmTests {

	SimpleHashtable<String, Integer> table;

	@Before
	public void setUp(){
		table = new SimpleHashtable<>();
		
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		table.put("Mirko", 3);
		table.put("Marko", 4);
		table.put("Siniša", 5);
		table.put("Mario", 3);
		table.put("Dario", 3);
		table.put("Štefica", 5);
	}

	@Test
	public void testCreation() {
		assertEquals("Expected 10 elements.", 10, table.size());
		assertEquals("Expected 5.", Integer.valueOf(5), table.get("Štefica"));
		assertTrue("Expected that table contains Štefica.", table.containsKey("Štefica"));
	}

	@Test
	public void testPut(){
		table.put("Milica", 1);
		assertEquals("Expected 11 elements.", 11, table.size());
		assertEquals("Expected 1.", Integer.valueOf(1), table.get("Milica"));
		assertTrue("Expected that table contains Milica.", table.containsKey("Milica"));
		assertTrue("Expected that table contains value=1.", table.containsValue(1));
		
		table.put("Ivan", null);
		assertEquals("Expected 12 elements.", 12, table.size());
		assertEquals("Expected null.", null, table.get("Ivan"));
		assertTrue("Expected that table contains null.", table.containsValue(null));
		
		table.put("Ivana", 5);
		assertEquals("Expected 5.", Integer.valueOf(5), table.get("Ivana"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPutWithKeyNull(){
		table.put(null, 1);
	}
	
	@Test
	public void testGet(){
		assertEquals("Expected 3.", Integer.valueOf(3), table.get("Mirko"));
		assertEquals("Expected null.", null, table.get("Morko"));
	}
	
	@Test
	public void testSize(){
		assertEquals("Expected 10.", 10, table.size());
	}
	
	@Test
	public void testContainsKey(){
		assertTrue("Expected that table contains Ivana.", table.containsKey("Ivana"));
		assertFalse("Expected that table does not contain given key.", table.containsKey("Ivanova livada"));
	}
	
	@Test
	public void testContainsValue(){
		assertTrue("Expected that table contains 2.", table.containsValue(2));
		assertFalse("Expected that table deos not contain 1.", table.containsValue(1));
		
		table.put("Miro", null);
		assertTrue("Expected that table contains null.", table.containsValue(null));
	}
	
	@Test
	public void testRemove(){
		table.remove("Marko");
		assertEquals("Expected 9 elements.", 9, table.size());
		assertEquals("Expected null.", null, table.get("Marko"));
		assertFalse("Expected that table does not contain Marko.", table.containsKey("Marko"));		
	}

	@Test
	public void testClearAndIsEmpty(){
		table.clear();
		assertEquals("Expected 0 elements.", 0, table.size());
		assertTrue("Expected that table is empty.", table.isEmpty());			
	}

	@Test
	public void testToString(){
		SimpleHashtable<String, Integer> tempTable = new SimpleHashtable<>();	
		tempTable.put("Mike", 2);
		tempTable.put("Scottie", 3);
		assertEquals("Expected elements as a string.", "[Scottie=3, Mike=2]", tempTable.toString());
	}

	@Test(expected=IllegalStateException.class)
	public void testTableIterator1(){
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter1 = table.iterator();
		while (iter1.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter1.next();
			if (pair.getKey().equals("Jasna")) {
				iter1.remove();
				iter1.remove();
			}
		}
	}

	@Test(expected=ConcurrentModificationException.class)
	public void testTableIterator2(){
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter2 = table.iterator();
		while (iter2.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter2.next();
			if (pair.getKey().equals("Kristina")) {
				table.remove("Kristina");
			}
		}
	}

	@Test
	public void tableIterator3(){
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); 
			}
		}
		
		assertFalse("Expected that table does not contain Ivana.", table.containsKey("Ivana"));		
	}

	@Test
	public void testTableIterator4(){
		SimpleHashtable<String, Integer> tempTable = new SimpleHashtable<>();	
		
		for(SimpleHashtable.TableEntry<String,Integer> entry : table){
			tempTable.put(entry.getKey(), entry.getValue());
		}
		
		assertEquals("Expected 10 elements.", 10, tempTable.size());
		assertEquals("Expected 1.", Integer.valueOf(5), tempTable.get("Štefica"));
		assertTrue("Expected that table contains Milica.", tempTable.containsKey("Štefica"));
		assertTrue("Expected that table contains value=1.", tempTable.containsValue(5));
		
		
	}
}
