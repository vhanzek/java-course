package hr.fer.zemris.java.tecaj.hw07.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConverterTests {

	@Test
    public void test1(){
        int i = 15;
        for (byte b : Converter.hexToBytes("fedcba9876543210")) {
            byte by = (byte) (i*16 + i-1);
            assertEquals(by, b);
            i -= 2;
        }
    }
   
    @Test
    public void test2(){
       
        byte[] b1 = Converter.hexToBytes("d6");
        byte[] b2 = Converter.hexToBytes("c4");
        byte[] b3 = Converter.hexToBytes("3a");
        byte[] b4 = Converter.hexToBytes("56");
        byte[] b5 = Converter.hexToBytes("8b");
       
        assertEquals(b1[0], (byte) (13*16 + 6));
        assertEquals(b2[0], (byte) (12*16 + 4));
        assertEquals(b3[0], (byte) (3*16 + 10));
        assertEquals(b4[0], (byte) (5*16 + 6));
        assertEquals(b5[0], (byte) (8*16 + 11));
       
    }
   
    @Test(expected = IllegalArgumentException.class)
    public void testNull(){
    	Converter.hexToBytes(null);
    }
   
    @Test(expected = IllegalArgumentException.class)
    public void IllegalArgumentTest(){
    	Converter.hexToBytes("xyz");
    }

}
