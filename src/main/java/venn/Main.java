package venn;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class Main extends Application {

	// initial resolution, 720p
	private static final int width = 1280;
	private static final int height = 720;

	protected Group vennGroup;
	private Scene scene;
	protected Stage stage;

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
		this.vennGroup.getChildren().addAll(left.element, right.element, intersection.element);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Union App");
		this.stage = stage;

		BorderPane mainLayout = new BorderPane();

		this.vennGroup = new Group();

		// the holder is to center, and the scroller is to ensure that the
		// entire venn is visible
		StackPane holder = new StackPane(this.vennGroup);
		ScrollPane vennScroller = new ScrollPane(holder);
		vennScroller.fitToHeightProperty().set(true);
		vennScroller.fitToWidthProperty().set(true);

		mainLayout.setCenter(vennScroller);
		
		this.scene = new Scene(mainLayout, Main.width, Main.height);
		this.scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		this.leftColumn = new VennLeftColumn(this);
		this.entries = new VennEntryHandler();

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
		stage.setMinHeight(height);
//		stage.setMinWidth(width);
//		stage.setResizable(false);
		stage.show(); 
	}

}
