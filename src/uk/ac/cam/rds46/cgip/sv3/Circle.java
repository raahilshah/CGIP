package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;

public class Circle extends Shape {
	public Vector center;
	public double radius;
	
	public Circle(double x, double y, double z, double r) {
		center = new Vector(x, y, z);
		radius = r;
	}
	
	public void setSurfaceProperties(double a, double d, double s, double n, Color c) {
		ka = a;
		kd = d;
		ks = s;
		nExp = n;
		color = c;
	}
	
	// Intersection point with the ray P + sD.
	@Override
	public Vector intersection(Vector P, Vector D) {
		double d = Math.pow(D.dot(P.minus(center)), 2) - (P.minus(center)).magSquared() + radius * radius;
		if (d < 0) return null;
		d = Math.sqrt(d);
		double s = D.dot(center.minus(P)) - d, t = D.dot(center.minus(P)) + d;
		s = Math.abs(s) < Math.abs(t) ? s : t;
		return P.plus(D.multiply(s));
	}
	
	public boolean onSurface(Vector P) {
		double epsilon = 1.0E-10;
		return Math.abs((P.minus(center)).magSquared() - (radius * radius)) < epsilon;
	}
	
	@Override
	public Vector normal(Vector P) {
		if (!onSurface(P)) return null;
		else return (P.minus(center)).normalized();
	}
	
	public static void main(String[] args) {
		
		Circle circle = new Circle(50, 50, 50, 30);
		
		Vector D = new Vector(0, 0, 1);
		for (int x = 0; x <= 100; x++) {
			for (int y = 0; y <= 100; y++) {
				Vector P = new Vector(x, y, 0);
				Vector I = circle.intersection(P, D);
				if (I != null) {
					if (!circle.onSurface(I))
						System.out.println(I);
				}
			}
		}
	}
}
