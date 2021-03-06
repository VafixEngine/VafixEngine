package dev.vafix.VafixEngine.physics.math;

public class Matrix4d {

	private double[][] matrix = new double[4][4];

	public Matrix4d(double[][] matrix) {
		this.matrix = matrix;
	}

	public Matrix4d(double a, double b, double c, double d,
					double e, double f, double g, double h,
					double i, double j, double k, double l,
					double m, double n, double o, double p) {
		matrix[0][0] = a; matrix[0][1] = b; matrix[0][2] = c; matrix[0][3] = d;
		matrix[1][0] = e; matrix[1][1] = f; matrix[1][2] = g; matrix[1][3] = h;
		matrix[2][0] = i; matrix[2][1] = j; matrix[2][2] = k; matrix[2][3] = l;
		matrix[3][0] = m; matrix[3][1] = n; matrix[3][2] = o; matrix[3][3] = p;
	}

	public Matrix4d() {
		matrix[0][0] = 1;
		matrix[1][1] = 1;
		matrix[2][2] = 1;
		matrix[3][3] = 1;
	}

	public Matrix4d clone() {
		return new Matrix4d(matrix);
	}

	public Matrix4d add(Matrix4d m) {
		for (int i = 0; i < 16; i++) {
			matrix[i / 4][i % 4] += m.getMatrix()[i / 4][i % 4];
		}
		return this;
	}

	public Matrix4d subtract(Matrix4d m) {
		return add(m.multiply(-1));
	}

	public Matrix4d multiply(double d) {
		for (int i = 0; i < 16; i++) {
			matrix[i / 4][i % 4] *= d;
		}
		return this;
	}

	public Matrix4d multiply(Matrix4d m) {
		double[][] result = new double[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result[i][j] = matrix[i][0] * m.matrix[0][j] + matrix[i][1] * m.matrix[1][j] + matrix[i][2] * m.matrix[2][j] + matrix[i][3] * m.matrix[3][j];
			}
		}
		matrix = result;
		return this;
	}

	public Matrix4d inverse() {
		double[] inv = new double[16];
		double[] m = flatten();
		double det;
		int i;

		inv[0]  =  m[5] * m[10] * m[15] - m[5] * m[11] * m[14] - m[9] * m[6] * m[15] + m[9] * m[7] * m[14] + m[13] * m[6] * m[11] - m[13] * m[7] * m[10];
		inv[4]  = -m[4] * m[10] * m[15] + m[4] * m[11] * m[14] + m[8] * m[6] * m[15] - m[8] * m[7] * m[14] - m[12] * m[6] * m[11] + m[12] * m[7] * m[10];
		inv[8]  =  m[4] * m[9]  * m[15] - m[4] * m[11] * m[13] - m[8] * m[5] * m[15] + m[8] * m[7] * m[13] + m[12] * m[5] * m[11] - m[12] * m[7] * m[9];
		inv[12] = -m[4] * m[9]  * m[14] + m[4] * m[10] * m[13] + m[8] * m[5] * m[14] - m[8] * m[6] * m[13] - m[12] * m[5] * m[10] + m[12] * m[6] * m[9];
		inv[1]  = -m[1] * m[10] * m[15] + m[1] * m[11] * m[14] + m[9] * m[2] * m[15] - m[9] * m[3] * m[14] - m[13] * m[2] * m[11] + m[13] * m[3] * m[10];
		inv[5]  =  m[0] * m[10] * m[15] - m[0] * m[11] * m[14] - m[8] * m[2] * m[15] + m[8] * m[3] * m[14] + m[12] * m[2] * m[11] - m[12] * m[3] * m[10];
		inv[9]  = -m[0] * m[9]  * m[15] + m[0] * m[11] * m[13] + m[8] * m[1] * m[15] - m[8] * m[3] * m[13] - m[12] * m[1] * m[11] + m[12] * m[3] * m[9];
		inv[13] =  m[0] * m[9]  * m[14] - m[0] * m[10] * m[13] - m[8] * m[1] * m[14] + m[8] * m[2] * m[13] + m[12] * m[1] * m[10] - m[12] * m[2] * m[9];
		inv[2]  =  m[1] * m[6]  * m[15] - m[1] * m[7]  * m[14] - m[5] * m[2] * m[15] + m[5] * m[3] * m[14] + m[13] * m[2] * m[7]  - m[13] * m[3] * m[6];
		inv[6]  = -m[0] * m[6]  * m[15] + m[0] * m[7]  * m[14] + m[4] * m[2] * m[15] - m[4] * m[3] * m[14] - m[12] * m[2] * m[7]  + m[12] * m[3] * m[6];
		inv[10] =  m[0] * m[5]  * m[15] - m[0] * m[7]  * m[13] - m[4] * m[1] * m[15] + m[4] * m[3] * m[13] + m[12] * m[1] * m[7]  - m[12] * m[3] * m[5];
		inv[14] = -m[0] * m[5]  * m[14] + m[0] * m[6]  * m[13] + m[4] * m[1] * m[14] - m[4] * m[2] * m[13] - m[12] * m[1] * m[6]  + m[12] * m[2] * m[5];
		inv[3]  = -m[1] * m[6]  * m[11] + m[1] * m[7]  * m[10] + m[5] * m[2] * m[11] - m[5] * m[3] * m[10] - m[9]  * m[2] * m[7]  + m[9]  * m[3] * m[6];
		inv[7]  =  m[0] * m[6]  * m[11] - m[0] * m[7]  * m[10] - m[4] * m[2] * m[11] + m[4] * m[3] * m[10] + m[8]  * m[2] * m[7]  - m[8]  * m[3] * m[6];
		inv[11] = -m[0] * m[5]  * m[11] + m[0] * m[7]  * m[9]  + m[4] * m[1] * m[11] - m[4] * m[3] * m[9]  - m[8]  * m[1] * m[7]  + m[8]  * m[3] * m[5];
		inv[15] =  m[0] * m[5]  * m[10] - m[0] * m[6]  * m[9]  - m[4] * m[1] * m[10] + m[4] * m[2] * m[9]  + m[8]  * m[1] * m[6]  - m[8]  * m[2] * m[5];

		det = m[0] * inv[0] + m[1] * inv[4] + m[2] * inv[8] + m[3] * inv[12];

		if (det == 0) {
			throw new ArithmeticException("Matrix determinant was 0!");
		}

		det = 1.0 / det;

		for (i = 0; i < 16; i++) {
			inv[i] *= det;
		}

		matrix = new Matrix4d(inv).getMatrix();
		return this;
	}

	public static Matrix4d perspectiveMatrix() {
		return null;
	}

	public String toString() {
		String r = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				r += matrix[i][j] + ", ";
			}
			r += "\b\b\n";
		}
		return r;
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
		return matrix[id / 4][id % 4];
	}

	public void setValue(int id, double value) {
		if (id > 15 || id < 0 ) {
			throw new IndexOutOfBoundsException(id + " is greater than or smaller than the limits of the matrix (0-15)");
		}
		matrix[id / 4][id % 4] = value;
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
			flat[i] = matrix[i / 4][i % 4];
		}
		return flat;
	}

	public Matrix4d(double[] flat) {
		for (int i = 0; i < 16; i++) {
			matrix[i / 4][i % 4] = flat[i];
		}
	}

}
