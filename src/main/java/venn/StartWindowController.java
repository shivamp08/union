package venn;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartWindowController {
	
	@FXML
    private JFXButton start;
	
	private Main app;

    @FXML
    void Begin(ActionEvent event) {
    	
    	Stage stage = new Stage();
    	Parent root = app.scene.getRoot();
    	Scene scene = new Scene(root);
    	
    	stage.setScene(scene);
    	stage.setMinHeight(720);
		stage.setMaximized(true);
		Image icon = new Image(getClass().getResource("/logo.png").toExternalForm());
		stage.getIcons().add(icon);
		stage.show(); 
    }

}
