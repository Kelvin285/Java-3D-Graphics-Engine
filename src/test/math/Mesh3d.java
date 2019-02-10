/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Color;
import test.Start;

public class Mesh3d {
	public ArrayList<Polygon3d> polygons = new ArrayList<Polygon3d>();
	   
	   public Color color = Color.WHITE;
	   
	   public Vector3d position = new Vector3d(0, 0, 0);
	   public float rotH, rotV;
	   
		public Mesh3d(Polygon3d...polygons) {
			for (Polygon3d v : polygons) {
				this.polygons.add(v);
			}
		}
		
		public Mesh3d(ArrayList<Polygon3d> polygons) {
			this.polygons = polygons;
		}
		
		public void clearPolygons() {
			this.polygons.clear();
		}
		
		public void addPolygons(Polygon3d...polygons) {
			for (Polygon3d v : polygons) {
				this.polygons.add(v);
			}
		}
		
		boolean[][][] cubeVisible = new boolean[3][3][3];
		
		public void draw() {
			for (Polygon3d p : polygons) {
				p.position = position;
				p.rotH = rotH;
				p.rotV = rotV;
				Start.drawing.add(p);
			}
		}
		
		public Mesh3d setAsCube() {
			this.clearPolygons();
			//front
			Polygon3d p = new Polygon3d(
					new Vector3d(0, 0, 0),
					new Vector3d(0, 1, 0),
					new Vector3d(1, 1, 0),
					new Vector3d(1, 0, 0)
					);
			p.normal = new Vector3d(0, 0, 1);
		   //back
		   Polygon3d p2 = new Polygon3d(
					new Vector3d(0, 0, 1),
					new Vector3d(0, 1, 1),
					new Vector3d(1, 1, 1),
					new Vector3d(1, 0, 1)
					);
		   p2.normal = new Vector3d(0, 0, -1);
		   //top     
		   Polygon3d p3 = new Polygon3d(
					new Vector3d(0, 0, 0),
					new Vector3d(0, 0, 1),
					new Vector3d(1, 0, 1),
					new Vector3d(1, 0, 0)
					);
		   p3.normal = new Vector3d(0, 1, 0);
		   //bottom 
		   Polygon3d p4 = new Polygon3d(
					new Vector3d(0, 1, 0),
					new Vector3d(0, 1, 1),
					new Vector3d(1, 1, 1),
					new Vector3d(1, 1, 0)
					);
		   p4.normal = new Vector3d(0, -1, 0);
		   //left 
		   Polygon3d p5 = new Polygon3d(
					new Vector3d(0, 0, 0),
					new Vector3d(0, 1, 0),
					new Vector3d(0, 1, 1),
					new Vector3d(0, 0, 1)
					);
		   p5.normal = new Vector3d(1, 0, 0);
		   //right
		   Polygon3d p6 = new Polygon3d(
					new Vector3d(1, 0, 0),
					new Vector3d(1, 1, 0),
					new Vector3d(1, 1, 1),
					new Vector3d(1, 0, 1)
					);
		   p6.normal = new Vector3d(-1, 0, 0);
            p.color = Color.YELLOW;
            p2.color = Color.WHITE;
            p3.color = Color.GRAY;
            p4.color = Color.BLUE;
            p5.color = Color.RED;
            p6.color = Color.GREEN;
		   	this.addPolygons(p, p2, p3, p4, p5, p6);
            
			return this;
		}
}
