package venn;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Entry {
	
	private static double X = 200; 
	private static double Y = 0;
	
	private String data; 
	
	public Entry(String s, Group root) {
		data = s;
		drawEntry(root); 
	}
	
	public void drawEntry(Group root) {
		Text text = new Text(data);
		Rectangle box = new Rectangle(0, 0, text.getLayoutBounds().getWidth() + 10, text.getLayoutBounds().getHeight() + 10);
		box.setFill(javafx.scene.paint.Color.WHITE);
		box.setStroke(javafx.scene.paint.Color.BLACK);
		StackPane stack = new StackPane(); 
		stack.getChildren().addAll(box, text);
		stack.setTranslateX(X);
		stack.setTranslateY(Y);
		Y = Y + box.getHeight();
		
		//Edit Icon
		ImageView edit = new ImageView(getClass().getResource("editicon.png").toExternalForm());
		edit.setFitHeight(25);
		edit.setFitWidth(25);
		edit.setTranslateX(box.getTranslateX() + box.getWidth()/2);
		edit.setTranslateY(edit.getTranslateY() - box.getHeight()/2);
		edit.setVisible(false);
		stack.getChildren().add(edit); 
		
		stack.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				box.setWidth(box.getWidth() + 10);
				box.setHeight(box.getHeight() + 10);
				edit.setVisible(true);
				
			}
		});
		
		stack.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				box.setWidth(box.getWidth() - 10);
				box.setHeight(box.getHeight() - 10);
				edit.setVisible(false);
			}
		});
		
		root.getChildren().add(stack); 
	}
	

}
