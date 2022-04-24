package nbodysystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import QuadTree.QuadTree;
import QuadTree.Region;

public class NBodyBackground {
	
	/*	private List<Body> bodyList;
	private Region systemRegion;
	private QuadTree quadtree;
	
	public NBodyBackground(List<Body> body, float x1, float y1, float x2, float y2) {
		bodyList = body;
		systemRegion = new Region(x1, y2, x2, y2);
		quadtree = new QuadTree(systemRegion);
		for(Body b : bodyList) {
			quadtree.addBody(b);
		}
		
	}
	 * 
	 */
	
	private ArrayList<Body> bodyList;
	private double xAccelerationNew;
	private double yAccelerationNew;
	private double xVelocityNew;
	private double yVelocityNew;
	private double secperframe;
	private final double G = (6.6743*(10^11));
	
	public NBodyBackground(ArrayList<Body> bodyList, double secperframe) {
		this.bodyList = bodyList;
		this.secperframe = secperframe;
	}
	
	private Body getBody(int spot) {
		return bodyList.get(spot);
	}
	
	private double getBodyX(int spot) {
		return bodyList.get(spot).getX();
	}
	
	private double getBodyY(int spot) {
		return bodyList.get(spot).getY();
	}
	
	private double getBodyMass(int spot) {
		return bodyList.get(spot).getMass();
	}
	
	public void addtoList(Body body) {
		bodyList.add(body);
		//System.out.println(bodyList.toString());
		
	}
	
	public void calculateNextPosition() {
		if(bodyList.size() > 1) {
			for(Body b : bodyList) {
				xAccelerationNew = 0;
				yAccelerationNew = 0;
				xVelocityNew = 0;
				yVelocityNew = 0;
				for(Body z : bodyList) {
					if(z.equals(b)) {
						bodyList.get(bodyList.indexOf(z) + 1); //skips to next neighbor;
					}
					else {
						//xAccelerationNew = (-G*z.getMass())*(((b.getX() - z.getX()))/(Math.pow((Math.abs((b.getX() - z.getX()))),2)));
						xAccelerationNew = (-G*z.getMass())*(((b.getX() - z.getX()))/(Math.abs(Math.pow(b.getX() - z.getX(), 2) + Math.pow(b.getY() - z.getY(), 2))));
						System.out.println(xAccelerationNew);
						yAccelerationNew = (-G*z.getMass())*(((b.getY() - z.getY()))/(Math.abs(Math.pow(b.getX() - z.getX(), 2) + Math.pow(b.getY() - z.getY(), 2))));
						System.out.println(yAccelerationNew);
						xVelocityNew = xAccelerationNew*secperframe;
						System.out.println(xVelocityNew);
						yVelocityNew = xAccelerationNew*secperframe;
						System.out.println(yVelocityNew);
						b.setXVelocity(xVelocityNew);
						b.setYVelocity(yVelocityNew);
						b.setX(b.getX() + b.getXVelocity());
						b.setY(b.getY() + b.getYVelocity());
						System.out.println(b.getX() + " " + b.getY());
					}
				}
				
			}
			
		}
		
	}
	
	public void debugCalculuate() {
		for(Body b : bodyList) {
			b.setX(b.getX() + 0.5);
		}
	}
	
	/*		for(int i = 0; i < bodyList.size(); i++) {
			System.out.println(getBodyX(i) + " " + getBodyY(i));
			xAccelerationNew = 0;
			yAccelerationNew = 0;
			xVelocityNew = 0;
			yVelocityNew = 0;
			for(int j = 0; j < bodyList.size(); j++){
				if(j == bodyList.indexOf(j)) {
					j++;
				}
				else {
					xAccelerationNew += -G*getBodyMass(j)*((getBodyX(i) - getBodyX(j))/(Math.pow((Math.abs((getBodyX(i) - getBodyX(j)))),2)));
					System.out.println("xAccelerationNew for " + i + ":" + xAccelerationNew);
					yAccelerationNew += -G*getBodyMass(j)*((getBodyY(i) - getBodyY(j))/(Math.pow((Math.abs((getBodyY(i) - getBodyY(j)))),2)));
					System.out.println("yAccelerationNew for " + i + ":" + yAccelerationNew);
					xVelocityNew = xAccelerationNew*secperframe;
					System.out.println("xVelocityNew for " + i + ":" + xVelocityNew);
					yVelocityNew = xAccelerationNew*secperframe;
					System.out.println("yVelocityNew for " + i + ":" + yVelocityNew);
					getBody(i).setXVelocity(xVelocityNew);
					getBody(i).setYVelocity(yVelocityNew);
					getBody(i).setX(getBodyX(i) + xVelocityNew);
					getBody(i).setY(getBodyY(i) + yVelocityNew);
					System.out.println(getBodyX(i) + " " + getBodyY(i));
				}
			}
		}
	}
	 * 
	 */

}
