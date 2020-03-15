package nl.tufa.complex;

import java.util.Arrays;
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


/**
 * 
 * @author M.C.Slooff
 *
 */
public class CMatrix {

	protected final C[][] c;
	protected final int rows;
	protected final int cols;

	/**
	 * Create an empty matrix of size rows x cols. The internal matrix (that is its
	 * size) is immutable. The values can be set and changed.
	 * 
	 * @param rows
	 * @param cols
	 */
	public CMatrix(int rows, int cols) {

		if (rows < 1 || cols < 1)
			throw new IllegalArgumentException("Matrix needs non-zero dimensions.");
		this.rows = rows;
		this.cols = cols;
		c = new C[rows][cols];

	}

	/**
	 * Create and empty matrix of size length x length/ The internal matrix (that is
	 * its size) is immutable. The values can be set and changed.
	 * 
	 * @param length
	 * @throws IllegalArgumentException
	 */
	public CMatrix(int length) throws IllegalArgumentException {

		if (length < 1)
			throw new IllegalArgumentException("Matrix needs non-zero dimensions.");
		this.rows = length;
		this.cols = length;
		c = new C[rows][cols];

	}

	/**
	 * Create a matrix from the List of Kets. The List must contains as many Kets as
	 * there are dimensions in each Ket. All Kets are assumed to have the same
	 * dimension.
	 * 
	 * @param kets
	 * @throws IllegalArgumentException
	 */
	public CMatrix(List<Ket> kets) throws IllegalArgumentException {

		if (kets.size() < 1)
			throw new IllegalArgumentException("Matrix needs non-zero dimensions.");

		cols = kets.size();
		rows = kets.get(0).size();

		c = new C[rows][cols];

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				c[j][i] = kets.get(i).get(j);
			}
		}
	}

	public Ket getKet(int col) throws IndexOutOfBoundsException {

		if (col > cols - 1)
			throw new IndexOutOfBoundsException();

		Ket k = new Ket(this.rows);

		for (int r = 0; r < this.rows; r++)
			k.set(r, c[r][col]);
		return k;
	}

	public Bra getBra(int row) throws IndexOutOfBoundsException {

		if (row > rows - 1)
			throw new IndexOutOfBoundsException();

		Bra b = new Bra(this.cols);

		for (int c = 0; c < this.cols; c++)
			b.set(c, this.c[row][c]);
		return b;
	}

	/**
	 * Return a clone of this CMatrix.
	 */
	public CMatrix clone() {

		CMatrix m = new CMatrix(this.rows, this.cols);

		for (int i = 0; i < this.rows; i++)
			for (int j = 0; j < this.cols; j++)
				m.c[i][j] = this.c[i][j].clone();

		return m;

	}

	/**
	 * Return the complex number C from the matrix from specified row and column.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public C get(int row, int col) {
		return c[row][col];
	}

	/**
	 * Set the complex number C in the matrix for the specified row and column.
	 * 
	 * @param row
	 * @param col
	 * @param c
	 */
	public void set(int row, int col, C c) {
		this.c[row][col] = c;
	}

	/**
	 * Multiply the provided Ket with this matrix and return the resulting Ket.
	 * 
	 * @param k
	 * @return
	 */
	public Ket mul(Ket k) {

		Ket r = new Ket(k.size());

		for (int i = 0; i < cols; i++) {
			C n = k.get(i);
			for (int j = 0; j < rows; j++) {
				// Multiply cell from column i and row j with row i from Ket.
				if (r.get(j) == null) {
					r.set(j, c[j][i].mul(n));
				} else {
					r.set(j, r.get(j).add(c[j][i].mul(n)));
				}
			}
		}

		return r;
	}

	public CMatrix mul(CMatrix m) throws IllegalArgumentException, IncompatibleVectorException {

		if (this.cols != m.rows || this.rows != m.cols)
			throw new IllegalArgumentException();

		int rs = this.rows < m.rows ? this.rows : m.rows;
		int cs = this.cols < m.cols ? this.cols : m.cols;

		CMatrix res = new CMatrix(rs, cs);

		for (int r = 0; r < rs; r++) {
			for (int c = 0; c < cs; c++) {
				Ket k;
				Bra b;
				if (this.cols < m.cols) {
					k = this.getKet(r);
					b = m.getBra(c);
				} else {
					k = m.getKet(r);
					b = this.getBra(c);
				}
				res.c[c][r] = k.dotProduct(b);
			}
		}

		return res;
	}

	/**
	 * Construct and return a transpose of this matrix (i.e. rows and columns are
	 * swapped, rows become columns, columns become rows)
	 * 
	 * @return
	 */
	public CMatrix transpose() {

		CMatrix res = new CMatrix(cols, rows);

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < rows; c++) {
				res.c[r][c] = this.c[c][r];
			}
		}

		return res;
	}

	/**
	 * Calculate and return the determinant of this matrix. The determinant is
	 * calculated using <a href=
	 * "https://www.ams.org/journals/mcom/1968-22-103/S0025-5718-1968-0226829-0/S0025-5718-1968-0226829-0.pdf">Bareiss
	 * algorithm</a> for computing determinants of matrices.
	 * 
	 * @return C, complex determinant of this matrix.
	 */
	public C det() throws ArithmeticException {
		
		if(cols!=rows) throw new ArithmeticException("Matrix is not of dimensions n x n, determinant cannot be calculated on non square matrices.");
		
		CMatrix A = this.clone();
		int n = this.cols;

		for (int i = 0; i < n - 1; i++) {
			// assert(a(i, i) == 0);
			for (int j = i + 1; j < n; j++)
				for (int k = i + 1; k < n; k++) {
					A.c[j][k] = A.c[j][k].mul(A.c[i][i]).sub(A.c[j][i].mul(A.c[i][k]));

					if (i != 0)
						A.c[j][k] = A.c[j][k].div(A.c[i - 1][i - 1]);
				}
		}

		return A.c[n - 1][n - 1];
	}

	/**
	 * Calculates and returns the transposesd matrix of the conjugated elements of
	 * this matrix.
	 * 
	 * @return CMatrix the transposed matrix of conjugated elements.
	 */
	public CMatrix transconj() {

		CMatrix t = this.transpose();

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < rows; c++) {
				t.c[r][c] = t.c[r][c].conjugate();
			}
		}
		return t;
	}

	@Override
	public String toString() {

		String s = "";

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < cols; i++) {
				s += c[j][i].toString() + "\t";
			}
			s += "\n";
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(c);
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
		CMatrix other = (CMatrix) obj;
		if (!Arrays.deepEquals(c, other.c))
			return false;
		return true;
	}
}
