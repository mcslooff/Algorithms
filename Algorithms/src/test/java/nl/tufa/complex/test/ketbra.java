package nl.tufa.complex.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.complex.C;
import nl.tufa.complex.CMatrix;
import nl.tufa.complex.Ket;

@SuppressWarnings("deprecation")
public class ketbra {

	@Test
	public void withKet_equals() {

		Ket k = new Ket(2);
		k.set(0, new C(Math.PI * 0.5, 2));
		k.set(1, new C(Math.PI * 1.5, 2));
		
		Ket r = new Ket(2);
		r.set(0, new C(Math.PI * 0.5, 2));
		r.set(1, new C(Math.PI * 1.5, 2));
		
		//System.out.println("K=\n" + k.toString());

		Assert.assertEquals(r, k);

	}

	@Test
	public void withKet_mul() {

		Ket k = new Ket(2);
		k.set(0, new C(Math.PI * 0, 2));
		k.set(1, new C(Math.PI * 0, 3));

		//System.out.println("K=\n" + k.toString());

		C c = new C(Math.PI * 0.5, 2);

		Ket r = k.mul(c);

		Ket e = new Ket(2);
		e.set(0, new C(Math.PI * 0.5, 4));
		e.set(1, new C(Math.PI * 0.5, 6));

		//System.out.println("R=\n" + r.toString());
		//System.out.println("E=\n" + e.toString());

		Assert.assertEquals(e, r);

	}

	@Test
	public void withKet_div() {

		Ket k = new Ket(2);
		k.set(0, new C(Math.PI * 0, 2));
		k.set(1, new C(Math.PI * 0, 3));

		System.out.println("K=\n" + k.toString());

		C c = new C(Math.PI * 0.5, 2);

		Ket r = k.div(c);

		Ket e = new Ket(2);
		e.set(0, new C(Math.PI * 1.5, 1));
		e.set(1, new C(Math.PI * 1.5, 1.5));

		System.out.println("R=\n" + r.toString());
		System.out.println("E=\n" + e.toString());

		Assert.assertEquals(e, r);

	}
	
	@Test
	public void withMatrix_equals_1() {
		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, 3), new C(0, 4)));
		k1.add(new Ket(new C(0, 1), new C(0, 2)));
		CMatrix m1 = new CMatrix(k1);
		
		List<Ket> k2 = new ArrayList<Ket>();
		k2.add(new Ket(new C(0, 3), new C(0, 4)));
		k2.add(new Ket(new C(0, 1), new C(0, 2)));
		CMatrix m2 = new CMatrix(k2);
		
		Assert.assertEquals(m1, m2);
	}
	
	@Test
	public void withMatrix_equals_2() {
		List<Ket> k1 = new ArrayList<Ket>();
		k1.add(new Ket(new C(0, 3), new C(0, 4)));
		k1.add(new Ket(new C(0, 1), new C(0, 2)));
		CMatrix m1 = new CMatrix(k1);
		
		List<Ket> k2 = new ArrayList<Ket>();
		k2.add(new Ket(new C(0, 3), new C(0, 4)));
		k2.add(new Ket(new C(0, 1), new C(0, 3)));
		CMatrix m2 = new CMatrix(k2);
		
		Assert.assertNotSame(m1, m2);
	}
	
	@Test
	public void withKet_matrix_mul() {
		
		List<Ket> k = new ArrayList<Ket>();
		k.add(new Ket(new C(0, 3), new C(0, 4)));
		k.add(new Ket(new C(0, 1), new C(0, 2)));
		CMatrix m = new CMatrix(k);
		
		Ket a = new Ket(new C(0, 8), new C(0, 5));
		Ket r = m.mul(a);
		
		// Math rounding errors :-(
		Ket b = new Ket(new C(6.720985918136855E-18 * Math.PI, 29.0), new C(9.281361505998514E-18 * Math.PI, 42));

		System.out.println(r);
		
		Assert.assertEquals(b,r);
		
	}
}
