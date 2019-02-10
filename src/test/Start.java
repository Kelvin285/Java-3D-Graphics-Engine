/*
 * Created by Kevin Merrill
 * kmerrill285@gmail.com
 */
package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

import test.math.Mesh3d;
import test.math.Polygon3d;
import test.math.Vector3d;
import test.math.VoronoiArea;

public class Start {

	public static String programSource;
	
	
	public static void main(String[] args) {
	   new Start();
	}
	
	private JFrame frame;
	private Image image;
	
	public static int WIDTH = 500, HEIGHT = 500;
	
	public Start() {
		frame = new JFrame("test");
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image = frame.createVolatileImage(WIDTH, HEIGHT);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) { 
					Start.dirZ = 1;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) { 
					Start.dirZ = -1;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_A) { 
					Start.dirX = -1;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) { 
					Start.dirX = 1;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) { 
					Start.dirY = -1;
				}
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) { 
					Start.dirY = 1;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) { 
					Start.dirH = -1;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
					Start.dirH = 1;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP) { 
					Start.dirV = -1;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
					Start.dirV = 1;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) { 
					Start.dirZ = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_S) { 
					Start.dirZ = 0;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_A) { 
					Start.dirX = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) { 
					Start.dirX = 0;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_SPACE) { 
					Start.dirY = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_SHIFT) { 
					Start.dirY = 0;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_LEFT) { 
					Start.dirH = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) { 
					Start.dirH = 0;
				}
				
				if (e.getKeyCode() == KeyEvent.VK_UP) { 
					Start.dirV = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_DOWN) { 
					Start.dirV = 0;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
      new Thread() {
         public void run() {
            while (true) {
               tick();
               try {
                  Thread.sleep(1);
               }catch (Exception e) {}
            }
         }
      }.start();
      
		while (true)
		{
			render();
		}
	}

	private static int SIZE = 1000;
	private static int RENDER_DIST = 40;
	
	
	private static int VORONOI_DIST = 25;
	
	public static float cx = SIZE / 2;
	public static float cy = 0;
	public static float cz = SIZE / 2;
	
	public static float rotH = 0, rotV = 0;
	
	public static int dirX = 0;
	public static int dirY = 0;
	public static int dirZ = 0;
	public static int dirH = 0;
	public static int dirV = 0;
	
	
   float FPS = 0;
   float desiredFPS = 60;
   
   float delta = 1;
   
   long lastTime = System.currentTimeMillis();
   
   
   
   
	public void tick() {
		delta = 5;
		if (Start.dirZ != 0) {
			Start.cx -= Start.dirZ * 0.0025f * Math.cos(Math.toRadians(Start.rotH + 90)) * delta;
			Start.cz += Start.dirZ * 0.0025f * Math.sin(Math.toRadians(Start.rotH + 90)) * delta;
		}
		
		
		if (Start.dirX != 0) {
			Start.cx += Start.dirX * 0.0025f * Math.cos(Math.toRadians(Start.rotH)) * delta;
			Start.cz -= Start.dirX * 0.0025f * Math.sin(Math.toRadians(Start.rotH)) * delta;
		}
		
		if (Start.dirY != 0) {
         Start.cy += Start.dirY * 0.0025f * delta;
      }
		
		Start.rotV = Math.max(-90, Math.min(90, Start.rotV));
		
		Start.rotH += Start.dirH * 0.025f * delta;
		Start.rotV += Start.dirV * 0.025f * delta;
	}
	
	public static ArrayList<Polygon3d> drawing = new ArrayList<Polygon3d>();
	public static ArrayList<Vector3d> lights = new ArrayList<Vector3d>();
	
   
	Mesh3d cube = new Mesh3d().setAsCube();
	Mesh3d cube2 = new Mesh3d().setAsCube();
	
	

	   private Point getVoronoi(float x, float y) {
	       Point v = new Point((int)(x / VORONOI_DIST) * VORONOI_DIST, (int)(y / VORONOI_DIST) * VORONOI_DIST);
	       Random random = new Random((v.x + v.y * 500) * 157917);
	       Random random2 = new Random((v.y + v.x * 500) * 157917);
	       v.x += random.nextFloat() * 25;
	       v.y += random2.nextFloat() * 25;
	       return v;
	   }

	   private float getHeight(Point voronoi)
	   {
	       float height = 0;
	       Random random = new Random((voronoi.x + voronoi.y * 500) * 157917);
	       height = random.nextFloat();

	       return (height + 1) / 2;
	   }
	   
	   
	   
	   private VoronoiArea getVoronoiArea(int x, int y) {
		  if (areas[x + y * SIZE] != null) {
			  return areas[x + y * SIZE];
		  }
		  Point[][] voronoi = new Point[3][3];
		  Point closest = new Point(0, 0);
		  double dist = Double.MAX_VALUE;
		  
		  float value = 0;
		  
 		  for (int xx = -1; xx < 2; xx++) {
               for (int yy = -1; yy < 2; yy++) {
                   Point p = getVoronoi(x + xx * VORONOI_DIST, y + yy * VORONOI_DIST);
                   voronoi[xx + 1][yy + 1] = p;
                   double currentDist = p.distance(x, y);
                   if (currentDist < dist) {
                	   dist = currentDist;
                	   closest = p;
                	   value += VoronoiArea.getHeight(p) * Math.min(1 - Math.min(1, currentDist / VORONOI_DIST), 0.5f);
                   }
                   
                   value += VoronoiArea.getHeight(p) * Math.min(1 - Math.min(1, currentDist / VORONOI_DIST), 0.5f);
               }
           }
 		  value /= 9f;
 		  VoronoiArea area = new VoronoiArea();
 		  area.voronoi = voronoi;
 		  area.closest = closest;
 		  area.value = value;
 		  area.X = x / VORONOI_DIST;
 		  area.Y = y / VORONOI_DIST;
 		  areas[x / VORONOI_DIST + (y / VORONOI_DIST) * SIZE] = area;
 		  return area;
	   }
	
	   public Color getColor(int x, int y, int VORONOI_DIST) {
		   Point[][] voronoi = new Point[3][3];
           for (int xx = -1; xx < 2; xx++) {
               for (int yy = -1; yy < 2; yy++) {
                   Point p = getVoronoi(x + xx * VORONOI_DIST, y + yy * VORONOI_DIST);
                   voronoi[xx + 1][yy + 1] = p;
               }
           }
           double dist = Double.MAX_VALUE;
           double[][] otherDist = new double[3][3];
           Point current = new Point(0, 0);
           for (int xx = 0; xx < 3; xx++) {
               for (int yy = 0; yy < 3; yy++) {
                   Point v = voronoi[xx][yy];
                   double distance = v.distance(x, y);
                   if (distance < dist) {
                       current = v;
                       dist = distance;
                   }
                   otherDist[xx][yy] = distance;
               }
           }

           Random random = new Random((int)(getHeight(current) * 255) * 157917);
           Color[][] colors = new Color[3][3];
           double valMul = 1 - Math.min(1, dist / VORONOI_DIST);
           double mul = 0;
           boolean test = true;

           Color c = new Color(0, 0, 0);
           int[] col = new int[3];
           col[0] += random.nextInt(255);
           col[1] += random.nextInt(255);
           col[2] += random.nextInt(255);
           int colorMul = 5;
           c = new Color(Math.min(255, (col[0]) * colorMul), Math.min(255, (col[1]) * colorMul), Math.min(255, (col[2]) * colorMul));

           if (test) {
               mul = 0;
               for (int xx = 0; xx < 3; xx++) {
                   for (int yy = 0; yy < 3; yy++) {
                       mul = Math.min(1 - Math.min(1, otherDist[xx][yy] / VORONOI_DIST), 0.5f);
                       //mul += valMul;
                       //mul /= 2;
                       random = new Random((int)(getHeight(voronoi[xx][yy]) * 255) * 157917);
                       colors[xx][yy] = new Color((int)(random.nextInt(255) * mul), (int)(random.nextInt(255) * mul), (int)(random.nextInt(255) * mul));
                       col[0] += colors[xx][yy].getRed();
                       col[1] += colors[xx][yy].getGreen();
                       col[2] += colors[xx][yy].getBlue();
                   }
               }

               c = new Color(Math.min(255, (col[0] / 9) * colorMul), Math.min(255, (col[1] / 9) * colorMul), Math.min(255, (col[2] / 9) * colorMul));
           }
           return c;
	   }
	   
	   public float getHeightFromColor(Color c) {
		   float height = 0;
		   height += c.getRed() / 255f;
		   height += c.getGreen() / 255f;
		   height += c.getBlue() / 255f;
		   height /= 3;
		   return height * 4;
	   }
	   
	   public float getTerrainHeight(float h) {
		   float height = h + 0;
		   
		   if (height >= 2) {
			   height = 2;
		   }
		   
		   height *= 10;
		   
		   
		   return height;
	   }
	  
	   
