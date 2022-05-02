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
	private Slider massSlider;
	
	@FXML
	private Slider speedXSlider;
	
	@FXML
	private Slider speedYSlider;
	
	@FXML
	private Button start;
	
	private Stack<Body> bodies;
	private Stack<BodyView> bodiesview;
	private NBodyBackground background;
	private GraphicsContext context;
	private double changeSize;
	private double changeVSpeed;
	private double changeHSpeed;

	private Movement clock;
	
	private class Movement extends AnimationTimer {

		private long FRAMES_PER_SEC = 60L;
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
	public void initialize() {
		double width = Screen.getPrimary().getBounds().getWidth();
		double height = Screen.getPrimary().getBounds().getHeight() - 100; //https://stackoverflow.com/questions/40951184/javafx-screen-getprimary-getbounds-returns-incorrect-screen-size
	    borderpane.setMaxSize(width, height);
		borderpane.setPrefSize(width, height);
		borderpane.setMinSize(width, height);
		pane.setMaxSize(width, height - 80);
		pane.setPrefSize(width, height - 80);
		pane.setMinSize(width, height - 80);
		canvas.setHeight(height - 80);
		canvas.setWidth(width);
		context = canvas.getGraphicsContext2D();
		bodies = new Stack<Body>();
		bodiesview = new Stack<BodyView>();
		background = new NBodyBackground(bodies, 0.01666666666, pane.getPrefWidth(), pane.getPrefHeight()); //0.01666666666
		massSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number t1, Number t2) {
				changeSize = massSlider.getValue();
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
		clock = new Movement();
		clock.setBodies(bodies);
		clock.setBackground(background);
		pane.setOnMousePressed(event -> pressed(event));
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
		bodies.clear(); //resets initial palette i.e., prevents "ghost" bodies
		bodiesview.clear();
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //https://stackoverflow.com/questions/27203671/javafx-how-to-clear-the-canvas
		start.setText("Start");
	}
	
	@FXML
	public void removeMostRecent() {
		background.popFromStack();
		pane.getChildren().remove(bodiesview.pop());
		//bodiesview.pop();
		//updateViews();
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
		Body n = new Body(event.getX(), event.getY(), changeHSpeed, changeVSpeed, changeSize, Color.rgb(r, g, b)); //last num changes with scale, implement later!
		System.out.println(n.toString());
		background.pushToStack(n);
		BodyView z = new BodyView(n);
		bodiesview.push(z);
		//bodiesview.push(z);
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
