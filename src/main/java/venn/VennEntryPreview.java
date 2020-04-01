package venn;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class VennEntryPreview {
	
	private StackPane preview;
	private Label label;
	private HBox previewContainer;
	
	public VennEntryPreview(Color bgClr, Font font, Color fClr){
		preview = new StackPane(); 
		label = new Label("");
		label.textProperty().bind(VennInternationalization.createStringBinding("modal_preview"));
		label.setTextFill(fClr);
		label.setFont(font);
		preview.getStyleClass().add("rounded-label");
		preview.setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(bgClr));
		preview.getChildren().add(label);
		
		previewContainer = new HBox(); 
		previewContainer.getChildren().add(preview);
		previewContainer.setAlignment(Pos.CENTER);
	}
	
	public StackPane getPreview() {
		return this.preview;
	}
	
	public Label getLabel() {
		return this.label;
	}
	
	public HBox getPreviewContainer() {
		return this.previewContainer; 
	}
	
	
}
