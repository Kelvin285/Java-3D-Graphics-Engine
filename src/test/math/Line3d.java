/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

public class Line3d {
	
	public Vector3d start, end;
	
	public Line3d(Vector3d start, Vector3d end) {
		this.start = start;
		this.end = end;
	}
	
	public boolean isIntersecting(Polygon3d polygon) {
		Line2D.Float line_xy = new Line2D.Float(start.x, start.y, end.x, end.y);
		Line2D.Float line_xz = new Line2D.Float(start.x, start.z, end.x, end.z);
		
		Polygon poly_xy = new Polygon();
		Polygon poly_xz = new Polygon();
		
		for (Vector3d p : polygon.points) {
			poly_xy.addPoint((int)p.x, (int)p.y);
			poly_xz.addPoint((int)p.x, (int)p.z);
		}
		Area line1 = new Area(line_xy);
		Area line2 = new Area(line_xz);
		
		Area poly1 = new Area(poly_xy);
		Area poly2 = new Area(poly_xz);
		line1.intersect(poly1);
		line2.intersect(poly2);
		return line1.isEmpty() == false && line2.isEmpty() == false;
	}
}
