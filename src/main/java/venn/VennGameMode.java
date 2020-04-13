package venn;

import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static venn.VennFileHandler.clearDiagram;
import static venn.VennFileHandler.VennExport;

public class VennGameMode {
    Main app;

    BooleanProperty running;
    VennExport imported;
    HashMap<String, EntryLocations> solutions; 
    File location; 

    public VennGameMode(Main app) {
        this.app = app;

        this.running = new SimpleBooleanProperty(false);
    }

    public boolean initialize () {
        boolean canContinue = this.showAlerts();
        if (!canContinue) return false;

        clearDiagram(this.app);

        // store the solutions (id: solution) in a hash map for verification
        this.solutions = new HashMap<>();
        
        this.app.left.sectionName.set(imported.left.sectionName.get()); 
        this.app.right.sectionName.set(imported.right.sectionName.get()); 
        this.app.intersection.sectionName.set(imported.intersection.sectionName.get()); 

        for (VennTextEntry entry : this.imported.elements.entries) {
            entry.draw();
            this.app.entries.initEntry(entry);

            // update the location
            entry.setLocation(entry.location);

            this.app.entries.addEntry(entry, false);

            solutions.put(entry.id, entry.location);
        }
        return true; 
    }

    public boolean showAlerts() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setGraphic(null);
        alert.setHeaderText(VennInternationalization.get("gm_information"));

        alert.setContentText(VennInternationalization.get("gm_msg_why_did_i_choose_to_make_this_bilingual_help"));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println(alert.getResult());

            location = VennLeftColumn.getFileLocationFromChooser(this.app.stage, "json", false);
            if (location == null) return false; // nothing selected

            Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

            // imported object
            try {
                imported = gson.fromJson(new FileReader(location), VennExport.class);
            } catch (IOException exception) {
                return false;
            }

            if (imported.elements.entries.size() == 0) {
                alert = new Alert(Alert.AlertType.ERROR, VennInternationalization.get("gm_msg_none_provided"));
                alert.showAndWait();
            } else {
                alert =  new Alert(Alert.AlertType.INFORMATION, VennInternationalization.get("gm_msg_beginning"));
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
    
    public boolean reset() {
    	clearDiagram(this.app); 
    	
        Gson gson = FxGson.fullBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

        // imported object
        try {
            imported = gson.fromJson(new FileReader(location), VennExport.class);
        } catch (IOException exception) {
            return false;
        }
        
        for (VennTextEntry entry : this.imported.elements.entries) {
            entry.draw();
            this.app.entries.initEntry(entry);

            // update the location
            entry.setLocation(entry.location);

            this.app.entries.addEntry(entry, false);
        }
        
        return true; 
    }

    private String getSectionString (EntryLocations location) {
        String locationName = "";
        switch (location) {
            case Left: {
                locationName = app.left.sectionName.get();
                break;
            }
            case Right: {
                locationName = app.right.sectionName.get();
                break;
            }
            case Center: {
                locationName = app.intersection.sectionName.get();
                break;
            }
            case Draggable: {
                break;
            }
        }
        return locationName;
    }

    // validate with the solutions
    public void validate () {
    	int marks = 0;
    	 
    	
    	ArrayList<String> corrections = new ArrayList<>(); 
    	
        for (String vennEntryId : this.solutions.keySet()) {
        	VennTextEntry entry = this.app.entries.getEntryById(vennEntryId);
        	
        	if (entry.location.equals(this.solutions.get(vennEntryId))) {
        		marks++;
        	} 
        	else {
        		corrections.add(vennEntryId); 
        	}
        }
        ButtonType cont = new ButtonType(VennInternationalization.get("gm_btn_continue"));
        ButtonType reveal = new ButtonType(VennInternationalization.get("gm_btn_reveal"));
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", cont, reveal);
        
        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(e -> alert.hide());
        
        alert.setGraphic(null);
        alert.setHeaderText(VennInternationalization.get("gm_result"));

        String result = VennInternationalization.get("gm_msg_score", marks, solutions.size());
        if (corrections.size() > 0) result += "\n\n" + VennInternationalization.get("gm_msg_score_information");

        alert.setContentText(result);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.isPresent() && option.get() == reveal) {
        	Alert warning = new Alert(Alert.AlertType.WARNING, VennInternationalization.get("gm_msg_warning"), ButtonType.YES, ButtonType.NO);
        	Optional<ButtonType> warningResult = warning.showAndWait();
        	if (warningResult.isPresent() && warningResult.get() == ButtonType.YES) {
        	    this.reset(true);
            	VennFileHandler importAnswer = new VennFileHandler(app, app.entries, app.right, app.left, app.intersection); 
            	importAnswer.importVenn(location);
        	}
        }
        else if (option.isPresent() && option.get() == cont) {
        	for (String i : corrections) {
        		this.app.entries.getEntryById(i).draggable.setBorder(new Border(new BorderStroke(Color.RED, 
        	            BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(2))));
        		this.app.entries.getEntryById(i).draggable.setOnDragDone(event -> {
        			this.app.entries.getEntryById(i).draggable.setBorder(null);
        		});
        	}
        }
    }
}
