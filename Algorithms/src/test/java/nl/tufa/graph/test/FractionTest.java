package nl.tufa.graph.test;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.fraction.Fraction;

@SuppressWarnings("deprecation")
public class FractionTest {

	@Test
	public void withSimpleNumbers_1() {

		Fraction x = new Fraction(1L, 2L);
		System.out.println(x.toString());

		Assert.assertEquals("1/2", x.toString());

	}

	@Test
	public void withSimpleNumbers_2() {

		Fraction x = new Fraction(2L, 4L);
		System.out.println(x.toString());

		Assert.assertEquals("1/2", x.toString());

	}

	@Test
	public void withSimpleDouble_1() {

		Fraction x = Fraction.valueOf((double) 0.5);
		System.out.println(x.toString());

		Assert.assertEquals("1/2", x.toString());

	}

	@Test
	public void withSimpleDouble_2() {

		Fraction x = Fraction.valueOf((double) 2 / (double) 7);
		System.out.println(x.toString());

		Assert.assertEquals("2/7", x.toString());

	}

	@Test
	public void withSimpleDouble_3() {

		Fraction x = Fraction.valueOf((double) 11 / (double) 13);
		System.out.println(x.toString());

		Assert.assertEquals("11/13", x.toString());

	}

	@Test
	public void withSimpleDouble_4() {

		Fraction x = Fraction.valueOf((double) 3 / (double) 2);
		System.out.println(x.toString());

		Assert.assertEquals("3/2", x.toString());

	}

	@Test
	public void withSimpleDouble_5() {

		Fraction x = Fraction.valueOf((double) 97 / (double) 101);
		System.out.println(x.toString());

		Assert.assertEquals("97/101", x.toString());

	}

	@Test
	public void withSimpleDouble_6() {

		Fraction x = Fraction.valueOf(Math.sqrt(2));
		System.out.println(x.toString() + " Fraction deviates: " + (Math.sqrt(2) - x.doubleValue())
				+ " from sqrt(2) on granularity double.");

		Assert.assertEquals("27688567065/19578773533", x.toString());

	}

	@Test
	public void withSimpleDouble_7() {

		Fraction x = Fraction.valueOf(Math.PI);
		System.out.println(x.toString() + " Fraction deviates: " + (Math.PI - x.doubleValue())
				+ " from Math.PI on granularity double.");

		Assert.assertEquals("96912279446380464/30848136640389084", x.toString());

	}

	@Test
	public void withSimpleDouble_7b() {

		Fraction x = Fraction.valueOf((double) 7723 / (double) 7919);

		Assert.assertEquals("7723/7919", x.toString());

	}

	@Test(expected = ArithmeticException.class)
	public void withSimpleDouble_8() {

		Fraction x = Fraction.valueOf(Math.sqrt(2));
		x.mul(x);

	}

	@Test
	public void withString_1() {

		Fraction x = Fraction.parseFraction("-2/-4");

		Assert.assertEquals("1/2", x.toString());

	}

	@Test
	public void withString_2() {

		Fraction x = Fraction.parseFraction("+2/+4");

		Assert.assertEquals("1/2", x.toString());

	}

	@Test
	public void withString_3() {

		Fraction x = Fraction.parseFraction("+2/-4");

		Assert.assertEquals("-1/2", x.toString());

	}

	@Test
	public void withString_4() {

		Fraction x = Fraction.parseFraction("  +2  /  -4  ");

		Assert.assertEquals("-1/2", x.toString());

	}

	@Test(expected = NumberFormatException.class)
	public void withString_5() {

		Fraction.parseFraction("  +  2  /  -4  ");

	}

	@Test
	public void withDivision_1() {

		Fraction x = Fraction.parseFraction("1/4");
		Fraction y = Fraction.parseFraction("1/2");

		Fraction z = x.div(y);

		Assert.assertEquals("1/2", z.toString());

	}

	@Test
	public void withDivision_2() {

		Fraction x = Fraction.parseFraction("11/13");
		Fraction y = Fraction.parseFraction("11/13");

		Fraction z = x.div(y);

		Assert.assertEquals("1/1", z.toString());

		Assert.assertEquals(true, z.isInteger());

	}

	@Test
	public void withDivision_3() {

		Fraction x = Fraction.parseFraction("13/11");
		Fraction y = Fraction.parseFraction("13/11");

		Fraction z = x.div(y);

		Assert.assertEquals("1/1", z.toString());

		Assert.assertEquals(true, x.hasInteger());
		Assert.assertEquals(true, z.isInteger());
		Assert.assertEquals(new Long(1), x.getInteger());

	}

	@Test
	public void withMod_1() {

		Fraction x = Fraction.parseFraction("2/3"); // 0.666666..
		Fraction y = Fraction.parseFraction("1/5"); // 0.2

		// 2/3 => 10/15
		// 1/5 => 3/15
		// 10 mod 3 = 1
		// 1/15

		Fraction z = x.mod(y);

		Assert.assertEquals("1/15", z.toString());

	}

