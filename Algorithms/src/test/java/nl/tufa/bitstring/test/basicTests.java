package nl.tufa.bitstring.test;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.bitstring.BitString;
import nl.tufa.bitstring.IncompatibleBitStringException;

@SuppressWarnings("deprecation")
public class basicTests {
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void basicTest_1() {
		BitString b = new BitString(1);
		b.get(1);
	}
	
	@Test()
	public void basicTest_2() {
		BitString b = new BitString(1);
		System.out.println("2: " + b);
		Assert.assertEquals(false, b.get(0));
		
		b.set(0, true);
		System.out.println("2: " + b);
		Assert.assertEquals(true, b.get(0));
		
	}
	
	@Test
	public void basicTest_3() {
		BitString b = new BitString(16);
		
		b.set(7, true);
		System.out.println("3: " + b);
		Assert.assertEquals(true, b.get(7));
		Assert.assertEquals(false, b.get(15));
	}

	@Test
	public void basicTest_4() {
		BitString b = new BitString(16);
		
		b.set(1, true);
		System.out.println("4: " + b);
		Assert.assertEquals(true, b.get(1));
		b.shiftLeft(1);
		System.out.println("4: " + b);
		Assert.assertEquals(true, b.get(2));
		Assert.assertEquals(false, b.get(1));
		Assert.assertEquals(false, b.get(0));
		b.shiftLeft(13);
		System.out.println("4: " + b);
		Assert.assertEquals(true, b.get(15));
		Assert.assertEquals(false, b.get(2));
	}

	@Test
	public void basicTest_5() {
		BitString b = new BitString(16);
		
		b.set(15, true);
		System.out.println("5: " + b);
		Assert.assertEquals(true, b.get(15));
		b.shiftRight(1);
		System.out.println("5: " + b);
		Assert.assertEquals(true, b.get(14));
		Assert.assertEquals(false, b.get(15));
		b.shiftRight(14);
		System.out.println("5: " + b);
		Assert.assertEquals(true, b.get(0));
		Assert.assertEquals(false, b.get(1));
		Assert.assertEquals(false, b.get(14));
		
	}
	
	@Test 
	public void basicTest_6() throws IncompatibleBitStringException {
		
		BitString a = BitString.valueOf("000000000000");
		BitString b = BitString.valueOf("000000000010");
		
		Assert.assertEquals("000000000000", a.toString());
		Assert.assertEquals("000000000010", b.toString());
		
		BitString c = a.and(b);
		Assert.assertEquals("000000000000", c.toString());
		
		a = BitString.valueOf("010000000010");
		
		c = a.and(b);
		Assert.assertEquals("000000000010", c.toString());
		
	}
	
	@Test 
	public void basicTest_7() throws IncompatibleBitStringException {
		
		BitString a = BitString.valueOf("000000000000");
		BitString b = BitString.valueOf("000000000010");
		
		BitString c = a.or(b);
		Assert.assertEquals("000000000010", c.toString());
		
		a = BitString.valueOf("010000000010");
		
		c = a.or(b);
		Assert.assertEquals("010000000010", c.toString());
		
	}
	
	@Test 
	public void basicTest_8() throws IncompatibleBitStringException {
		
		BitString a = BitString.valueOf("000000000000");
		
		BitString c = a.not();
		Assert.assertEquals("111111111111", c.toString());
		
		a = BitString.valueOf("010000000010");
		
		c = a.not();
		Assert.assertEquals("101111111101", c.toString());
		
	}

	@Test 
	public void basicTest_9() throws IncompatibleBitStringException {
		
		BitString a = BitString.valueOf("000000000101");
		BitString b = BitString.valueOf("000000000110");
		
		BitString c = a.xor(b);
		Assert.assertEquals("000000000011", c.toString());
		
	}

	@Test 
	public void basicTest_A() throws IncompatibleBitStringException {
		
		BitString a = BitString.valueOf("000000000101");
		BitString b = BitString.valueOf("000000000110");
		
		BitString c = a.xnor(b);
		Assert.assertEquals("111111111100", c.toString());
		
	}
	
}
