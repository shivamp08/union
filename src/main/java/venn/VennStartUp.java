package venn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.*;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class VennStartUp  extends Application implements Initializable {
    //Has a menu that fits entire screen
	
	protected Stage stage;
	
	 @FXML
	 private AnchorPane root;
	
    @Override
    public void start(Stage primaryStage) throws IOException{
    		
    		this.stage = primaryStage;
    		
            Parent root = FXMLLoader.load(getClass().getResource("/StartWindow.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            //new view
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
    }
    
    @Override
	public void initialize(URL url, ResourceBundle rb) { }

    public static void main(String[] args) {
        launch(args);
    }

    public void begin() {
    	Main app = new Main();
    	Stage stage = new Stage();
    	app.start(stage);
    }

    public void close() {
    	root.getScene().getWindow().hide();
    }
}