package dev.vafix.VafixEngine.physics.math;

public class Vector4 {

	private double w, x, y, z;

	public Vector4(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector4() {}

	public String toString() {
		return w + ", " + x + ", " + y + ", " + z;
	}

	public Vector4 clone() {
		return new Vector4(w, x, y, z);
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}
