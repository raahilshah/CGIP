package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;

public class Circle extends Shape {
	public Vector center;
	public double radius;
	
	public Circle(double x, double y, double z, double r) {
		center = new Vector(x, y, z);
		radius = r;
	}
	
	// Intersection with the ray P + sD.
	@Override
	public double intersection(Vector P, Vector D) {
		double d = Math.pow(D.dot(P.minus(center)), 2) - (P.minus(center)).magSquared() + radius * radius;
		if (d < 0) return -1;
		d = Math.sqrt(d);
		double s = D.dot(center.minus(P)) - d, t = D.dot(center.minus(P)) + d;
		return (Math.abs(s) < Math.abs(t) ? s : t);
	}
	
	public boolean onSurface(Vector P) {
		double epsilon = 1.0E-9;
		return Math.abs((P.minus(center)).magSquared() - (radius * radius)) < epsilon;
	}
	
	@Override
	public Vector normal(Vector P) {
			return (P.minus(center)).normalized();
	}
	
	public static void main(String[] args) {
		
		Circle circle = new Circle(50, 50, 50, 30);
		Vector eye = new Vector(500, 500, -1000);
		for (int x = 0; x <= 100; x++) {
			for (int y = 0; y <= 100; y++) {
				Vector S =  new Vector(x, y, 0);
				Vector P = eye;
				Vector D = S.minus(eye).normalized();
				Vector I = P.plus(D.multiply(circle.intersection(P, D)));
				if (I != null) {
					if (!circle.onSurface(I))
						System.out.println(Math.abs((I.minus(circle.center)).magSquared() - (circle.radius * circle.radius)));
				}
			}
		}
	}
}
