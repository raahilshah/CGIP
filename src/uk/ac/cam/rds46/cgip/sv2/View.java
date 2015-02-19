package uk.ac.cam.rds46.cgip.sv2;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class View extends JFrame {
			
	public View () {
		super("CGIP SV2 - Midpoint algorithms and Bezier Cubics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Circle c = new Circle(300, 300, 50, 500, 500);
		c.draw();
		add(c);
		
		int H = 1024, W = 720;
		
		Point2D P0 = new Point2D(1, 1);
		Point2D P1 = new Point2D(2 * H, 1);
		Point2D P2 = new Point2D(-H, W - 1);
		Point2D P3 = new Point2D(H - 1, W - 1);
		
		BezierCubic bc = new BezierCubic(P0, P1, P2, P3, H, W);
		bc.draw();
		add(bc);
		
		Circle c1 = new Circle(245, 209, 15);
		c1.draw();
		BufferedImage img = c1.getImage();
		Circle c2 = new Circle(img, 245, 209, 10);
		c2.draw();
		Circle c3 = new Circle(img, 245, 209, 5);
		c3.draw();
		BezierCubic b1 = new BezierCubic(img, new Point2D(245, 209), new Point2D(9, 451), new Point2D(476, 451), new Point2D(245, 209));
		b1.draw();
		BezierCubic b2 = new BezierCubic(img, new Point2D(245, 209), new Point2D(33, -28), new Point2D(480, -28), new Point2D(245, 209));
		b2.draw();
		BezierCubic b3 = new BezierCubic(img, new Point2D(245, 209), new Point2D(-28, 10), new Point2D(-28, 430), new Point2D(245, 209));
		b3.draw();
		BezierCubic b4 = new BezierCubic(img, new Point2D(245, 209), new Point2D(518, 10), new Point2D(518, 430), new Point2D(245, 209));
		b4.draw();

		add(c1);
		
		pack();	
		setResizable(false);
	}
	
	public static void main(String[] args) {
		View v = new View();
		v.setVisible(true);
	}

}
