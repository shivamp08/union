package venn;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class VennShape_Intersect {
	
	public Shape intersect;

	
	public VennShape_Intersect()
	{
		
		VennCircle_Right  right = new VennCircle_Right();
		Circle r = right.right;
		
		VennCircle_Left  left = new VennCircle_Left();
		Circle l = left.left  ;
		
		 intersect = Shape.intersect(r,l);

		intersect.setFill(Color.LIGHTGREEN);
		intersect.setStroke(Color.WHITE);
		intersect.setStrokeWidth(3);
		
		
	}
}
