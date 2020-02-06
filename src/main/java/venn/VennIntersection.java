package venn;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class VennIntersection extends VennSection {
    public VennIntersection(Scene scene, VennSectionLeft left, VennSectionRight right) {
        super(scene);

        this.sectionName = "intersection";

        this.color = Color.LIGHTGREEN;
        this.hoverColor = Color.GREEN;

        shape = Shape.intersect(left.shape, right.shape);

        this.draw();
        this.initDropHandlers();
    }

    public void draw () {
        shape.setFill(this.color);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);
    }
}
