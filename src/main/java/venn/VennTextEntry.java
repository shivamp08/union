package venn;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.UUID;

import static venn.VennEntryHandler.getWebColor;

public class VennTextEntry extends Region {
    String data;
    EntryLocations location;

    Region draggable;
    HBox pane;
    VennSection section;

    String id;
    
    Color draggableColor;

    public VennTextEntry(String data) {
        super();
        this.data = data;

        this.id = UUID.randomUUID().toString();

        this.section = null;

        // the location where it is after creation
        this.location = EntryLocations.Draggable;
        
        this.draggable = null;

        this.draw();
    }

    public void setLocation(EntryLocations location) {
        this.location = location;
    }

    public void setDraggable () {
        StackPane pane = new StackPane();
        Label label = new Label(this.data);

        pane.getStyleClass().add("rounded-label");

        this.draggableColor = VennEntryHandler.generateColour();

        pane.setStyle("-fx-background-color: " + getWebColor(this.draggableColor));

        Tooltip tooltip = new Tooltip(this.data);
        Tooltip.install(label, tooltip);

        pane.getChildren().add(label);

        this.draggable = pane;
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
