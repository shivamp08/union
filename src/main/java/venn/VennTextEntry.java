package venn;

import javafx.geometry.Insets;
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
        Text text = new Text(this.data);

        this.pane = new HBox();
        this.pane.getChildren().addAll(text);

        this.pane.setPadding(new Insets(5));
        this.pane.getStyleClass().add("el-default");

        this.pane.setUserData(this);

        return this.pane;
    }
}
