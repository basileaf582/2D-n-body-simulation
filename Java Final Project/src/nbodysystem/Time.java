package nbodysystem;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;

public class Time {
	
private Movement clock;
	
	private class Movement extends AnimationTimer {

		private long FRAMES_PER_SEC = 50L;
		private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

		private long last = 0;
		private ArrayList<Body> bodies;

		@Override
		public void handle(long now) {
			if (now - last > INTERVAL) {
				for (Body b : bodies) {
				//	b.move();
				}
			//	updateViews();
				last = now;
			}
		}
	}
	
	public Time(Movement clock) {
		
	}

}
