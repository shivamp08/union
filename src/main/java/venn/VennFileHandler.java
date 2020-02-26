package venn;

import com.google.gson.Gson;
import javafx.scene.layout.StackPane;
import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VennFileHandler {
    Main app;
    VennEntryHandler handler;
    VennSectionRight right;
    VennSectionLeft left;
    VennIntersection intersection;

    public VennFileHandler (Main app, VennEntryHandler handler, VennSectionRight right, VennSectionLeft left, VennIntersection intersection) {
        this.app = app;
        this.handler = handler;
        this.right = right;
        this.left = left;
        this.intersection = intersection;
    }

    public void exportVenn () {
        File location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", true);

        if (location == null) return;

        VennExport export = new VennExport(handler, right, left, intersection);

        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        try {
            FileWriter writer = new FileWriter(location);
            gson.toJson(export, writer);
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }

        System.out.println("Done exporting!");
        System.out.println("Exported " + this.handler.entries.size() + " entries");
    }

    public void importVenn () {
        File location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", false);

        if (location == null) return;

        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        VennExport imported;
        try {
            imported = gson.fromJson(new FileReader(location), VennExport.class);
        } catch (IOException exception) {
            return;
        }

        // update the titles and colors
        this.left.sectionName.set(imported.left.sectionName.getValue());
        this.left.color.set(imported.left.color.getValue());

        this.right.sectionName.set(imported.right.sectionName.getValue());
        this.right.color.set(imported.right.color.getValue());

        this.intersection.sectionName.set(imported.intersection.sectionName.getValue());
        this.intersection.color.set(imported.intersection.color.getValue());

        // go over each of the imported entries
        for (VennTextEntry entry : imported.elements.entries) {
            if (
                EntryLocations.Left.equals(entry.location) ||
                EntryLocations.Right.equals(entry.location) ||
                EntryLocations.Center.equals(entry.location) ||
                EntryLocations.Draggable.equals(entry.location)
            ) {
                entry.draw();
                this.handler.initEntry(entry);

                // init the location of the draggable
                if (!EntryLocations.Draggable.equals(entry.location)) {
                    entry.draggable.relocate(entry.xCoordinate, entry.yCoordinate);
                }

                VennSection section = null;

                // get where to store it
                if (EntryLocations.Left.equals(entry.location)) {
                    section = left;
                } else if (EntryLocations.Right.equals(entry.location)) {
                    section = right;
                } else if (EntryLocations.Center.equals(entry.location)) {
                    section = intersection;
                }

                // store the element
                if (section != null) {
                    section.elements.add(entry);
                    section.element.getChildren().add(entry.draggable);
                } else if (EntryLocations.Draggable.equals(entry.location)) {
                    // add to the draggable area
                    this.handler.addEntry(entry);
                }
            }
        }

        System.out.println("Done importing!");
        System.out.println("Imported " + imported.elements.entries.size() + " entries");
    }
}
