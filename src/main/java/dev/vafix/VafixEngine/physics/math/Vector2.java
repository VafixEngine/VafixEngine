package dev.vafix.VafixEngine.physics.math;

public class Vector2 {

	private double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
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
