package venn;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VennSectionRight extends VennSection {
    public VennSectionRight (Scene scene, Main app, VennEntryHandler handler) {
        super(scene, handler, app);

        this.section = EntryLocations.Right;

        this.color = Color.RED;
        this.hoverColor = Color.DARKRED;

        shape = new Circle();
        pane = new VBox(5);
        pane.setUserData(this);
        
        ((VBox) pane).setAlignment(Pos.CENTER);
        pane.getChildren().add(VennPanelTitle.create("Right", false));

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
