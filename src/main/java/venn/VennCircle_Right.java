package venn;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennCircle_Right {
	
	private double width = 1000;
	private double height = 500;

	private double radius = (height / 2) - (height / 10);
	private double intersectionWidth = radius / 2;
	private int strokeWidth = 3;
	public Circle right;

	
	public  VennCircle_Right()
	{
		right = new Circle();

		right.setCenterX((width / 2) + intersectionWidth);
		right.setCenterY(height / 2);

		right.setRadius(radius);

		right.setFill(Color.RED);
		right.setStroke(Color.BLACK);
		right.setStrokeWidth(strokeWidth);
		
	 
	}
}
