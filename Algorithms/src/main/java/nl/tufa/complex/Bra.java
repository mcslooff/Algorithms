package nl.tufa.complex;

/*
Copyright 2020 M.C.Slooff

This file is part of 'Algorithms'

'Algorithms' is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

'Algorithms' is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
	
You should have received a copy of the GNU General Public License
along with 'Algorithms'.  If not, see <https://www.gnu.org/licenses/>.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.	
*/

/**
 * <code>&lt;B|</code>
 * @author M.C.Slooff
 *
 */
public class Bra implements CVector {

	private final C[] c;
	
	public Bra(C... c) throws IllegalArgumentException {
		if (c.length == 0)
			throw new IllegalArgumentException("Zero length vetcor not allowed.");
		this.c = new C[c.length];
		for (int i = 0; i < c.length; i++) {
			this.c[i] = c[i];
		}
	}

	public Bra(int size) {
		if (size < 1)
			throw new IllegalArgumentException("Zero length or negative length vetcor not allowed.");

		c = new C[size];
	}
	
	public int size() {
		return c.length;
	}

	public C get(int i) {
		return c[i];
	}

	public void set(int i, C c) {
		this.c[i] = c;
	}

	public C dotProduct(CVector v) throws IncompatibleVectorException {

		if (c.length != v.size()) {
			throw new IncompatibleVectorException();
		}

		C r = null;
		for (int i = 0; i < c.length; i++) {
			if (r == null) {
				r = c[i].mul(v.get(i));
			} else {
				r = r.add(c[i].mul(v.get(i)));
			}
		}

		return r;
	}
	
	public Bra mul(C c) {
		
		Bra b = new Bra(this.c.length);
		
		for (int i = 0; i < this.c.length; i++) {
			b.set(i, this.c[i].mul(c));
		}
		
		return b;
	}
	
	public Bra div(C c) {
		
		Bra b = new Bra(this.c.length);
		
		for (int i = 0; i < this.c.length; i++) {
			b.set(i, this.c[i].div(c));
		}
		
		return b;
	}
	
	public Bra add(Bra v) throws IncompatibleVectorException {
		if (c.length != v.size())
			throw new IncompatibleVectorException();
		Bra b = new Bra(c.length);
		for(int i=0; i<c.length; i++) {
			b.set(i, c[i].add(v.get(i)));
		}
		return b;
	}

	public Bra sub(Bra v) throws IncompatibleVectorException {
		if (c.length != v.size())
			throw new IncompatibleVectorException();
		Bra b = new Bra(c.length);
		for(int i=0; i<c.length; i++) {
			b.set(i, c[i].sub(v.get(i)));
		}
		return b;
	}
	
	/**
	 * Computes and returns the Ket from this Bra.
	 * @return the Ket (|A&#x27e9;) from this Bra (&#x27e8;A|)
	 */
	public Ket getKet() {
		Ket k = new Ket(c.length);
		for(int i=0;i<c.length;i++) {
			k.set(i, c[i].conjugate());
		}
		return k;
	}
	
	@Override
	public String toString() {

		String s = "+" + new String(new char[c.length]).replace("\0", "\t\t") + "+\n|";

		for (int i = 0; i < c.length; i++) {
			s += c[i].toString() + "\t";
		}
		s+="|\n";

		s += "+" + new String(new char[c.length]).replace("\0", "\t\t") + "+";

		return s;
	}
	
}
