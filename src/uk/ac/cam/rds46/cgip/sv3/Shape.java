package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;

public abstract class Shape {
	public double ka, kd, ks, km; // Reflection coefficients.
	public double nExp; // Phong's roughness coefficient.
	public Color color; // Base color for the surface.
	
	public abstract double intersection(Vector P, Vector D);
	public abstract Vector normal(Vector P);
	
	public void setSurfaceProperties(double a, double d, double s, double m, double n, Color c) {
		ka = a;
		kd = d;
		ks = s;
		km = m;
		nExp = n;
		color = c;
	}
 
}
