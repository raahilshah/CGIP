package uk.ac.cam.rds46.cgip.sv1;

import java.awt.Color;

public class Surface {
	public double ka, kd, ks; // Reflection coefficients.
	public double nExp; // Phong's roughness coefficient.
	public Color color; // Base color for the surface.
	
	public Surface(double a, double d, double s, double n, Color c) {
		ka = a;
		kd = d;
		ks = s;
		nExp = n;
		color = c;
	}
}
