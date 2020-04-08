package venn;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static venn.Main.changeHandler;
import static venn.Main.gameModeHandler;
import static venn.VennFileHandler.clearDiagram;

public class VennLeftColumn {
    VBox root;

    VBox entries;

    VennEntryHandler handler;
    VennDeleteEntry trash;

    Main app;

    VennSectionLeft left;
    VennSectionRight right;
    VennIntersection intersection;
    VennOptions options;
    VennFileHandler fileHandler;

    public VennLeftColumn(VennEntryHandler handler) {
        this.handler = handler;
        this.trash = new VennDeleteEntry(handler);

//        this.draw();
    }

    public VennLeftColumn(Main app) {
        this.app = app;
//        this.draw();
    }

    public void setHandler(VennEntryHandler handler) {
        this.handler = handler;
        this.trash = new VennDeleteEntry(handler);
    }

    public void setSections (VennSectionLeft left, VennSectionRight right, VennIntersection intersection) {
        this.left = left;
        this.right = right;
        this.intersection = intersection;
    }

    private Button getAddButton () {
        Tooltip CtrlN = new Tooltip("CTRL/CMD + N");
        Button add = new Button();
        add.textProperty().bind(VennInternationalization.createStringBinding("add_entry_button"));
        add.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(add, Priority.ALWAYS);

        // no adding while running
        add.disableProperty().bind(gameModeHandler.running);

        add.setOnAction(event -> VennEntryModalHandler.add(this.handler));
        Tooltip.install(add, CtrlN);

        return add;
    }

