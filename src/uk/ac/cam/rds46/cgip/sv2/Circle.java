package uk.ac.cam.rds46.cgip.sv2;

import java.awt.image.BufferedImage;

public class Circle extends Drawable {
	private int x0, y0, r;
	
	public Circle(BufferedImage img, int x, int y, int radius) {
		super(img);
		x0 = x;
		y0 = y;
		r = radius;
	}
	
	public Circle(int x, int y, int radius) {
		super();
		x0 = x;
		y0 = y;
		r = radius;
	}
	
	public Circle(int x, int y, int radius, int w, int h) {
		super(w, h);
		x0 = x;
		y0 = y;
		r = radius;
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
	
	@Override
	public void draw() {
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
