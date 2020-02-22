package venn;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    public void draw (VennSectionLeft left, VennSectionRight right) {
        Shape shape = Shape.intersect(left.shape, right.shape);
        shape.fillProperty().bind(this.color);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);

        Label title = new Label(this.sectionName.getValue());
        title.textProperty().bind(this.sectionName);

        this.bindTitleEditing(title);

        // manually position it
        title.setLayoutY(height * 2 - (height / 2.5));
        title.widthProperty().addListener((obs, oldVal, newVal) -> {
            title.setLayoutX(width - (title.getWidth() / 2));
        });

//        title.layoutXProperty().bind(width);

        this.element.getChildren().addAll(shape, title);
        this.element.setOpacity(50);
        this.shape = shape;
    }
}
