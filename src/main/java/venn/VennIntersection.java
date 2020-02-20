package venn;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class VennIntersection extends VennSection {
    public VennIntersection(Scene scene, Main app, VennSectionLeft left, VennSectionRight right) {
        super(scene, app);

        this.section = EntryLocations.Center;

        this.color = Color.GREY;
        this.hoverColor = this.color.darker();

        this.draw(left, right);
        this.initDropHandlers();
    }

    public void draw (VennSectionLeft left, VennSectionRight right) {
        Shape shape = Shape.intersect(left.shape, right.shape);
        shape.setFill(this.color);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);
        this.initGroup(shape);
    }
}
