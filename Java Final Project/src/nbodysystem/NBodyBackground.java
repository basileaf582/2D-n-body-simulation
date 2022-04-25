package nbodysystem;

import java.util.ArrayList;
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
	
	private double getBodyXVelocity(int spot) {
		return bodyList.get(spot).getXVelocity();
	}
	
	private double getBodyYVelocity(int spot) {
		return bodyList.get(spot).getYVelocity();
	}
	
	public void addtoList(Body body) {
		bodyList.add(body);
		//System.out.println(bodyList.toString());
		
	}
	
	public int returnListSize() {
		return bodyList.size();
	}
	
	public void calculateNextPosition() {
		if(bodyList.size() > 1) {
			for(Body b : bodyList) {
				xAccelerationNew = 0;
				yAccelerationNew = 0;
				xVelocityNew = b.getXVelocity();
				yVelocityNew = b.getYVelocity();
				for(Body z : bodyList) {
					if(z.equals(b)) {
						continue; //https://stackoverflow.com/questions/11160952/goto-next-iteration-in-for-loop-in-java#:~:text=If%20you%20want%20to%20skip,(another%20question%20about%20label).
						//z = bodyList.get((bodyList.indexOf(z) + 1) % bodyList.size()); //skips to next neighbor;
					}
						//xAccelerationNew = (-G*z.getMass())*(((b.getX() - z.getX()))/(Math.pow((Math.abs((b.getX() - z.getX()))),2)));
						xAccelerationNew = (-G*z.getMass())*(((b.getX() - z.getX()))/(Math.abs(Math.pow(b.getX() - z.getX(), 2) + Math.pow(b.getY() - z.getY(), 2))));
						System.out.println(xAccelerationNew);
						yAccelerationNew = (-G*z.getMass())*(((b.getY() - z.getY()))/(Math.abs(Math.pow(b.getX() - z.getX(), 2) + Math.pow(b.getY() - z.getY(), 2))));
						System.out.println(yAccelerationNew);
						xVelocityNew = b.getXVelocity() + xAccelerationNew*secperframe;
						System.out.println(xVelocityNew);
						yVelocityNew = b.getYVelocity() + yAccelerationNew*secperframe;
						System.out.println(yVelocityNew);
						b.setXVelocity(xVelocityNew);
						b.setYVelocity(yVelocityNew);
						b.setX(b.getX() + b.getXVelocity());
						b.setY(b.getY() + b.getYVelocity());
						System.out.println(b.getX() + " " + b.getY());
				}
			}
		}
		else if(bodyList.size() == 1) {
			getBody(0).setX(getBodyX(0) + getBodyXVelocity(0));
			System.out.println(getBodyXVelocity(0));
			getBody(0).setY(getBodyY(0) + getBodyYVelocity(0));
			System.out.println(getBodyYVelocity(0));
		}
	}
			
	
	/*	public void calculateNextPosition() {
		if(bodyArray.length > 1) {
			for(int i = 0; i < bodyArray.length + 1; i++) {
				xAccelerationNew = 0;
				yAccelerationNew = 0;
				xVelocityNew += bodyArray[i].getXVelocity();
				yVelocityNew += bodyArray[i].getYVelocity();
				for(int j = 0; j < bodyArray.length + 1; j++) {
					if(bodyArray[j].equals(bodyArray[i])) {
						j = ((j+1) % bodyArray.length + 1);
					}
					//xAccelerationNew = (-G*z.getMass())*(((b.getX() - z.getX()))/(Math.pow((Math.abs((b.getX() - z.getX()))),2)));
					xAccelerationNew = (-G*bodyArray[j].getMass())*(((bodyArray[i].getX() - bodyArray[j].getX()))/(Math.abs(Math.pow(bodyArray[i].getX() - bodyArray[j].getX(), 2) + Math.pow(bodyArray[i].getY() - bodyArray[j].getY(), 2))));
					System.out.println(xAccelerationNew);
					yAccelerationNew = (-G*bodyArray[j].getMass())*(((bodyArray[i].getY() - bodyArray[j].getY()))/(Math.abs(Math.pow(bodyArray[i].getX() - bodyArray[j].getX(), 2) + Math.pow(bodyArray[i].getY() - bodyArray[j].getY(), 2))));
					System.out.println(yAccelerationNew);
					xVelocityNew = xAccelerationNew*secperframe;
					System.out.println(xVelocityNew);
					yVelocityNew = xAccelerationNew*secperframe;
					System.out.println(yVelocityNew);
					bodyArray[i].setXVelocity(xVelocityNew);
					bodyArray[i].setYVelocity(yVelocityNew);
					bodyArray[i].setX(bodyArray[i].getX() + bodyArray[i].getXVelocity());
					bodyArray[i].setY(bodyArray[i].getY() + bodyArray[i].getYVelocity());
					System.out.println(bodyArray[i].getX() + " " + bodyArray[i].getY());
					
				}
			}
		}
		else {
			
		}
	}
	 * 
	 */
	
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
