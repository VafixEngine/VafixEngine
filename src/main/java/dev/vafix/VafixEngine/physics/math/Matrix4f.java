package dev.vafix.VafixEngine.physics.math;

public class Matrix4f {

	private double[][] matrix = new double[4][4];

	public Matrix4f(double[][] matrix) {
		this.matrix = matrix;
	}

	public Matrix4f(double a, double b, double c, double d,
					double e, double f, double g, double h,
					double i, double j, double k, double l,
					double m, double n, double o, double p) {
		matrix[0][0] = a; matrix[0][1] = b; matrix[0][2] = c; matrix[0][3] = d;
		matrix[1][0] = e; matrix[1][1] = f; matrix[1][2] = g; matrix[1][3] = h;
		matrix[2][0] = i; matrix[2][1] = j; matrix[2][2] = k; matrix[2][3] = l;
		matrix[3][0] = m; matrix[3][1] = n; matrix[3][2] = o; matrix[3][3] = p;
	}

	public Matrix4f() {
		matrix[0][0] = 1;
		matrix[1][1] = 1;
		matrix[2][2] = 1;
		matrix[3][3] = 1;
	}

	public Matrix4f clone() {
		return new Matrix4f(matrix);
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public double getValue(int id) {
		if (id > 15 || id < 0 ) {
			throw new IndexOutOfBoundsException(id + " is greater than or smaller than the limits of the matrix (0-15)");
		}
		return matrix[(int) id / 4][id % 4];
	}

	public void setValue(int id, double value) {
		if (id > 15 || id < 0 ) {
			throw new IndexOutOfBoundsException(id + " is greater than or smaller than the limits of the matrix (0-15)");
		}
		matrix[(int) id / 4][id % 4] = value;
	}

	public double getValue(int x, int y) {
		if (x > 3 || x < 0 ) {
			throw new IndexOutOfBoundsException(x + " is greater than or smaller than the limits of the matrix (0-3)");
		}
		if (y > 3 || y < 0 ) {
			throw new IndexOutOfBoundsException(y + " is greater than or smaller than the limits of the matrix (0-3)");
		}
		return matrix[x][y];
	}

	public void setValue(int x, int y, double value) {
		if (x > 3 || x < 0 ) {
			throw new IndexOutOfBoundsException(x + " is greater than or smaller than the limits of the matrix (0-3)");
		}
		if (y > 3 || y < 0 ) {
			throw new IndexOutOfBoundsException(y + " is greater than or smaller than the limits of the matrix (0-3)");
		}
		matrix[x][y] = value;
	}

	public double[] flatten() {
		double[] flat = new double[16];
		for (int i = 0; i < 16; i++) {
			flat[i] = matrix[(int) i / 4][i % 4];
		}
		return flat;
	}

}
