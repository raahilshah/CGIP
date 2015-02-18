package uk.ac.cam.rds46.cgip.sv2;

public class Point2D {
	public final double x;
	public final double y;
	
	public Point2D(double px, double py) {
		x = px;
		y = py;
	}
	
	public Point2D(Point2D Q) {
		x = Q.x;
		y = Q.y;
	}
	
	public Point2D plus(Point2D Q) {
		return new Point2D(x + Q.x, y + Q.y);
	}
	
	public Point2D minus(Point2D Q) {
		return new Point2D(x - Q.x, y - Q.y);
	}
	
	public double dot(Point2D Q) {
		return (x * Q.x) + (y * Q.y);
	}
	
	public double magnitude() {
		return Math.sqrt((x * x) + (y * y));
	}
	
	
}
