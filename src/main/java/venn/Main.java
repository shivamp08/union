package venn;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
	
	private static final int width = 1000;
	private static final int height = 500;

	private Group vennGroup;
	private Scene scene;
	private Stage stage;

	private BorderPane mainLayout;

	VennEntryHandler entries;

	VennSectionLeft left;
	VennSectionRight right;
	VennIntersection intersection;

	public Main () {
		super();
		this.entries = new VennEntryHandler();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void drawVenn() {
		this.left = new VennSectionLeft(scene, this.entries);
		this.right = new VennSectionRight(scene, this.entries);
		this.intersection = new VennIntersection(scene, this.entries, left, right);
		this.vennGroup.getChildren().addAll(left.shape, right.shape, intersection.shape);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setTitle("Venn");

		this.mainLayout = new BorderPane();
		this.vennGroup = new Group();

		this.mainLayout.setCenter(this.vennGroup);

		Button addButton = new Button("Add Entry");

		Insets padding = new Insets(5);
		
		this.scene = new Scene(this.mainLayout, Main.width, Main.height);
		this.scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		this.drawVenn();

		VBox topVBox = new VBox(5);
		VBox leftVBox = (VBox) this.left.pane;
		VBox rightVBox = (VBox) this.right.pane;
		HBox bottomHBox = (HBox) this.intersection.pane;

		HBox addHBox = new HBox(5);
		HBox dragHbox = new HBox(2);

		topVBox.setPadding(padding);
		leftVBox.setPadding(padding);
		rightVBox.setPadding(padding);

		topVBox.getChildren().addAll(addHBox, dragHbox);

		addHBox.getChildren().addAll(addButton);

		this.mainLayout.setTop(topVBox);
		this.mainLayout.setRight(rightVBox);
		this.mainLayout.setLeft(leftVBox);
		this.mainLayout.setBottom(bottomHBox);

		addButton.setOnAction(event -> {
			Text data = VennAddEntry.display();
			if (data.getText().contentEquals("")) return;

			VennTextEntry entry = new VennTextEntry(data.getText());

			this.entries.addEntry(entry);
			dragHbox.getChildren().add(entry.pane);
		});
		
		stage.setScene(scene);
//		stage.setResizable(false);
		stage.show(); 
	}

}
