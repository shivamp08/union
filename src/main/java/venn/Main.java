package venn;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

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
		
		Color mix = new Color(
			0.5 * leftColor.getRed() + 0.5 * rightColor.getRed(),
			0.5 * leftColor.getGreen() + 0.5 * rightColor.getGreen(),
			0.5 * leftColor.getBlue() + 0.5 * rightColor.getBlue(),
			1
		);
		
		intersect.setFill(mix);
		intersect.setStroke(Color.WHITE);
		intersect.setStrokeWidth(strokeWidth);
		
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
		
		this.root.getChildren().add(button);
		
		this.scene = new Scene(root, Main.width, Main.height);
		
		this.drawVenn();
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show(); 
	}

}
