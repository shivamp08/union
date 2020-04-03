package venn;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionLeft extends VennSection {
    public VennSectionLeft (Scene scene, Main app) {
        super(scene, app);

        this.section = EntryLocations.Left;
        this.sectionName = new SimpleStringProperty("Left");
        this.sectionInternationalizedBinding = VennInternationalization.createStringBinding("left_title");
        this.bindSectionNameTranslation();

        this.color = new SimpleObjectProperty<>(Color.LIGHTBLUE);

        this.draw();
        this.initDropHandlers();
    }
}
