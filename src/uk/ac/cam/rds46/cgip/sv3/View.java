package uk.ac.cam.rds46.cgip.sv3;

import java.awt.Color;

import javax.swing.JFrame;

import uk.ac.cam.rds46.cgip.sv2.Drawable;

public class View extends JFrame {
	
	public View(Drawable d) {
		super("CGIP SV3 - Ray Tracing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(d);
		d.draw();
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Scene scene = new Scene();
		scene.setBackground(Color.BLACK);
		scene.setSceneBounds(-700, 700, -500, 500);
		scene.setEye(new Vector(0, 0, 500));
		
		Circle c1 = new Circle(0, 0, 0, 100);
		c1.setSurfaceProperties(0.1, 0.8, 0.2, 10, Color.BLUE);
		scene.addObject(c1);
		
		Circle c2 = new Circle(-100, -100, -100, 150);
		c2.setSurfaceProperties(0.1, 0.8, 0.2, 20, Color.RED);
		scene.addObject(c2);
		
		scene.addLight(new Vector(500, 300, 800));
		View v = new View(scene);
	}
}
