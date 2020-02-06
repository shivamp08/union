package venn;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionLeft extends VennSection {
    public VennSectionLeft (Scene scene) {
        super(scene);

        this.sectionName = "left";

        this.color = Color.BLUE;
        this.hoverColor = Color.DARKBLUE;

        shape = new Circle();

        this.draw();
        this.initDropHandlers();
    }

    public void draw () {
        ((Circle) shape).setCenterX(width - (this.radius / 2));
        ((Circle) shape).setCenterY(height);

        ((Circle) shape).setRadius(this.radius);

        shape.setFill(this.color);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(strokeWidth);
    }
}
