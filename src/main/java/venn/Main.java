package venn;




import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	
//	private Group layout;
	private Scene scene;
//	private BorderPane mainLayout;
//	private Color hoverColor;

	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage stage) throws Exception {
//		this.mainLayout = new BorderPane();// initiate border plane layout
//		this.layout = new Group(); // initiate group layout.
//		this.mainLayout.setCenter(layout);
//		
//		// vbox for top
//		VBox vbox = new VBox();
//	    vbox.setPadding(new Insets(10));
//    
//	    
//	    Text t = new Text("Trial");
//	    t.setOnDragDetected(e-> {
//	    	 Dragboard db = t.startDragAndDrop(TransferMode.ANY);
//	    	 
//	    	 ClipboardContent content = new ClipboardContent();
//	         content.putString(t.getText());
//	         db.setContent(content);
//	         
//	         e.consume();
//	    });
//	    t.setCursor(Cursor.HAND);
//	    
//	    
//	    
//	    
//	    
//	    //List of data
//		ListView<Text> list = new ListView<>();
//		list.setOrientation(Orientation.HORIZONTAL);
//		list.setMaxHeight(50);
//		list.getItems().add(t);
//		
//
//
//		
//		
//		// Button to add new data.
//		Button add = new Button("Add Data");
//		add.setOnAction(e -> {
//			Text data = AddData.display();
//			list.getItems().add(data);
//			
//		});
//		
//		vbox.getChildren().addAll(list,add);
//		this.mainLayout.setTop(vbox);
//		
//		
//		// Draws the Venn diagram.
//		VennCircle_Right  right = new VennCircle_Right();
//		Circle r = right.right;
//		
//		VennCircle_Left  left = new VennCircle_Left();
//		Circle l = left.left  ;
//		
//		VennShape_Intersect  inter = new VennShape_Intersect();
//		Shape i = inter.intersect;
//		
//		this.layout.getChildren().addAll(l,r,i);
//		
//		i.setOnDragDone(event ->{
//	    	if (event.getGestureSource() != i &&
//	                event.getDragboard().hasString()) {
//	            /* allow for both copying and moving, whatever user chooses */
//	            event.acceptTransferModes(TransferMode.ANY);
//	        }
//	        
//	        event.consume();
//	    
//	    });
//		
//		i.setOnDragDropped(event->{
//			Dragboard db = event.getDragboard();
//	        boolean success = false;
//	        if (db.hasString()) {
//	          // i.setText(db.getString());
//	           success = true;
//	        }
//	        /* let the source know whether the string was successfully 
//	         * transferred and used */
//	        event.setDropCompleted(success);
//	        
//	        event.consume();
//		});
//		
//		
//		i.setOnDragDone(event->{
//			if (event.getTransferMode() == TransferMode.MOVE) {
//	            t.setText("");
//	        }
//	        event.consume();
//		});
//		
//		
//		i.setOnDragEntered(event -> {
//            if (event.getDragboard().hasString()) {
//                i.setFill(this.hoverColor);
//            }
//            event.consume();
//        });
		
		Parent root = FXMLLoader.load(getClass().getResource("VennFXML_Main.fxml"));														

		stage.setTitle("Venn");

		
		this.scene = new Scene(root, 1000, 700);
//		this.scene.setFill(Color.web("#f6f8fa"));
		stage.setScene(scene);
		stage.show(); 
	}

}
