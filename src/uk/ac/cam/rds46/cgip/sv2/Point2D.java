package uk.ac.cam.rds46.cgip.sv2;

public class Point2D {
	public final int x;
	public final int y;
	
	public Point2D(int px, int py) {
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
	
	public int dot(Point2D Q) {
		return (x * Q.x) + (y * Q.y);
	}
	
	public double magnitude() {
		return Math.sqrt((double)((x * x) + (y * y)));
	}
}
