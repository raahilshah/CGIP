package uk.ac.cam.rds46.cgip.sv2;

public class BezierCubic extends Drawable {
	private Point2D P0, P1, P2, P3;
	private final double tolerance = 3.3;
	
	public BezierCubic(Point2D Q0, Point2D Q1, Point2D Q2, Point2D Q3) {
		super();
		P0 = new Point2D(Q0);
		P1 = new Point2D(Q1);
		P2 = new Point2D(Q2);
		P3 = new Point2D(Q3);
	}
	
	public BezierCubic(Point2D Q0, Point2D Q1, Point2D Q2, Point2D Q3, int w, int h) {
		super(w, h);
		P0 = new Point2D(Q0);
		P1 = new Point2D(Q1);
		P2 = new Point2D(Q2);
		P3 = new Point2D(Q3);
	}
	
	public double distance(Point2D A, Point2D B, Point2D C) {
		double distance = 0.0;
		Point2D AB = B.minus(A);
		Point2D AC = C.minus(A);
		double s = (AB.dot(AC)) / (AB.dot(AB));
		if (s < 0) distance = AC.magnitude();
		else if (s > 1) distance = (B.minus(C)).magnitude();
		else {
			Point2D P = new Point2D(A.x + s * AB.x, A.y + s * AB.y);
			distance = (C.minus(P)).magnitude();
		}
		return distance; 
	}
	
	public boolean isFlat(Point2D P0, Point2D P1, Point2D P2, Point2D P3) {
		return ((distance(P0, P3, P1) < tolerance) && 
				(distance(P0, P3, P2) < tolerance));
	}
	
	public void draw(Point2D P0, Point2D P1, Point2D P2, Point2D P3) {
		if (isFlat(P0, P1, P2, P3)) {
			drawLine((int)P0.x, (int)P0.y, (int)P3.x, (int)P3.y);
		}
		else {
			Point2D Q0 = P0, 
					Q1 = new Point2D((P0.x + P1.x) / 2, (P0.y + P1.y) / 2),
					Q2 = new Point2D((P0.x + 2 * P1.x + P2.x) / 4, (P0.y + 2 * P1.y + P2.y) / 4),
					Q3 = new Point2D((P0.x + 3 * P1.x + 3 * P2.x + P3.x) / 8, (P0.y + 3 * P1.y + 3 * P2.y + P3.y) / 8);
			Point2D R0 = Q3, 
					R1 = new Point2D((P1.x + 2 * P2.x + P3.x) / 4, (P1.y + 2 * P2.y + P3.y) / 4),
					R2 = new Point2D((P2.x + P3.x) / 2, (P2.y + P3.y) / 2),
					R3 = P3;
			draw(Q0, Q1, Q2, Q3);
			draw(R0, R1, R2, R3);
		}
	}
	
	@Override
	public void draw() {
		draw(P0, P1, P2, P3);
	}
}
