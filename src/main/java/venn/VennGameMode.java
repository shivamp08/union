package venn;

import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static venn.VennFileHandler.clearDiagram;

public class VennGameMode {
    Main app;

    BooleanProperty running;
    VennExport imported;
    HashMap<String, EntryLocations> solutions;

    public VennGameMode(Main app) {
        this.app = app;

        this.running = new SimpleBooleanProperty(false);
    }

    public void initialize () {
        boolean canContinue = this.showAlerts();
        if (!canContinue) return;

        clearDiagram(this.app);

        // store the solutions (id: solution) in a hash map for verification
        this.solutions = new HashMap<>();

        for (VennTextEntry entry : this.imported.elements.entries) {
            entry.draw();
            this.app.entries.initEntry(entry);

            // update the location
            entry.setLocation(entry.location);

            this.app.entries.addEntry(entry, false);

            solutions.put(entry.id, entry.location);
        }
    }

    public boolean showAlerts() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setGraphic(null);
        alert.setHeaderText("Information");

        alert.setContentText(
            "Once you enable Game Mode, you will be asked to select a file. These are the expected answers. \n\n" +
            "Once the file has been selected, you will no longer be able to create new entries for the diagram. \n\n" +
            "You may click the button on the left to verify your response, or to close Game Mode."
        );

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println(alert.getResult());

            File location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", false);
            if (location == null) return false; // nothing selected

            Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

            // imported object
            try {
                imported = gson.fromJson(new FileReader(location), VennExport.class);
            } catch (IOException exception) {
                return false;
            }

            if (imported.elements.entries.size() == 0) {
                alert = new Alert(Alert.AlertType.ERROR, "No entries provided!");
                alert.showAndWait();
            } else {
                alert =  new Alert(Alert.AlertType.INFORMATION, "Game Mode beginning...");
                alert.showAndWait();

                this.running.set(true);

                return true;
            }
        }

        this.reset(false);
        return false;
    }

    public void reset(boolean clean) {
        this.imported = null;
        this.solutions = null;
        this.running.set(false);

        if (clean) clearDiagram(this.app);
    }

    // validate with the solutions
    public void validate () {
        for (String vennEntryId : this.solutions.keySet()) {
            System.out.println(vennEntryId + ", supposed to be at: " + this.solutions.get(vennEntryId));
        }
    }
}
