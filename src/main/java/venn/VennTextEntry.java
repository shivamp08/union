package venn;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class VennTextEntry extends Region {
    String data;
    EntryLocations location;

    StackPane draggable;
    HBox pane;
    Line line;

    public VennTextEntry(String data) {
        super();
        this.data = data;

        // the location where it is after creation
        this.location = EntryLocations.Draggable;
        
        this.draggable = null;

        this.draw();
    }

    public void setLocation(EntryLocations location) {
        this.location = location;
    }
    
    public Boolean canDrawLine() {
    	return this.pane != null && this.draggable != null;
    }
    
    public Line drawLine() {
    	Bounds draggableBoundsInScene = draggable.localToScene(draggable.getBoundsInLocal());
    	Bounds paneBoundsInScene = pane.localToScene(pane.getBoundsInLocal());
    	
    	System.out.println("min " + paneBoundsInScene.getMinX());
    	System.out.println("max " + paneBoundsInScene.getMinY());
    	System.out.println("pane " + pane);
    	
    	Line line = new Line(
			paneBoundsInScene.getMaxX() - (paneBoundsInScene.getWidth() / 2),
			paneBoundsInScene.getMinY() + 10,
			draggableBoundsInScene.getMaxX() - (draggableBoundsInScene.getWidth() / 2),
			draggableBoundsInScene.getMaxY() - 10
    	);
    	
    	line.setStroke(Color.BLACK);
    	line.setStrokeWidth(5);
    	
    	this.line = line;
    	
    	return line;
    }
    
    public void hover() {
    	if (this.pane != null) {
    		this.pane.getStyleClass().remove("el-default");
    		this.pane.getStyleClass().add("el-hover");
    	}
    }
    
    public void endHover() {
    	if (this.pane != null) {
    		this.pane.getStyleClass().remove("el-hover");
    		this.pane.getStyleClass().add("el-default");
    	}
    }

    private HBox draw () {
        Label text = new Label(this.data);

        this.pane = new HBox();
        this.pane.setAlignment(Pos.CENTER);
        this.pane.getChildren().addAll(text);

        this.pane.setPadding(new Insets(5));
        this.pane.getStyleClass().add("el-default");
        
        this.pane.setPrefWidth(200);

        this.pane.setUserData(this);
        
        Tooltip tooltip = new Tooltip(this.data);
        Tooltip.install(this.pane, tooltip);

        return this.pane;
    }
}
