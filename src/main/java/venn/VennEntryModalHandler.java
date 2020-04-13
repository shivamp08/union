package venn;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static venn.Main.changeHandler;
import static venn.Main.gameModeHandler;

enum ModalType {
    AddEntry,
    EditEntry,
    EditTitle,
}

public class VennEntryModalHandler {
	//Add
    public static void add (VennEntryHandler handler) {
        // no adding while running game mode
        if (gameModeHandler.running.get()) return;

        final int maxLength = 40;
        String[] add = VennEntryModalHandler.create(
            ModalType.AddEntry,
            VennInternationalization.createStringBinding("add_title"),
            VennInternationalization.createStringBinding("add_prompt", maxLength),
            VennInternationalization.createStringBinding("add_action"),
            null,
            "",
            null,
            null,
            null,
            maxLength
        );

        if (add == null) return;

        System.out.println("change from add entry");
        changeHandler.calculateChange();

        VennTextEntry entry = new VennTextEntry(add[0], add[1], add[2], new Font(add[3], Integer.parseInt(add[4])), add[4], add[5]);
        handler.addEntry(entry, false);
        
        changeHandler.calculateChange();

        // mm yes, booleans with strings
        if (add[6].contentEquals("true")) add(handler);
    }

    public static void edit (ModalType type, StringProperty currTitle, StringProperty currDes, ObjectProperty<Color> draggableColor, ObjectProperty<Font> draggableFont, ObjectProperty<Color> fColor) {
        String[] edited = VennEntryModalHandler.create(
            type,
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt"),
            VennInternationalization.createStringBinding("edit_action"),
            currTitle.getValue(),
            currDes.getValue(),
            draggableColor.getValue(),
            draggableFont,
            fColor.getValue(),
            -1
        );

        if (edited == null) return;

        System.out.println("adding from edit entry");
        changeHandler.calculateChange();

        currTitle.set(edited[0]);
        currDes.set(edited[1]);
        draggableColor.set(Color.valueOf(edited[2]));
        draggableFont.set(new Font(edited[3], Integer.parseInt(edited[4])));
        fColor.set(Color.valueOf(edited[5]));
        
        changeHandler.calculateChange();
    }

