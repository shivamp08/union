package venn;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static venn.Main.changeHandler;

public class VennOptions {
    VennSectionLeft left;
    VennSectionRight right;
    VennIntersection intersection;

    public VennOptions (VennSectionLeft left, VennSectionRight right, VennIntersection intersection) {
        this.left = left;
        this.right = right;
        this.intersection = intersection;
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

//        picker.prefWidthProperty().bind(box.widthProperty());
        picker.setMaxWidth(300);

        // init and wait for update
        picker.setValue(section.color.getValue());
        picker.setOnAction(event -> {
            section.color.set(picker.getValue());
        });

        box.setAlignment(Pos.CENTER);

        return box;
    }

    private VBox[] getColorPickers () {
        VBox leftPickerBox = createColorPicker(left);

        VBox rightPickerBox = createColorPicker(right);

        VBox intersectionPickerBox = createColorPicker(intersection);

        return new VBox[]{ leftPickerBox, rightPickerBox, intersectionPickerBox };
    }

    public void show () {
        Stage window = new Stage();
        window.setTitle("Options");
        window.setWidth(400);
        window.setHeight(400);

        // prevents user to modify other window
        window.initModality(Modality.APPLICATION_MODAL);

        //Add button.
        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            changeHandler.calculateChange();
            window.close();
        });

        HBox allButtons = new HBox(10);
        allButtons.setPadding(new Insets(0, 10, 10, 10));
        allButtons.getChildren().addAll(okButton);
        allButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            VennPanelTitle.create("Colors", false, "left-column-title")
        );
        layout.setAlignment(Pos.CENTER);

        VBox[] colorPickers = this.getColorPickers();
        for (VBox colorPicker : colorPickers) {
            layout.getChildren().add(colorPicker);
        }
        Region filler = new Region();
        VBox.setVgrow(filler, Priority.ALWAYS);
        layout.getChildren().addAll(filler, allButtons);

        Scene scene = new Scene(layout,100,100);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // some annoying code to make it actually detect when closing the window
        window.sceneProperty().addListener((obs, oldScene, newScene) -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) newScene.getWindow();
                stage.setOnCloseRequest(e -> {
                    changeHandler.calculateChange();
                });
            });
        });

        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
