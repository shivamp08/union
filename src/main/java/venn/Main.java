package venn;




import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
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
		this.mainLayout = new BorderPane();// initiate borderplane layout
		this.layout = new Group(); // initiate group layout.
		this.mainLayout.setCenter(layout);
		
		// Button to add new data.
		Button add = new Button("Add Data");
		add.setOnAction(e -> AddData.display());
		this.mainLayout.setTop(add);
		
		
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