    private Button getDeleteAllButton() {
        Button clearAll = new Button();
        clearAll.textProperty().bind(VennInternationalization.createStringBinding("delete_all"));
        clearAll.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(clearAll, Priority.ALWAYS);

        // no clearing while running
        clearAll.disableProperty().bind(gameModeHandler.running);

        clearAll.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING, VennInternationalization.get("delete_all_warning"));
            Optional<ButtonType> result =  alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                clearDiagram(this.app);
            }
        });

        return clearAll;
    }

    private Button getOptionsButton () {
        Button optionsButton = new Button();
        optionsButton.textProperty().bind(VennInternationalization.createStringBinding("options_button"));
        optionsButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(optionsButton, Priority.ALWAYS);

        options = new VennOptions(left, right, intersection);
        optionsButton.setOnAction(event -> options.show());

        return optionsButton;
    }

    private Button getScreenshotButton () {
        Button saveScreenshotButton = new Button();
        saveScreenshotButton.textProperty().bind(VennInternationalization.createStringBinding("screenshot_button"));
        saveScreenshotButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(saveScreenshotButton, Priority.ALWAYS);

        saveScreenshotButton.setOnAction(event -> {
            File file = getFileLocationFromChooser(this.app.stage, "png", true);
            if (file != null) {
                boolean success = this.saveScreenshot(file);
                System.out.println("Saved file: " + success);
            }
        });

        return saveScreenshotButton;
    }

    private HBox getImportExportButtons () {
        HBox importExportButtons = new HBox(5);

        Button importButton = new Button();
        importButton.textProperty().bind(VennInternationalization.createStringBinding("import_button"));
        importButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(importButton, Priority.ALWAYS);

        importButton.setOnAction(event -> {
            fileHandler.importVenn(null);
        });

        Button exportButton = new Button();
        exportButton.textProperty().bind(VennInternationalization.createStringBinding("export_button"));
        exportButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(exportButton, Priority.ALWAYS);

        exportButton.setOnAction(event -> {
            fileHandler.exportVenn();
        });

        // no while running game mode
        importButton.disableProperty().bind(gameModeHandler.running);

        importExportButtons.getChildren().addAll(importButton, exportButton);
        return importExportButtons;
    }

    private HBox getUndoRedoButtons () {
        HBox undoRedoButtons = new HBox(5);

        Button undoButton = new Button();
        undoButton.textProperty().bind(VennInternationalization.createStringBinding("undo_button"));
        undoButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(undoButton, Priority.ALWAYS);

        undoButton.setOnAction(event -> {
            changeHandler.undo();
        });
        undoButton.setDisable(true);
        undoButton.disableProperty().bind(changeHandler.canUndo.not());

        Button redoButton = new Button();
        redoButton.textProperty().bind(VennInternationalization.createStringBinding("redo_button"));
        redoButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(redoButton, Priority.ALWAYS);

        redoButton.setOnAction(event -> {
            changeHandler.redo();
        });
        redoButton.setDisable(true);
        redoButton.disableProperty().bind(changeHandler.canRedo.not());

        undoRedoButtons.getChildren().addAll(undoButton, redoButton);
        return undoRedoButtons;
    }

    private boolean saveScreenshot (File file) {
        Group vennGroup = this.app.vennGroup;
        WritableImage writableImage = new WritableImage(
            (int) vennGroup.getBoundsInParent().getWidth(),
            (int) vennGroup.getBoundsInParent().getHeight()
        );
        SnapshotParameters spa = new SnapshotParameters();
        Image snapshot = vennGroup.snapshot(spa, writableImage);
        spa.setTransform(Transform.scale(5, 5));
        try {
            return ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static File getFileLocationFromChooser(Stage stage, String fileExtension, boolean save) {
        FileChooser fileChooser = new FileChooser();

        String title = fileExtension.toUpperCase();
        String extension = "*." + fileExtension;

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(title + " files (" + extension +")", extension);
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file;
        if (save) {
            file = fileChooser.showSaveDialog(stage);
        } else {
            file = fileChooser.showOpenDialog(stage);
        }

        return file;
    }

    private VBox getGameModeButtons() {
    	VBox gameModeButtons = new VBox(5);
    	HBox sideBySide = new HBox(5);

    	Button actionButton = new Button();
        actionButton.textProperty().bind(VennInternationalization.createStringBinding("gm_start"));
        actionButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(actionButton, Priority.ALWAYS);

        Button resetButton = new Button();
        resetButton.textProperty().bind(VennInternationalization.createStringBinding("gm_reset"));
        resetButton.setMaxWidth(Double.MAX_VALUE);

        resetButton.setOnAction(resetEvent -> {
            gameModeHandler.reset();
        });

        gameModeHandler.running.addListener((event, oldValue, newValue) -> {
            // running, so cancel
            if (newValue) {
                actionButton.textProperty().bind(VennInternationalization.createStringBinding("gm_stop"));
                gameModeButtons.getChildren().add(resetButton); 
            } else {
                // not running, so start
                actionButton.textProperty().bind(VennInternationalization.createStringBinding("gm_start"));
                gameModeButtons.getChildren().remove(resetButton);
                this.app.left.sectionName.set(VennInternationalization.get("left_title"));
                this.app.right.sectionName.set(VennInternationalization.get("right_title"));
                this.app.intersection.sectionName.set(VennInternationalization.get("middle_title"));
            }
        });

        actionButton.setOnAction(event -> {
            if (!gameModeHandler.running.get()) gameModeHandler.initialize();
            else gameModeHandler.reset(true);
        });
        actionButton.setDisable(false);

        Button verificationButton = new Button();
        verificationButton.textProperty().bind(VennInternationalization.createStringBinding("gm_verify"));
        verificationButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(verificationButton, Priority.ALWAYS);

        verificationButton.setOnAction(event -> {
            if (entries.getChildren().size() == 0) {
            	gameModeHandler.validate();
            } else {
            	Alert alert = new Alert(Alert.AlertType.ERROR, VennInternationalization.get("gm_msg_error_place_all"));
            	alert.showAndWait();
            }
        });
        verificationButton.setDisable(true);
        verificationButton.disableProperty().bind(gameModeHandler.running.not());

        sideBySide.getChildren().addAll(actionButton, verificationButton);
        gameModeButtons.getChildren().addAll(sideBySide);
        return gameModeButtons;
    }

    private HBox getTrashCan () {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().add(this.trash.pane);
        return box;
    }

    public void draw () {
        root = new VBox(5);

        Insets padding = new Insets(5);

        root.setPrefWidth(200);
        root.setPadding(padding);

        // init the containers and it's padding
        HBox topContainer = new HBox(5);
        VBox top = new VBox(3);

        HBox bottomContainer = new HBox(5);
        VBox bottom = new VBox(3);

        this.entries = bottom;

        top.prefHeightProperty().bind(root.heightProperty());
        bottom.prefHeightProperty().bind(root.heightProperty());

        topContainer.setPadding(padding);
        bottomContainer.setPadding(padding);

        // styles for the columns
        topContainer.getStyleClass().add("column-border");
        bottomContainer.getStyleClass().add("column-border");

        // importing hanndler
        fileHandler = new VennFileHandler(app, handler, right, left, intersection);

        // adding buttons
        Button addButton = this.getAddButton();
        Button optionsButton = this.getOptionsButton();
        Button screenshotButton = this.getScreenshotButton();
        Button deleteAllButton = this.getDeleteAllButton();

        HBox importExportHBox = this.getImportExportButtons();
        HBox undoRedoBox = this.getUndoRedoButtons();

        VBox gameModeButtons = this.getGameModeButtons();

        VBox topControls = new VBox(5);

        // color pickers
        topControls.getChildren().addAll(
            VennPanelTitle.create(VennInternationalization.createStringBinding("add_entry_title"), false, "left-column-title"),
            addButton,
            VennPanelTitle.create(VennInternationalization.createStringBinding("options_title"), false, "left-column-title"),
            optionsButton,
            deleteAllButton,
            VennPanelTitle.create(VennInternationalization.createStringBinding("screenshot_title"), false, "left-column-title"),
            screenshotButton,
            VennPanelTitle.create(VennInternationalization.createStringBinding("import_export_title"), false, "left-column-title"),
            importExportHBox,
            VennPanelTitle.create(VennInternationalization.createStringBinding("undo_redo_title"), false, "left-column-title"),
            undoRedoBox,
            VennPanelTitle.create(VennInternationalization.createStringBinding("gm_title"), false, "left-column-title"),
            gameModeButtons
        );

        // top scrollpane
        ScrollPane topScrollable = new ScrollPane(topControls);
        topScrollable.setFitToHeight(true);
        topScrollable.setFitToWidth(true);

        // trash can
        HBox trashCan = this.getTrashCan();
        Region filler = new Region();
        VBox.setVgrow(filler, Priority.ALWAYS);
        top.getChildren().addAll(topScrollable, filler, trashCan);

        // bottom scrollpane
        ScrollPane bottomScrollable = new ScrollPane(bottom);
        bottomScrollable.fitToHeightProperty().set(true);
        bottomScrollable.fitToWidthProperty().set(true);
//        bottom.getChildren().add(bottomScrollable);
        bottomContainer.getChildren().add(bottomScrollable);

        topContainer.getChildren().add(top);

        root.getChildren().addAll(topContainer, bottomContainer);

        VBox.setVgrow(topContainer, Priority.ALWAYS);
        HBox.setHgrow(top, Priority.ALWAYS);
        VBox.setVgrow(bottomContainer, Priority.SOMETIMES);
        VBox.setVgrow(bottom, Priority.ALWAYS);
        HBox.setHgrow(bottom, Priority.ALWAYS);
    }
}
