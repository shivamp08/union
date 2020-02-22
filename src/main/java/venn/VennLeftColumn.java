package venn;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class VennLeftColumn {
    VBox root;

    VBox entries;

    VennEntryHandler handler;
    VennDeleteEntry trash;

    VennSectionLeft left;
    VennSectionRight right;
    VennIntersection intersection;

    public VennLeftColumn(VennEntryHandler handler) {
        this.handler = handler;
        this.trash = new VennDeleteEntry(handler);

//        this.draw();
    }

    public VennLeftColumn() {
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
        Button add = new Button("Add Entry");
        add.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(add, Priority.ALWAYS);

        add.setOnAction(event -> VennEntryModalHandler.add(this.handler));
        Tooltip.install(add, CtrlN);

        return add;
    }

    private Button getAddMultipleButton () {
        Button add = new Button("Add Multiple");
        add.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(add, Priority.ALWAYS);

//        add.setOnAction(event -> VennAddEntry.add(this.handler));

        return add;
    }

    private HBox getTrashCan () {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().add(this.trash.pane);
        return box;
    }

    private static VBox createColorPicker (VennSection section) {
        VBox box = new VBox();
        ColorPicker picker = new ColorPicker();
        Label label = new Label(section.sectionName.getValue() + " Color:");
        box.getChildren().addAll(label, picker);

        // bind for updates
        section.sectionName.addListener((event, old, newValue) -> {
            label.textProperty().set(newValue + " Color:");
        });

        picker.prefWidthProperty().bind(box.widthProperty());

        // init and wait for update
        picker.setValue(section.color.getValue());
        picker.setOnAction(event -> {
            section.color.set(picker.getValue());
        });

        return box;
    }

    private VBox[] getColorPickers () {
        VBox leftPickerBox = createColorPicker(left);

        VBox rightPickerBox = createColorPicker(right);

        VBox intersectionPickerBox = createColorPicker(intersection);

        return new VBox[]{ leftPickerBox, rightPickerBox, intersectionPickerBox };
    }

    public void draw () {
        root = new VBox(5);

        Insets padding = new Insets(5);

        root.setPrefWidth(200);
        root.setPadding(padding);

        // init the containers and it's padding
        HBox topContainer = new HBox(5);
        VBox top = new VBox(5);
        topContainer.getChildren().add(top);
        HBox bottomContainer = new HBox(5);
        VBox bottom = new VBox(5);
//        bottomContainer.getChildren().add(bottom);
        this.entries = bottom;

        top.prefHeightProperty().bind(root.heightProperty());
        bottom.prefHeightProperty().bind(root.heightProperty());

        topContainer.setPadding(padding);
        bottomContainer.setPadding(padding);

        // styles for the columns
        topContainer.getStyleClass().add("column-border");
        bottomContainer.getStyleClass().add("column-border");

        // adding buttons
        Button addButton = this.getAddButton();
//        Button addMultipleButton = this.getAddMultipleButton();

        // color pickers
        VBox[] colorPickers = this.getColorPickers();
        top.getChildren().addAll(
            VennPanelTitle.create("Add Entries", false, "left-column-title"),
            addButton,
//            addMultipleButton,
            VennPanelTitle.create("Colors", false, "left-column-title")
        );

        for (VBox colorPickerBox : colorPickers) {
            top.getChildren().add(colorPickerBox);
        }

        // trash can
        HBox trashCan = this.getTrashCan();
        Region filler = new Region();
        VBox.setVgrow(filler, Priority.ALWAYS);
        top.getChildren().addAll(filler, trashCan);

        // bottom scrollpane
        ScrollPane bottomScrollable = new ScrollPane(bottom);
        bottomScrollable.fitToHeightProperty().set(true);
        bottomScrollable.fitToWidthProperty().set(true);
//        bottom.getChildren().add(bottomScrollable);
        bottomContainer.getChildren().add(bottomScrollable);

        root.getChildren().addAll(topContainer, bottomContainer);

        VBox.setVgrow(topContainer, Priority.ALWAYS);
        HBox.setHgrow(top, Priority.ALWAYS);
        VBox.setVgrow(bottomContainer, Priority.SOMETIMES);
        VBox.setVgrow(bottom, Priority.ALWAYS);
        HBox.setHgrow(bottom, Priority.ALWAYS);
    }
}
