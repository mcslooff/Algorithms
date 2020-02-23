package nl.tufa.bitstring.test;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.bitstring.BitString;

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
}
