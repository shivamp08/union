package venn;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class VennController_Main  implements Initializable{


	@FXML
	private Circle left;

	@FXML
	private Circle right;

	@FXML
	private Path intersect;
	
	@FXML
    private Group group;
	
	 
	 @FXML
	 private MenuItem Add;
	 
	  @FXML
	  private MenuItem Colour;
	  
	  @FXML
	  private JFXMasonryPane dataPane;
	 
	 

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{	

		Path path = new Path();
		path = (Path) Shape.intersect(left, right);
		intersect.getElements().addAll(path.getElements());
		intersect.setLayoutX(0);
		intersect.setLayoutY(0);
		
		
		
	}
	
	@FXML
    void colourScene(ActionEvent event) throws Exception{
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("colourFXML.fxml"));
		Parent colour = loader.load();		
		
		ColourCountroller controller = loader.getController();
		
		// binds the colour property to the circles
			left.fillProperty().bind(controller.leftI.fillProperty());
			right.fillProperty().bind(controller.rightI.fillProperty());
			
		
		// Create the new scene
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Colour Picker");
		stage.setScene(new Scene(colour));
		stage.setResizable(false);
		stage.getOnCloseRequest();
		stage.showAndWait();
		
		
	}
	
	
	 @FXML
	 void addData(ActionEvent event) {
		 
		 Text data = AddData.display();
		 Label label = new Label();
		 label.setText(data.getText());
		 label.setOnDragDetected(e ->{
			 Dragboard db = label.startDragAndDrop(TransferMode.MOVE);
		        
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
		        content.putString(label.getText());
		        db.setContent(content);
		        
		        event.consume();
		 });
		 dataPane.getChildren().add(label); 
	 }
	 
	  @FXML
	  void handleDragOver(DragEvent event) {
		  if (event.getDragboard().hasString()) {
	            /* allow for both copying and moving, whatever user chooses */
	            event.acceptTransferModes(TransferMode.MOVE);
	        }
	        
	        event.consume();
	    }
	  
	  @FXML
	  void handleDrop(DragEvent event) {
		  
	    }


	public Circle getLeft() {
		return left;
	}

	public Circle getRight() {
		return right;
	}
}


	

