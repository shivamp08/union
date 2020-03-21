package venn;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class VennSectionRight extends VennSection {
    public VennSectionRight (Scene scene, MainWindow mainWindow) {
        super(scene, mainWindow);

        this.section = EntryLocations.Right;
        this.sectionName = new SimpleStringProperty("Right");

        this.color = new SimpleObjectProperty<>(Color.LIGHTCORAL);

        this.draw();
        this.initDropHandlers();
    }
}
