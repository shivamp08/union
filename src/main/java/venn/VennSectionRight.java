package venn;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionRight extends VennSection {
    public VennSectionRight (Scene scene, Main app) {
        super(scene, app);

        this.section = EntryLocations.Right;
        this.sectionName = new SimpleStringProperty("Right");

        this.color = new SimpleObjectProperty<>(Color.LIGHTCORAL);

        this.draw();
        this.initDropHandlers();
    }
}
