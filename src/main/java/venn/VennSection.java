package venn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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

import static venn.Main.changeHandler;
import static venn.Main.gameModeHandler;

public abstract class VennSection {
    @SerializedName("c")
    @Expose
    ObjectProperty<Color> color;
    ObjectProperty<Color> mutatingColor;

    static SimpleIntegerProperty radius = new SimpleIntegerProperty(300);

    SimpleIntegerProperty width;
    SimpleIntegerProperty height;
    int strokeWidth = 3;

    protected Shape shape;
    public Group element;

    Scene scene;

    protected EntryLocations section;
    @SerializedName("n")
    @Expose
    protected StringProperty sectionName;
    protected StringBinding sectionInternationalizedBinding;

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

        this.width = new SimpleIntegerProperty(750);
        this.height = new SimpleIntegerProperty(500);
    }

    public void beginHover () {
        Color darker = this.mutatingColor.getValue().deriveColor(
            0, 1.0, 0.8, 1.0
        );
        this.mutatingColor.set(darker);
    }

    public void endHover () {
        Color brighter = this.mutatingColor.getValue().deriveColor(
            0, 1.0, 1.0 / 0.8, 1.0
        );
        this.mutatingColor.set(brighter);
    }

    public void draw () {
        this.bindColorCopy();
        this.drawCircle();
    }

    private int getMultiplier () {
        int multiplier = 1;
        if (this.section == EntryLocations.Left) multiplier = -1;
        return multiplier;
    }

    private double getXPosition () {
        int multiplier = this.getMultiplier();
        return this.width.getValue() + (multiplier * ((double) radius.getValue() / 2));
    }

    protected void bindSectionNameTranslation () {
        // conditionally update iff the value has not changed from the translations
        this.sectionInternationalizedBinding.addListener((observable, oldValue, newValue) -> {
            if (this.sectionName.getValue().contentEquals(oldValue)) this.sectionName.set(newValue);
        });
    }

    protected void bindColorCopy () {
        // store the actual color (for hovering) here
        if (this.color != null) {
            this.mutatingColor = new SimpleObjectProperty<>(this.color.get());
            this.color.addListener((observable, oldValue, newValue) -> {
                this.mutatingColor.set(newValue);
            });
        }
    }

    protected void bindTitleEditing (Control control) {
        // double click to edit
        control.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)){
                // not while running
                if (event.getClickCount() == 2 && !gameModeHandler.running.get()) {
                    VennEntryModalHandler.edit(ModalType.EditTitle, this.sectionName, 25);
                }
            }
        });
    }

    private void drawCircle () {
        Circle shape = new Circle();

        Label title = new Label();
        title.textProperty().bind(this.sectionName);

        this.bindTitleEditing(title);

        title.layoutYProperty().bind(this.height.subtract(radius).subtract(title.heightProperty()).subtract(10));

        title.widthProperty().addListener((obs, oldVal, newVal) ->
            title.setLayoutX(this.getXPosition() - (title.getWidth() / 2) + (this.getMultiplier() * 20))
        );
        radius.addListener((obs, oldVal, newVal) ->
            title.setLayoutX(this.getXPosition() - (title.getWidth() / 2) + (this.getMultiplier() * 20))
        );

        shape.centerXProperty().bind(
            this.width.add(radius.divide(2).multiply(this.section == EntryLocations.Left ? -1 : 1))
        );
        shape.centerYProperty().bind(this.height);

        shape.radiusProperty().bind(radius);

        shape.fillProperty().bind(this.mutatingColor);
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
//                    System.out.println("was at " + entry.location + ", now at " + section);
                    
                    entry.setLocation(section);
                } else if (removed.size() == 1) {
                	VennTextEntry entry = removed.get(0);
                	
//                	System.out.println("removed from " + entry.location);
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
//            System.out.println("dropped in " + this.section);

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
//                entry.setDraggablePositionCenter(event.getX(), event.getY());
                entry.positionDraggable(event.getX(), event.getY(), true, true);
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
