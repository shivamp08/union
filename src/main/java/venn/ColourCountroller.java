package venn;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ColourCountroller implements Initializable {

    @FXML
    private Circle left;

    @FXML
    private Circle right;

    @FXML
    private JFXColorPicker leftColourpicker;

    @FXML
    private JFXColorPicker rightColourpicker;
    
    @FXML
    private JFXButton save;

    @FXML
    private JFXButton close;
    
    Circle leftI;
    Circle rightI;
    
    Color set ;
    
    
    
    @FXML
    void LeftSelect(ActionEvent event) {
    	 set = leftColourpicker.getValue();
    	 left.setFill(set);


    }

    @FXML
    void RightSelect(ActionEvent event) {
    	 set = rightColourpicker.getValue();
    	 right.setFill(set);
    }
    
    @FXML
    void Close(ActionEvent event) {

    	
    	Stage stage = (Stage) close.getScene().getWindow();
    	stage.close();
    }
    

	@FXML
    void Save(ActionEvent event) {
		
		leftI.setFill(left.getFill());
		rightI.setFill(right.getFill());
    	
    	Stage stage = (Stage) close.getScene().getWindow();
    	stage.close();
    
    }
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{	
		

		
		
		
		set = Color.LIGHTGREEN;
		leftI = new Circle();
		leftI.setFill(left.getFill());
		rightI = new Circle();
		rightI.setFill(left.getFill());
	}
    
	  public Circle getLeft() {
			return left;
		}

		public Circle getRight() {
			return right;
		}

		public void setLeft(Circle left) {
			this.left = left;
		}

		public void setRight(Circle right) {
			this.right = right;
		}
    
    
    
    
    

}
