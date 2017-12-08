package hr.fer.zemris.java.cstr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import static hr.fer.zemris.java.cstr.CString.*;

import org.junit.Test;


// TODO: Auto-generated Javadoc
/**
 * The Class CStringTests.
 */
public class CStringTests {
	
	/**
	 * Test c string creation.
	 */
	@Test
	public void testCStringCreation() {
		CString string = new CString(fromString("Štefica").toCharArray(), 1, 4);
		
		assertEquals("Invalid string.", "tefi", string.toString());
        assertEquals("Invalid length.", 4, string.length());
        assertEquals("Invalid character.", 'e', string.charAt(1));
        
        CString string2 = new CString(fromString("Štefica").toCharArray());
        assertEquals("Expected 'Štefica'", "Štefica", string2.toString());
 
        CString string3 = new CString(string2);
        assertEquals("Expected 'Štefica'", "Štefica", string3.toString());
	}
	
	/**
	 * Test null input.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput(){
		//must throw
		new CString(null, 2, 7);
	}
	
	/**
	 * Test lengths starts ends.
	 */
	@Test
    public void testLengthsStartsEnds() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        
        assertEquals("Expected 3x 'Štefica' with whitespaces.", "Štefica Štefica Štefica", string.toString());
        assertEquals("Expected 23.", 23, string.length());
        assertEquals("Expected 23.", 23, string.toCharArray().length);
        assertEquals("Expected ' '.", ' ', string.charAt(7));
        assertEquals("Expected 'true'", true, string.endsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));
        assertEquals("Expected 'true'", true, string.startsWith(new CString(String.valueOf("Štefica").toCharArray(), 0, 7)));
        assertEquals("Expected 'false'", false, string.endsWith(new CString(String.valueOf("Šfetica").toCharArray(), 0, 7)));
        assertEquals("Expected 'false'", false, string.endsWith(new CString(String.valueOf("Šfe").toCharArray(), 0, 3)));
    }
	
	/**
	 * Test index out of bounds.
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testIndexOutOfBounds(){
		new CString(fromString("Milica").toCharArray(), 3, 7);
	}
	
	/**
	 * Test negative offset.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeOffset(){
		new CString(fromString("Milica").toCharArray(), -4, 7);
	}
	
	/**
	 * Test negative length.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNegativeLength(){
		new CString(fromString("Milica").toCharArray(), 3, -3);
	}
	
	/**
	 * Test from string.
	 */
	@Test
	public void testFromString(){
		CString string = fromString("Milica");
		
		assertEquals("Invalid string", "Milica", string.toString());
		assertEquals("Invalid length.", 6, string.length());
        assertEquals("Invalid character.", 'a', string.charAt(5));
	}
	
	/**
	 * Test char at.
	 */
	@Test
	public void testCharAt(){
		CString string = new CString(fromString("Štefanija").toCharArray(), 0, 8);
		
		assertEquals("Invalid character.", 'Š', string.charAt(0));
		assertEquals("Invalid character.", 'n', string.charAt(5));
	}
	
	/**
	 * Test char at invalid index.
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testCharAtInvalidIndex(){
		CString string = new CString(fromString("Štefanija").toCharArray(), 4, 3);
		
		//must throw 
		string.charAt(7);
	}
	
	/**
	 * Test to char array.
	 */
	@Test
	public void testToCharArray(){
		CString string = new CString(new char[]{'M', 'a', 'r', 'i', 'o'});
		char[] chars = string.toCharArray();
		
		assertEquals("Invalid length", 5, chars.length);
		assertTrue(Arrays.equals(String.valueOf("Mario").toCharArray(), chars));
	}
	
	/**
	 * Test to string.
	 */
	@Test
	public void testToString(){
		assertEquals("Different strings.", String.valueOf("Štef"), new CString(String.valueOf("Štef").toCharArray()).toString());
	}
	
	/**
	 * Test index of with one searched character.
	 */
	@Test
	public void testIndexOfWithOneSearchedCharacter(){
		CString string = new CString(String.valueOf("Štef").toCharArray());
		
		assertEquals("Invalid index.", 1, string.indexOf('t'));
	}
	
	/**
	 * Test index of with more searched characters.
	 */
	@Test
	public void testIndexOfWithMoreSearchedCharacters(){
		CString string = new CString(String.valueOf("čečenija").toCharArray());
		
		assertEquals("Invalid index.", 0, string.indexOf('č'));
		assertEquals("Invalid index.", -1, string.indexOf('z'));
		assertEquals("Invalid index.", string.length() - 1, string.indexOf('a'));
	}
	
	/**
	 * Test starts with.
	 */
	@Test
	public void testStartsWith(){
		CString string = new CString(String.valueOf("Čečenija").toCharArray());
		
		assertTrue(string.startsWith(new CString(String.valueOf("Čeče").toCharArray())));
	}
	
	/**
	 * Test ends with.
	 */
	@Test
	public void testEndsWith(){
		CString string = new CString(String.valueOf("Čečenija").toCharArray());
		
		assertTrue(string.endsWith(new CString(String.valueOf("nija").toCharArray())));
		assertEquals(false, string.endsWith(new CString(String.valueOf("Čeče").toCharArray())));
	}
	
	/**
	 * Test starts with with null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStartsWithWithNull(){
		CString string = new CString(String.valueOf("Čečenija").toCharArray());
		
		//must throw 
		string.startsWith(null);
	}
	
	/**
	 * Test ends with with null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEndsWithWithNull(){
		CString string = new CString(String.valueOf("Čečenija").toCharArray());
		
		//must throw 
		string.endsWith(null);
	}
	
	/**
	 * Test contains.
	 */
	@Test
	public void testContains(){
		CString string = new CString(String.valueOf("Ivančica").toCharArray());
		
		assertTrue("It is expected that string contains given string.", string.contains(new CString(String.valueOf("čic").toCharArray())));
		assertTrue("It is expected that string contains given string.", string.contains(new CString(String.valueOf("Ivan").toCharArray())));
		assertEquals("It is expected that string does not contain given string.", false, string.contains(new CString(String.valueOf("mir").toCharArray())));
	}
	
	/**
	 * Test contains with null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testContainsWithNull(){
		CString string = new CString(String.valueOf("Ivančica").toCharArray());
		
		//must throw 
		string.contains(null);
	}
	
	/**
	 * Test substring.
	 */
	@Test
	public void testSubstring(){
		CString string = new CString(String.valueOf("Ivančica Maslačica").toCharArray());
		
		assertEquals("Invalid substring.", "čica", string.substring(4, 8).toString());
	}
	
	/**
	 * Test substring with invalid start index.
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testSubstringWithInvalidStartIndex(){
		CString string = new CString(String.valueOf("Ivančica Maslačak").toCharArray());
		
		string.substring(-7, 8);
	}
	
	/**
	 * Test substring with invalid end index.
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testSubstringWithInvalidEndIndex(){
		CString string = new CString(String.valueOf("Ivančica Maslačak").toCharArray());
		
		string.substring(7, 20);
	}
	
	/**
	 * Test substring with start bigger than end.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSubstringWithStartBiggerThanEnd(){
		CString string = new CString(String.valueOf("Ivančica Maslačak").toCharArray());
		
		//must throw 
		string.substring(9, 7);
	}
	
	/**
	 * Test left.
	 */
	@Test
    public void testLeft() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        assertEquals("Expected 'Šte'.", "Šte", string.left(3).toString());
        assertEquals("Expected 'Štefica '.", "Štefica ", string.left(8).toString());
        assertEquals("Expected ''.", "", string.left(0).toString());
        assertEquals("Expected 'Štefica Štefica Štefica'.", "Štefica Štefica Štefica", string.left(23).toString());
    }
 
    /**
     * Test right.
     */
    @Test
    public void testRight() {
        CString string = new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23);
        assertEquals("Expected 'ica'", "ica", string.right(3).toString());
        assertEquals("Expected ' Štefica'", " Štefica", string.right(8).toString());
        assertEquals("Expected ''", "", string.right(0).toString());
        assertEquals("Expected 'Štefica Štefica Štefica'", "Štefica Štefica Štefica", string.right(23).toString());
    }
 
    /**
     * Test left negative index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLeftNegativeIndex() {
        // must throw
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(-1);
 
    }
 
    /**
     * Test left bigger than length index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testLeftBiggerThanLengthIndex() {
        // must throw
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).left(24);
    }
 
    /**
     * Test right negative index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRightNegativeIndex() {
        // must throw
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(-1);
    }
 
    /**
     * Test right bigger than length index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRightBiggerThanLengthIndex() {
        // must throw
        new CString(String.valueOf("Štefica Štefica Štefica").toCharArray(), 0, 23).right(24);
    }
    
    /**
     * Test add with null value.
     */
    @Test(expected = NullPointerException.class)
    public void testAddWithNullValue() {
        // must throw!
        CString.fromString("Marko").add(null);
    }
 
    /**
     * Test add with valid argumetns.
     */
    @Test
    public void testAddWithValidArgumetns() {
        CString string1 = CString.fromString("Štefica").add(CString.fromString(" i "));
        CString string2 = CString.fromString("Mario");
 
        assertEquals("Expected 'Štefica i '", "Štefica i ", string1.toString());
        assertEquals("Expected 'Štefica i Mario'.", "Štefica i Mario", string1.add(string2).toString());
    }
    
    /**
     * Test replace all with chars as arguments.
     */
    @Test
    public void testReplaceAllWithCharsAsArguments() {
        CString string1 = CString.fromString("# Štefica# i Marko # se vole. # #");
        CString string2 = string1.replaceAll('#', '*');
        CString string3 = string2.replaceAll('$', '*');
        CString string4 = string3.replaceAll(' ', '$');
 
        assertEquals("I should be full of stars.", "* Štefica* i Marko * se vole. * *", string2.toString());
        assertEquals("I should be also full of stars.", "* Štefica* i Marko * se vole. * *", string3.toString());
        assertEquals("I should be also full of stars and dollaz.", "*$Štefica*$i$Marko$*$se$vole.$*$*",
                string4.toString());
    }
 
    /**
     * Test replace all with null as value1.
     */
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue1() {
        // must throw
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, CString.fromString("zeko"));
    }
 
    /**
     * Test replace all with null as value2.
     */
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue2() {
        // must throw
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(CString.fromString("Marko"), null);
    }
 
    /**
     * Test replace all with null as value3.
     */
    @Test(expected = NullPointerException.class)
    public void testReplaceAllWithNullAsValue3() {
        // must throw
        CString.fromString("Štefica i Marko šetaju se šumom.").replaceAll(null, null);
    }
 
    /**
     * Test replace all with c strings as arguments.
     */
    @Test
    public void testReplaceAllWithCStringsAsArguments() {
        CString string1 = CString.fromString("Štefica i Marko šetaju šumom.\n" + "Marko i Štefica drže se za ruke.\n"
                + "Štefici se sviđa Marko.\n" + "Marku se sviđa Štefica.");
        CString result1 = string1.replaceAll(CString.fromString("Štefica"), CString.fromString("Marica"));
        CString result2 = string1.replaceAll(CString.fromString("Marko"), CString.fromString("Ivica"));
        CString result3 = string1.replaceAll(CString.fromString("medvjed"), CString.fromString("zeko"));
 
        CString compare1 = CString.fromString("Marica i Marko šetaju šumom.\n" + "Marko i Marica drže se za ruke.\n"
                + "Štefici se sviđa Marko.\n" + "Marku se sviđa Marica.");
        CString compare2 = CString.fromString("Štefica i Ivica šetaju šumom.\n" + "Ivica i Štefica drže se za ruke.\n"
                + "Štefici se sviđa Ivica.\n" + "Marku se sviđa Štefica.");
 
        assertEquals("We should be the same.", 0, result1.toString().compareTo(compare1.toString()));
        assertEquals("We should be the same.", 0, result2.toString().compareTo(compare2.toString()));
        assertEquals("We should be the same.", 0, result3.toString().compareTo(string1.toString()));
    }
    
    /**
     * Test replace all with empty string.
     */
    @Test
    public void testReplaceAllWithEmptyString(){
    	CString s = fromString("");
		assertEquals("Expected empty string.", String.valueOf(""), s.replaceAll(fromString("abc"), fromString("ddd")).toString());
    }
	
	
}
