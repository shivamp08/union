package venn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VennEntryHandler {
    @SerializedName("e")
    @Expose
    ArrayList<VennTextEntry> entries;
    Pane container;

    Main app;

    public VennEntryHandler (Main app) {
        this.app = app;
        this.entries = new ArrayList<>();
    }

    public void setContainer (Pane container) {
        this.container = container;
    }

    /**
     * Generates a light color
     * @return the color
     */
    public static Color generateColour() {
    	String[] mColors = {
                "#39add1", // light blue
                "#3079ab", // dark blue
                "#c25975", // mauve
                "#e15258", // red
                "#f9845b", // orange
                "#838cc7", // lavender
                "#7d669e", // purple
                "#53bbb4", // aqua
                "#51b46d", // green
                "#e0ab18", // mustard
                "#637a91", // dark gray
                "#f092b0", // pink
                "#b7c0c7"  // light gray
        };
    	
    	Random randomGenerator = new Random(); // Construct a new Random number generator
        int randomNumber = randomGenerator.nextInt(mColors.length);

        String color = mColors[randomNumber];
        return Color.web(color);
    }

    public static String getWebColor (Color color) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public void initEntry (VennTextEntry entry) {
        this.entries.add(entry);
        entry.setDraggable();

        VennEntryHandler.bindDragHandler(entry.pane, entry, this);
        VennEntryHandler.bindDragHandler(entry.draggable, entry, this);
        VennEntryHandler.bindHoverHandler(entry.draggable, entry, this);
    }

    public void addEntry (VennTextEntry entry) {
        if (entry.draggable == null) this.initEntry(entry);
        this.container.getChildren().add(entry.pane);
        this.app.changeHandler.calculateChange();
    }

    public void deleteEntry (VennTextEntry entry, boolean pushChange) {
        this.entries.remove(entry);
        if (pushChange) this.app.changeHandler.calculateChange();
    }
    
    public static void bindHoverHandler (Region pane, VennTextEntry entry, VennEntryHandler handler) {
    	pane.setOnMouseEntered(event -> {
            // darken the circle
            if (entry.draggable != null) {
                Region draggable = entry.draggable;
                draggable.setStyle("-fx-background-color: " + getWebColor(entry.draggableColor.darker()));
            }
        });
        pane.setOnMouseExited(event -> {
            // reset circle back to original
            if (entry.draggable != null) {
                Region draggable = entry.draggable;
                draggable.setStyle("-fx-background-color: " + getWebColor(entry.draggableColor));
            }
        });
    }

    private static Image getSnapshot (VennTextEntry entry) {
        if (entry.draggable.getScene() == null) new Scene(entry.draggable);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        return entry.draggable.snapshot(sp, null);
    }
    
    public static void bindDragHandler(Region pane, VennTextEntry entry, VennEntryHandler handler) {
    	pane.setOnDragDetected(event -> {
	    	Dragboard db = pane.startDragAndDrop(TransferMode.ANY);

	        db.setDragView(
	        	getSnapshot(entry),
	        	event.getX() - (pane.getWidth() / 2),
	        	event.getY() - (pane.getHeight() / 2)
	        );

	        ClipboardContent content = new ClipboardContent();
	        content.putString(entry.id);
	        db.setContent(content);
	
	        event.consume();
    	});
    }

    public void removeFromDragContainer (VennTextEntry entry) {
        this.container.getChildren().remove(entry.pane);
    }

    public VennTextEntry getEntryById (String id) {
        for (VennTextEntry entry : this.entries) {
            if (entry.id.contentEquals(id)) return entry;
        }
        return null;
    }
}
