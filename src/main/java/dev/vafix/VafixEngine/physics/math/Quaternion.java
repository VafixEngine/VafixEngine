package dev.vafix.VafixEngine.physics.math;

public class Quaternion {

	private double w, x, y, z;

	public Quaternion(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Quaternion() {}

	public static Quaternion fromEuler(double roll, double pitch, double yaw) {
		double cy = Math.cos(yaw * 0.5);
		double sy = Math.sin(yaw * 0.5);
		double cp = Math.cos(pitch * 0.5);
		double sp = Math.sin(pitch * 0.5);
		double cr = Math.cos(roll * 0.5);
		double sr = Math.sin(roll * 0.5);

		double w = cr * cp * cy + sr * sp * sy;
		double x = sr * cp * cy - cr * sp * sy;
		double y = cr * sp * cy + sr * cp * sy;
		double z = cr * cp * sy - sr * sp * cy;
		return new Quaternion(w, x, y, z);
	}

	public static Quaternion fromEuler(Vector3 v) {
		return fromEuler(v.getX(), v.getY(), v.getZ());
	}

	public static Quaternion fromDegreeEuler(double x, double y, double z) {
		return fromEuler(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z));
	}

	public static Quaternion fromDegreeEuler(Vector3 v) {
		return fromEuler(MathUtils.toRadians(v));
	}

	public Vector3 toEuler() {
		double roll, pitch, yaw;

		// roll (x-axis rotation)
		double sinr_cosp = 2 * (w * x + y * z);
		double cosr_cosp = 1 - 2 * (x * x + y * y);
		roll = Math.atan2(sinr_cosp, cosr_cosp);

		// pitch (y-axis rotation)
		double sinp = 2 * (w * y - z * x);
		if (Math.abs(sinp) >= 1) {
			pitch = Math.PI / 2 * (sinp / Math.abs(sinp));
		}
    	else {
			pitch = Math.asin(sinp);
		}

		// yaw (z-axis rotation)
		double siny_cosp = 2 * (w * z + x * y);
		double cosy_cosp = 1 - 2 * (y * y + z * z);
		yaw = Math.atan2(siny_cosp, cosy_cosp);

		return new Vector3(x, y, z);
	}

	public double magnitude() {
		return Math.sqrt(w * w + x * x + y * y + z * z);
	}

	public Quaternion normalize() {
		double n = magnitude();
		w /= n;
		x /= n;
		y /= n;
		z /= n;
		return this;
	}

	public Quaternion scale(double s) {
		w *= s;
		x *= s;
		y *= s;
		z *= s;
		return this;
	}

	public Quaternion multiply(Quaternion q) {
		x =  x * q.w + y * q.z - z * q.y + w * q.x;
		y = -x * q.z + y * q.w + z * q.x + w * q.y;
		z =  x * q.y - y * q.x + z * q.w + w * q.z;
		w = -x * q.x - y * q.y - z * q.z + w * q.w;
		return this;
	}
	
	public Quaternion add(Quaternion q) {
		w += q.w;
		x += q.x;
		y += q.y;
		z += q.z;
		return this;
	}

	public Quaternion conjugate(Quaternion q) {
		x = -q.x;
		y = -q.y;
		z = -q.z;
		w = q.w;
		return this;
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
