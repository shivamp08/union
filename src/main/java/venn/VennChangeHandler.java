package venn;

import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.hildan.fxgson.FxGson;

import java.util.ArrayDeque;
import java.util.Deque;

public class VennChangeHandler {
    Main app;

    private final Deque<String> undo;
    private final Deque<String> redo;

    public BooleanProperty canRedo;
    public BooleanProperty canUndo;

    private int sizeMax;

    public VennChangeHandler(Main app) {
        this.app = app;

        undo = new ArrayDeque<>();
        redo = new ArrayDeque<>();

        canRedo = new SimpleBooleanProperty(false);
        canUndo = new SimpleBooleanProperty(false);

        sizeMax = 30;
    }

    private String getCurrentString () {
        return app.leftColumn.fileHandler.getExportString(false);
    }

    private void updateProperties () {
        if (undo.isEmpty()) canUndo.set(false);
        else canUndo.set(true);

        if (redo.isEmpty()) canRedo.set(false);
        else canRedo.set(true);
    }

    public void calculateChange() {
        if(sizeMax > 0) {
            if(undo.size() == sizeMax) {
                undo.removeLast();
            }

            String current = this.getCurrentString();

            // ignore if the same change is being pushed
            String lastUndo = undo.peek();
            if (lastUndo != null && lastUndo.contentEquals(current)) {
                System.out.println("ignoring change since same as last");
            } else {
                System.out.println("adding change");
                undo.push(current);
            }
            redo.clear(); /* The redoable objects must be removed. */
            this.updateProperties();
        }
    }

    private void importAndClear (String importString) {
        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().create();
        VennExport change = gson.fromJson(importString, VennExport.class);

        Object[] elements = app.entries.entries.toArray();

        for (Object element : elements) {
            VennTextEntry entry = (VennTextEntry) element;

            this.app.entries.deleteEntry(entry, false);
            if (entry.draggable.getParent() != null) {
                ((Group) entry.draggable.getParent()).getChildren().remove(entry.draggable);
            }
            if (entry.pane.getParent() != null) {
                ((Pane) entry.pane.getParent()).getChildren().remove(entry.pane);
            }
        }

        app.leftColumn.fileHandler.importFromObject(change, false);
    }

    public void undo () {
        if(!undo.isEmpty()) {
            System.out.println("undoing change");
            String last = undo.pop();

            String current = this.getCurrentString();
            if (last.contentEquals(current) && undo.size() > 0) {
                if (!redo.contains(last)) redo.push(last);
                last = undo.peek();
            }

            this.importAndClear(last);
            if (!redo.contains(last)) redo.push(last);
        }
        this.updateProperties();
    }

    public void redo () {
        if(!redo.isEmpty()) {
            System.out.println("redoing change");
            String next = redo.pop();

            String current = this.getCurrentString();
            if (next.contentEquals(current) && redo.size() > 0) {
                if (!undo.contains(next)) undo.push(next);
                next = redo.pop();
            }

            System.out.println(next);

            this.importAndClear(next);
            if (!undo.contains(next)) undo.push(next);
        }
        this.updateProperties();
    }
}
