package nbodysystem;

import java.util.ArrayList;

import nbodysystem.Body;
import nbodysystem.BodyView;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class NBodySystemController {
	
	@FXML
	private Pane pane;
	
	@FXML
	private Button start;
	
	@FXML
	private Button stop;
	
	@FXML
	private Button reset;

	private ArrayList<Body> bodies;
	private ArrayList<BodyView> bodiesview;
	private NBodyBackground background;

	private Movement clock;
	
	private class Movement extends AnimationTimer {

		private long FRAMES_PER_SEC = 25L;
		private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

		private long last = 0;
		private ArrayList<Body> bodies;
		private NBodyBackground background;
		
		public void setBodies(ArrayList<Body> bodies) {
			this.bodies = bodies;
		}
		
		public void setBackground(NBodyBackground background) {
			this.background = background;
		}

		@Override
		public void handle(long now) {
			if (now - last > INTERVAL) {
				background.calculateNextPosition();
			//	background.debugCalculuate(); //works - the problem is with calculateNextPosition;
				updateViews();
				last = now;
			}
		}
	}
	
	@FXML
	public void initialize() {
		bodies = new ArrayList<Body>();
		bodiesview = new ArrayList<BodyView>();
		background = new NBodyBackground(bodies, 1);
		clock = new Movement();
		clock.setBodies(bodies);
		clock.setBackground(background);
		pane.setOnMousePressed(event -> pressed(event));
		//start.setOnAction(event -> start());
		//stop.setOnAction(event -> stop());
		//reset.setOnAction(event -> reset());
		//resume.setOnAction(event -> resume());
		
		
	}
	
	@FXML
	public void start() {
		clock.start();
	}
	
	@FXML
	public void stop() {
		clock.stop();
		start.setText("Resume");
	}
	
	@FXML
	public void reset() {
		for(BodyView g: bodiesview) {
			pane.getChildren().remove(g);
		}
		bodies.clear();
		bodiesview.clear();
		start.setText("Start");
	}
	
	@FXML
	public void pressed(MouseEvent event){
		makeBodies(event);
		//event.consume();
	}
	
	@FXML
	public void makeBodies(MouseEvent event) {
		System.out.println(background.returnListSize());
		Body b = new Body(event.getX(), event.getY(), 0, 0, 10); //last num changes with scale, implement later!
		System.out.println(background.returnListSize());
	//	bodies.add(b);
		System.out.println(background.returnListSize());
		background.addtoList(b);
		System.out.println(background.returnListSize());
		BodyView g = new BodyView(b);
		bodiesview.add(g);
		pane.getChildren().add(g);
		System.out.println(b.getX() + " " + b.getY());
		System.out.println(background.returnListSize());
		updateViews();
	}
	
	public void updateViews() {
		for (BodyView g : bodiesview) {
			g.update();
			//System.out.println(bodies.toString());
			//System.out.println(bodiesview.toString());
		}
	}
}
