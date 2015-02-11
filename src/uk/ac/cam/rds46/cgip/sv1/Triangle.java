package uk.ac.cam.rds46.cgip.sv1;

public class Triangle implements Comparable<Triangle> {
	
	public Vertex A, B, C;
	
	public Triangle(Vertex a, Vertex b, Vertex c) {
		A = a;
		B = b;
		C = c;
	}
	
	public Vertex calculateNormal() {
		Vertex N = (B.minus(A)).crossWith3D((C.minus(A))); 
		N.normalize();
		return N;
	}

	@Override
	// Comparing triangles by z depth. Key assumption: no overlapping.
	public int compareTo(Triangle that) {
		double thisMinZ = Math.min(A.z, Math.min(B.z, C.z));
		double thatMinZ = Math.min(that.A.z, Math.min(that.B.z, that.C.z));
		return (int) (thisMinZ - thatMinZ); 
	}
}
