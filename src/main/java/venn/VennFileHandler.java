package venn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static venn.Main.changeHandler;

public class VennFileHandler {
    Main app;
    VennEntryHandler handler;
    VennSectionRight right;
    VennSectionLeft left;
    VennIntersection intersection;

    /**
     * The class for Gson to import and export, for easy handling
     */
    static public class VennExport {
        @SerializedName("e")
        @Expose
        VennEntryHandler elements;
        @SerializedName("r")
        @Expose
        VennSectionRight right;
        @SerializedName("l")
        @Expose
        VennSectionLeft left;
        @SerializedName("i")
        @Expose
        VennIntersection intersection;

        public VennExport (VennEntryHandler handler, VennSectionRight right, VennSectionLeft left, VennIntersection intersection) {
            this.elements = handler;
            this.right = right;
            this.left = left;
            this.intersection = intersection;
        }
    }

    public VennFileHandler (Main app, VennEntryHandler handler, VennSectionRight right, VennSectionLeft left, VennIntersection intersection) {
        this.app = app;
        this.handler = handler;
        this.right = right;
        this.left = left;
        this.intersection = intersection;
    }

    public String getExportString (boolean pretty) {
        VennExport export = new VennExport(handler, right, left, intersection);

        GsonBuilder gsonBuilder = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation();

        if (pretty) {
            gsonBuilder.setPrettyPrinting();
        }

        Gson gson = gsonBuilder.create();

        return gson.toJson(export);
    }

    public void exportVenn () {
        File location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", true);

        if (location == null) return;

        String export = this.getExportString(true);

        try {
            FileWriter writer = new FileWriter(location);
            writer.write(export);
            writer.flush();
            writer.close();
        } catch (IOException ignored) {
        }

        System.out.println("Done exporting!");
        System.out.println("Exported " + this.handler.entries.size() + " entries");
    }

    public void importFromObject (VennExport imported, boolean fromFile) {
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
                    entry.section = section;
                } else if (EntryLocations.Draggable.equals(entry.location)) {
                    // add to the draggable area
                    this.handler.addEntry(entry, fromFile);
                }
            }
        }
    }

    public static void clearDiagram (Main app) {
        Object[] elements = app.entries.entries.toArray();

        for (Object element : elements) {
            VennTextEntry entry = (VennTextEntry) element;

            app.entries.deleteEntry(entry, false);
            if (entry.draggable.getParent() != null) {
                ((Group) entry.draggable.getParent()).getChildren().remove(entry.draggable);
            }
            if (entry.pane.getParent() != null) {
                ((Pane) entry.pane.getParent()).getChildren().remove(entry.pane);
            }
        }
    }

    public void importVenn (File x) {
        System.out.println(this.handler.entries.size());
        
        File location; 
        if (x == null) {
        	location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", false);
        }
        else {
        	location = x; 
        }

        if (location == null) return;

        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        VennExport imported;
        try {
            imported = gson.fromJson(new FileReader(location), VennExport.class);
        } catch (IOException exception) {
            return;
        }

        if (this.handler.entries.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "This will delete everything on the Venn diagram, are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.NO) return;
        }

        clearDiagram(this.app);
        this.importFromObject(imported, true);
        changeHandler.reset();

        System.out.println("Done importing!");
        System.out.println("Imported " + imported.elements.entries.size() + " entries");
    }
}
