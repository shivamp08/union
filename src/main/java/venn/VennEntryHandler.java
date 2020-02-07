package venn;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class VennEntryHandler {
    List<VennTextEntry> entries;

    public VennEntryHandler () {
        this.entries = new ArrayList<>();
    }

    public void addEntry (VennTextEntry entry) {
        this.entries.add(entry);

        entry.pane.setOnDragDetected(event -> {
            Dragboard db = entry.pane.startDragAndDrop(TransferMode.ANY);

            Group previewGroup = new Group();

            Circle preview = new Circle();
            preview.setRadius(20);
            preview.setFill(Color.ORANGERED);

            Text previewText = new Text(String.valueOf(this.getIndexOfEntry(entry) + 1));
            previewText.setFill(Color.WHITE);

            previewGroup.getChildren().addAll(preview, previewText);

            SnapshotParameters sp = new SnapshotParameters();
            sp.setFill(Color.TRANSPARENT);

            db.setDragView(previewGroup.snapshot(sp, null), event.getX(), event.getY());

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
            if (entry.pane.equals(pane)) return entry;
        }
        return null;
    }
}
