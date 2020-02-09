package venn;

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

public class VennAddEntry {

    public static Text display()
    {
//        VennEntry entry = new VennEntry("");

        Text text = new Text();

        Stage window = new Stage();
        window.setTitle("Add data");
        window.setMinWidth(400);
        window.setMinHeight(200);

        // prevents user to modify other window
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText("Add the new Data");

        TextField data = new TextField();
        data.autosize();
        data.setMaxWidth(300);

        // cancel button
        Button closeButton = new Button("Cancel");
        closeButton.setOnAction(e -> window.close());

        //Add button.
        Button addButton = new Button("Add");
        addButton.setOnAction( e->{
//            message.setText(data.getText());
//            entry.setData(data.getText());
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

        return text;
    }

}