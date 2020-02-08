package venn;

import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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

    protected EntryLocations section;

    VennEntryHandler handler;

    Pane pane;
    VBox innerPane; // temp pane

    public VennSection (Scene scene, VennEntryHandler handler) {
        this.handler = handler;
        this.scene = scene;

        this.shape = new Circle();
        this.pane = new VBox();
        this.innerPane = new VBox();

//        this.radius = (scene.getHeight() / 2) - (scene.getHeight() / 10);
//        this.width = (scene.getWidth() / 2);
//        this.height = (scene.getHeight() / 2);
        this.radius = 250;
        this.width = 750;
        this.height = 500;
    }

    protected void initChangeHandler () {
        this.pane.getChildren().addListener(new ListChangeListener<Object>() {
            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public void onChanged(ListChangeListener.Change c) {
                c.next();
                List<Pane> added = c.getAddedSubList();
                List<Pane> removed = c.getRemoved();
                if (added.size() == 1) {
                    VennTextEntry entry = (VennTextEntry) ((Pane) added.get(0)).getUserData();
                    System.out.println("was at " + entry.location + ", now at " + section);
                    entry.setLocation(section);
                } else if (removed.size() == 1) {
                	VennTextEntry entry = (VennTextEntry) ((Pane) removed.get(0)).getUserData();
                	System.out.println("removed from " + entry.location);
                }
            }
        });
    }

    protected void initHoverHandlers () {
        List<Line> lines = new ArrayList<>();

        shape.setOnMouseEntered(event -> {
            shape.setFill(this.hoverColor);
            for (Node n : this.pane.getChildren()) {
                Pane box = (Pane) n;
                box.getStyleClass().remove("el-default");
                box.getStyleClass().add("el-hover");
            }
        });
        shape.setOnMouseExited(event -> {
            shape.setFill(this.color);
            for (Node n : this.pane.getChildren()) {
                Pane box = (Pane) n;
                box.getStyleClass().add("el-default");
                box.getStyleClass().remove("el-hover");
            }
        });

        this.initChangeHandler();
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
            System.out.println("dropped in " + this.section);

            Pane source = (Pane) event.getGestureSource();
            VennTextEntry entry = this.handler.findByPane(source);

            ((Pane) entry.pane.getParent()).getChildren().removeIf(c -> {
                if (c != null && c.getUserData() != null) {
                    return c.getUserData().equals(entry);
                }
                return false;
            });

            this.pane.getChildren().add(entry.pane);

//            Button button = (Button) event.getGestureSource();
//            double buttonCenterX = event.getX() - (button.getWidth() / 2);
//            double buttonCenterY = event.getY() - (button.getHeight() / 2);
//
//            button.setLayoutX(buttonCenterX);
//            button.setLayoutY(buttonCenterY);

            event.setDropCompleted(true);
            event.consume();
        });

        this.initHoverHandlers();
    }
}
