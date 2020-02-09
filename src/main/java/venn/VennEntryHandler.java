package venn;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class VennEntryHandler {
    List<VennTextEntry> entries;
    Pane container;

    public VennEntryHandler (Pane container) {
        this.entries = new ArrayList<>();
        this.container = container;
    }
    
    public static StackPane getCircle (int index) {
    	StackPane pane = new StackPane();
    	
    	Circle circle = new Circle();
    	circle.setRadius(20);
    	circle.setFill(Color.ORANGERED);
        
        Text previewText = new Text(String.valueOf(index));
        previewText.setFill(Color.WHITE);
        
        pane.getChildren().addAll(circle, previewText);
        
        return pane;
    }

    public void addEntry (VennTextEntry entry, Group vennCircles) {
        this.entries.add(entry);
        this.container.getChildren().add(entry.pane);

        entry.pane.setOnDragDetected(event -> {
            Dragboard db = entry.pane.startDragAndDrop(TransferMode.ANY);
         
            StackPane pane = VennEntryHandler.getCircle(this.getIndexOfEntry(entry) + 1);           

            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);
            db.setDragView(pane.snapshot(sp, null), event.getX(), event.getY());

            /* Put a string on a dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(entry.data);
            db.setContent(content);

            event.consume();
        });
        
        entry.pane.setOnDragDone(event -> {
        	Point p = MouseInfo.getPointerInfo().getLocation(); 
        	StackPane pane = VennEntryHandler.getCircle(this.getIndexOfEntry(entry) + 1);
        	pane.setTranslateX(p.getX());
        	pane.setTranslateY(p.getY());
        	vennCircles.getChildren().addAll(pane);
        });
    }

    public int getIndexOfEntry (VennTextEntry entry) {
        return this.entries.indexOf(entry);
    }

    public VennTextEntry findByPane (Pane pane) {
        for (VennTextEntry entry : this.entries) {
            if (entry.pane.equals(pane)) return entry;
        }
        return null;
    }
}
