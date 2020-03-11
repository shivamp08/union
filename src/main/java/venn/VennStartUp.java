package venn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class VennStartUp extends Application {
	//Has a menu that fits entire screen 
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("UnionApp.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			
			//new view 
			primaryStage.initStyle(StageStyle.UNDECORATED);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
