package venn;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static venn.Main.changeHandler;

import java.util.ArrayList;

enum ModalType {
    Entry,
    Title,
}

public class VennEntryModalHandler {
	//Add
    public static void add (VennEntryHandler handler) {
        final int maxLength = 40;
        String[] add = VennEntryModalHandler.create(
            ModalType.Entry,
            VennInternationalization.createStringBinding("add_title"),
            VennInternationalization.createStringBinding("add_prompt", maxLength),
            VennInternationalization.createStringBinding("add_action"),
            null,
            "",
            null,
            null,
            maxLength
        );

        if (add == null) return;

        System.out.println("change from add entry");
        changeHandler.calculateChange();

        VennTextEntry entry = new VennTextEntry(add[0], add[1], add[2], new Font(add[3], Integer.parseInt(add[4])), add[4]);
        handler.addEntry(entry, false);
        
        changeHandler.calculateChange();
    }

    public static void edit (StringProperty currTitle, StringProperty currDes, ObjectProperty<Color> draggableColor, ObjectProperty<Font> draggableFont) {
        String[] edited = VennEntryModalHandler.create(
            ModalType.Entry,
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt"),
            VennInternationalization.createStringBinding("edit_action"),
            currTitle.getValue(),
            currDes.getValue(),
            draggableColor.getValue(),
            draggableFont,
            -1
        );

        if (edited == null) return;

        System.out.println("adding from edit entry");
        changeHandler.calculateChange();

        currTitle.set(edited[0]);
        currDes.set(edited[1]);
        draggableColor.set(Color.valueOf(edited[2]));
        draggableFont.set(new Font(edited[3], Integer.parseInt(edited[4])));
    }

    public static void edit (StringProperty current, int maxLength) {
        String[] edited = VennEntryModalHandler.create(
            ModalType.Title,
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt_maxlength", maxLength),
            VennInternationalization.createStringBinding("edit_action"),
            current.getValue(),
            null,
            null,
            null,
            maxLength
        );

        if (edited == null) return;

        current.set(edited[0]);
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((obs, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    public static String[] create (ModalType type, StringBinding title, StringBinding prompt, StringBinding action, String current, String des, Color color, ObjectProperty<Font> font, int maxLength) {
    	Text text = new Text();
        Text desText = new Text(); 

        Stage window = new Stage();
        window.titleProperty().bind(title);
        window.setWidth(500);
        window.setHeight(400);
        if (des == null) window.setHeight(200);

        // prevents user to modify other window
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.textProperty().bind(prompt);

        TextField data = new TextField();
        if (current != null) {
            data.setText(current);
        }
        if (maxLength != -1) {
            addTextLimiter(data, maxLength);
        }
        data.autosize();
        data.setMaxWidth(300);
        
        TextArea description = new TextArea(); 
        if (des != null) {
        	description.setText(des);
        }
        description.setWrapText(true);
        description.setMaxHeight(75);
        description.setMaxWidth(300);

        Label descriptionLabel = new Label();
        descriptionLabel.textProperty().bind(VennInternationalization.createStringBinding("modal_description"));
        
        Label entryColor = new Label("Color:");
        ColorPicker picker = new ColorPicker();
        HBox HColor = new HBox(5);
        HColor.setMaxWidth(300);
        HColor.getChildren().addAll(entryColor, picker);
        HColor.setAlignment(Pos.CENTER_LEFT);
        if (color != null) picker.setValue(color);

//        ComboBox<Label> fontSelector = new ComboBox<Label>(); 
//        fontSelector.setItems(FXCollections.observableArrayList(Main.allFonts));
//        Label fontLabel = new Label("Font:");
//        HBox HFont = new HBox(9); 
//        fontLabel.setTranslateY(4);
//        HFont.getChildren().addAll(fontLabel, fontSelector);
//        HFont.setTranslateX(100);
        
        Label fontLabel = new Label("Font: ");
        fontLabel.setTranslateY(4);
        ComboBox<String> fontSelector = new ComboBox<>();
        fontSelector.setItems(FXCollections.observableArrayList(Main.allFonts));
        HBox HFont = new HBox(7);
        HFont.setMaxWidth(300);
        HFont.getChildren().addAll(fontLabel, fontSelector);
        HFont.setAlignment(Pos.CENTER_LEFT);
        fontSelector.setMaxWidth(300 - 50);

        Slider fontSizeSlider = new Slider();
        Text size = new Text("10px");
        Label fontSizeLabel = new Label("Font Size: ");
        HBox fontSizeBox = new HBox(5);
        fontSizeBox.getChildren().addAll(fontSizeLabel, size, fontSizeSlider);
        fontSizeSlider.setMax(32);
        fontSizeSlider.setMin(10);
        if (font != null && font.getValue() != null) fontSizeSlider.setValue(font.getValue().getSize());

        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	size.setText((int) fontSizeSlider.getValue() + "px");
        });
        fontSizeBox.setAlignment(Pos.CENTER_LEFT);
        fontSizeBox.setMaxWidth(300);

        if (font == null) {
        	fontSelector.setValue("System");
        } else {
        	fontSelector.setValue(font.getValue().getName());
        }
        
        // cancel button
        Button closeButton = new Button();
        closeButton.textProperty().bind(VennInternationalization.createStringBinding("modal_cancel"));
        closeButton.setOnAction(e -> window.close());

        data.setOnAction(e -> {
            text.setText(data.getText());
            desText.setText(description.getText());
            window.close();
        });

        //Add button.
        Button addButton = new Button();
        addButton.textProperty().bind(action);
        addButton.setOnAction( e->{
            text.setText(data.getText());
            desText.setText(description.getText());
            window.close();
        });

        HBox allButtons = new HBox();
        allButtons.setPadding(new Insets(0, 10, 10, 10));
        allButtons.setSpacing(10);
        allButtons.getChildren().addAll(addButton, closeButton);
        allButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, data);
        layout.setAlignment(Pos.CENTER);
        
        //Test

        if (des != null) {
            layout.getChildren().addAll(descriptionLabel, description);
        }

        if (type == ModalType.Entry) {
            layout.getChildren().add(HColor);
            layout.getChildren().add(HFont);
            layout.getChildren().add(fontSizeBox);
        }
        layout.getChildren().add(allButtons);
        //layout.getChildren().add(pane);
        
        Scene scene = new Scene(layout,100,100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        String[] textContent = {
            text.getText(),
            desText.getText(),
            picker.getValue().toString(),
            fontSelector.getValue(),
            size.getText().substring(0, 2)
        };
        if (textContent[0].contentEquals("")) {
            return null;
        } else {
            return textContent;
        }
    }
}
