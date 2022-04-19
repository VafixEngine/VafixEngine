package dev.vafix.VafixEngine.physics.math;

public class Vector3 {

	private double x, y, z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3() {}

	public String toString() {
		return x + ", " + y + ", " + z;
	}

	public Vector3 clone() {
		return new Vector3(x, y, z);
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
