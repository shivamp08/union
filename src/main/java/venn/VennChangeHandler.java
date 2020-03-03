package venn;

import com.google.gson.Gson;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.hildan.fxgson.FxGson;

import javax.json.*;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Stack;

public class VennChangeHandler {
    Main app;

    String lastExport;
    Stack<JsonPatch> patches;

    public VennChangeHandler(Main app) {
        this.app = app;

        this.patches = new Stack<>();
    }

    public void calculateChange() {
        String current = app.leftColumn.fileHandler.getExportString();

        if (lastExport == null) {
            lastExport = current;
            return;
        }

        JsonValue source = Json.createReader(new StringReader(lastExport)).readValue();
        JsonValue target = Json.createReader(new StringReader(current)).readValue();

        JsonPatch diff = Json.createDiff(source.asJsonObject(), target.asJsonObject());

        System.out.println(diff.toString());

        lastExport = current;
        patches.push(diff);
    }

    public void undo () {
        if (patches.size() == 0) {
            System.out.println("none");
            return;
        }

        JsonPatch last = patches.peek();
        String current = app.leftColumn.fileHandler.getExportString();

        JsonObject curr = Json.createReader(new StringReader(current)).readValue().asJsonObject();

        JsonObject patched = last.apply(curr);

        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        VennExport change = gson.fromJson(patched.toString(), VennExport.class);

        System.out.println(app.entries.entries);

        Object[] elements = app.entries.entries.toArray();

        for (Object element : elements) {
            VennTextEntry entry = (VennTextEntry) element;

            System.out.println(entry);
            this.app.entries.deleteEntry(entry, false);
            if (entry.draggable.getParent() != null) {
                ((Group) entry.draggable.getParent()).getChildren().remove(entry.draggable);
            }
            if (entry.pane.getParent() != null) {
                ((Pane) entry.pane.getParent()).getChildren().remove(entry.pane);
            }
        }

        app.leftColumn.fileHandler.importFromObject(change);

        patches.pop();
    }
}
