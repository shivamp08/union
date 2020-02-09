package venn;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VennEntryHandler {
    List<VennTextEntry> entries;
    Pane container;
    
    Group overlayGroup;

    public VennEntryHandler (Pane container, Group overlayGroup) {
        this.entries = new ArrayList<>();
        this.container = container;
        this.overlayGroup = overlayGroup;
    }
    
    /**
     * Generates a light color
     * @return
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
    
    public static StackPane getCircle (int index) {
    	StackPane pane = new StackPane();
    	
    	Circle circle = new Circle();
    	circle.setRadius(20);
    	circle.setFill(VennEntryHandler.generateColour());
        
        Text previewText = new Text(String.valueOf(index));
        previewText.setFill(Color.WHITE);
        
        pane.getChildren().addAll(circle, previewText);
        
        return pane;
    }

    public void addEntry (VennTextEntry entry) {
        this.entries.add(entry);
        entry.draggable = VennEntryHandler.getCircle(this.getIndexOfEntry(entry) + 1);
        
        this.container.getChildren().add(entry.pane);
        
        VennEntryHandler.bindDragHandler(entry.pane, entry, this);
        VennEntryHandler.bindDragHandler(entry.draggable, entry, this);
        VennEntryHandler.bindHoverHandler(entry.draggable, entry, overlayGroup);
       
    }
    
    public static void handleLineDrawings(Group overlayGroup, VennTextEntry entry, Boolean draw) {
    	if (draw) {
    		if (entry.line == null && entry.canDrawLine()) {
    			entry.drawLine();
    			if (!overlayGroup.getChildren().contains(entry.line)) {
    				overlayGroup.getChildren().add(entry.line);
    			}
    		}
    	} else {
    		if (entry.line != null) {
    			if (overlayGroup.getChildren().contains(entry.line)) {
    				overlayGroup.getChildren().remove(entry.line);
    				entry.line = null;
    			}
    		}
    	}
    }
    
    public static void bindHoverHandler (Pane pane, VennTextEntry entry, Group overlayGroup) {
    	pane.setOnMouseEntered(event -> {
            entry.hover();
            
            if (entry.line == null) {
        		Line line = entry.drawLine();
        		overlayGroup.getChildren().add(line);
        	}
        });
        pane.setOnMouseExited(event -> {
            entry.endHover();
        });
    }
    
    public static void bindDragHandler(Pane pane, VennTextEntry entry, VennEntryHandler handler) {
    	pane.setOnDragDetected(event -> {
	    	Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
	    	
	    	StackPane circle = entry.draggable;        
	
	        SnapshotParameters sp = new SnapshotParameters();
	        sp.setFill(Color.TRANSPARENT);
	        db.setDragView(
	        	circle.snapshot(sp, null),
	        	event.getX() - (pane.getWidth() / 2),
	        	event.getY() - (pane.getHeight() / 2)
	        );
	
	        /* Put a string on a dragboard */
	        ClipboardContent content = new ClipboardContent();
	        content.putString(entry.data);
	        db.setContent(content);
	
	        event.consume();
    	});
    }

    public int getIndexOfEntry (VennTextEntry entry) {
        return this.entries.indexOf(entry);
    }

    public VennTextEntry findByPane (Pane pane) {
        for (VennTextEntry entry : this.entries) {
            if (entry.pane.equals(pane) || entry.draggable.equals(pane)) return entry;
        }
        return null;
    }
}
