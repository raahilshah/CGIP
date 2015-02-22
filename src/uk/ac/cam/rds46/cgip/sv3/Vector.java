package uk.ac.cam.rds46.cgip.sv3;

import uk.ac.cam.rds46.cgip.sv1.Vertex;

public class Vector {
	public final double x, y, z;
	
	public Vector(double x1, double y1, double z1) {
		x = x1; y = y1; z = z1;
	}
	
	public Vector(Vector v) {
		x = v.x; y = v.y; z = v.z;
	}
	
	public Vector plus(Vector v) {
		return new Vector(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector minus(Vector v) {
		return new Vector(x - v.x, y - v.y, z - v.z);
	}
	
	public double dot(Vector v) {
		return (x * v.x) + (y * v.y) + (z * v.z);
	}
	
	public Vector cross(Vector v) {
		return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
	}
	
	public Vector multiply(double s) {
		return new Vector(s * x, s * y, s * z);
	}
	
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public double magSquared() {
		return (x * x) + (y * y) + (z * z);
	}
	
	public Vector normalized() {
		double m = magnitude();
		return new Vector(x / m, y / m, z / m);
	}
	
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
}
