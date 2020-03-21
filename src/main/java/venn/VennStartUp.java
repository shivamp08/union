package venn;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class VennStartUp extends Application {
    //Has a menu that fits entire screen
	
	protected Stage stage;
	
	 @FXML
	 private AnchorPane root;
	
    @Override
    public void start(Stage primaryStage) throws IOException{
    		
    		this.stage = primaryStage;
    		
            Parent root = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            //new view
            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    

    public void begin(ActionEvent actionEvent) {
    	
    	Main app = new Main();
    	Stage stage = new Stage();
    	app.start(stage);
    	
    	
    }

    public void close(MouseEvent mouseEvent) {
    	root.getScene().getWindow().hide();
    }
}