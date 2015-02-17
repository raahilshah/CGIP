package uk.ac.cam.rds46.cgip.sv2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Circle extends JPanel {
	private BufferedImage img;
	private int width, height;
	
	private int x0, y0, r;
	
	public Circle(int x, int y, int radius) {
		x0 = x;
		y0 = y;
		r = radius;
		img = new BufferedImage(2 * radius, 2 * radius, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(2 * radius, 2 * radius));
	}
	
	public Circle(int x, int y, int radius, int w, int h) {
		x0 = x;
		y0 = y;
		r = radius;
		width = w;
		height = h;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	
	// Overloaded setPixel to use default color. 
	protected void setPixel(int x, int y) {
		setPixel(x, y, Color.RED);
	}

	protected void setPixel(int x, int y, Color c) {
		// Do nothing if (x, y) are out of bounds.
		if(img == null || x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight()) 
			return;
		img.setRGB(x, y, c.getRGB());
	}
	
	// Sets pixels in all 8 octants given (x, y) in octant 2.
	public void setPixelAllOctants(int x, int y) {
		setPixel(x0 + x, y0 + y);
		setPixel(x0 - x, y0 + y);
		setPixel(x0 + x, y0 - y);
		setPixel(x0 - x, y0 - y);
		setPixel(x0 + y, y0 + x);
		setPixel(x0 + y, y0 - x);
		setPixel(x0 - y, y0 + x);
		setPixel(x0 - y, y0 - x);
	}
	
	public void drawCircle() {
		int x = 0, y = r;
		int d = x * x + y * y - r * r;
		while (y >= x) {
			setPixelAllOctants(x, y);
			if (d <= 0) {
				d = d + 2 * x + 3;
			}
			else {
				y--;
				d = d + 2 * x - 2 * y + 5;
			}
			x++;
		}
	}
	
	
}
