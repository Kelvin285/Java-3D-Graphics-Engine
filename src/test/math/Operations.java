/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

import java.awt.Point;

import test.Start;

public class Operations {
	public static Point projection(Vector3d vec) {
		int i = 500;
		Point p = new Point((int)(vec.x / (vec.z / i)) + Start.WIDTH / 2, (int)(vec.y / (vec.z / i)) + Start.HEIGHT / 2);
//		System.out.println(p);
		return p;
	}
}
