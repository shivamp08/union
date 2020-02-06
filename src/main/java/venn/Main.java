package venn;




import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	private Group layout;
	private Scene scene;
	private BorderPane mainLayout;

	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Venn");
		this.mainLayout = new BorderPane();// initiate border plane layout
		this.layout = new Group(); // initiate group layout.
		this.mainLayout.setCenter(layout);
		
		// vbox for top
		VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
		

		//trial
	    
//	    button.setOnDragDetected(new EventHandler<MouseEvent>() {
//		    public void handle(MouseEvent event) {
//		        /* drag was detected, start a drag-and-drop gesture*/
//		        /* allow any transfer mode */
//		        Dragboard db = button.startDragAndDrop(TransferMode.ANY);
//		        
//		        Group previewGroup = new Group();
//		        
//		        Circle preview = new Circle();
//		        preview.setRadius(20);
//		        preview.setFill(Color.ORANGERED);
//		        
//		        Text previewText = new Text("1");
//		        previewText.setFill(Color.ANTIQUEWHITE);
//		        
//		        previewText.setTranslateX(2);
//		        previewText.setTranslateY(2);
//		        
//		        previewGroup.getChildren().addAll(preview, previewText);
//		        
//		        SnapshotParameters sp = new SnapshotParameters();
//		        sp.setFill(Color.TRANSPARENT);
//		        
//		        db.setDragView(previewGroup.snapshot(sp, null), event.getX(), event.getY());
//		        
//		        /* Put a string on a dragboard */
//		        ClipboardContent content = new ClipboardContent();
//		        content.putString(button.getText());
//		        db.setContent(content);
//		        
//		        event.consume();
//		    }
//		});
//
//		button.setOnDragDone(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
////				System.out.println(event.get);
//				System.out.println(button.getLayoutX());
//				
//				event.consume();
//			}
//		});
	    
	    
	    
	    //trial
	    
	    
	    
	    
	    Text t = new Text("Trial");
	    t.setOnDragDetected(e-> {
	    	 Dragboard db = t.startDragAndDrop(TransferMode.ANY);
	    	 
	    	 ClipboardContent content = new ClipboardContent();
	         content.putString(t.getText());
	         db.setContent(content);
	         
	         e.consume();
	    });
	    
	    
	    //List of data
		ListView<Text> list = new ListView<>();
		list.setOrientation(Orientation.HORIZONTAL);
		list.setMaxHeight(50);
		list.getItems().add(t);
		


		
		
		// Button to add new data.
		Button add = new Button("Add Data");
		add.setOnAction(e -> {
			Text data = AddData.display();
			list.getItems().add(data);
			
		});
		
		vbox.getChildren().addAll(list,add);
		this.mainLayout.setTop(vbox);
		
		
		// Draws the Venn diagram.
		VennCircle_Right  right = new VennCircle_Right();
		Circle r = right.right;
		
		VennCircle_Left  left = new VennCircle_Left();
		Circle l = left.left  ;
		
		VennShape_Intersect  inter = new VennShape_Intersect();
		Shape i = inter.intersect;
		
		this.layout.getChildren().addAll(l,r,i);
																

		

		this.scene = new Scene(mainLayout, 1000, 700);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show(); 
	}

}
