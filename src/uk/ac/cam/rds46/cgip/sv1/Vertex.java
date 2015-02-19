package uk.ac.cam.rds46.cgip.sv1;

public class Vertex {
	public double x, y, z;
	public Vertex N;
	
	public Vertex() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
		N = null;
	}
	
	public Vertex(Vertex V) {
		x = V.x;
		y = V.y;
		z = V.z;
		N = V.N;
	}
	
	public Vertex(double a, double b, double c) {
		x = a;
		y = b;
		z = c;
		N = null;
	}
	
	public void setNormal(Vertex n) {
		N = n;
	}
	
	// 3D vector dot product.
	public double dotWith(Vertex V) {
		return x * V.x + y * V.y + z * V.z;
	}
	// 3D cross product this x V.
	public Vertex crossWith(Vertex V) {
		Vertex cross = new Vertex();
		cross.x = y * V.z - z * V.y;
		cross.y = - (x * V.z - z * V.x);
		cross.z = x * V.y - y * V.x;
		return cross;
	}
	
	// 3D vector subtraction.
	public Vertex minus(Vertex V) {
		return new Vertex(x - V.x, y - V.y, z - V.z);
	}
	
	// 3D vector addition.
	public Vertex plus(Vertex V) {
		return new Vertex(x + V.x, y + V.y, z + V.z);
	}
	
	// 3D shift to system centered at 'origin'.
	public void shiftTo(Vertex origin) {
		x = x - origin.x;
		y = y - origin.y;
		z = z - origin.z;
	}
	
	// Scalar multiplication.
	public void scaleBy(double n) {
		x = n * x;
		y = n * y;
		z = n * z;
	}
	public Vertex multiplyWith(double n) {
		return new Vertex(n * x, n * y, n * z);
	}
	
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y) + (z * z));
	}
	
	public void normalize() {
		double n = magnitude();
		x = x / n;
		y = y / n;
		z = z / n;
	}
	
	@Override
	public String toString() {
		return  "(" + x + ", " + y + ", " + z + ")";
	}
}
