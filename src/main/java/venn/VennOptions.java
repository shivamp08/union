package venn;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Locale;

import com.jfoenix.controls.JFXColorPicker;

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
        JFXColorPicker picker = new JFXColorPicker();
        Label label = new Label();
        label.setText(VennInternationalization.get("options_color_title", section.sectionName.getValue()));

        box.getChildren().addAll(label, picker);

        // bind for updates on either language or section title
        VennInternationalization.createStringBinding("options_color_title").addListener((obs, oldValue, newValue) -> {
            label.setText(VennInternationalization.get("options_color_title", section.sectionName.getValue()));
        });
        section.sectionName.addListener((event, old, newValue) -> {
            label.setText(VennInternationalization.get("options_color_title", section.sectionName.getValue()));
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
        Button okButton = new Button();
        okButton.textProperty().bind(VennInternationalization.createStringBinding("options_ok"));
        okButton.setOnAction(e -> {
            window.close();
        });

        HBox allButtons = new HBox(10);
        allButtons.setPadding(new Insets(0, 10, 10, 10));
        allButtons.getChildren().addAll(okButton);
        allButtons.setAlignment(Pos.CENTER);

        ComboBox<Locale> languages = new ComboBox<>();
        for (Locale locale : VennInternationalization.getSupportedLocales()) {
            languages.getItems().add(locale);
        }
        languages.setValue(VennInternationalization.getLocale());
        languages.setOnAction(event -> VennInternationalization.setLocale(languages.getValue()));

        languages.setConverter(new StringConverter<Locale>() {
            @Override
            public String toString(Locale object) {
                // display the friendly name
                return object.getDisplayName(VennInternationalization.getLocale());
            }

            @Override
            public Locale fromString(String string) {
                return new Locale(string);
            }
        });

        languages.setMaxWidth(300);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            VennPanelTitle.create(VennInternationalization.createStringBinding("options_language_title"), false, "left-column-title"),
            languages,
            VennPanelTitle.create(VennInternationalization.createStringBinding("options_colors_title"), false, "left-column-title")
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

        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }
}
