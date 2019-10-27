package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader();
        	//loader.setLocation(getClass().getResource("MainWindow.fxml"));
        	loader.setLocation(getClass().getResource("InsertForm.fxml"));
        	
            AnchorPane root;
            root = (AnchorPane) loader.load();
            
            //MainWindowController mainWindowController = loader.getController();
            
            InsertFormController insertFormController = loader.getController();
            insertFormController.createForm();
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
