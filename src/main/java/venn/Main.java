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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.*;

public class Main extends Application {
	
	private static final int width = 1250;
	private static final int height = 750;

	protected Group vennGroup;
	protected Group overlayGroup;
	private Scene scene;

	VennEntryHandler entries;

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

		HBox dragHbox = new HBox(2);
		
		this.entries = new VennEntryHandler(dragHbox, overlayGroup);
		this.drawVenn();
		
		VBox top = new VBox(5);
		
		top.getChildren().addAll(VennMenu.create(this.entries), dragHbox);

		mainLayout.setTop(top);
		
		mainLayout.prefHeightProperty().bind(scene.heightProperty());
        mainLayout.prefWidthProperty().bind(scene.widthProperty());
		
		dragHbox.getChildren().add(VennPanelTitle.create("Items: ", false));
		
		//Add Button
		Tooltip CtrlN = new Tooltip("CTRL + N"); 
		Button add = new Button("Add Entry");
		add.setOnAction(event -> VennAddEntry.add(this.entries));
		add.setTranslateY(80);
		add.setTranslateX(5);
		Tooltip.install(add, CtrlN);
		layout.getChildren().add(add);
		
		//Recyling Bin 
		ImageView bin = new ImageView(getClass().getResource("recyclingbin.png").toExternalForm());
		bin.setFitHeight(70);
		bin.setFitWidth(70);
		HBox hbRecycle = new HBox(); 
		hbRecycle.getChildren().add(bin);
		hbRecycle.setAlignment(Pos.BOTTOM_RIGHT);
		mainLayout.setBottom(hbRecycle);
		
		
		
		// keyboard combo
		KeyCombination kc1 = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);
		Runnable rn = () -> VennAddEntry.add(this.entries);
		scene.getAccelerators().put(kc1, rn);

		stage.setScene(scene);
//		stage.setResizable(false);
		stage.show(); 
	}

}
