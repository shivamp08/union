package venn;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class VennPanelTitle {
	public static Pane create (String data, Boolean vertical, String ID) {
		Pane pane = new HBox();
		if (vertical) {
			pane = new VBox();	
		}
        
        if (pane instanceof HBox) {
        	((HBox) pane).setAlignment(Pos.CENTER);
        } else {
        	((VBox) pane).setAlignment(Pos.CENTER);
        }

        pane.setPadding(new Insets(5));
        pane.getStyleClass().add("el-title");
        
        pane.setPrefWidth(200);
        
        final Pane pane2 = pane; 
        ImageView edit = new ImageView(ID); 
		edit.setFitHeight(25);
		edit.setFitWidth(25);
		edit.setTranslateX(80);
		edit.setTranslateY(-15);
		edit.setVisible(false);
		pane.getChildren().add(edit);
        
        pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				edit.setVisible(true);
			}
		});
        
        pane.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				edit.setVisible(false);
				
			}
		});
        
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				
			}
        	
		});
        
        Label text = new Label(data);
        pane.getChildren().addAll(text);

        return pane;
	}
}
