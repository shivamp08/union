package venn;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class VennSection {

    Color color;
    Color hoverColor;
    double radius;
    double width;
    double height;
    int strokeWidth = 3;

    protected Shape shape;

    Scene scene;

    protected String sectionName;

    public List<VennEntry> items;

    public VennSection (Scene scene) {
        this.items = new ArrayList<>();
        this.scene = scene;

        this.shape = new Circle();

        this.radius = (scene.getHeight() / 2) - (scene.getHeight() / 10);
        this.width = (scene.getWidth() / 2);
        this.height = (scene.getHeight() / 2);
    }

    protected void initDropHandlers () {
        shape.setOnDragEntered(event -> {
            if (event.getDragboard().hasString()) {
                shape.setFill(this.hoverColor);
            }
            event.consume();
        });

        shape.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        shape.setOnDragExited(event -> {
            // reset fill
            shape.setFill(this.color);
            event.consume();
        });

        shape.setOnDragDropped(event -> {
            System.out.println("dropped in " + this.sectionName);

            Button button = (Button) event.getGestureSource();
            double buttonCenterX = event.getX() - (button.getWidth() / 2);
            double buttonCenterY = event.getY() - (button.getHeight() / 2);

            button.setLayoutX(buttonCenterX);
            button.setLayoutY(buttonCenterY);

            event.setDropCompleted(true);
            event.consume();
        });
    }
}
