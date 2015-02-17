package uk.ac.cam.rds46.cgip.sv2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public abstract class Drawable extends JPanel {
	private BufferedImage img;
	private int width, height;
	
	public Drawable() {
		width = 1024;
		height = 720;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(width, height));
	}
	
	public Drawable (int w, int h) {
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
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		Graphics2D g2d = img.createGraphics();
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	public abstract void draw();
}
