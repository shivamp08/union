package venn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SplashController implements Initializable {


	@FXML
	private Group rootGroup;


	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		new SplashScreen().start();
	}




	class SplashScreen extends Thread
	{
		@Override
		public void run() 
		{
			try {
				Thread.sleep(5000);
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("VennFXML_Main.fxml"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}														



						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setTitle("Venn");
						stage.setScene(scene);
						stage.show(); 
						
						rootGroup.getScene().getWindow().hide();
						
					}});

				


			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
