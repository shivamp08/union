package venn;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class VennTextEntry extends Region {
    String data;
    EntryLocations location;

    HBox pane;

    public VennTextEntry(String data) {
        super();
        this.data = data;

        // the location where it is after creation
        this.location = EntryLocations.Draggable;

        this.draw();
    }

    public void setLocation(EntryLocations location) {
        this.location = location;
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
