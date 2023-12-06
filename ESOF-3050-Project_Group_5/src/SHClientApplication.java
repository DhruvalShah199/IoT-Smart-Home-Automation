/**
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SHClientApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception { 
		Parent root = FXMLLoader.load(getClass().getResource("SmartHomeAutomation.fxml"));

		Scene scene = new Scene(root);
		stage.setTitle("Smart Home Automation System");
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args) {
		launch(args); 
	}	
}