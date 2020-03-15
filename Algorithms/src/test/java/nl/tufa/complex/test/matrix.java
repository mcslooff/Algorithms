package nl.tufa.complex.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.complex.C;
import nl.tufa.complex.CMatrix;
import nl.tufa.complex.IncompatibleVectorException;
import nl.tufa.complex.Ket;

@SuppressWarnings("deprecation")
public class matrix {

	@Test
	public void withMatrix_0() {

		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, -3), new C(0, -4)));
		k1.add(new Ket(new C(0, -5), new C(0, 4)));
		CMatrix m1 = new CMatrix(k1);
		Assert.assertEquals(new C(0, -32), m1.det());

		List<Ket> k2 = new ArrayList<Ket>();
		k2.add(new Ket(new C(0, -5), new C(0, 4)));
		k2.add(new Ket(new C(0, 2), new C(0, -6)));
		CMatrix m2 = new CMatrix(k2);
		Assert.assertEquals(new C(2 * Math.PI, 22), m2.det());

		List<Ket> k3 = new ArrayList<Ket>();
		k3.add(new Ket(new C(0, -3), new C(0, -4)));
		k3.add(new Ket(new C(0, 2), new C(0, -6)));
		CMatrix m3 = new CMatrix(k3);
		Assert.assertEquals(new C(2 * Math.PI, 26), m3.det());
	}

	@Test
	public void withMatrix_1() {

		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, 1), new C(0, -3), new C(0, -4)));
		k1.add(new Ket(new C(0, 3), new C(0, -5), new C(0, 4)));
		k1.add(new Ket(new C(0, -3), new C(0, 2), new C(0, -6)));
		CMatrix m1 = new CMatrix(k1);

		Assert.assertEquals(new C(1.9999999999999998 * Math.PI, 40), m1.det());

	}

	@Test
	public void withMatrix_3() {

		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, 2), new C(0, 0), new C(0, -2), new C(0, 4)));
		k1.add(new Ket(new C(0, 0), new C(0, 1), new C(0, -3), new C(0, -4)));
		k1.add(new Ket(new C(0, 0), new C(0, 3), new C(0, -5), new C(0, 4)));
		k1.add(new Ket(new C(0, 1), new C(0, -3), new C(0, 2), new C(0, -6)));
		CMatrix m1 = new CMatrix(k1);

		Assert.assertEquals(new C(1.9999999999999993 * Math.PI, 32), m1.det());

	}

	@Test
	public void withMatrix_2() throws IllegalArgumentException, IncompatibleVectorException, ArithmeticException {

		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, 1), new C(0, 4)));
		k1.add(new Ket(new C(0, 2), new C(0, 5)));
		k1.add(new Ket(new C(0, 3), new C(0, 6)));
		CMatrix m1 = new CMatrix(k1);

		List<Ket> k2 = new ArrayList<Ket>();
		k2.add(new Ket(new C(0, 7), new C(0, 9), new C(0, 11)));
		k2.add(new Ket(new C(0, 8), new C(0, 10), new C(0, 12)));
		CMatrix m2 = new CMatrix(k2);

		CMatrix mr = m1.mul(m2);

		Assert.assertEquals(58.0, mr.get(0, 0).getRe());
		Assert.assertEquals(64.0, mr.get(0, 1).getRe());
		Assert.assertEquals(139.0, mr.get(1, 0).getRe());
		Assert.assertEquals(154.0, mr.get(1, 1).getRe());

		Assert.assertEquals(36.0, mr.det().getRe());

	}

}
