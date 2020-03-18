package venn;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static venn.Main.changeHandler;

public class VennEntryModalHandler {
    public static void add (VennEntryHandler handler) {
        final int maxLength = 40;
        String[] add = VennEntryModalHandler.create(
            VennInternationalization.createStringBinding("add_title"),
            VennInternationalization.createStringBinding("add_prompt", maxLength),
            VennInternationalization.createStringBinding("add_action"),
            null,
            "",
            null,
            maxLength
        );

        if (add == null) return;

        System.out.println("change from add entry");
        changeHandler.calculateChange();

        
        VennTextEntry entry = new VennTextEntry(add[0], add[1], add[2]);
        handler.addEntry(entry, false);
        
        changeHandler.calculateChange();
    }

    public static void edit (StringProperty currTitle, StringProperty currDes, Color color) {
        String[] edited = VennEntryModalHandler.create(
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt"),
            VennInternationalization.createStringBinding("edit_action"),
            currTitle.getValue(),
            currDes.getValue(),
            color,
            -1
        );

        if (edited == null) return;

        System.out.println("adding from edit entry");
        changeHandler.calculateChange();

        currTitle.set(edited[0]);
        currDes.set(edited[1]);
    }

    public static void edit (StringProperty current, int maxLength) {
        String[] edited = VennEntryModalHandler.create(
            VennInternationalization.createStringBinding("edit_title"),
            VennInternationalization.createStringBinding("edit_prompt_maxlength", maxLength),
            VennInternationalization.createStringBinding("edit_action"),
            current.getValue(),
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

    public static String[] create (StringBinding title, StringBinding prompt, StringBinding action, String current, String des, Color color, int maxLength) {
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
        HColor.getChildren().addAll(entryColor, picker); 
        entryColor.setTranslateY(4);
        HColor.setTranslateX(100);
        
        if (color != null) picker.setValue(color);
        
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
        allButtons.getChildren().addAll(addButton , closeButton);
        allButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, data);
        layout.setAlignment(Pos.CENTER);

        if (des != null) {
            layout.getChildren().addAll(descriptionLabel, description);
        }
        layout.getChildren().add(HColor);
        layout.getChildren().add(allButtons);

        Scene scene = new Scene(layout,100,100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        String[] textContent = {text.getText(), desText.getText(), picker.getValue().toString()};
        if (textContent[0].contentEquals("")) {
            return null;
        } else {
            return textContent;
        }
    }
}
