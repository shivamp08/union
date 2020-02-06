package venn;




import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
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
		this.mainLayout = new BorderPane();// initiate border plane layout
		this.layout = new Group(); // initiate group layout.
		this.mainLayout.setCenter(layout);
		
		// vbox for top
		VBox vbox = new VBox();
	    vbox.setPadding(new Insets(10));
		
//		Label la1 = new Label("hi there1");
//		Label la2 = new Label("hi there2");
//		Label la3 = new Label("hi there3");
//		Label la4 = new Label("hi there4");
//		Label la5 = new Label("hi there5");
//		
//		//List of data
//		ListView<Label> list = new ListView<>();
//		ObservableList<Label> items =FXCollections.observableArrayList (la1,la2,la3,la4,la5);
//		list.setItems(items);
		

		Text t1 = new Text("This is a text sample,");
		Text t2 = new Text("This is a text sample");
		Text t3 = new Text("This is a text sample");
		Text t4 = new Text("This is a text sample");
		Text t5 = new Text("This is a text sample");
		Text t6 = new Text("This is a text sample");

		
		 HBox hbox = new HBox();
		 //hbox.setPadding(new Insets(15, 12, 15, 12));
		 hbox.setSpacing(100);
		 hbox.setPrefHeight(100);
		 hbox.setMaxWidth(1);
		 hbox.getChildren().addAll(t1,t2,t3);
		 //hbox.minWidthProperty().bind(mainLayout.widthProperty());
		
		
		// Button to add new data.
		Button add = new Button("Add Data");
		add.setOnAction(e -> AddData.display());
		
		vbox.getChildren().addAll(hbox,add);
		this.mainLayout.setTop(vbox);
		
		
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
