package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.rds46.cgip.sv2.Drawable;

public class Scene extends Drawable {
	public List<Shape> objects;
	public List<Vector> lights;
	public Vector eye;
	
	public Scene() {
		super();
		objects = new LinkedList<Shape>();
		lights = new LinkedList<Vector>();
		eye = null;
	}
	
	public void addObject(Shape s) {
		objects.add(s);
	}
	
	public void addLight(Vector l) {
		lights.add(l);
	}
	
	public void setEye(Vector v) {
		eye = v;
	}
	
	public Color calculateShade(Vector P, Vector N, Shape s, List<Vector> lights) {
		
		double I = s.ka;
		for (Vector LS : lights) {
			Vector L = (LS.minus(P)).normalized();
			Vector R = (N.multiply(2.0 * N.dot(L))).minus(L); // Reflection vector.
			Vector V = null;
			if (eye == null) // Fixed eye at infinite distance behind screen.
				V = new Vector(0, 0, -1);
			else // Vector to point eye.
				V = (eye.minus(P)).normalized(); 
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
	
	public Shape trace(Vector P, Vector D) {
		Vector I = null;
		Shape closest = null;
		for (Shape s : objects) {
			Vector temp = s.intersection(P, D);
			if (temp != null && (I == null || 
					(temp.minus(eye).magSquared() < I.minus(eye).magSquared()))) {
				I = temp;
				closest = s;
			}
		}
		return (I == null ? null : closest);
	}

	@Override
	public void draw() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Vector S =  new Vector(x, y, 0);
				Vector P = (eye == null) ? S : eye;
				Vector D = (eye == null) ? new Vector(0, 0, 1) : (S.minus(eye)).normalized();
				Shape closest = trace(P, D); 
				if (closest == null) setPixel(x, y);
				else {
					Vector I = closest.intersection(P, D);
					Vector N = closest.normal(I);
					List<Vector> ls = new LinkedList<Vector>();
					for (Vector l : lights) {
						Vector v = (l.minus(I)).normalized();
						Shape obstr = trace(I, v);
						if (obstr == null || obstr == closest)
							ls.add(l);
					}
					Color c = calculateShade(I, N, closest, ls);
					setPixel(x, y, c);
				}
			}
		}
	}
}
