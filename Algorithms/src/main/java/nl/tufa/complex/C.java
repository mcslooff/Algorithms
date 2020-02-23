package nl.tufa.complex;

import java.util.HashSet;
import java.util.Set;

import nl.tufa.common.MathUtils;

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
 * Implementation of complex scalar <code>c = x + yi</code> in polar form
 * <code>c = r<i>e</i><sup>i&alpha;</sup></code> where
 * <code>c &#8712; &#8450;</code>. This class provides a number of basic
 * operations such as addition, substraction, multiplication, division, power
 * and root and conjugate.
 * 
 * @author M.C.Slooff
 *
 */
public class C {

	public final static double C_GRANULARITY = .000000000000001;

	private final double alpha;
	private final double z;

	public C(double alpha, double z) throws ArithmeticException {
		//if (z == 0)
		//	throw new ArithmeticException("Zero length vectors cannot be instantiated.");
		double a = (z < 0 ? Math.PI : 0) + alpha;
		a = (a > Math.PI * 2 ? a - (Math.floor(a / (2 * Math.PI)) * 2 * Math.PI) : a);
		a = (a < 0 ? Math.PI * 2 : 0) + a;
		this.alpha = a;
		this.z = Math.abs(z);
	}

	public double getZ() {
		return z;
	}

	public double getRe() {
		return MathUtils.roundTo(Math.cos(alpha) * z, C_GRANULARITY);
	}

	public double getIm() {
		return MathUtils.roundTo(Math.sin(alpha) * z, C_GRANULARITY);
	}

	public double getAlpha() {
		return alpha;
	}

	public static C fromCartesian(double x, double y) throws ArithmeticException {

		if (x == 0 && y == 0)
			throw new ArithmeticException("Zero length vectors cannot be instantiated.");
		double z = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double alpha = 0;
		if (x == 0) {
			alpha = Math.PI * (y > 0 ? 0.5 : 1.5);
		} else {
			alpha = Math.atan(y / x) - (x < 0 ? Math.PI : 0);
		}

		return new C(alpha, z);
	}

	public C add(C c) {
		
		// Cosine rule c^2 = a^2 - b^2 + 2*a*b*cos(a)
		// Determine relative angles between vectors:
		// a'= a
		// b' = 180-b
		// rel-angle = a' + b'
		// Determine angle of result vector using sine rule
		// sin(a)/a = sin(b)/b -> sin(b)=(sin(a)*b)/a
		// b = sin-1((sin(a)*b)/a)
		// c = b+a
		
		double gamma = this.alpha + Math.PI - c.getAlpha();
		
		double l = Math.sqrt(Math.pow(this.z, 2) + Math.pow(c.getZ(), 2) - 2*this.z*c.getZ()*Math.cos(gamma));
		
		double lambda = 0;
		if(l==0) {
			// Assume the result vector could be zero length but the two vectors to be summed are not.
			double betta = Math.asin(c.getZ()*Math.sin(gamma)/this.z);
			// Triangle in Euclidian space has sum of angles equal to pi.
			lambda = Math.PI-betta-gamma;
		} else {
			lambda = Math.asin(c.getZ()*Math.sin(gamma)/l);
		}
		double delta = this.alpha+lambda;
		
		return new C(delta, l);
		
	}

	public C sub(C c) {
		// Use addition by inverting direction of vector c.
		return this.add(c.inverse());
		//return fromCartesian(this.getRe() - c.getRe(), this.getIm() - c.getIm());
	}

	public C mul(C c) {
		return new C(this.alpha + c.getAlpha(), this.z * c.getZ());
	}

	public C div(C c) {
		return new C(this.alpha - c.getAlpha(), this.z / c.getZ());
	}

	public C pow(long n) {
		return new C(this.alpha * n, Math.pow(this.z, n));
	}

	public Set<C> root(long n) {

		Set<C> results = new HashSet<C>();

		if (n == 0) {
			results.add(new C(0, 1));
		}

		for (int k = 0; k < n; k++) {
			results.add(new C((1 / n * this.alpha) + ((2 * k * Math.PI) / n), Math.pow(z, 1 / n)));
		}

		return results;
	}

	/**
	 * 
	 * @return conjugate <code>c<sup>*</sup></code> of c (x + yi) where <code>c<sup>*</sup> = x - yi</code>.
	 */
	public C conjugate() {
		return new C(-this.alpha, this.z);
	}
	
	public C inverse() {
		return new C(this.alpha + Math.PI, this.z);
	}
	
	@Override
	public String toString() {
		return z + "\u00d7e^(" + alpha/Math.PI + "\u00d7\u03c0\u00d7i)";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(alpha);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		C other = (C) obj;
		if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
}
