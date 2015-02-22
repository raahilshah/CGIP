package uk.ac.cam.rds46.cgip.sv2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Drawable extends JPanel {
	protected BufferedImage img;
	protected int width, height;

	public Drawable() {
		width = 1024;
		height = 720;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
	}
	
	public Drawable(BufferedImage i) {
		width = i.getWidth();
		height = i.getHeight();
		img = i;
	}

	public Drawable (int w, int h) {
		width = w;
		height = h;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
	}
	
	public BufferedImage getImage() {
		return img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	// Overloaded setPixel to use default color. 
	protected void setPixel(int x, int y) {
		setPixel(x, y, Color.BLACK);
	}

	protected void setPixel(int x, int y, Color c) {
		// Do nothing if (x, y) are out of bounds.
		if(img == null || x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight()) 
			return;
		img.setRGB(x, y, c.getRGB());
	}

	public int getOctant(int x1, int y1, int x2, int y2) {
		int dy = y2 - y1, dx = x2 - x1;
		int ady = Math.abs(dy), adx = Math.abs(dx);
		if (dy >= 0) { 
			if (dx >= 0) {
				if (adx >= ady) return 0;
				return 1;
			}
			else {
				if (adx >= ady) return 3;
				return 2;
			}
		}
		else {
			if (dx >= 0) {
				if (adx >= ady)	return 7;
				return 6;
			}
			else {
				if (adx >= ady) return 4;
				return 5;
			}
		}
	}

	public void drawLine(int xs, int ys, int xf, int yf) {
		int octant = getOctant(xs, ys, xf, yf);
		
		int x1 = xs, y1 = ys, x2 = xf, y2 = yf;
		
//		Graphics2D g2d = img.createGraphics();
//		g2d.drawLine(x1, y1, x2, y2);
		
		// Transform all lines to first octate lines. 
		switch(octant) {
		case 0:
			x1 = xs; y1 = ys; x2 = xf; y2 = yf;
			break;
		case 1: 
			x1 = ys; y1 = xs; x2 = yf; y2 = xf;
			break;
		case 2:
			x1 = ys; y1 = -xs; x2 = yf; y2 = -xf;
			break;
		case 3: 
			x1 = -xs; y1 = ys; x2 = -xf; y2 = yf;
			break;	
		case 4:
			x1 = -xs; y1 = -ys; x2 = -xf; y2 = -yf;
			break;
		case 5: 
			x1 = -ys; y1 = -xs; x2 = -yf; y2 = -xf;
			break;	
		case 6:
			x1 = -ys; y1 = xs; x2 = -yf; y2 = xf;
			break;
		case 7: 
			x1 = xs; y1 = -ys; x2 = xf; y2 = -yf;
			break;	
		default: 
			break;
		}

		int a = y2 - y1, b = x1 - x2, c = (x2 * y1) - (x1 * y2);
		int x = x1, y = y1;
		double d = a * (x + 1) + b * (y + 0.5) + c;

		while(x <= x2) {

			// Transform back to set correct pixels.
			switch(octant) {
			case 0:
				setPixel(x, y);
				break;
			case 1:
				setPixel(y, x);
				break;
			case 2:
				setPixel(-y, x);
				break;
			case 3: 
				setPixel(-x, y);
				break;	
			case 4:
				setPixel(-x, -y);
				break;
			case 5: 
				setPixel(-y, -x);
				break;	
			case 6:
				setPixel(y, -x);
				break;
			case 7: 
				setPixel(x, -y);
				break;	
			default:
				break;
			}
			
			if (d <= 0) d = d + a;
			else {
				d = d + a + b;
				y++;
			}
			x++;
		}
	}
	
	public abstract void draw();
}
