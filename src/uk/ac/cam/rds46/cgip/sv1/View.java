package uk.ac.cam.rds46.cgip.sv1;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class View extends JFrame {
	
	private static final int WIDTH = 1024, HEIGHT = 720, BORDER = 100;
	
	private Rasterizer r;
	
	public View () {
		super("CGIP SV1 - Triangle Rasterizer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Surface s = new Surface(0.0, 0.7, 0.2, 1, new Color(10, 160, 250));
		Vertex L = new Vertex(1, 1, -1); // Assumed fixed light source at a distance.
		L.normalize();
		r = new Rasterizer(WIDTH, HEIGHT, s, L);
		
		try {
			loadFile("wt_teapot.obj");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		add(r);
		pack();	
		setResizable(false);
	}
	
	public void loadFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner scn = new Scanner(file);
		double xMax = Double.NEGATIVE_INFINITY, xMin = Double.POSITIVE_INFINITY;
		double yMax = Double.NEGATIVE_INFINITY, yMin = Double.POSITIVE_INFINITY;
		double xScale = 1.0;
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		List<Vertex> normals = new ArrayList<Vertex>();
		List<Triangle> triangles = new ArrayList<Triangle>();
		
		// Read in vertices, normals and faces from the file.
		while (scn.hasNextLine()) {
			String line = scn.nextLine().trim();
			String[] arr = line.split(" ");
			if (arr[0].equals("v")) {
				double x = Double.parseDouble(arr[1]);
				double y = Double.parseDouble(arr[2]);
				double z = Double.parseDouble(arr[3]);
				if (x > xMax) xMax = x; if (x < xMin) xMin = x;
				if (y > yMax) yMax = y; if (y < yMin) yMin = y;
				vertices.add(new Vertex(x, y, z));
			} 
			else if (arr[0].equals("vn")) {
				double x = Double.parseDouble(arr[1]);
				double y = Double.parseDouble(arr[2]);
				double z = Double.parseDouble(arr[3]);
				Vertex v = new Vertex(x, y, z);
				v.normalize();
				normals.add(v);
			}
			else if (arr[0].equals("f")) {
				int v1 = Integer.parseInt(arr[1].split("//")[0]) - 1;
				int v2 = Integer.parseInt(arr[2].split("//")[0]) - 1;
				int v3 = Integer.parseInt(arr[3].split("//")[0]) - 1;
				Vertex A = vertices.get(v1), B = vertices.get(v2), C = vertices.get(v3);
				triangles.add(new Triangle(A, B, C));
				int n1 = Integer.parseInt(arr[1].split("//")[1]) - 1;
				int n2 = Integer.parseInt(arr[2].split("//")[1]) - 1;
				int n3 = Integer.parseInt(arr[3].split("//")[1]) - 1;
				A.setNormal(normals.get(n1));
				B.setNormal(normals.get(n2)); 
				C.setNormal(normals.get(n3));
			}
		}
		
		// Shift and scale the vertices to fit the display system.
		Vertex x0y0 = new Vertex(xMin, yMin, 0);
		
		xScale = (WIDTH) / (xMax - xMin);
		for (Vertex v : vertices) {
			v.shiftTo(x0y0); // Shifting origin.
			v.y = yMax - v.y; // Flipping in the y for inverted y axis. 
			v.scaleBy(xScale); // Scaling.
			v.shiftTo(new Vertex(0, -BORDER, 0)); // Spacing.
		}
		
		// Sort the triangles by z. Assuming no overlapping faces.
		Collections.sort(triangles);
		
		// Draw the triangles.
		for (Triangle t : triangles)
			r.drawTriangle(t);
		
		scn.close();
	}
	
	public static void main(String[] args) {
		View v = new View();
		v.setVisible(true);
	}

}
