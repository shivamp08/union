package venn;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionLeft extends VennSection {
    public VennSectionLeft (Scene scene, MainWindow mainWindow) {
        super(scene, mainWindow);

        this.section = EntryLocations.Left;
        this.sectionName = new SimpleStringProperty("Left");

        this.color = new SimpleObjectProperty<>(Color.LIGHTBLUE);

        this.draw();
        this.initDropHandlers();
    }
}
