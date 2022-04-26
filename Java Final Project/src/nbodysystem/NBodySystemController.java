package nbodysystem;

import java.util.ArrayList;

import nbodysystem.Body;
import nbodysystem.BodyView;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class NBodySystemController {
	
	@FXML
	private Pane pane;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private Slider massSlider;
	
	@FXML
	private Slider speedXSlider;
	
	@FXML
	private Slider speedYSlider;
	
	@FXML
	private Button start;
	
	@FXML
	private Button stop;
	
	@FXML
	private Button reset;

	private ArrayList<Body> bodies;
	private ArrayList<BodyView> bodiesview;
	private NBodyBackground background;
	private GraphicsContext context;
	private double changeSize;
	private double changeVSpeed;
	private double changeHSpeed;

	private Movement clock;
	
	private class Movement extends AnimationTimer {

		private long FRAMES_PER_SEC = 50L;
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
		context = canvas.getGraphicsContext2D();
		bodies = new ArrayList<Body>();
		bodiesview = new ArrayList<BodyView>();
		background = new NBodyBackground(bodies, 0.1);
		massSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeSize = massSlider.getValue();
			}
			
		});
		speedXSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeVSpeed = speedXSlider.getValue();
			}
			
		});
		speedYSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeHSpeed = speedYSlider.getValue();
			}
			
		});
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
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
		start.setText("Start");
	}
	
	@FXML
	public void pressed(MouseEvent event){
		makeBodies(event);
		//event.consume();
	}
	
	@FXML
	public void makeBodies(MouseEvent event) {
		int r = (int) (Math.random()*255);
		int g = (int) (Math.random()*255);
		int b = (int) (Math.random()*255);
		Body n = new Body(event.getX(), event.getY(), changeVSpeed, changeHSpeed, changeSize, Color.rgb(r, g, b)); //last num changes with scale, implement later!
		System.out.println(n.toString());
		background.addtoList(n);
		BodyView z = new BodyView(n);
		bodiesview.add(z);
		pane.getChildren().add(z);
		updateViews();
	}
	
	public void updateViews() {
		for (BodyView g : bodiesview) {
			g.update(context);
			//System.out.println(bodies.toString());
			//System.out.println(bodiesview.toString());
		}
	}
}
