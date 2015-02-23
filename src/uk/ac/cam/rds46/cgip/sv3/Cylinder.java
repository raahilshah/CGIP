package uk.ac.cam.rds46.cgip.sv3;

public class Cylinder extends Shape {
	
	public Vector center;
	public double radius;
	public double yMin, yMax;
	
	public Cylinder(double x, double z, double r, double y1, double y2) {
		center = new Vector(x, 0.0, z);
		radius = r;
		yMin = y1;
		yMax = y2;
	}

	@Override
	public double intersection(Vector Py, Vector Dy) {
		Vector P = new Vector(Py.x, 0.0, Py.z), D = new Vector(Dy.x, 0.0, Dy.z);
		Vector C = new Vector(center.x, 0.0, center.z);
		double d = Math.pow(D.dot(P.minus(C)), 2) - (P.minus(C)).magSquared() + radius * radius;
		if (d < 0) return -1;
		d = Math.sqrt(d);
		double s = D.dot(C.minus(P)) - d, t = D.dot(C.minus(P)) + d;
		double y1 = Py.y + s * Dy.y, y2 = Py.y + t * Dy.y;
		
		s = Math.abs(s) < Math.abs(t) ? s : t;
		return s;
		
		
//		if (y1 > yMin && y1 < yMax) { 
//			if ((y2 > yMin && y2 < yMax) && (Math.abs(t) < Math.abs(s))) 
//				return Py.plus(Dy.multiply(t));
//			else
//				return Py.plus(Dy.multiply(s));
//		}
//		else {
//			if (y2 > yMin && y2 < yMax) return Py.plus(Dy.multiply(t));
//			else return null;
//		}
	}
	
	public boolean onSurface(Vector P) {
		double epsilon = 1.0E-10;
		return Math.abs(Math.pow(P.x - center.x, 2) + Math.pow(P.z - center.z, 2) - (radius * radius)) < epsilon;
	}

	@Override
	public Vector normal(Vector P) {
		return (P.minus(new Vector(center.x, P.y, center.z))).normalized();
	}

}
