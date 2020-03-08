package venn;

import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class VennPanelTitle {
	public static Pane create (StringBinding binding, Boolean vertical, String className) {
		Pane pane = new HBox();
		if (vertical) {
			pane = new VBox();	
		}
		
		Label text = new Label();
		text.textProperty().bind(binding);
        pane.getChildren().addAll(text);
        
        if (pane instanceof HBox) {
        	((HBox) pane).setAlignment(Pos.CENTER);
        } else {
        	((VBox) pane).setAlignment(Pos.CENTER);
        }

        pane.setPadding(new Insets(5));
        pane.getStyleClass().add(className);

        return pane;
	}
}
