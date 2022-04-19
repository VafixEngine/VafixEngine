package dev.vafix.VafixEngine.physics.math;

public class MathUtils {

	public static Vector3 toRadians(Vector3 v) {
		return new Vector3(Math.toRadians(v.getX()), Math.toRadians(v.getY()), Math.toRadians(v.getZ()));
	}

	public static Vector3 toDegrees(Vector3 v) {
		return new Vector3(Math.toDegrees(v.getX()), Math.toDegrees(v.getY()), Math.toDegrees(v.getZ()));
	}

}
