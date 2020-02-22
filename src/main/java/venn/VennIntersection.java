package venn;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class VennIntersection extends VennSection {
    public VennIntersection(Scene scene, Main app, VennSectionLeft left, VennSectionRight right) {
        super(scene, app);

        this.section = EntryLocations.Center;
        this.sectionName = new SimpleStringProperty("Intersection");

        this.color = new SimpleObjectProperty<>(Color.GREY);

        this.draw(left, right);
        this.initDropHandlers();
    }

    public ObjectProperty<Color> colorProperty() {
        return this.color;
    }

    public void draw (VennSectionLeft left, VennSectionRight right) {
        Shape shape = Shape.intersect(left.shape, right.shape);
        shape.fillProperty().bind(this.color);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);
        this.initGroup(shape);
    }
}
