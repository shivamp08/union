package venn;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionRight extends VennSection {
    public VennSectionRight (Scene scene) {
        super(scene);

        this.sectionName = "right";

        this.color = Color.RED;
        this.hoverColor = Color.DARKRED;

        shape = new Circle();

        this.draw();
        this.initDropHandlers();
    }

    public void draw () {
        ((Circle) shape).setCenterX(width + (this.radius / 2));
        ((Circle) shape).setCenterY(height);

        ((Circle) shape).setRadius(this.radius);

        shape.setFill(this.color);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(strokeWidth);
    }
}
