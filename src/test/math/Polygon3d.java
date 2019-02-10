/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import test.Start;

public class Polygon3d implements Comparable<Polygon3d> {
	public ArrayList<Vector3d> points = new ArrayList<Vector3d>();
   
   public Color color = Color.WHITE;
   
   public Vector3d position = new Vector3d(0, 0, 0);
   public Vector3d normal = new Vector3d(0, 1, 0);
   public float rotH, rotV;
   
	public Polygon3d(Vector3d...points) {
		for (Vector3d v : points) {
			this.points.add(v);
		}
	}
	
	public Polygon3d(ArrayList<Vector3d> points) {
		this.points = points;
	}
	
	public void clearPoints() {
		this.points.clear();
	}
	
	public void addPoints(Vector3d...points) {
		for (Vector3d v : points) {
			this.points.add(v);
		}
	}
	
	public float getAverageZFromCamera(float cx, float cy, float cz, float rotH, float rotV) {
		float zz = 0;
		int i = 0;
		
		
		for (Vector3d p : points) {
					
			Vector3d p2 = new Vector3d(p.x - 0.5f, p.y - 0.5f, p.z - 0.5f);
			
			
					float dx1 = p2.x + 0;
					float dy1 = p2.y + 0;
					float dz1 = p2.z + 0;
					
					p2.x = (float)(dx1 * Math.cos(Math.toRadians(this.rotH)) - dz1 * Math.sin(Math.toRadians(this.rotH)));
			        p2.y = (float)(dy1 * Math.cos(Math.toRadians(this.rotV)) - dz1 * Math.sin(Math.toRadians(this.rotV)) * Math.cos(Math.toRadians(this.rotH)) - dx1 * Math.sin(Math.toRadians(this.rotV)) * Math.sin(Math.toRadians(this.rotH)));
					p2.z = (float)(dz1 * Math.cos(Math.toRadians(this.rotH)) + dx1 * Math.sin(Math.toRadians(this.rotH)) + dy1 * Math.sin(Math.toRadians(this.rotV)));
					
					p2.x += 0.5f + position.x;
					p2.y += 0.5f + position.y;
					p2.z += 0.5f + position.z;
					
		//			averagePos.x += p2.x;
		//			averagePos.y += p2.y;
		//			averagePos.z += p2.z;
		//			pts++;
					
					float dx = p2.x - cx;
					float dy = p2.y - cy;
					float dz = p2.z - cz;
					
		         
		//	         p2.x = (float)(dx * Math.cos(Math.toRadians(rotH)) - dz * Math.sin(Math.toRadians(rotH)));
		//	         p2.y = (float)(dy * Math.cos(Math.toRadians(rotV)) - dz * Math.sin(Math.toRadians(rotV)) * Math.cos(Math.toRadians(rotH)) - dx * Math.sin(Math.toRadians(rotV)) * Math.sin(Math.toRadians(rotH)));
					 p2.z = (float)(dz * Math.cos(Math.toRadians(rotH)) + dx * Math.sin(Math.toRadians(rotH)) + dy * Math.sin(Math.toRadians(rotV)) * 1.5f);
			 
					zz += p2.z;
					i++;
		}
		return zz;
	
	}
	
