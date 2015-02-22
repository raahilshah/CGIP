package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.rds46.cgip.sv2.Drawable;

public class Scene extends Drawable {
	public List<Shape> objects;
	public List<Vector> lights;
	
	public Scene() {
		super();
		objects = new LinkedList<Shape>();
		lights = new LinkedList<Vector>();
	}
	
	public void addObject(Shape s) {
		objects.add(s);
	}
	
	public void addLight(Vector l) {
		lights.add(l);
	}
	
	public Color calculateShade(Vector P, Vector N, Shape s) {
		
		double I = s.ka;
		for (Vector LS : lights) {
			Vector L = (LS.minus(P)).normalized();
			Vector R = (N.multiply(2.0 * N.dot(L))).minus(L); // Reflection vector.
			Vector V = new Vector(0, 0, -1); // Vector to eye.
			double diffuse = 0.0, specular = 0.0;
			diffuse = s.kd * (L.dot(N));
			if (diffuse > 0) {
				specular = s.ks * Math.pow(R.dot(V), s.nExp);
				if (specular < 0) specular = 0.0;
				I += (diffuse + specular);
			}
		}
		if (I > 1.0) I = 1.0;
		Color c = new Color((int)(s.color.getRed() * I), 
							(int)(s.color.getGreen() * I),
							(int)(s.color.getBlue() * I));

		return c;
	}

	@Override
	public void draw() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Vector P = new Vector(x, y, 0), D = new Vector(0, 0, 1);
				Vector I = null;
				Shape closest = null;
				for (Shape s : objects) {
					Vector temp = s.intersection(P, D);
					if (I == null || (temp!= null && temp.z < I.z)) {
						I = temp;
						closest = s;
					}
				}
				if (I == null) setPixel(x, y);
				else {
					Vector N = closest.normal(I);
					Color c = calculateShade(I, N, closest);
					setPixel(x, y, c);
				}
			}
		}
	}
}
