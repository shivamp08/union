package venn;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public abstract class VennSection {

    ObjectProperty<Color> color;
    double radius;
    double width;
    double height;
    int strokeWidth = 3;

    protected Shape shape;
    protected Group element;

    Scene scene;

    protected EntryLocations section;
    protected StringProperty sectionName;

    VennEntryHandler handler;

    ObservableList<VennTextEntry> elements;
    Pane pane;

    Main app;

    public VennSection (Scene scene, Main app) {
        this.handler = app.entries;
        this.scene = scene;

        this.shape = null;
        this.element = new Group();

        this.pane = new VBox();
        this.elements = FXCollections.observableList(new ArrayList<>());
        this.app = app;

        this.radius = 300;
        this.width = 750;
        this.height = 500;
    }

    public void beginHover () {
        Color darker = this.color.getValue().deriveColor(
            0, 1.0, 0.8, 1.0
        );
        this.color.set(darker);
    }

    public void endHover () {
        Color brighter = this.color.getValue().deriveColor(
            0, 1.0, 1.0 / 0.8, 1.0
        );
        this.color.set(brighter);
    }

    public void draw () {
        this.drawCircle();
    }

    private int getMultiplier () {
        int multiplier = 1;
        if (this.section == EntryLocations.Left) multiplier = -1;
        return multiplier;
    }

    private double getXPosition () {
        int multiplier = this.getMultiplier();
        return width + (multiplier * (this.radius / 2));
    }

    protected void bindTitleEditing (Control control) {
        // double click to edit
        control.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                if (event.getClickCount() == 2){
                    VennEntryModalHandler.edit(this.sectionName, 25);
                }
            }
        });
    }

    private void drawCircle () {
        Circle shape = new Circle();
        double x = this.getXPosition();

        Label title = new Label();
        title.textProperty().bind(this.sectionName);

        this.bindTitleEditing(title);

        title.setLayoutY(height / 3);
        title.widthProperty().addListener((obs, oldVal, newVal) ->
            title.setLayoutX(x - (title.getWidth() / 2) + (this.getMultiplier() * 20))
        );

        shape.setCenterX(x);
        shape.setCenterY(height);

        shape.setRadius(this.radius);

        shape.fillProperty().bind(this.color);
        shape.setStroke(Color.BLACK);
        shape.setStrokeWidth(strokeWidth);

        this.element.getChildren().addAll(title, shape);
        this.element.setOpacity(50);
        this.shape = shape;
    }

    protected void initChangeHandler () {
        this.elements.addListener(new ListChangeListener<Object>() {
            @SuppressWarnings({ "unchecked" })
			@Override
            public void onChanged(ListChangeListener.Change c) {
                c.next();
                List<VennTextEntry> added = c.getAddedSubList();
                List<VennTextEntry> removed = c.getRemoved();
                if (added.size() == 1) {
                    VennTextEntry entry = added.get(0);
                    System.out.println("was at " + entry.location + ", now at " + section);
                    
                    entry.setLocation(section);
                } else if (removed.size() == 1) {
                	VennTextEntry entry = removed.get(0);
                	
                	System.out.println("removed from " + entry.location);
                }
            }
        });
    }

    protected void initHoverHandlers () {
        shape.setOnMouseEntered(event -> {
            this.beginHover();
        });
        shape.setOnMouseExited(event -> {
            this.endHover();
        });

        this.initChangeHandler();
    }

    private boolean hasNoCollision(Region dragged, double x, double y) {
        // For each node
        for (VennTextEntry entry : this.elements) {
            Node n = entry.draggable;
            // If it is not the same, and future position overlaps
            if (!n.equals(dragged) &&
                    n.localToScene(n.getBoundsInLocal()).intersects(x, y, dragged.getWidth(), dragged.getHeight())) {
                // Then prevent collision
                return false;
            }
        }
        // Otherwise all is good!
        return true;
    }

    protected void initDropHandlers () {
        shape.setOnDragEntered(event -> {
            if (event.getDragboard().hasString()) {
                this.beginHover();
            }
            event.consume();
        });

        shape.setOnDragOver(event -> {
            VennTextEntry entry = this.handler.getEntryById(event.getDragboard().getString());

//            boolean collides =
//                !this.hasNoCollision(
//                        entry.draggable,
//                        event.getSceneX() - draggable.getWidth() / 2,
//                        event.getSceneY() - draggable.getHeight() / 2 - 10
//                );
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        shape.setOnDragExited(event -> {
            // reset fill
            this.endHover();
            event.consume();
        });

        shape.setOnDragDropped(event -> {
            System.out.println("dropped in " + this.section);

            VennTextEntry entry = this.handler.getEntryById(event.getDragboard().getString());

            if (entry == null) return;

            if (entry.section != null) {
                entry.section.elements.remove(entry);
            } else {
                this.handler.removeFromDragContainer(entry);
            }
            entry.section = this;
            this.elements.add(entry);

            Region draggable = entry.draggable;

            if (draggable != null) {
            	draggable.setLayoutX(event.getX() - (draggable.getWidth() / 2));
            	draggable.setLayoutY(event.getY() - (draggable.getHeight() / 2));
            	if (!this.element.getChildren().contains(draggable)) {
            		this.element.getChildren().add(draggable);
            	}
            }

            event.setDropCompleted(true);
            event.consume();
        });

        this.initHoverHandlers();
    }
}
