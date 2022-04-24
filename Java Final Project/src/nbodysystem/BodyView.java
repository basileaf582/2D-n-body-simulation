package nbodysystem;

import java.util.List;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BodyView extends Parent {
	
	private Body body;
	private Circle c;
	
	public BodyView(Body body) {
		this.body = body;
		c = new Circle();
		c.setRadius(body.getMass());
		c.setFill(Color.BLACK);
		c.setStroke(Color.BLACK);
        getChildren().add(c);
	}
	
    public void update() {
    	c.setRadius(body.getMass());
        c.setTranslateX(body.getX());
        c.setTranslateY(body.getY());
    }
    
    public void debugUpdate() {
    	
    }

}
