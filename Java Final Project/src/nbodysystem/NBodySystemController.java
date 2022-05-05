package nbodysystem;


import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class NBodySystemController {
	
	@FXML
	private BorderPane borderpane;
	
	@FXML
	private Pane pane;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private Slider sizeSlider;
	
	@FXML
	private Slider massSlider;
	
	@FXML
	private Slider speedXSlider;
	
	@FXML
	private Slider speedYSlider;
	
	@FXML
	private CheckBox enableBodyCollision;
	
	@FXML
	private CheckBox enableWallCollision;
	
	@FXML
	private Button start;
	
	private Stack<Body> bodies;
	private Stack<BodyView> bodiesview;
	private NBodyBackground background;
	private GraphicsContext context;
	private double changeSize;
	private double changeMass;
	private double changeVSpeed;
	private double changeHSpeed;
	private final double secsperframe = 0.00714285714; //reciprocal of frames per second

	private Movement clock;
	
	private class Movement extends AnimationTimer {

		private long FRAMES_PER_SEC = 140L;
		private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

		private long last = 0;
		private Stack<Body> bodies;
		private NBodyBackground background;
		
		public void setBodies(Stack<Body> bodies) {
			this.bodies = bodies;
		}
		
		public void setBackground(NBodyBackground background) {
			this.background = background;
		}

		@Override
		public void handle(long now) {
			if (now - last > INTERVAL) {
				background.calculateNextPosition();
				updateViews();
				last = now;
			}
		}
	}
	
	@FXML
	public void initialize() {//adjusts screen resolution for each machine and initializes slider objects, the canvas object, stacks of type body and bodyview, the background object, and the clock object
		double width = Screen.getPrimary().getBounds().getWidth();
		double height = Screen.getPrimary().getBounds().getHeight() - 100; //https://stackoverflow.com/questions/40951184/javafx-screen-getprimary-getbounds-returns-incorrect-screen-size
	    borderpane.setMaxSize(width, height);
		borderpane.setPrefSize(width, height);
		borderpane.setMinSize(width, height);
		pane.setMaxSize(width, height - 60); //adjusted for optimal viewing
		pane.setPrefSize(width, height - 60);
		pane.setMinSize(width, height - 60);
		canvas.setHeight(height - 60);
		canvas.setWidth(width);
		context = canvas.getGraphicsContext2D();
		bodies = new Stack<Body>();
		bodiesview = new Stack<BodyView>();
		background = new NBodyBackground(bodies, secsperframe, pane.getPrefWidth(), pane.getPrefHeight(), false, false); //0.01666666666, 
		massSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeMass = massSlider.getValue();
			}
			
		});
		sizeSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeSize = sizeSlider.getValue();
			}
			
		});
		speedXSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeHSpeed = speedXSlider.getValue();
			}
			
		});
		speedYSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeVSpeed = -speedYSlider.getValue();
			}
			
		});
		changeMass = 7; //default mass and size of each ball
		changeSize = 7;
		clock = new Movement();
		clock.setBodies(bodies);
		clock.setBackground(background);
		pane.setOnMousePressed(event -> pressed(event)); //maps the pane to the makeBody method via the pressed method
		
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
		bodies.clear(); //resets initial body pool i.e., prevents "ghost" bodies
		bodiesview.clear();
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
		start.setText("Start");
	}
	
	@FXML
	public void checkBodyBox() {
		background.toggleBodyCollision();
	}
	
	@FXML
	public void checkWallBox() {
		background.toggleWallCollision();
	}
	
	@FXML
	public void removeMostRecent() {//interacts with body stack in background class (different from body stack in controller class if more bodies are added or removed after the simulation starts)
		background.popFromStack();
		pane.getChildren().remove(bodiesview.pop());
	}
	
	@FXML
	public void pressed(MouseEvent event){
		makeBodies(event);
	}
	
	@FXML
	public void makeBodies(MouseEvent event) {//assigns each body a random colored trail and designated mass, radius, and velocities
		int r = (int) (Math.random()*255);
		int g = (int) (Math.random()*255);
		int b = (int) (Math.random()*255);
		Body n = new Body(event.getX(), event.getY(), changeHSpeed, changeVSpeed, changeMass, changeSize, Color.rgb(r, g, b)); 
		background.pushToStack(n);
		BodyView z = new BodyView(n);
		bodiesview.push(z);
		pane.getChildren().add(z);
		updateViews();
	}
	
	public void updateViews() {//responsible for updating the GUI per frame
		for (BodyView g : bodiesview) {
			g.update(context);
		}
	}
}
