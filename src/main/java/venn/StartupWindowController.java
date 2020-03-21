package venn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartupWindowController {

    @FXML
    void StartMainWindow(ActionEvent event) {
    	
    	MainWindow window = new  MainWindow();
    	window.Display();
    }

}
