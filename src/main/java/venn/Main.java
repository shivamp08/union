package venn;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Venn");
		
		Group root = new Group(); 
		Scene scene = new Scene(root, 1000, 500);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

}
