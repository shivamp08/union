package venn;




import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static final int width = 1000;
	private static final int height = 500;
	
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
		
		Button add = new Button("Add Data");
		add.setOnAction(e -> AlertBox.display("Add", "Add the Data"));
		this.mainLayout.setTop(add);
		
		
		// Draws the Venn diagram.
		DrawVenn draw = new DrawVenn();
		this.layout.getChildren().addAll(draw.left(),draw.right(),draw.intersect());

		this.scene = new Scene(mainLayout, Main.width, Main.height);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show(); 
	}

}
