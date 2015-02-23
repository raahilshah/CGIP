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
		scene.setEye(new Vector(100, 300, 1000));
		
		Circle c1 = new Circle(400, 400, -100, 100);
		Circle c2 = new Circle(450, 350, 0, 70);
		c1.setSurfaceProperties(0.05, 0.8, 0.2, 10, Color.BLUE);
		c2.setSurfaceProperties(0.05, 0.7, 0.3, 20, Color.RED);
		
		scene.addObject(c1);
		scene.addObject(c2);
		scene.addLight(new Vector(0, 0, 0));
		scene.addLight(new Vector(400, 400, 800));
		View v = new View(scene);
	}
}
