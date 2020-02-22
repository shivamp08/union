package venn;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class Main extends Application {

	// initial resolution, 720p
	private static final int width = 1280;
	private static final int height = 720;

	protected Group vennGroup;
	protected Group overlayGroup;
	private Scene scene;

	VennEntryHandler entries;

	VennLeftColumn leftColumn;

	VennSectionLeft left;
	VennSectionRight right;
	VennIntersection intersection;

	public Main () {
		super();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void drawVenn() {
		this.left = new VennSectionLeft(scene, this);
		this.right = new VennSectionRight(scene, this);
		this.intersection = new VennIntersection(scene, this, left, right);
		this.vennGroup.getChildren().addAll(left.group, right.group, intersection.group);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Union App");

		Group layout = new Group();
		BorderPane mainLayout = new BorderPane();
		this.vennGroup = new Group();
		this.overlayGroup = new Group();

		mainLayout.setCenter(this.vennGroup);
		
		layout.getChildren().add(overlayGroup);
		layout.getChildren().add(mainLayout);
		overlayGroup.toFront();
		
		this.scene = new Scene(layout, Main.width, Main.height);
		this.scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		this.leftColumn = new VennLeftColumn();
		this.entries = new VennEntryHandler(overlayGroup);

		// draw the main three sections
		this.drawVenn();

		// set for the column its data
		this.leftColumn.setHandler(this.entries);
		this.leftColumn.setSections(left, right, intersection);
		this.leftColumn.draw();

		// give the entry handler it's container
		this.entries.setContainer(this.leftColumn.entries);

		mainLayout.setLeft(this.leftColumn.root);
		
		mainLayout.prefHeightProperty().bind(scene.heightProperty());
        mainLayout.prefWidthProperty().bind(scene.widthProperty());
		
		// keyboard combo
		KeyCombination kc1 = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
		Runnable rn = () -> VennEntryModalHandler.add(this.entries);
		scene.getAccelerators().put(kc1, rn);

		scene.setFill(Color.web("#f6f8fa"));

		stage.setScene(scene);
//		stage.setResizable(false);
		stage.show(); 
	}

}
