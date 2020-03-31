package venn;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VennEntryPreview {
	
	StringProperty string; 
	StringProperty description;
	StackPane pane;
	ObjectProperty<Color> backgroundColor;
	ObjectProperty<Font> fontFamily;
	StringProperty fontSize;
	ObjectProperty<Color> fontColor; 
	Region entry; 
	
	public VennEntryPreview(String string, String description, String color, Font font, String size, String fColor){
		this.string = new SimpleStringProperty(string);
		backgroundColor =  new SimpleObjectProperty<>(Color.valueOf(color));
	    fontColor = new SimpleObjectProperty<>(Color.valueOf(fColor));
	    fontFamily = new SimpleObjectProperty<>(font);
	    fontSize = new SimpleStringProperty(size);
	    this.draw(); 
	}
	
	public void draw() {
		pane = new StackPane();
		Label label = new Label("");
        label.textProperty().bind(this.string);
        label.setTextFill(fontColor.getValue());
        label.setFont(fontFamily.getValue());
        
        string.addListener((observable, oldValue, newValue) -> {
        	label.textProperty().bind(this.string);
        });
        
        fontColor.addListener((observable, oldValue, newValue) -> {
        	label.setTextFill(fontColor.getValue());
        });
        fontFamily.addListener((observable, oldValue, newValue) -> {
        	label.setFont(fontFamily.getValue());
        });
        
        pane.getStyleClass().add("rounded-label");
        pane.setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(backgroundColor.getValue()));
        
        backgroundColor.addListener((observable, oldValue, newValue) -> {
        	pane.setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(backgroundColor.getValue()));
        });
        
        pane.getChildren().add(label);
        pane.setMaxWidth(100);
        entry = pane; 
        
	}
	
	public Region getPreview() {
		return entry; 
	}
}
