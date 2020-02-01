package venn;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class DrawVenn {
	
	private double width = 1000;
	private double height = 500;

	private double radius = (height / 2) - (height / 10);
	private double intersectionWidth = radius / 2;
	private int strokeWidth = 3;
	

//	public void drawVenn() {
//
//		Circle left = new Circle();
//
//		double width = 1000;
//		double height = 500;
//
//		double radius = (height / 2) - (height / 10);
//		double intersectionWidth = radius / 2;
//		int strokeWidth = 3;
//
//		left.setCenterX((width / 2) - intersectionWidth);
//		left.setCenterY(height / 2);
//		left.setRadius(radius);
//
//		left.setFill(Color.BLUE);
//		left.setStroke(Color.BLACK);
//		left.setStrokeWidth(strokeWidth);
//
//		Circle right = new Circle();
//
//		right.setCenterX((width / 2) + intersectionWidth);
//		right.setCenterY(height / 2);
//
//		right.setRadius(radius);
//
//		right.setFill(Color.RED);
//		right.setStroke(Color.BLACK);
//		right.setStrokeWidth(strokeWidth);
//
//		Shape intersect = Shape.intersect(left, right);
//
//		intersect.setFill(Color.LIGHTGREEN);
//		intersect.setStroke(Color.WHITE);
//		intersect.setStrokeWidth(strokeWidth);
//
//		intersect.setOnDragEntered(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* the drag-and-drop gesture entered the target */
//				/* show to the user that it is an actual gesture target */
//				if (event.getGestureSource() != intersect && event.getDragboard().hasString()) {
//					intersect.setFill(Color.GREEN);
//				}
//
//				event.consume();
//			}
//		});
//
//		left.setOnDragEntered(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* the drag-and-drop gesture entered the target */
//				/* show to the user that it is an actual gesture target */
//				if (event.getGestureSource() != left && event.getDragboard().hasString()) {
//					left.setFill(Color.DARKBLUE);
//				}
//
//				event.consume();
//			}
//		});
//
//		right.setOnDragEntered(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* the drag-and-drop gesture entered the target */
//				/* show to the user that it is an actual gesture target */
//				if (event.getGestureSource() != right && event.getDragboard().hasString()) {
//					right.setFill(Color.DARKRED);
//				}
//
//				event.consume();
//			}
//		});
//
//		intersect.setOnDragExited(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* mouse moved away, remove the graphical cues */
//				intersect.setFill(Color.LIGHTGREEN);
//
//				event.consume();
//			}
//		});
//
//		left.setOnDragExited(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* mouse moved away, remove the graphical cues */
//				left.setFill(Color.BLUE);
//
//				event.consume();
//			}
//		});
//
//		right.setOnDragExited(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				/* mouse moved away, remove the graphical cues */
//				right.setFill(Color.RED);
//
//				event.consume();
//			}
//		});
//
//		intersect.setOnDragDropped(new EventHandler<DragEvent>() {
//			public void handle(DragEvent event) {
//				event.setDropCompleted(true);
//
//				event.consume();
//			}
//		});
//
//	}
	
	
	
	public Circle right()
	{
		Circle right = new Circle();

		right.setCenterX((width / 2) + intersectionWidth);
		right.setCenterY(height / 2);

		right.setRadius(radius);

		right.setFill(Color.RED);
		right.setStroke(Color.BLACK);
		right.setStrokeWidth(strokeWidth);
		
		return right;
	}
	
	public Circle left()
	{
		Circle left = new Circle();

		left.setCenterX((width / 2) - intersectionWidth);
		left.setCenterY(height / 2);

		left.setRadius(radius);

		left.setFill(Color.BLUE);
		left.setStroke(Color.BLACK);
		left.setStrokeWidth(strokeWidth);
		
		return left;
	}
	
	
	public Shape intersect()
	{
		Shape intersect = Shape.intersect(this.left(),this.right());

		intersect.setFill(Color.LIGHTGREEN);
		intersect.setStroke(Color.WHITE);
		intersect.setStrokeWidth(strokeWidth);
		
		return intersect;
	}
	
	
	
	
	
	
	

}
