package nbodysystem;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class NBodySystem extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice(); //https://stackoverflow.com/questions/40320199/how-to-automatically-resize-windows-in-javafx-for-different-resolutions
			int width = gd.getDisplayMode().getWidth();
			int height = gd.getDisplayMode().getHeight();
			//double height = Screen.getPrimary().getBounds().getHeight();   
			//double width = Screen.getPrimary().getBounds().getWidth();  
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("NBodySystemGUI.fxml"));
			Scene scene = new Scene(root, width - 20, height - 80);
			primaryStage.setScene(scene);
			primaryStage.show();
			root.requestFocus();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
