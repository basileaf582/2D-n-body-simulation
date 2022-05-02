package nbodysystem;

import java.util.ArrayList;
import java.util.Stack;
//import QuadTree.QuadTree;
//import QuadTree.Region;

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
	
	private Stack<Body> bodyStack;
	private double screenWidth;
	private double screenHeight;
	private double xAccelerationNew;
	private double yAccelerationNew;
	private double xVelocityNew;
	private double yVelocityNew;
	private double secperframe;
	private boolean isTouching;
	private final double G = (6.6743*(10^11));
	
	public NBodyBackground(Stack<Body> bodyStack, double secperframe, double screenWidth, double screenHeight) {
		this.bodyStack = bodyStack;
		this.secperframe = secperframe;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	private Body getBody(int spot) {
		return bodyStack.get(spot);
	}
	
	private double getBodyX(int spot) {
		return bodyStack.get(spot).getX();
	}
	
	private double getBodyY(int spot) {
		return bodyStack.get(spot).getY();
	}
	
	private double getBodyMass(int spot) {
		return bodyStack.get(spot).getMass();
	}
	
	private double getBodyXVelocity(int spot) {
		return bodyStack.get(spot).getXVelocity();
	}
	
	private double getBodyYVelocity(int spot) {
		return bodyStack.get(spot).getYVelocity();
	}
	
	private double distanceBetween(Body b1, Body b2) {
		return Math.sqrt(Math.pow(Math.abs(b1.getX()) - b2.getX(), 2) + Math.pow(Math.abs(b1.getY() - b2.getY()), 2));
		//return Math.abs(Math.pow(b1.getX() - b2.getX(),2) + Math.pow(b1.getY() - b2.getY(), 2));
	}
	
	public Boolean isTouching(Body b1, Body b2) {
		if(distanceBetween(b1,b2) <= (b1.getRadius() + b2.getRadius())) {
			isTouching = true;
		}
		else {
			isTouching = false;
		}
		return isTouching;
	}
	
	public void registerCollision(Body b1, Body b2) {  //https://gamedev.stackexchange.com/questions/20516/ball-collisions-sticking-together/20525#20525 experimental
		double xDistance = Math.abs(b1.getX() - b2.getX());
		double yDistance = Math.abs(b1.getY() - b2.getY());
        double dotProduct = xDistance*(b2.getXVelocity() - b1.getXVelocity()) + yDistance*(b2.getYVelocity() - b1.getYVelocity());
        if(dotProduct > 0){
            double collisionScale = dotProduct / distanceBetween(b1,b2);
            double xCollision = xDistance * collisionScale;
            double yCollision = yDistance * collisionScale;
            double combinedMass = b1.getMass() + b2.getMass();
            double collisionWeightB1 = 2 * b2.getMass() / combinedMass;
            double collisionWeightB2 = 2 * b1.getMass() / combinedMass;
            b1.setXVelocity(b1.getXVelocity() + (collisionWeightB1 * xCollision));
            b1.setYVelocity(b1.getYVelocity() + (collisionWeightB1 * yCollision));
            b2.setXVelocity(b2.getXVelocity() + (collisionWeightB2 * xCollision));
            b2.setYVelocity(b2.getYVelocity() + (collisionWeightB2 * yCollision));
            b1.setX(b1.getX() + b1.getXVelocity());
            b1.setY(b1.getY() + b1.getYVelocity());
            b2.setX(b2.getX() - b2.getXVelocity());
            b2.setY(b2.getY() - b2.getYVelocity());
            
        }
    }
	/*xVelocityNew = ((b1.getMass() - b2.getMass())/(b1.getMass() + b2.getMass()))*b1.getXVelocity() + ((2*b2.getMass())/(b1.getMass() + b2.getMass()))*b2.getXVelocity();
		yVelocityNew = ((b1.getMass() - b2.getMass())/(b1.getMass() + b2.getMass()))*b1.getYVelocity() + ((2*b2.getMass())/(b1.getMass() + b2.getMass()))*b2.getYVelocity(); //derived from the conservation of momentum and kinetic energy principles;
		//xVelocityNew = (-1*b1.getXVelocity());
		//yVelocityNew = (-1*b1.getYVelocity());
		b1.setXVelocity(xVelocityNew);
		b1.setYVelocity(yVelocityNew);
		b1.setX(b1.getX() + xVelocityNew);
		b1.setY(b1.getY() + yVelocityNew);
		
		xVelocityNew = ((b2.getMass() - b1.getMass())/(b1.getMass() + b2.getMass()))*b2.getXVelocity() + ((2*b1.getMass())/(b1.getMass() + b2.getMass()))*b1.getXVelocity();
		yVelocityNew = ((b2.getMass() - b1.getMass())/(b1.getMass() + b2.getMass()))*b2.getYVelocity() + ((2*b1.getMass())/(b1.getMass() + b2.getMass()))*b1.getYVelocity(); //derived from the conservation of momentum and kinetic energy principles;
	//	xVelocityNew = (-1*b2.getXVelocity());
	//	yVelocityNew = (-1*b2.getXVelocity());
		b2.setXVelocity(xVelocityNew);
		b2.setYVelocity(yVelocityNew);
		b2.setX(b2.getX() + xVelocityNew);
		b2.setY(b2.getY() + yVelocityNew);
	 * 
	 */
	
	public void pushToStack(Body body) {
		bodyStack.push(body);
		//bodyStack.add(body);
		//System.out.println(bodyList.toString());
		
	}
	
	public void popFromStack() {
		if(bodyStack.size() > 0) {
			bodyStack.pop();
		}
	}
	
	public int returnStackSize() {
		return bodyStack.size();
	}
	
	public void calculateNextPosition() {
		if(bodyStack.size() > 1) {
			for(Body b : bodyStack) {
				xAccelerationNew = 0;
				yAccelerationNew = 0;
				xVelocityNew = b.getXVelocity();
				yVelocityNew = b.getYVelocity();
				OutofBoundsTest(b);
				for(Body z : bodyStack) {
					if(z.equals(b)) {
						continue; //https://stackoverflow.com/questions/11160952/goto-next-iteration-in-for-loop-in-java#:~:text=If%20you%20want%20to%20skip,(another%20question%20about%20label).
					}
					else if(isTouching(b,z)) {
						//registerCollision(b,z); still buggy, fix later
						b.setX(b.getX() + b.getXVelocity());
						b.setY(b.getY() + b.getYVelocity());
					//	z.setX(z.getX() + z.getXVelocity());
					//	z.setY(z.getY() + z.getYVelocity());

					//	continue;
					}
					else {
						xAccelerationNew += (-G*z.getMass())*(((b.getX() - z.getX()))/Math.pow(distanceBetween(b,z), 2));
						yAccelerationNew += (-G*z.getMass())*(((b.getY() - z.getY()))/Math.pow(distanceBetween(b,z), 2));
						xVelocityNew += xAccelerationNew*secperframe;
						yVelocityNew += yAccelerationNew*secperframe;
						b.setXVelocity(xVelocityNew);
						b.setYVelocity(yVelocityNew);
						b.setX(b.getX() + b.getXVelocity());
						b.setY(b.getY() + b.getYVelocity());
				}
			}
		}
		}
		else if(bodyStack.size() == 1) {
			OutofBoundsTest(getBody(0));
			getBody(0).setX(getBodyX(0) + getBodyXVelocity(0));
			getBody(0).setY(getBodyY(0) + getBodyYVelocity(0));
		}
	}
	

	public void OutofBoundsTest(Body b) {
		if((b.getX() + b.getRadius()) >= screenWidth | (b.getX() - b.getRadius()) <= 10) {
			reflectX(b);
			b.setX(b.getX() + b.getXVelocity());
		}
		if((b.getY() + b.getRadius()) >= screenHeight | (b.getY() - b.getRadius()) <= 10) {
			reflectY(b);
			b.setY(b.getY() + b.getYVelocity());
		}
		
	
	}
	
	/*	public void OutofBoundsTest(Body b) { //unleashes the physics demons
		double overX = Math.max(0, (b.getX() + b.getRadius() - 1900));
		overX = Math.max(overX, (b.getX() - b.getRadius() + 1900));
		if(overX > 0) {
			reflectX(b);
			b.setX(b.getX() + Math.signum(b.getXVelocity())*overX);
			
		}
		double overY = Math.max(0, (b.getY() + b.getRadius() - 700));
		overY = Math.max(overY, (b.getY() - b.getRadius() + 700));
		if(overY > 0) {
			reflectY(b);
			b.setY(b.getY() + Math.signum(b.getYVelocity())*overY);
		}
	 * 
	 */
	
	public void reflectX(Body b) {
		xVelocityNew = (-1*b.getXVelocity())/1.2;
		b.setXVelocity(xVelocityNew);
		
	}
	
	public void reflectY(Body b) {
		yVelocityNew = (-1*b.getYVelocity())/1.2;
		b.setYVelocity(yVelocityNew);
	}
	
	/*	public Boolean isXOutOfBounds(Body b) {
		double overX = Math.max(0, (b.getX() + b.getRadius()) - 1875);
		overX = Math.max(overX, b.getRadius() - b.getX());
		if(overX > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean isYOutOfBounds(Body b) {
		double overY = Math.max(0, b.getY() + b.getRadius() - 900);
		overY = Math.max(overY, b.getRadius() - b.getY());
		if(overY > 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void adjustOutOfBounds(Body b) {
		if(isXOutOfBounds(b)) {
			xVelocityNew = (-1*(b.getXVelocity()))/2;
			//xAccelerationNew = b.getXVelocity();
			//xVelocityNew = b.getXVelocity() + xAccelerationNew*secperframe;
			b.setXVelocity(xVelocityNew);
			b.setX(b.getX() + b.getXVelocity());
		}
		if(isYOutOfBounds(b)) {
			yVelocityNew = (-1*(b.getYVelocity()))/2;
			//yAccelerationNew = b.getYVelocity();
			//yVelocityNew = b.getYVelocity() + yAccelerationNew*secperframe;
			b.setYVelocity(yVelocityNew);
			b.setY(b.getY() + b.getYVelocity());
		}
		
	}
	 * 
	 */
	

			
	
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
