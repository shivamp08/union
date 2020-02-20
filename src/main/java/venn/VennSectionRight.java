package venn;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionRight extends VennSection {
    public VennSectionRight (Scene scene, Main app) {
        super(scene, app);

        this.section = EntryLocations.Right;

        this.color = Color.LIGHTCORAL;
        this.hoverColor = this.color.darker();

        this.draw();
        this.initDropHandlers();
    }

    public void draw () {
        Circle shape = new Circle();
        shape.setCenterX(width + (this.radius / 2));
        shape.setCenterY(height);

        shape.setRadius(this.radius);

        shape.setFill(this.color);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(strokeWidth);
        this.initGroup(shape);
    }
}
