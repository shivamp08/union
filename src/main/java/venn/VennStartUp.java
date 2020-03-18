package venn;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class VennStartUp extends Application {
    //Has a menu that fits entire screen
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StartWindow.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            //new view
            primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void begin(ActionEvent actionEvent) {
    }

    public void close(MouseEvent mouseEvent) {
    }
}