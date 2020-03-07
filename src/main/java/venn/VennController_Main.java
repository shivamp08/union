package venn;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class VennController_Main  implements Initializable{


	@FXML
	private Circle left;

	@FXML
	private Circle right;

	@FXML
	private Path intersect;

	@FXML
	private Group group;


	@FXML
	private MenuItem Add;

	@FXML
	private MenuItem Colour;

	@FXML
	private JFXMasonryPane dataPane;







	@Override
	public void initialize(URL url, ResourceBundle rb)
	{	

		Path path = new Path();
		path = (Path) Shape.intersect(left, right);
		intersect.getElements().addAll(path.getElements());
		intersect.setLayoutX(0);
		intersect.setLayoutY(0);



	}

	@FXML
	void colourScene(ActionEvent event) throws Exception{

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("colourFXML.fxml"));
		Parent colour = loader.load();		

		ColourCountroller controller = loader.getController();

		// binds the colour property to the circles
		left.fillProperty().bind(controller.leftI.fillProperty());
		right.fillProperty().bind(controller.rightI.fillProperty());


		// Create the new scene
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Colour Picker");
		stage.setScene(new Scene(colour));
		stage.setResizable(false);
		stage.getOnCloseRequest();
		stage.showAndWait();


	}


	@FXML
	void addData(ActionEvent event) {

		// Creates new label and give it texts.
		Text data = AddData.display();
		Label label = new Label();
		label.setText(data.getText());
		// drag dected method
		label.setOnDragDetected(e ->{
			Dragboard db = label.startDragAndDrop(TransferMode.MOVE);

			/* Put a string on a dragboard */
			ClipboardContent content = new ClipboardContent();
			content.putString(label.getText());
			db.setContent(content);

			event.consume();
		});

		dataPane.getChildren().add(label); // adding the label to the data.

		// drag done method on label
		label.setOnDragDone(e -> {
			//System.out.print(e);


			this.dataPane.getChildren().remove(label);

			e.consume();

		});
	}

	// drag over method for the diagram.
	@FXML
	void handleDragOver(DragEvent event) {
		if (event.getDragboard().hasString()) {
			/* allow for both copying and moving, whatever user chooses */
			event.acceptTransferModes(TransferMode.MOVE);
		}

		event.consume();
	}

	// drop handel method for the diagram.
	@FXML
	void handleDrop(DragEvent event) {

		Dragboard d = event.getDragboard();

		Pane pane = new Pane();

		Text t = new Text(d.getString());
		pane.getChildren().add(t);
		this.group.getChildren().add(pane);



		// setting the plane layouts to the cursor.
		event.setDropCompleted(false);
		this.group.setOnMouseMoved(e->{
			if(!event.isDropCompleted())
			{
				pane.setLayoutX(e.getX());
				pane.setLayoutY(e.getY());
				event.setDropCompleted(true);
			}
			e.consume();
		});



		event.consume();
	}




	public Circle getLeft() {
		return left;
	}

	public Circle getRight() {
		return right;
	}
}




