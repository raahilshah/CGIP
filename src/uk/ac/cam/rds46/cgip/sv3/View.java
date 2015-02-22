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
		Circle c1 = new Circle(300, 300, 300, 100);
		Circle c2 = new Circle(350, 200, 80, 70);
		c1.setSurfaceProperties(0.2, 0.7, 0.2, 7, Color.BLUE);
		c2.setSurfaceProperties(0.2, 0.7, 0.3, 20, Color.RED);
		Scene scene = new Scene();
		scene.addObject(c1);
		scene.addObject(c2);
		scene.addLight(new Vector(0, 0, 0));
		scene.addLight(new Vector(400, 800, 100));
		View v = new View(scene);
	}
}
