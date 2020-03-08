package venn;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static venn.Main.changeHandler;

public class VennEntryModalHandler {
    public static void add (VennEntryHandler handler) {
        String[] add = VennEntryModalHandler.create("Add entry", "What do you want to add?", "Add", null, "", -1);

        if (add == null) return;

        System.out.println("change from add entry");
        changeHandler.calculateChange();

        VennTextEntry entry = new VennTextEntry(add[0], add[1]);
        handler.addEntry(entry, false);
    }

    public static void edit (StringProperty currTitle, StringProperty currDes) {
        String[] edited = VennEntryModalHandler.create("Edit entry", "Text:", "Update", currTitle.getValue(), currDes.getValue(), -1);

        if (edited == null) return;

        System.out.println("adding from edit entry");
        changeHandler.calculateChange();

        currTitle.set(edited[0]);
        currDes.set(edited[1]);
    }

    public static void edit (StringProperty current, int maxLength) {
        String[] edited = VennEntryModalHandler.create("Edit entry", "Text (Max Length "  + maxLength + "):", "Update", current.getValue(), null, maxLength);

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

    public static String[] create (String title, String prompt, String action, String current, String des, int maxLength) {
        Text text = new Text();
        Text desText = new Text(); 

        Stage window = new Stage();
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(300);
        if (des == null) window.setHeight(200);

        // prevents user to modify other window
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(prompt);

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
        descriptionLabel.setText("Description");
        
        // cancel button
        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> window.close());

        data.setOnAction(e -> {
            text.setText(data.getText());
            desText.setText(description.getText());
            window.close();
        });

        //Add button.
        Button addButton = new Button(action);
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
        layout.getChildren().add(allButtons);

        Scene scene = new Scene(layout,100,100);
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        String[] textContent = {text.getText(), desText.getText()};
        if (textContent[0].contentEquals("")) {
            return null;
        } else {
            return textContent;
        }
    }
}
