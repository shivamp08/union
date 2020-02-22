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

    public void draw () {
        Circle shape = new Circle();
        shape.setCenterX(width + (this.radius / 2));
        shape.setCenterY(height);

        shape.setRadius(this.radius);

        shape.fillProperty().bind(this.color);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(strokeWidth);
        this.initGroup(shape);
    }
}
