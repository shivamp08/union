package venn;

import com.google.gson.Gson;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import org.hildan.fxgson.FxGson;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class VennChangeHandler {
    Main app;

    Stack<String> changes;

    IntegerProperty currentIndex;

    public VennChangeHandler(Main app) {
        this.app = app;

        this.currentIndex = new SimpleIntegerProperty(0);

        this.changes = new Stack<>();
    }

    public void calculateChange() {
        String current = app.leftColumn.fileHandler.getExportString(false);

        while (currentIndex.get() > 0) {
            this.changes.pop();
            currentIndex.set(currentIndex.get() - 1);
        }
        System.out.println(changes);

        this.changes.push(current);
    }

    private void importAndClear (String importString) {
        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().create();
        VennExport change = gson.fromJson(importString, VennExport.class);

//        System.out.println(app.entries.entries);

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

    public boolean canUndo () {
        return this.changes.size() - currentIndex.get() - 1 >= 0;
    }

    public void undo () {
        int size = this.changes.size();
        if (size == 0) {
            return;
        }

        // if this is too far back
        if (size - currentIndex.get() - 2 <= 0) return;

        String last = this.changes.get(size - currentIndex.get() - 1);

        currentIndex.set(currentIndex.get() + 1);
        System.out.println("undo: index at " + currentIndex.get());

        this.importAndClear(last);
    }

    public void redo () {
        int size = this.changes.size();
        if (currentIndex.get() > 0) {
            currentIndex.set(currentIndex.get() - 1);
            System.out.println("redo: index at " + currentIndex);

            String change = this.changes.get(size - currentIndex.get() - 1);

            this.importAndClear(change);
        }
    }
}
