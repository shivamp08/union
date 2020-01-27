package venn;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;

public class Main extends Application {
	
	private static final int width = 1000;
	private static final int height = 500;
	
	private Group root;
	private Group vennGroup;
	private Scene scene;

	public static void main(String[] args) {
		launch(args);
	}
	
	private void drawVenn() {
		Circle left = new Circle();
		
		double width = 1000;
		double height = 500;
		
		double radius = (height / 2) - (height / 10);
		double intersectionWidth = radius / 2;
		int strokeWidth = 3;
		
		Color leftColor = Color.BLUE;
		Color rightColor = Color.RED;
		
		left.setCenterX((width / 2) - intersectionWidth);
		left.setCenterY(height / 2);
		
		left.setRadius(radius);
		
		left.setFill(Color.BLUE);
		left.setStroke(Color.BLACK);
		left.setStrokeWidth(strokeWidth);
		
		Circle right = new Circle();
		
		right.setCenterX((width / 2) + intersectionWidth);
		right.setCenterY(height / 2);
		
		right.setRadius(radius);
		
		right.setFill(Color.RED);
		right.setStroke(Color.BLACK);
		right.setStrokeWidth(strokeWidth);
		
		Shape intersect = Shape.intersect(left, right);
		
		intersect.setFill(Color.LIGHTGREEN);
		intersect.setStroke(Color.WHITE);
		intersect.setStrokeWidth(strokeWidth);
		
		intersect.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != intersect &&
		                 event.getDragboard().hasString()) {
		             intersect.setFill(Color.GREEN);
		         }
		                
		         event.consume();
		    }
		});
		
		left.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != left &&
		                 event.getDragboard().hasString()) {
		             left.setFill(Color.DARKBLUE);
		         }
		                
		         event.consume();
		    }
		});
		
		right.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != right &&
		                 event.getDragboard().hasString()) {
		             right.setFill(Color.DARKRED);
		         }
		                
		         event.consume();
		    }
		});
		
		intersect.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		        intersect.setFill(Color.LIGHTGREEN);

		        event.consume();
		    }
		});
		
		left.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		        left.setFill(Color.BLUE);

		        event.consume();
		    }
		});

		right.setOnDragExited(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* mouse moved away, remove the graphical cues */
		        right.setFill(Color.RED);

		        event.consume();
		    }
		});
		
		intersect.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				event.setDropCompleted(true);
				
				event.consume();
			}
		});

		this.vennGroup.getChildren().clear();
		
		this.vennGroup.getChildren().addAll(left, right, intersect);
	}


	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Venn");
		
		this.root = new Group(); 
		this.vennGroup = new Group();
		
		this.root.getChildren().add(this.vennGroup);
		
		Button button = new Button("Hover Over Me");
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

		this.root.getChildren().add(button);
		
		Text e1 = new Text("This is thsefse fsfs eeifjsioefsiefjsoeijf osifjseofisjefos \n ifjsoiefjsefsfs  s test test test test");
		Rectangle entry = new Rectangle(50, 50, e1.getLayoutBounds().getWidth() + 10, e1.getLayoutBounds().getHeight() + 10);
		System.out.println(e1.getWrappingWidth());
		entry.setFill(javafx.scene.paint.Color.WHITE);
		entry.setStroke(javafx.scene.paint.Color.BLACK);
		StackPane stack = new StackPane(); 
		stack.getChildren().addAll(entry, e1); 
		stack.setTranslateX(100);
		stack.setTranslateY(100);
		
		ImageView edit = new ImageView(getClass().getResource("editicon.png").toExternalForm());
		edit.setFitHeight(25);
		edit.setFitWidth(25);
		edit.setTranslateX(entry.getTranslateX() + entry.getWidth()/2);
		edit.setTranslateY(edit.getTranslateY() - 15);
		edit.setVisible(false);
		stack.getChildren().add(edit); 
		
		stack.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				entry.setWidth(entry.getWidth() + 10);
				entry.setHeight(entry.getHeight() + 10);
				edit.setVisible(true);
				
			}
		});
		
		stack.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				entry.setWidth(entry.getWidth() - 10);
				entry.setHeight(entry.getHeight() - 10);
				edit.setVisible(false);
			}
		});
		
		
		this.root.getChildren().add(stack);
		
		this.scene = new Scene(root, Main.width, Main.height);
		
		this.drawVenn();
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show(); 
	}

}
