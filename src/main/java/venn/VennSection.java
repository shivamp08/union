package venn;

import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    Main app;

    public VennSection (Scene scene, VennEntryHandler handler, Main app) {
        this.handler = handler;
        this.scene = scene;

        this.shape = new Circle();
        this.pane = new VBox();
        this.app = app;

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
                    VennTextEntry entry = (VennTextEntry) added.get(0).getUserData();
                    System.out.println("was at " + entry.location + ", now at " + section);
                    
                    entry.setLocation(section);
                } else if (removed.size() == 1) {
                	VennTextEntry entry = (VennTextEntry) removed.get(0).getUserData();
                	
                	VennEntryHandler.handleLineDrawings(app.overlayGroup, entry, false);
                	
                	System.out.println("removed from " + entry.location);
                }
            }
        });
    }

    protected void initHoverHandlers () {
        Group linesGroup = this.app.overlayGroup;

        shape.setOnMouseEntered(event -> {
            shape.setFill(this.hoverColor);
            
            for (Node n : this.pane.getChildren()) {
                Pane box = (Pane) n;
                
                VennTextEntry entry = this.handler.findByPane(box);
                
                if (entry != null) {
                	entry.hover();
                	VennEntryHandler.handleLineDrawings(linesGroup, entry, true);
                }
            }
        });
        shape.setOnMouseExited(event -> {
            shape.setFill(this.color);

            for (Node n : this.pane.getChildren()) {
                Pane box = (Pane) n;
                
                VennTextEntry entry = this.handler.findByPane(box);
                
                if (entry != null) {
                	entry.endHover();
                	VennEntryHandler.handleLineDrawings(linesGroup, entry, false);
                }
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
            
            if (source == null) return;

            System.out.println("source " + source);
            Parent parent = entry.pane.getParent();
            if (parent != null) {
            	((Pane) entry.pane.getParent()).getChildren().removeIf(c -> {
                    if (c != null && c.getUserData() != null) {
                        return c.getUserData().equals(entry);
                    }
                    return false;
                });	
            }
            
            Group vennGroup = this.app.vennGroup;
            StackPane draggable = entry.draggable;
            if (draggable != null) {
            	draggable.setLayoutX(event.getX() - (draggable.getWidth() / 2));
            	draggable.setLayoutY(event.getY() - (draggable.getHeight() / 2));
            	if (!vennGroup.getChildren().contains(draggable)) {
            		vennGroup.getChildren().add(draggable);
            	}
            }

            this.pane.getChildren().add(entry.pane);

            event.setDropCompleted(true);
            event.consume();
        });

        this.initHoverHandlers();
    }
}
