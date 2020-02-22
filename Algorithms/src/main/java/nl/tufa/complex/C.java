package nl.tufa.complex;

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

public class C {

	public final static double C_GRANULARITY = .000000000000001;

	private final double alpha;
	private final double z;

	public C(double alpha, double z) throws ArithmeticException {
		if (z == 0)
			throw new ArithmeticException("Zero length vectors cannot be instantiated.");
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
		return fromCartesian(c.getRe() + this.getRe(), c.getIm() + this.getIm());
	}

	public C sub(C c) {
		return fromCartesian(this.getRe() - c.getRe(), this.getIm() - c.getIm());
	}

	public C mul(C c) {
		return new C(this.alpha + c.getAlpha(), this.z * c.getZ());
	}

	public C div(C c) {
		return new C(this.alpha - c.getAlpha(), this.z / c.getZ());
	}

}