	public boolean seenByPoint(Vector3d start, Vector3d end, int index) {
		Line3d line = new Line3d(start, end);
		for (int i = index; i < Start.drawing.size(); i++) {
			Polygon3d p = Start.drawing.get(i);
			if (line.isIntersecting(p))
				return false;
		}
		return true;
	}
   	
	
	@SuppressWarnings("unchecked")
	public void render(Graphics g, float cx, float cy, float cz, float rotH, float rotV, int index) {
		
//		if (seenByPoint(position, new Vector3d(cx, cy, cz), index) == false) {
//			return;
//		}
		
//		Math.atan2(normal.y, normal.x) * 180 / Math.PI
		if (normal.y != 0) {
//			System.out.println(rotV);
			if (normal.y == 1) {
				if (rotV < -30)
					return;
			}
			if (normal.y == -1) {
				if (rotV > 30)
					return;
			}
		}
		
		
		if (normal.z != 0) {
			
			if (normal.z == -1) {
				if (Math.abs(rotH % 360) < Math.abs(Math.atan2(normal.z, normal.x) * 180 / Math.PI)) {
					return;
				}
			} else {

				if (Math.abs(rotH % 360) > Math.atan2(normal.z, normal.x) * 180 / Math.PI + 45 && Math.abs(rotH % 360) < 360 - Math.atan2(normal.z, normal.x) * 180 / Math.PI) {
					return;
				}
			}
			
		}
		
		if (normal.x != 0) {
			
			if (normal.x == -1) {

				if (rotH % 360 > 0 && rotH % 360 < 200) {
					return;
				}
			} else {
				if (rotH > 0) {
					if (Math.abs(rotH % 360) > 190) {
						return;
					}
				} else {
					
					if (Math.abs(rotH % 360) > 14 && Math.abs(rotH % 360) < 160) {
						return;
					}
				}
				
				
			}
			
		}
		g.setColor(color);
		Polygon polygon = new Polygon();
//		int ambient = 255 / 4;

		Vector3d averagePos = new Vector3d(0, 0, 0);
		float light = 0;
		int pts = 0;
		
		int seen = 0;
		
		for (Vector3d p : points) {
			
			Vector3d p2 = new Vector3d(p.x - 0.5f, p.y - 0.5f, p.z - 0.5f);
			
			
			float dx1 = p2.x + 0;
			float dy1 = p2.y + 0;
			float dz1 = p2.z + 0;
			
			p2.x = (float)(dx1 * Math.cos(Math.toRadians(this.rotH)) - dz1 * Math.sin(Math.toRadians(this.rotH)));
	        p2.y = (float)(dy1 * Math.cos(Math.toRadians(this.rotV)) - dz1 * Math.sin(Math.toRadians(this.rotV)) * Math.cos(Math.toRadians(this.rotH)) - dx1 * Math.sin(Math.toRadians(this.rotV)) * Math.sin(Math.toRadians(this.rotH)));
			p2.z = (float)(dz1 * Math.cos(Math.toRadians(this.rotH)) + dx1 * Math.sin(Math.toRadians(this.rotH)) + dy1 * Math.sin(Math.toRadians(this.rotV)));
			
			p2.x += 0.5f + position.x;
			p2.y += 0.5f + position.y;
			p2.z += 0.5f + position.z;
			
			averagePos.x += p2.x;
			averagePos.y += p2.y;
			averagePos.z += p2.z;
			pts++;
			
			float dx = p2.x - cx;
			float dy = p2.y - cy;
			float dz = p2.z - cz;
			
			
	         p2.x = (float)(dx * Math.cos(Math.toRadians(rotH)) - dz * Math.sin(Math.toRadians(rotH)));
	         p2.y = (float)(dy * Math.cos(Math.toRadians(rotV)) - dz * Math.sin(Math.toRadians(rotV)) * Math.cos(Math.toRadians(rotH)) - dx * Math.sin(Math.toRadians(rotV)) * Math.sin(Math.toRadians(rotH)));
			 p2.z = (float)(dz * Math.cos(Math.toRadians(rotH)) + dx * Math.sin(Math.toRadians(rotH)) + dy * Math.sin(Math.toRadians(rotV)) * 1.5f);
			 
			 
			 boolean outOfView = false;
          
			if (p2.z > 0) {
				Point pt = Operations.projection(p2);
				if (pt.x < -250 || pt.y < -250 || pt.x > Start.WIDTH + 250 || pt.y > Start.HEIGHT + 250)
					outOfView = true;
				polygon.addPoint(pt.x, pt.y);
			}
			if (outOfView == false)
				 seen++;
		}
		if (seen < pts / 2)
			return;
		averagePos.x /= pts;
		averagePos.y /= pts;
		averagePos.z /= pts;
		
		for (int i = 0; i < Start.lights.size(); i++) {
			light += (1 / (Vector3d.distance(averagePos, Start.lights.get(i)) + 0.001f));
		}
		light *= 10;
		light = Math.max(0, Math.min(light, 1));
		g.setColor(new Color(Math.min((int)((float)color.getRed() * light), 255), Math.min((int)((float)color.getGreen() * light), 255), Math.min((int)((float)color.getBlue() * light), 255)));
		g.fillPolygon(polygon);
	}
	//getAverageZFromCamera(float cx, float cy, float cz, float rotH, float rotV)
   public int compareTo(Polygon3d o) {
      float z = o.getAverageZFromCamera(Start.cx, Start.cy, Start.cz, Start.rotH, Start.rotV);
      float mz = getAverageZFromCamera(Start.cx, Start.cy, Start.cz, Start.rotH, Start.rotV);
      return z > mz ? 1 : z == mz ? 0 : -1;
   }
   
}
