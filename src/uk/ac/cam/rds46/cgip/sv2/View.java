package uk.ac.cam.rds46.cgip.sv2;

import javax.swing.JFrame;

public class View extends JFrame {
			
	public View () {
		super("CGIP SV1 - Triangle Rasterizer");
		Circle c = new Circle(50, 50, 50, 720, 480);
		c.drawCircle();
		add(c);
		pack();	
		setResizable(false);
	}
	
	public static void main(String[] args) {
		View v = new View();
		v.setVisible(true);
	}

}
