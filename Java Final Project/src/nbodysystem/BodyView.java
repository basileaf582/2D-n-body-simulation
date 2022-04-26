package nbodysystem;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BodyView extends Parent {
	
	private Body body;
	private Circle c;
	private Circle tracepoint;
	
	public BodyView(Body body) {
		this.body = body;
		c = new Circle();
		c.setRadius(body.getMass());
		c.setFill(Color.BLACK);
		c.setStroke(Color.BLACK);
        getChildren().add(c);
	}
	
	public void makeTrace(Body b, GraphicsContext context) {
		context.setFill(b.getTrailColor());
		context.fillOval(b.getX() - 1, b.getY() - 1, 3, 3);
	//	context.fillArc(b.getX(), b.getY(), 200.0, 200.0, 90.0, Math.PI*4*200, null);
	//	context.arc(b.getX(), b.getY(), 200.0, 200.0, 90.0, Math.PI*4*200);
	////	tracepoint = new Circle();
	//	tracepoint.setRadius(1);
	//	tracepoint.setFill(b.getTrailColor());
	//	tracepoint.setStroke(b.getTrailColor());
	//	tracepoint.setTranslateX(b.getX());
	//	tracepoint.setTranslateY(b.getY());
	//	getChildren().add(tracepoint);
		
		
	}
	
    public void update(GraphicsContext context ) {
    	makeTrace(body, context);
    	c.setRadius(body.getMass());
        c.setTranslateX(body.getX());
        c.setTranslateY(body.getY());
    }
    
    public void debugUpdate() {
    	
    }

}
