package venn;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class VennIntersection extends VennSection {
    public VennIntersection(Scene scene, VennEntryHandler handler, VennSectionLeft left, VennSectionRight right) {
        super(scene, handler);

        this.section = EntryLocations.Center;

        this.color = Color.LIGHTGREEN;
        this.hoverColor = Color.GREEN;

        shape = Shape.intersect(left.shape, right.shape);
        pane = new HBox(5);
        ((HBox)pane).setAlignment(Pos.CENTER);
        pane.setUserData(this);

        this.draw();
        this.initDropHandlers();
    }

    public void draw () {
        shape.setFill(this.color);
        shape.setStroke(Color.WHITE);
        shape.setStrokeWidth(strokeWidth);
    }
}
