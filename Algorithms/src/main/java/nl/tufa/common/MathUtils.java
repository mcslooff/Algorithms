package nl.tufa.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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

public class MathUtils {

	private static double minimum = 0.0001;

	public final static double roundTo(double value, double roundTo) throws IllegalArgumentException {

		if (roundTo == 0 || roundTo < 0)
			throw new IllegalArgumentException("Value to round to must be non-zero positive real scalar.");

		return Math.round(value / roundTo) * roundTo;

	}

	public static long gcd(long a, long b) {
		return (long) gcd((double) a, (double) b);
	}

	// Recursive function to return gcd
	// of a and b
	public static double gcd(double a, double b) {
		return gcd(a, b, 30);
	}

	private static double gcd(double a, double b, int callStack) {

		if (callStack == 0)
			return 1;

		if (a < b)
			return gcd(b, a, callStack - 1);

		// base case
		// By dividing by minimum and then rounding followed by multiplying by minimum
		// we ensure we round to the digit specified in minimum. This will also work
		// with for example 0.025 or 0.002
		if (Math.abs(b) < minimum)
			return roundTo(a, minimum);
		else
			return (gcd(b, a - Math.floor(a / b) * b, callStack - 1));
	}

	// Function to find gcd of array of
	// numbers
	public static double findGCD(Double arr[]) {
		List<Double> lst = Arrays.asList(arr);
		return findGCD(lst);
	}

	public static double findGCD(List<Double> arr) {

		// We need to find the smallest number which is not an Integer, this must be the
		// starting
		// point of our calculation. We need to do this since the GCD aslgorithm will
		// stop when the
		// calculated divider equals one. This will happen when we start with value 1
		// but is wrong
		// when the list also contains a non-integer value larger than 1.

		// First sort the list so we will start with the smallest number first.
		arr.sort(new Comparator<Double>() {
			@Override
			public int compare(Double arg0, Double arg1) {
				return arg0.compareTo(arg1);
			}
		});

		// Now, find the smallest number which has decimals.
		Double result = null;
		for (Double scalar : arr) {
			if (Math.floor(scalar) != scalar) {
				result = scalar;
				break;
			}
		}
		if (result == null)
			result = arr.get(0);

		// Start calculation of GCD.
		for (int i = 1; i < arr.size(); i++) {
			result = gcd(arr.get(i), result);
			if (result == 1) {
				return 1;
			}
		}

		return result;
	}

	public static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}

}
