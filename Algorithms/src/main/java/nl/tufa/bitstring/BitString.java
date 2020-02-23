package nl.tufa.bitstring;

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
 * <p>
 * Provides basic functionality to handle larger bit strings. Example of
 * purpose: Sorting and de-duplication of large lists of integer values.
 * Provided a file with a large number of integer values needs to be checked for
 * duplicates and must be sorted the <code>BitString</code> class can be used to
 * set bit n for integer n. After reading all integers loop through the bits LSB
 * to MSB and output integer n if bit n is set.
 * </p>
 * <p>
 * After: Programming Pearls, second edition by Jon Bentley, Addison-Wesley
 * (2000)
 * </p>
 * <p>
 * Additional functionality has been provided in this class to find unions,
 * intersection and complements of bit-strings (<code>or</code>,
 * <code>and</code> and <code>not</code> respectively). Also <code>xor</code>
 * and <code>xnor</code> functions are provided for more complex set operations.
 * 
 * @author M.C.Slooff
 *
 */
public class BitString {

	private final byte[] bits;
	private final int length;

	public BitString(int length) {
		int bytes = (int) Math.ceil((double) length / (double) 8);
		bits = new byte[bytes];
		this.length = length;
	}

	private int getByteIndex(int bitIndex) {
		return bitIndex / 8;
	}

	public int length() {
		return length;
	}

	public boolean get(int index) throws IndexOutOfBoundsException {

		if (index >= length || index < 0)
			throw new IndexOutOfBoundsException();

		int byteIndex = getByteIndex(index);
		int bitIndex = index % 8;

		byte v = bits[byteIndex];

		byte mask = (byte) (1 << bitIndex);

		return ((v & mask) == mask);

	}

	public void set(int index, boolean value) throws IndexOutOfBoundsException {
		if (index >= length || index < 0)
			throw new IndexOutOfBoundsException();

		int byteIndex = getByteIndex(index);
		int bitIndex = index % 8;

		byte v = bits[byteIndex];

		byte mask = (byte) (1 << bitIndex);
		if (!value) {
			mask = (byte) ((byte) 255 - mask);
			bits[byteIndex] = (byte) (v & mask);
		} else {
			bits[byteIndex] = (byte) (v | mask);
		}
	}

	public void shiftLeft(int numBits) {

		for (int i = length - 1; i > numBits; i--) {
			this.set(i, this.get(i - numBits));
		}
		for (int i = numBits; i > 0; i--) {
			this.set(i, false);
		}
	}

	public void shiftRight(int numBits) {
		for (int i = numBits; i < length; i++) {
			this.set(i - numBits, this.get(i));
		}
		for (int i = length - numBits; i < length; i++) {
			this.set(i, false);
		}
	}

	public BitString and(BitString b) throws IncompatibleBitStringException {
		if (length != b.length())
			throw new IncompatibleBitStringException();

		BitString r = new BitString(length);
		for (int i = 0; i < length; i++) {
			r.set(i, this.get(i) && b.get(i));
		}

		return r;
	}

	public BitString or(BitString b) throws IncompatibleBitStringException {
		if (length != b.length())
			throw new IncompatibleBitStringException();

		BitString r = new BitString(length);
		for (int i = 0; i < length; i++) {
			r.set(i, this.get(i) || b.get(i));
		}

		return r;
	}

	public BitString not() {

		BitString r = new BitString(length);
		for (int i = 0; i < length; i++) {
			r.set(i, !this.get(i));
		}

		return r;
	}

	public BitString xor(BitString b) throws IncompatibleBitStringException {
		if (length != b.length())
			throw new IncompatibleBitStringException();

		BitString r = new BitString(length);
		for (int i = 0; i < length; i++) {
			r.set(i, this.get(i) != b.get(i));
		}

		return r;
	}

	public BitString xnor(BitString b) throws IncompatibleBitStringException {
		if (length != b.length())
			throw new IncompatibleBitStringException();

		BitString r = new BitString(length);
		for (int i = 0; i < length; i++) {
			r.set(i, this.get(i) == b.get(i));
		}

		return r;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = length - 1; i >= 0; i--) {
			s += this.get(i) ? "1" : "0";
		}
		return s;
	}
}
