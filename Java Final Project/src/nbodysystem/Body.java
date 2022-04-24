package nbodysystem;

import java.util.List;

public class Body {
	
	private double x;
	private double y;
	private double radius;
	private double xVelocity;
	private double yVelocity;
	private double mass;
	
	public Body(double x, double y, double xVelocity, double yVelocity, double mass) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.mass = mass;
		radius = mass;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
		radius = mass;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getXVelocity() {
		return xVelocity;
	}
	
	public double getYVelocity() {
		return yVelocity;
	}
	
	public double getMass() {
		return mass;
	}
	

}
