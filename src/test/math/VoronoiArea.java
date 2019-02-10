/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

import java.awt.Point;
import java.util.Random;

public class VoronoiArea {
	public Point[][] voronoi = new Point[3][3];
	public Point closest;
	public float value;
	public int X, Y;
	
	
	public static float getHeight(Point voronoi) {
		float height = 0;
        Random random = new Random((voronoi.x + voronoi.y * 500) * 157917);
        height = random.nextFloat();
        return (height + 1) / 2;
	}
	
	
}
