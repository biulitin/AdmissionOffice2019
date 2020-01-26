package controllers_tabs;

import backend.ModelDBConnection;
import controllers_simple.AddEditDeleteButtonsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSetMetaData;

public class AdditionalInfoTabController {
    @FXML
    public FlowPane buttonsPane;

    String[] fields, fieldsTypes;
    FXMLLoader[] fieldsControllers;
    int countFields;

    String query;

    public void prepareData() throws Exception {
    	ModelDBConnection.setDefaultConnectionParameters();
    	//ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();

        query = "SELECT * FROM AbiturientExtraInfo";

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(query);
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel(i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
        }
    }

    public void fillTab(FXMLLoader tabController) throws Exception {
        prepareData();
        addButtons(buttonsPane, tabController);
    }

    public void addButtons(Pane pane, FXMLLoader tabController) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        pane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        pane.getChildren().add(newButtonsPane);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        addEditDeleteButtonsController.setParameters("Допсведения", tabController, fields, fieldsTypes, fieldsControllers);
        addEditDeleteButtonsController.setEditable(false);
    }
}
