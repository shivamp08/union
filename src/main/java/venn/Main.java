package venn;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	
	private static final int width = 1000;
	private static final int height = 500;
	
	private Group layout;
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Venn");
		
		this.layout = new Group(); 
		
		
		
		final Button button = new Button("Hover Over Me");
		button.setTooltip(new Tooltip("Tooltip for Button"));
		
		button.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		        /* drag was detected, start a drag-and-drop gesture*/
		        /* allow any transfer mode */
		        Dragboard db = button.startDragAndDrop(TransferMode.ANY);
		        
		        Group previewGroup = new Group();
		        
		        Circle preview = new Circle();
		        preview.setRadius(20);
		        preview.setFill(Color.ORANGERED);
		        
		        Text previewText = new Text("1");
		        previewText.setFill(Color.ANTIQUEWHITE);
		        
		        previewText.setTranslateX(2);
		        previewText.setTranslateY(2);
		        
		        previewGroup.getChildren().addAll(preview, previewText);
		        
		        SnapshotParameters sp = new SnapshotParameters();
		        sp.setFill(Color.TRANSPARENT);
		        
		        db.setDragView(previewGroup.snapshot(sp, null), event.getX(), event.getY());
		        
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
		        content.putString(button.getText());
		        db.setContent(content);
		        
		        event.consume();
		    }
		});

		button.setOnDragDone(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
//				System.out.println(event.get);
				System.out.println(button.getLayoutX());
				
				event.consume();
			}
		});

		this.layout.getChildren().add(button);
		
		DrawVenn draw = new DrawVenn();
		this.layout.getChildren().addAll(draw.left(),draw.right(),draw.intersect());

		this.scene = new Scene(layout, Main.width, Main.height);
		
		
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show(); 
	}

}
