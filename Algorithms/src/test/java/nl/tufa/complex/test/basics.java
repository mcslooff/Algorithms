package nl.tufa.complex.test;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.common.MathUtils;
import nl.tufa.complex.C;

@SuppressWarnings("deprecation")
public class basics {

	@Test
	public void withCBasic_1() {

		C c = new C(0, 1);

		Assert.assertEquals((double) 1, c.getRe());
		Assert.assertEquals((double) 0, c.getIm());
	}

	@Test
	public void withCBasic_2() {

		C c = new C(Math.PI, 1);

		Assert.assertEquals((double) -1, c.getRe());
		Assert.assertEquals((double) 0, c.getIm());
	}

	@Test
	public void withCBasic_3() {

		C c = new C(Math.PI * 0.5, 1);

		Assert.assertEquals((double) 0, c.getRe());
		Assert.assertEquals((double) 1, c.getIm());
	}

	@Test
	public void withCBasic_4() {

		C c = new C(Math.PI * 1.5, 1);

		Assert.assertEquals((double) 0, c.getRe());
		Assert.assertEquals((double) -1, c.getIm());
	}

	@Test
	public void withCBasic_5() {

		C c = new C(Math.PI * 0.25, 2);

		Assert.assertEquals(Math.sqrt(2), c.getRe());
		Assert.assertEquals(Math.sqrt(2), c.getIm());
	}

	@Test
	public void withCBasic_6() {

		C c = new C(Math.PI * 0, -1);

		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI, c.getAlpha());
	}

	@Test
	public void withCBasic_7() {

		C c = new C(Math.PI * 2.5, 1);

		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI * 0.5, c.getAlpha());
		Assert.assertEquals((double) 0, c.getRe());
		Assert.assertEquals((double) 1, c.getIm());
	}

	@Test
	public void withCBasic_8() {

		C c = C.fromCartesian(1, 0);
		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI * 0, c.getAlpha());
		
		c = C.fromCartesian(0, 1);
		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI * 0.5, c.getAlpha());
		
		c = C.fromCartesian(1, 1);
		Assert.assertEquals(Math.sqrt(2), c.getZ());
		Assert.assertEquals(Math.PI * 0.25, c.getAlpha());

		c = C.fromCartesian(-1, 0);
		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI * 1, c.getAlpha());

		c = C.fromCartesian(0, -1);
		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(Math.PI * 1.5, c.getAlpha());

		c = C.fromCartesian(-1, -1);
		Assert.assertEquals(Math.sqrt(2), c.getZ());
		Assert.assertEquals(Math.PI * 1.25, c.getAlpha());

	}
	
	@Test
	public void withBasic_9() {
		
		C c = new C(Math.PI * 12.5, 1);
		Assert.assertEquals((double) 1, c.getZ());
		Assert.assertEquals(MathUtils.roundTo(Math.PI * 0.5, 0.0000000001), MathUtils.roundTo(c.getAlpha(), 0.0000000001));
		
	}
	
	@Test
	public void withBasic_10() {

		C c1 = new C(Math.PI * 0, 1);
		C c2 = new C(Math.PI * 0, 1);
		
		C c3 = c1.add(c2);
		Assert.assertEquals((double) 2, c3.getZ());
		Assert.assertEquals(Math.PI * 0, c3.getAlpha());
	}
	
	@Test(expected=ArithmeticException.class)
	public void withBasic_11() {

		C c1 = new C(Math.PI * 0, 1);
		C c2 = new C(Math.PI * 0, 1);
		
		C c3 = c1.sub(c2);
		Assert.assertEquals((double) 0, c3.getZ());
		Assert.assertEquals(Math.PI * 0, c3.getAlpha());
	}

	@Test
	public void withBasic_12() {

		C c1 = new C(Math.PI * 0, 2);
		C c2 = new C(Math.PI * 0, 1);
		
		C c3 = c1.sub(c2);
		Assert.assertEquals((double) 1, c3.getZ());
		Assert.assertEquals(Math.PI * 0, c3.getAlpha());
	}

}
