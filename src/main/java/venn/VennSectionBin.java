package venn;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class VennSectionBin extends VennSection {
	
	BorderPane binLayout; 
	
	public VennSectionBin(Scene scene, Main app) {
		super(scene, app);
        this.section = EntryLocations.Bin;
        
        this.draw();
	}
	
	public void draw() {
		binLayout = new BorderPane(); 
		binLayout.prefHeightProperty().bind(scene.heightProperty());
        binLayout.prefWidthProperty().bind(scene.widthProperty());
        
		ImageView bin = new ImageView(getClass().getResource("recyclingbin.png").toExternalForm());
		bin.setFitHeight(70);
		bin.setFitWidth(70);
		HBox hbRecycle = new HBox(); 
		hbRecycle.getChildren().add(bin);
		hbRecycle.setAlignment(Pos.BOTTOM_RIGHT);
        binLayout.setBottom(hbRecycle);
	}
}
