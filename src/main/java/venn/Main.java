package venn;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.*;

import java.util.Locale;

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

	public static VennChangeHandler changeHandler;

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
		this.stage = stage;

		changeHandler = new VennChangeHandler(this);

		BorderPane mainLayout = new BorderPane();

		this.vennGroup = new Group();

		// the holder is to center, and the scroller is to ensure that the
		// entire venn is visible
		StackPane holder = new StackPane(this.vennGroup);
		ScrollPane vennScroller = new ScrollPane(holder);
		vennScroller.fitToHeightProperty().set(true);
		vennScroller.fitToWidthProperty().set(true);

		VennAnimatedZoomHandler zoomOperator = new VennAnimatedZoomHandler();
		holder.setOnScroll(event -> {
			double zoomFactor = 1.5;
			if (event.getDeltaY() <= 0) {
				// zoom out
				zoomFactor = 1 / zoomFactor;
			}
			zoomOperator.zoom(holder, zoomFactor, event.getSceneX(), event.getSceneY());
		});

		mainLayout.setCenter(vennScroller);
		
		Rectangle2D r = Screen.getPrimary().getBounds();
		this.scene = new Scene(mainLayout, r.getWidth(), r.getHeight());
		this.scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		this.leftColumn = new VennLeftColumn(this);
		this.entries = new VennEntryHandler(this);

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

		KeyCombination kc2 = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);
		Runnable rn2 = () -> changeHandler.undo();
		scene.getAccelerators().put(kc2, rn2);

		KeyCombination kc3 = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
		Runnable rn3 = () -> changeHandler.redo();
		scene.getAccelerators().put(kc3, rn3);

		scene.setFill(Color.web("#f6f8fa"));

//		System.out.println("initial change");
//		changeHandler.calculateChange();

		stage.titleProperty().bind(VennInternationalization.createStringBinding("app_title"));
		stage.setScene(scene);
		stage.setMinHeight(height);
		stage.setMaximized(true);
//		stage.setMinWidth(width);
//		stage.setResizable(false);
		Image icon = new Image(getClass().getResource("/logo.png").toExternalForm());
		stage.getIcons().add(icon);
		stage.show(); 
	}

}
