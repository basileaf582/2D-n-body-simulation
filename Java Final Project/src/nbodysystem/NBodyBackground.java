package nbodysystem;

import java.util.Stack;

public class NBodyBackground {
	

	private Stack<Body> bodyStack;
	private double screenWidth;
	private double screenHeight;
	private double xAccelerationNew;
	private double yAccelerationNew;
	private double xVelocityNew;
	private double yVelocityNew;
	private double secperframe;
	private boolean isTouching;
	private boolean canCollideWithBody;
	private boolean canCollideWithWall;
	private final double G = (6.6743*(10^11)); //gravitational constant
	
	public NBodyBackground(Stack<Body> bodyStack, double secperframe, double screenWidth, double screenHeight, boolean canCollideWithBody, boolean canCollideWithWall) {
		this.bodyStack = bodyStack;
		this.secperframe = secperframe;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.canCollideWithBody = canCollideWithBody;
		this.canCollideWithWall = canCollideWithWall;
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

	private double getBodyXVelocity(int spot) {
		return bodyStack.get(spot).getXVelocity();
	}
	
	private double getBodyYVelocity(int spot) {
		return bodyStack.get(spot).getYVelocity();
	}
	
	private double distanceBetween(Body b1, Body b2) {
		return Math.sqrt(Math.pow(Math.abs(b1.getX()) - b2.getX(), 2) + Math.pow(Math.abs(b1.getY() - b2.getY()), 2));
	}
	
	private Boolean isTouching(Body b1, Body b2) {
		if(distanceBetween(b1,b2) <= (b1.getRadius() + b2.getRadius())) {
			isTouching = true;
		}
		else {
			isTouching = false;
		}
		return isTouching;
	}
	
	public void pushToStack(Body body) {
		bodyStack.push(body);
	}
	
	public void popFromStack() {
		if(bodyStack.size() > 0) {
			bodyStack.pop();
		}
	}
	
	public void toggleBodyCollision() {
		canCollideWithBody = !canCollideWithBody;
	}
	
	public void toggleWallCollision() {
		canCollideWithWall = !canCollideWithWall;
	}
	
	
	public void calculateNextPosition() { //calculates gravitational force for each body per frame
		if(bodyStack.size() > 1) {
			for(Body b : bodyStack) {
				xAccelerationNew = 0;
				yAccelerationNew = 0;
				xVelocityNew = b.getXVelocity();
				yVelocityNew = b.getYVelocity();
				for(Body z : bodyStack) {
					if(z.equals(b)) {//skips gravitational force calculation if the body is itself
						continue; //https://stackoverflow.com/questions/11160952/goto-next-iteration-in-for-loop-in-java#:~:text=If%20you%20want%20to%20skip,(another%20question%20about%20label).
					}
					else if(isTouching(b,z)) {//checks to see whether two bodies are touching
						if(canCollideWithBody) {
							reflectX(b);
							reflectY(b);
						}
					}
					else {//calculates the net gravitational acceleration for body b based on every other body in the list
						xAccelerationNew += (-G*z.getMass())*(((b.getX() - z.getX()))/Math.pow(distanceBetween(b,z), 2));
						yAccelerationNew += (-G*z.getMass())*(((b.getY() - z.getY()))/Math.pow(distanceBetween(b,z), 2));
						
				}
			}//calculates final velocity and position for the frame
				xVelocityNew += xAccelerationNew*secperframe;
				yVelocityNew += yAccelerationNew*secperframe;
				b.setXVelocity(xVelocityNew);
				b.setYVelocity(yVelocityNew);
				if(canCollideWithWall) {//testing after forces have been calculated prevents bodies becoming stuck to the wall.
					OutofBoundsCollisionTest(b);
				}
				else {
					OutofBoundsLoopTest(b);
				}
				b.setX(b.getX() + b.getXVelocity());
				b.setY(b.getY() + b.getYVelocity());
		}
		}
		else if(bodyStack.size() == 1) { 
			if(canCollideWithWall) {
				OutofBoundsCollisionTest(getBody(0));
			}
			else {
				OutofBoundsLoopTest(getBody(0));
			}
			getBody(0).setX(getBodyX(0) + getBodyXVelocity(0));
			getBody(0).setY(getBodyY(0) + getBodyYVelocity(0));
		}
	}
	

	private void OutofBoundsLoopTest(Body b) { 
		if((b.getX() > screenWidth | (b.getX() < 0))) {
			translateX(b);
		}
		if((b.getY()) > screenHeight | (b.getY()) < 0) {
			translateY(b);
		}
		
	
	}
	
	private void OutofBoundsCollisionTest(Body b) {
		if((b.getX() + b.getRadius()) >= screenWidth | (b.getX() - b.getRadius()) <= 10) {
			reflectX(b);
		}
		if((b.getY() + b.getRadius()) >= screenHeight | (b.getY() - b.getRadius()) <= 10) {
			reflectY(b);
		}
		
	
	}
	
	private void reflectX(Body b) { //perfect elastic collision with wall
		xVelocityNew = -b.getXVelocity();
		b.setXVelocity(xVelocityNew);
		
	}
	
	private void reflectY(Body b) {
		yVelocityNew = -b.getYVelocity();
		b.setYVelocity(yVelocityNew);
	}
	
	private void translateX(Body b) {
		if((b.getX()) > screenWidth){
			b.setX(0);
		}
		else if((b.getX()) < 0) {
			b.setX(screenWidth);
		}
	}
	
	private void translateY(Body b) {
		if((b.getY()) > screenHeight) {
			b.setY(0);
		}
		else if((b.getY()) < 0) {
			b.setY(screenHeight);
		}
	}
	
	

}
