package dev.vafix.VafixEngine.physics.math;

public class Vector2 {

	private double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
	}

	public String toString() {
		return x + ", " + y;
	}

	public Vector2 clone() {
		return new Vector2(x, y);
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
}
