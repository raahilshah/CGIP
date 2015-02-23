package uk.ac.cam.rds46.cgip.sv1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Rasterizer extends JPanel {
	private BufferedImage img;
	private int width, height;
	private Surface s;
	private int shadingType = 0;
	
	public static final int PHONG = 0, FLAT = 1; 

	private final Vertex L; // Light source position.

	public Rasterizer (int w, int h, Surface surf, Vertex light) {
		s = surf;
		L = light;
		width = w; height = h;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		paintBackground();
		setPreferredSize(new Dimension(width, height));
	}
	
	protected void setShading(int shading) {
		if (shading == FLAT) shadingType = FLAT;
		else shadingType = PHONG;

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	protected void setPixel(int x, int y, Color c) {
		// Do nothing if (x, y) are out of bounds.
		if(img == null || x < 0 || x >= width || y < 0 || y >= height) return;

		img.setRGB(x, y, c.getRGB());
	}

	public void paintBackground() {
		for (int i = 0; i <= width; i++)
			for (int j = 0; j <= height; j++) 
				setPixel(i, j, new Color(208,208,208) ); // Grey background.
	}

	/*
	 * Computes the color shade at a point P with unit normal N.
	 * Uses Phong reflection model and point light source.
	 */
	public Color calculateShade(Vertex N) {
		Vertex R = N.multiplyWith(2.0 * N.dotWith(L)).minus(L); // Reflection vector.
		Vertex V = new Vertex(0, 0, -1); // Vector to the eye.
		V.normalize();

		// Calculating reflection based on the Phong reflection model.
		double ambiant = s.ka, diffuse = 0.0, specular = 0.0, I = ambiant;
		diffuse = s.kd * L.dotWith(N);
		if (diffuse > 0) {
			specular = s.ks * Math.pow(R.dotWith(V), s.nExp);
			if (specular < 0) specular = 0.0;
			I = ambiant + diffuse + specular;
		}
		if (I > 1) I = 1;
		Color c = new Color((int)(s.color.getRed() * I), 
				(int)(s.color.getGreen() * I),
				(int)(s.color.getBlue() * I));
		return c;
	}

	/*
	 * Goes through all pixels in the bounding rectangle and fills those that are in the triangle.
	 * Uses the Barycentric Algorithm to test if a point is in the triangle.
	 * Uses flat shading.
	 */
	public void drawTriangle(Triangle t) {
		int xMax = (int) (Math.max(t.A.x, Math.max(t.B.x, t.C.x)));
		int xMin = (int) (Math.min(t.A.x, Math.min(t.B.x, t.C.x)));
		int yMax = (int) (Math.max(t.A.y, Math.max(t.B.y, t.C.y)));
		int yMin = (int) (Math.min(t.A.y, Math.min(t.B.y, t.C.y)));

		// Vectors AB and AC.
		Vertex AB = t.B.minus(t.A);
		Vertex AC = t.C.minus(t.A);


		for (int x = xMin; x <= xMax; x++)
			for (int y = yMin; y <= yMax; y++)
			{
				Vertex AP = new Vertex(x - t.A.x, y - t.A.y, 0);
				double ABxAC = AB.crossWith(AC).z;
				double ABxAP = AB.crossWith(AP).z;
				double APxAC = AP.crossWith(AC).z;

				// Parameters of the Barycentric coordinates of P in the AB-AC system.
				double p = ABxAP / ABxAC;
				double q = APxAC / ABxAC;

				// Test for P in ABC.
				if (p >= 0.0 && q >= 0.0 && (p + q) <= 1.0) {
					Vertex N = interpolatedNormal(t, x, y);
					Color color = calculateShade(N);
					setPixel(x, y, color);
				}
			}
	}
	
	// Interpolating normal at (x, y) in t for Phong shading.
	public Vertex interpolatedNormal(Triangle t, int x, int y) {
		Vertex A = t.A, B = t.B, C = t.C;
		Vertex N = t.calculateNormal(), k = new Vertex(0, 0, 1);
		
		double z = (N.dotWith(A) - N.dotWith(new Vertex(x, y, 0))) / N.dotWith(k);
		Vertex P = new Vertex(x, y, z);
		// Get barycentric coordinates.
		double alpha = 0.0, beta = 0.0, gamma = 0.0;
		
		Vertex AB = B.minus(A), AC = C.minus(A), BC = C.minus(B);
		double area = (AB.crossWith(AC)).magnitude();
		alpha = ((P.minus(B)).crossWith(BC)).magnitude() / area;
		beta = ((P.minus(A)).crossWith(AC)).magnitude() / area;
		gamma = 1.0 - alpha - beta;
		
		if (alpha < 0.0 || alpha > 1.0 || beta < 0 || beta > 1.0 || gamma < 0.0 || gamma > 1.0)
			System.out.println("FLAG: " + alpha + " " + beta + " " + gamma);
		
		
		// Interpolate with barycentric coordinates.
		Vertex normal = ((A.N.multiplyWith(alpha)).plus(B.N.multiplyWith(beta))).plus(C.N.multiplyWith(gamma));
		normal.normalize();
//		System.out.println(normal +  " " + N);
		return (shadingType == PHONG ? normal : N);
	}
}