package application;

import controllers_simple.*;
import controllers_tabs.*;

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
            //loader.setLocation(getClass().getResource("../patterns_tabs/IndividualAchievementsTab.fxml"));
            //loader.setLocation(getClass().getResource("../patterns_tabs/CompetitiveGroupsTab.fxml"));
        	//loader.setLocation(getClass().getResource("../patterns_tabs/EducationTab.fxml"));
        	//loader.setLocation(getClass().getResource("../patterns_tabs/PassportTab.fxml"));
        	//loader.setLocation(getClass().getResource("../patterns_tabs/AdditionalInfoTab.fxml"));
        	
            AnchorPane root;
            root = (AnchorPane) loader.load();
            
            /*MainWindowController mainWindowController = loader.getController();
            mainWindowController.fillInPatterns();*/
            
            /*InsertFormController insertFormController = loader.getController();
            insertFormController.createForm();*/
            
            /*IndividualAchievementsTabController individualAchievementsTabController = loader.getController();
            individualAchievementsTabController.fillTab();*/
            
            /*CompetitiveGroupsTabController competitiveGroupsTabController = loader.getController();
            competitiveGroupsTabController.fillTab();*/
            
            /*EducationTabController educationTabController = loader.getController();
            educationTabController.fillTab();*/

            /*PassportTabController passportTabController = loader.getController();
            passportTabController.fillTab();*/
            
            /*AdditionalInfoTabController additionalInfoTabController = loader.getController();
            additionalInfoTabController.fillTab();*/

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
