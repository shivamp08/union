package venn;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennCircle_Left  {

	private double width = 1000;
	private double height = 500;

	private double radius = (height / 2) - (height / 10);
	private double intersectionWidth = radius / 2;
	private int strokeWidth = 3;
	public Circle left;
	
	
	
	public VennCircle_Left()
	{
		left = new Circle();


		left.setCenterX((width / 2) - intersectionWidth);
		left.setCenterY(height / 2);

		left.setRadius(radius);

		left.setFill(Color.BLUE);
		left.setStroke(Color.BLACK);
		left.setStrokeWidth(strokeWidth);
		
	}
}
