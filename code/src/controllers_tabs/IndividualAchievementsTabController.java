package controllers_tabs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

import backend.ModelDBConnection;
import controllers_simple.*;

public class IndividualAchievementsTabController {
    @FXML
    public FlowPane buttonsPane;

    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    int countFields;

    String url, query;
    Connection conn;
    CallableStatement cstmt;
    ResultSet rset;
    Properties props;
    Statement st;
    ResultSet rs;

    FXMLLoader loader;
    Pane newPane;

    public void fillTab(FXMLLoader tabController) throws IOException, SQLException, ClassNotFoundException {
        prepareData();
        addButtons(buttonsPane, 2);
    }

    public void addButtons(Pane pane, int numberOfVisibleButtons) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        pane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        pane.getChildren().add(newButtonsPane);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        if (numberOfVisibleButtons == 2) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.setWidthHideButtons(250.0, 50.0, 2);
        }
        else if (numberOfVisibleButtons == 1) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.hideButton(1);
            addEditDeleteButtonsController.setWidthHideButtons(200.0, 50.0, 1);
        }
    }

    public void prepareData() throws ClassNotFoundException, SQLException {
        url = "jdbc:sqlserver://" + "localhost" + ":1433;databaseName=" + "Abiturient" + ";user="
                + "igor_sa" + ";password=" + "200352" + ";";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url);

        query = "SELECT AbiturientExtraInfo.nameOfDocument, AbiturientExtraInfo.series_document," +
                "AbiturientExtraInfo.number_document, AbiturientExtraInfo.dateOf_issue," +
                "AbiturientExtraInfo.issued_by\n" +
                "FROM AbiturientExtraInfo";
        cstmt = conn.prepareCall(query, 1004, 1007);
        rset = cstmt.executeQuery();

        rset.beforeFirst();

        ResultSetMetaData rsmd = rset.getMetaData();
        countFields = rsmd.getColumnCount();
        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
        }
    }

    public void openModalWindow() throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    flowPane.getChildren().add(newPane);

                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setWidthHeight(300.0, 35.0, 120.0);
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientExtraInfo"));
                case "varchar":
                    if(!fields[i].equals("dateOf_issue")) {
                        loader = new FXMLLoader();

                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        TextInputPatternController textInputPatternController = loader.getController();
                        switch (fields[i]) {
                            case "name_of_document":
                                textInputPatternController.setWidthHeight(450.0, 35.0, 120.0);
                                break;
                            case "issued_by":
                                textInputPatternController.setWidthHeight(450.0, 85.0, 120.0);
                                break;
                            case "series_of_document":
                                textInputPatternController.setWidthHeight(230.0, 35.0, 120.0);
                                break;
                            case "number_of_document":
                                textInputPatternController.setWidthHeight(220.0, 35.0, 80.0);
                                break;
                        }
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientExtraInfo"));
                    }
            }
        }

        FlowPane modalButtonsPane = new FlowPane();
        addButtons(modalButtonsPane, 1);
        modalButtonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        modalButtonsPane.setPrefWidth(450.0);
        flowPane.getChildren().add(modalButtonsPane);

        Scene scene = new Scene(flowPane);
        modalStage.setScene(scene);
        modalStage.setWidth(500.0);
        modalStage.setHeight(350.0);
        modalStage.show();
    }
}
