package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import uk.ac.cam.rds46.cgip.sv2.Drawable;

public class Scene extends Drawable {

	private List<Shape> objects;
	private List<Vector> lights;
	private Vector eye;
	private double l, r, t, b;
	private Color background;
	
	private static final double EPSILON = 0.001;

	public Scene() {
		super();
		objects = new LinkedList<Shape>();
		lights = new LinkedList<Vector>();
		background = Color.BLACK;
	}

	public void setEye(Vector v) {
		eye = v;
	}

	public void setSceneBounds(double left, double right, double bottom, double top) {
		l = left;
		r = right;
		t = top;
		b = bottom;
	}

	public void setBackground(Color c) {
		background = c;
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
			Shape obstr = trace(P, L, EPSILON, Double.POSITIVE_INFINITY);
			if (obstr == null) {
				Vector R = (N.multiply(2.0 * N.dot(L))).minus(L); // Reflection vector.
				Vector V = (eye.minus(P)).normalized(); 
				double diffuse = 0.0, specular = 0.0;
				diffuse = s.kd * (L.dot(N));
				if (diffuse > 0) {
					specular = s.ks * Math.pow(R.dot(V), s.nExp);
					if (specular < 0) specular = 0.0;
					I += (diffuse + specular);
				}
			}
		}
		if (I > 1.0) I = 1.0;
		Color c = new Color((int)(s.color.getRed() * I), 
				(int)(s.color.getGreen() * I),
				(int)(s.color.getBlue() * I));

		return c;
	}

	public Shape trace(Vector P, Vector D, double sLow, double sHigh) {
		Shape closest = null;
		double sMin = Double.POSITIVE_INFINITY;
		for (Shape shape : objects) {
			double s = shape.intersection(P, D);
			if (s != -1 && s < sMin && s >= sLow && s < sHigh) {
				closest = shape;
				sMin = s;
			}
		}
		return closest;
	}
	
	public Color rayColor(Vector P, Vector D, double sLow, double sHigh) {
		Shape hit = trace(P, D, sLow, sHigh);
		if (hit != null) {
			Vector intersect = eye.plus(D.multiply(hit.intersection(eye, D)));
			Vector normal = hit.normal(intersect);
			Color shade = calculateShade(intersect, normal, hit);
			Vector R = D.minus(normal.multiply(2 * D.dot(normal))).normalized();
			Color ref = rayColor(P, R, EPSILON, Double.POSITIVE_INFINITY);
			return new Color((int)(shade.getRed() + hit.km * ref.getRed()),
							 (int)(shade.getGreen() + hit.km * ref.getGreen()),
							 (int)(shade.getBlue() + hit.km * ref.getBlue()));
		}
		return background;
	}

	@Override
	public void draw() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double u = l + ((r - l) * (i + 0.5)) / width;
				double v = b + ((t - b) * (j + 0.5)) / height;
				Vector D = (new Vector(u, v, -eye.z)).normalized();
				setPixel(i, j, rayColor(eye, D, 0.0, Double.POSITIVE_INFINITY));

			}
		}
	}
}