//	Mesh3d[][][] cubes = new Mesh3d[SIZE][SIZE][SIZE];
//   boolean[][][] rendered = new boolean[SIZE][SIZE][SIZE];
   
	Polygon3d[] heightmap = new Polygon3d[SIZE * SIZE];
	Color[] colors = new Color[SIZE * SIZE];
	Color[] temp = new Color[SIZE * SIZE];
	Color[] humidity = new Color[SIZE * SIZE];
	VoronoiArea[] areas = new VoronoiArea[SIZE * SIZE];
	{
		Random random = new Random();
      
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				colors[x + y * SIZE] = getColor(x, y, 25);
				temp[x + y * SIZE] = getColor(x, y, 50);
				humidity[x + y * SIZE] = getColor(y, x, 50);
			}
		}
		
      for (int x = 0; x < SIZE - 1; x++) {
    	  for (int y = 0; y < SIZE - 1; y++) {
    		  
//    		  VoronoiArea voronoi = getVoronoiArea(x, y);
//    		  VoronoiArea voronoi2 = getVoronoiArea(x + 1, y);
//    		  VoronoiArea voronoi3 = getVoronoiArea(x, y + 1);
//    		  VoronoiArea voronoi4 = getVoronoiArea(x + 1, y + 1);
    		  float mul = 10;
    		  float height = getHeightFromColor(colors[x + y * SIZE]);
    		  float humidity = getHeightFromColor(this.humidity[x + y * SIZE]);
    		  float temp = getHeightFromColor(this.temp[x + y * SIZE]);
    		  Vector3d B_L = new Vector3d(x, getTerrainHeight(height), y);
    		  Vector3d B_R = new Vector3d(x + 1, getTerrainHeight(getHeightFromColor(colors[x + 1 + y * SIZE])), y);
    		  
    		  Vector3d T_L = new Vector3d(x, getTerrainHeight(getHeightFromColor(colors[x + (y + 1) * SIZE])), y + 1);
    		  Vector3d T_R = new Vector3d(x + 1, getTerrainHeight(getHeightFromColor(colors[x + 1 + (y + 1) * SIZE])), y + 1);
    		  
//    		  System.out.println(B_L.y + ", " + B_R.y + ", " + T_L.y + ", " + T_R.y);
    		  
    		  heightmap[x + y * SIZE] = new Polygon3d(B_L, T_L, T_R, B_R);
    		  
    		  heightmap[x + y * SIZE].color = colors[x + y * SIZE];
    		  int r = 0;
			  int g = 0;
			  int b = 255;
    		  
    		  if (height >= 2) {
    			  
    			  b -= (height - 2) * 100;
//    			  if (b < 125)
//    				  b = 125;
    			  
//    			  System.out.println(b);
    			  
    		  } else {
    			  r = 0;
    			  g = 0;
    			  b = 0;
    			  
    			  
    			  g = 255;
    			  
    			  if (humidity < 1.5f && temp > 2f) {
    				  r = 255;
    				  g = 255;
    				  b = 100;
    			  }
    			  
    			  if (humidity < 1.5f && temp < 1.5f || height <= 1.2f) {
    				  r = 150;
    				  g = 150;
    				  b = 150;
    			  }
    			  
    			  if (height >= 1.9f) {
    				  r = 255;
    				  b = 100;
    			  }
    			  
    			  if (temp >= 2.5f && height <= 1f) {
    				  r = 200;
    				  g = 200;
    				  b = 200;
    			  }
    			  
    			  
    			  b += humidity * 50;
    			  
    			 
    		  }
    		  
    		  r = Math.min(255, Math.max(0, r));
    		  g = Math.min(255, Math.max(0, g));
    		  b = Math.min(255, Math.max(0, b));
    		  
    		  heightmap[x + y * SIZE].color = new Color(r, g, b);
    		  
    		 
    	  }
      }
	}
	
	public void render() {
	  
      if (System.currentTimeMillis() > lastTime + 1000) {
         lastTime = System.currentTimeMillis();
         delta = (desiredFPS / FPS) * 5;
         System.out.println(FPS + ", " + desiredFPS + ", " + delta);
         FPS = 0;
      }
   
      	FPS ++;
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		
		
		
		drawing.clear();
		lights.clear();
		
		lights.add(new Vector3d(cx, cy, cz));
		
		for (int X = (int)cx - RENDER_DIST; X < (int)cx + RENDER_DIST; X++) {
			for (int Z = (int)cz - RENDER_DIST; Z < (int)cz + RENDER_DIST; Z++) {
				if (X >= 0 && Z >= 0 && X < SIZE - 1 && Z < SIZE - 1) {
					drawing.add(heightmap[X + Z * SIZE]);
				}
			}
		}
		
		//draw
      if (drawing.size() > 0) {
         
   		Polygon3d[] list = new Polygon3d[drawing.size()];
   		for (int i = 0; i < list.length; i++) {
   			list[i] = drawing.get(i);
   		}
         try {
   		Arrays.sort(list);
         }catch (Exception e){}
   		int i = 0;
   		for (Polygon3d p : list) {
   			p.render(g, Start.cx, Start.cy, Start.cz, Start.rotH, Start.rotV, i);
   			i++;
   		}
      }
		//end draw
		
		g = frame.getGraphics();
		g.drawImage(image, 0, 0, frame.getWidth(), frame.getHeight(), null);
	}
}
