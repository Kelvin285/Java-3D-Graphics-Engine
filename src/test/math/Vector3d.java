/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

public class Vector3d {
	public float x, y, z;
	
	public Vector3d (float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static double distance(Vector3d a, Vector3d b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y) + (a.z - b.z) * (a.z - b.z));
	}
}