	@Test
	public void withMod_2() {

		Fraction x = Fraction.parseFraction("3/2"); // 1.5
		Fraction y = Fraction.parseFraction("1/5"); // 0.2

		// 3/2 => 15/10
		// 1/5 => 2/10
		// 15 mod 2 = 1
		// 1/10

		Fraction z = x.mod(y);

		Assert.assertEquals("1/10", z.toString());

	}

	@Test
	public void withFractionGcd() {

		// 2/3 => 10/15
		// 2/5 => 6/15
		// GCD(10, 6) = 2
		// GCD(2/3, 1/5) = 2/15

		Fraction x = Fraction.parseFraction("2/3");
		Fraction y = Fraction.parseFraction("2/5");

		Fraction z = x.gcd(y);

		Assert.assertEquals("2/15", z.toString());

	}

	@Test
	public void withFractionLcm_1() {

		// 2/3
		// 2/5
		// lcm(3, 5) = 15

		Fraction x = Fraction.parseFraction("2/3");
		Fraction y = Fraction.parseFraction("2/5");

		Fraction z = x.lcm(y);

		Assert.assertEquals("2/1", z.toString());

	}

	@Test
	public void withFractionLcm_2() {

		// 3/7
		// 6/11
		// lcm(7, 11) = 7*11 = 77
		// 11*3 = 33
		// 7*6 = 42
		// lcm(33, 42) = lcm(11*3, 7*6) = 11*7*3 = 420
		// lcm(3/7, 6/11) = 420/77 = 7*11*6/7*11 = 6.

		// 6/3/7 = 6*7/3 = 14
		// 6/6/11 = 6*11/6 = 11 -> prime. has no divisors.

		Fraction x = Fraction.parseFraction("3/7");
		Fraction y = Fraction.parseFraction("6/11");

		Fraction z = x.lcm(y);

		Assert.assertEquals("6/1", z.toString());

	}

	@Test
	public void withFractionLcm_3() {

		Fraction x = Fraction.parseFraction("1/8");
		Fraction y = Fraction.parseFraction("2/8");

		Fraction z = x.lcm(y);

		Assert.assertEquals("1/4", z.toString());

	}

	@Test
	public void withFractionAdd_1() {

		Fraction x = Fraction.parseFraction("1/8");
		Fraction y = Fraction.parseFraction("2/8");

		Fraction z = x.add(y);

		Assert.assertEquals("3/8", z.toString());

	}

	@Test
	public void withFractionAdd_2() {

		Fraction x = Fraction.parseFraction("2/21");
		Fraction y = Fraction.parseFraction("1/6");

		Fraction z = x.add(y);

		Assert.assertEquals("11/42", z.toString());

	}

	@Test
	public void withFractionSub_1() {

		Fraction x = Fraction.parseFraction("2/21");
		Fraction y = Fraction.parseFraction("1/6");

		Fraction z = x.sub(y);

		Assert.assertEquals("-1/14", z.toString());

	}

	@Test
	public void withFractionSub_2() {

		Fraction x = Fraction.parseFraction("1/11");
		Fraction y = Fraction.parseFraction("1/11");

		Fraction z = x.sub(y);

		Assert.assertEquals("0/1", z.toString());

	}

	@Test
	public void withFractionAdd_3() {

		Fraction x = Fraction.parseFraction("0/11");
		Fraction y = Fraction.parseFraction("1/11");

		Fraction z = x.add(y);

		Assert.assertEquals("1/11", z.toString());

	}

	@Test
	public void withFractionMul_1() {

		Fraction x = Fraction.parseFraction("0/11");
		Fraction y = Fraction.parseFraction("1/11");

		Fraction z = x.mul(y);

		Assert.assertEquals("0/1", z.toString());

	}

	@Test
	public void withFractionDiv_1() {

		Fraction x = Fraction.parseFraction("0/11");
		Fraction y = Fraction.parseFraction("1/11");

		Fraction z = x.div(y);

		Assert.assertEquals("0/1", z.toString());

	}

	@Test(expected = ArithmeticException.class)
	public void withFractionDiv_2() {

		Fraction x = Fraction.parseFraction("1/11");
		Fraction y = Fraction.parseFraction("0/11");

		x.div(y);

	}

	@Test
	public void withCompareAndEquals_1() {
		
		Fraction x = Fraction.parseFraction("1/2");
		Fraction y = Fraction.parseFraction("2/4");

		Assert.assertEquals(true, x.equals(y));
	}
	
	@Test
	public void withCompareAndEquals_2() {
		
		Fraction x = Fraction.parseFraction("1/2");
		Fraction y = Fraction.parseFraction("2/4");

		Assert.assertEquals(0, x.compareTo(y));
	}
	
	@Test
	public void withCompareAndEquals_3() {
		
		Fraction x = Fraction.parseFraction("2/3");
		Fraction y = Fraction.parseFraction("4/5");

		Assert.assertEquals(-1, x.compareTo(y));
	}
	
	@Test
	public void withCompareAndEquals_4() {
		
		Fraction x = Fraction.parseFraction("3/2");
		Fraction y = Fraction.parseFraction("5/4");

		Assert.assertEquals(1, x.compareTo(y));
	}
	
}
