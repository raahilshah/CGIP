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

	private final Vertex L; // Light source position.

	public Rasterizer (int w, int h, Surface surf) {
		s = surf;
		L = new Vertex(-1, -1, -1); // Assumed fixed light source at a distance.
		L.normalize();
		width = w; height = h;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		paintBackground();
		setPreferredSize(new Dimension(width, height));
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
		if (t.A.y == t.B.y) {
			A = t.C; C = t.A;
		}
		if (t.A.y == t.C.y) {
			A = t.B; B = t.A;
		}
		
		Vertex Np = A.N.multiplyWith(B.y - y).plus(B.N.multiplyWith(y - A.y));
		Np.scaleBy(1.0 / (B.y - A.y));
		Vertex Nq = A.N.multiplyWith(C.y - y).plus(C.N.multiplyWith(y - C.y));
		Np.scaleBy(1.0 / (C.y - A.y));
		double mAB = (B.y - A.y) / (B.x - A.x);
		double mAC = (C.y - A.y) / (C.x - A.x);
		double Px = (B.x == A.x) ? A.x : ((y - A.y) / mAB) + A.x;
		double Qx = (C.x == A.x) ? A.x : ((y - A.y) / mAC) + A.x;
		
		Vertex Ns = Np.multiplyWith(Qx - x).plus(Nq.multiplyWith(x - Px));
		Ns.scaleBy(1.0 / (Qx - Px));
		Ns.normalize();
		
		return Ns;
	}
}