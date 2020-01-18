package controllers_tabs;

import backend.ModelDBConnection;
import controllers_simple.AddEditDeleteButtonsController;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSetMetaData;

public class AdditionalDataController {
    @FXML
    public FlowPane buttonsPane;

    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    int countFields;

    String query;
    Dotenv dotenv = Dotenv.load();

    public void prepareData() throws Exception {
        ModelDBConnection.setConnectionParameters("MSServer",
                                                            dotenv.get("DB_HOST"),
                                                            dotenv.get("DB_NAME"),
                                                            dotenv.get("DB_USER"),
                                                            dotenv.get("DB_PASS"));
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

    public void fillTab() throws Exception {
        prepareData();
        addButtons(buttonsPane, 3);
    }

    public void addButtons(Pane pane, int numberOfVisibleButtons) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        pane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        pane.getChildren().add(newButtonsPane);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        if (numberOfVisibleButtons == 3) {
            addEditDeleteButtonsController.setWidthHideButtons(250.0, 50.0, 3);
        }
        else if (numberOfVisibleButtons == 2) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.setWidthHideButtons(200.0, 50.0, 2);
        }
        else if (numberOfVisibleButtons == 1) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.hideButton(1);
            addEditDeleteButtonsController.setWidthHideButtons(150.0, 50.0, 1);
        }
        addEditDeleteButtonsController.setEditable(false);
    }
}
