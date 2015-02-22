package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;

public abstract class Shape {
	public double ka, kd, ks; // Reflection coefficients.
	public double nExp; // Phong's roughness coefficient.
	public Color color; // Base color for the surface.
	
	public abstract Vector intersection(Vector P, Vector D);
	public abstract Vector normal(Vector P);
 
}
