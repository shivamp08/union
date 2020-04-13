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
        this.sectionInternationalizedBinding = VennInternationalization.createStringBinding("middle_title");
        this.bindSectionNameTranslation();

        this.color = new SimpleObjectProperty<>(Color.GREY);
        this.bindColorCopy();

        this.draw(left, right);
        this.initDropHandlers();
    }

    public void draw (VennSectionLeft left, VennSectionRight right) {
        Shape shape = Shape.intersect(left.shape, right.shape);
        shape.fillProperty().bind(this.mutatingColor);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);

        Label title = new Label(this.sectionName.getValue());
        title.textProperty().bind(this.sectionName);

        this.bindTitleEditing(title);

        // manually position it

        title.layoutYProperty().bind(this.height.add(radius).add(title.heightProperty().divide(2)).subtract(10));
        title.widthProperty().addListener((obs, oldVal, newVal) -> {
            title.setLayoutX(width.getValue() - (title.getWidth() / 2));
        });

        this.element.getChildren().addAll(shape, title);
        this.element.setOpacity(50);
        this.shape = shape;
    }
}
