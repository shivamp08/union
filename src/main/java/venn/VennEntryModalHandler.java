package venn;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VennEntryModalHandler {
    public static void add (VennEntryHandler handler) {
        String add = VennEntryModalHandler.create("Add entry", "What do you want to add?", "Add", null);

        if (add == null) return;

        VennTextEntry entry = new VennTextEntry(add);
        handler.addEntry(entry);
    }

    public static void edit (StringProperty current) {
        String edited = VennEntryModalHandler.create("Edit entry", "Text:", "Update", current.getValue());

        if (edited == null) return;

        current.set(edited);
    }

    public static String create (String title, String prompt, String action, String current) {
        Text text = new Text();

        Stage window = new Stage();
        window.setTitle(title);
        window.setWidth(400);
        window.setHeight(200);

        // prevents user to modify other window
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(prompt);

        TextField data = new TextField();
        if (current != null) {
            data.setText(current);
        }
        data.autosize();
        data.setMaxWidth(300);

        // cancel button
        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> window.close());

        data.setOnAction(e -> {
            text.setText(data.getText());
            window.close();
        });

        //Add button.
        Button addButton = new Button(action);
        addButton.setOnAction( e->{
            text.setText(data.getText());
            window.close();
        });

        HBox allButtons = new HBox();
        allButtons.setPadding(new Insets(0, 10, 10, 10));
        allButtons.setSpacing(10);
        allButtons.getChildren().addAll(addButton , closeButton);
        allButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, data, allButtons);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout,100,100);
        window.setScene(scene);
        window.showAndWait();

        String textContent = text.getText();
        if (textContent.contentEquals("")) {
            return null;
        } else {
            return textContent;
        }
    }
}