    public static void edit (ModalType type, StringProperty current, int maxLength) {
        String[] edited = VennEntryModalHandler.create(
            type,
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt_maxlength", maxLength),
            VennInternationalization.createStringBinding("edit_action"),
            current.getValue(),
            null,
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

    public static String[] create (ModalType type, StringBinding title, StringBinding prompt, StringBinding action, String current, String des, Color color, ObjectProperty<Font> font, Color fColor, int maxLength) {
    	Text text = new Text();
        Text desText = new Text();
        CheckBox addAnother = new CheckBox();

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
        
        Label entryColor = new Label();
        entryColor.textProperty().bind(VennInternationalization.createStringBinding("modal_background_color"));
        ColorPicker picker = new ColorPicker();
        HBox HColor = new HBox(5);
        HColor.setMaxWidth(300);
        HColor.getChildren().addAll(entryColor, picker);
        HColor.setAlignment(Pos.CENTER_LEFT);
        if (color != null) picker.setValue(color);
        
        Label fontColorLabel = new Label();
        fontColorLabel.textProperty().bind(VennInternationalization.createStringBinding("modal_font_color"));
        ColorPicker fontColorPicker = new ColorPicker(); 
        HBox fontColorBox = new HBox(5);
        fontColorBox.setMaxWidth(300);
        fontColorBox.getChildren().addAll(fontColorLabel, fontColorPicker); 
        fontColorBox.setAlignment(Pos.CENTER_LEFT);
        fontColorPicker.setValue(Color.BLACK);
        if (fColor == null) fontColorPicker.setValue(Color.BLACK);
        else fontColorPicker.setValue(fColor);

//        ComboBox<Label> fontSelector = new ComboBox<Label>(); 
//        fontSelector.setItems(FXCollections.observableArrayList(Main.allFonts));
//        Label fontLabel = new Label("Font:");
//        HBox HFont = new HBox(9); 
//        fontLabel.setTranslateY(4);
//        HFont.getChildren().addAll(fontLabel, fontSelector);
//        HFont.setTranslateX(100);
        
        Label fontLabel = new Label();
        fontLabel.textProperty().bind(VennInternationalization.createStringBinding("modal_font_family"));
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
        Label fontSizeLabel = new Label();
        fontSizeLabel.textProperty().bind(VennInternationalization.createStringBinding("modal_font_size"));
        HBox fontSizeBox = new HBox(5);
        fontSizeBox.getChildren().addAll(fontSizeLabel, size, fontSizeSlider);
        fontSizeSlider.setMax(32);
        fontSizeSlider.setMin(10);
        if (font != null && font.getValue() != null) {
        	fontSizeSlider.setValue(font.getValue().getSize());
        	size.setText((int)font.getValue().getSize()+"px");
        }

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

        // Add another button
        Button addAnotherButton = new Button();
        addAnotherButton.textProperty().bind(VennInternationalization.createStringBinding("add_another_action"));
        addAnotherButton.setOnAction(e -> {
            text.setText(data.getText());
            desText.setText(description.getText());
            addAnother.setSelected(true);
            window.close();
        });

        addButton.disableProperty().bind(data.lengthProperty().isEqualTo(0));
        addAnotherButton.disableProperty().bind(data.lengthProperty().isEqualTo(0));

        HBox allButtons = new HBox();
        allButtons.setPadding(new Insets(0, 10, 10, 10));
        allButtons.setSpacing(10);
        allButtons.getChildren().addAll(addButton);
        if (type == ModalType.AddEntry) {
            allButtons.getChildren().add(addAnotherButton);
        }
        allButtons.getChildren().add(closeButton);
        allButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, data);
        layout.setAlignment(Pos.CENTER);
        
        //Preview Entry
        //VennEntryPreview preview = new VennEntryPreview("Text", "Text", Color.WHITE.toString(), Font.font("System"), null, Color.BLACK.toString());
//        VennTextEntry preview = new VennTextEntry("Text", "Text", Color.BLUE.toString(), Font.font("System"), null, Color.BLACK.toString());
//        preview.setDraggable();
//        HBox pHbox = new HBox();
//        preview.draggable.get
//        pHbox.getChildren().add(preview.draggable);
        
        
        VennEntryPreview preview = new VennEntryPreview(picker.getValue(), new Font(fontSelector.getValue(), Integer.parseInt(size.getText().substring(0, 2))) , fontColorPicker.getValue()); 
        
        VBox designOptions = new VBox(10); 
        designOptions.getChildren().addAll(HColor, HFont, fontSizeBox, fontColorBox);
        
        VBox previewBox = new VBox(); 
        previewBox.getChildren().add(preview.getPreview());
        previewBox.setAlignment(Pos.CENTER);
        
        HBox options = new HBox(50); 
        options.getChildren().add(designOptions);
        options.getChildren().add(previewBox);
        options.setAlignment(Pos.CENTER_LEFT);
        options.setTranslateX(50);
        
        fontSizeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
        	preview.getLabel().setFont(new Font(fontSelector.getValue(), Integer.parseInt(size.getText().substring(0, 2))));
        });
        
        picker.valueProperty().addListener((observable, oldValue, newValue) -> {
        	preview.getPreview().setStyle("-fx-background-color: " + VennEntryHandler.getWebColor(picker.getValue()));
        });
        
        fontSelector.valueProperty().addListener((observable, oldValue, newValue) -> {
        	preview.getLabel().setFont(new Font(fontSelector.getValue(), Integer.parseInt(size.getText().substring(0, 2))));
        });
        
        fontColorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
        	preview.getLabel().setTextFill(fontColorPicker.getValue());
        });
        
        
        if (des != null) {
            layout.getChildren().addAll(descriptionLabel, description);
        }

        if (type == ModalType.AddEntry || type == ModalType.EditEntry) {
            layout.getChildren().add(options);
        }
        layout.getChildren().add(allButtons);
        //layout.getChildren().add(pane);
        
        Scene scene = new Scene(layout,100,100);
        scene.getStylesheets().add(VennEntryModalHandler.class.getResource("/styles.css").toExternalForm());
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        String[] textContent = {
            text.getText(),
            desText.getText(),
            picker.getValue().toString(),
            fontSelector.getValue(),
            size.getText().substring(0, 2),
            fontColorPicker.getValue().toString(),
            String.valueOf(addAnother.isSelected())
        };
        if (textContent[0].contentEquals("")) {
            return null;
        } else {
            return textContent;
        }
    }
}
