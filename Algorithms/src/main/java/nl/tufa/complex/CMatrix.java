package nl.tufa.complex;

import java.util.Arrays;
import java.util.List;

public class CMatrix {

	private final C[][] c;
	private final int rows;
	private final int cols;

	public CMatrix(int length) throws IllegalArgumentException {

		if (length < 1)
			throw new IllegalArgumentException("Matrix needs non-zero dimensions.");
		this.rows = length;
		this.cols = length;
		c = new C[rows][cols];

	}

	public CMatrix(List<Ket> kets) throws IllegalArgumentException {

		if (kets.size() < 1)
			throw new IllegalArgumentException("Matrix needs non-zero dimensions.");
		if (kets.size() != kets.get(0).size())
			throw new IllegalArgumentException("Number of rows and columns do not match.");

		cols = kets.size();
		rows = kets.get(0).size();

		c = new C[rows][cols];

		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				c[j][i] = kets.get(i).get(j);
			}
		}
	}

	public C get(int row, int col) {
		return c[row][col];
	}

	public void set(int row, int col, C c) {
		this.c[row][col] = c;
	}

	public Ket mul(Ket k) {

		Ket r = new Ket(k.size());

		for (int i = 0; i < cols; i++) {
			C n = k.get(i);
			for (int j = 0; j < rows; j++) {
				// Multiply cell from column i and row j with row i from Ket.
				if(r.get(j)==null) {
					r.set(j, c[j][i].mul(n));
				} else {
					r.set(j, r.get(j).add(c[j][i].mul(n)));
				}
			}
		}
		
		return r;
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
