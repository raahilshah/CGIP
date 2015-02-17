package uk.ac.cam.rds46.cgip.sv2;

import javax.swing.JFrame;

public class View extends JFrame {
			
	public View () {
		super("CGIP SV1 - Triangle Rasterizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Circle c = new Circle(300, 300, 50, 720, 480);
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
		
		pack();	
		setResizable(false);
	}
	
	public static void main(String[] args) {
		View v = new View();
		v.setVisible(true);
	}

}
