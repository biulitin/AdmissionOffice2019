/*
    Controller for tab: 100б.
 */

package controllers_tabs;

import backend.MessageProcessing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

import backend.ModelDBConnection;
import controllers_simple.*;

public class OlympiadsTabController {
    @FXML
    public FlowPane buttonsPane;
    @FXML
    private Button addOlympiadsButton;

    String[] fields, fieldsTypes;
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

    String aid;

    public void fillTab(FXMLLoader tabController) throws Exception {
        prepareData();
        addButtons(buttonsPane, 2, tabController);
    }

    public void addButtons(Pane pane, int numberOfVisibleButtons, FXMLLoader tabController) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        pane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        pane.getChildren().add(newButtonsPane);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        addEditDeleteButtonsController.setParameters("100б", tabController, fields, fieldsTypes, fieldsControllers);
        if (numberOfVisibleButtons == 2) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.setWidthHideButtons(250.0, 50.0, 2);
        } else if (numberOfVisibleButtons == 1) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.hideButton(1);
            addEditDeleteButtonsController.setWidthHideButtons(200.0, 50.0, 1);
        }
        addEditDeleteButtonsController.setEditable(false);
    }

    public void prepareData() throws Exception {
        ModelDBConnection.setDefaultConnectionParameters();
        ModelDBConnection.initConnection();

        query = "";

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
                    dateInputPatternController.setWidthHeight(350.0, 35.0, 140.0);
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                case "int":
                    loader = new FXMLLoader();
                    if (Pattern.compile("(id_).*").matcher(fields[i]).matches()) {
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        if (fields[i].equals("id_formOfEducation")) {
                            choiceInputPatternController.setWidthHeight(450.0, 35.0, 250.0);
                        } else {
                            choiceInputPatternController.setWidthHeight(450.0, 35.0, 140.0);
                        }
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                    } else if (fields[i].equals("is_enrolled")) {
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(100.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                    }
            }
        }

        FlowPane modalButtonsPane = new FlowPane();
        //addButtons(modalButtonsPane, 1, tabs);
       // modalButtonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        //modalButtonsPane.setPrefWidth(450.0);
        flowPane.getChildren().add(modalButtonsPane);

        Scene scene = new Scene(flowPane);
        modalStage.setScene(scene);
        modalStage.setWidth(500.0);
        modalStage.setHeight(350.0);
        modalStage.show();
    }

    public void setEditable(Boolean value) {
        for (int i = 0; i < fieldsControllers.length; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);
                    break;
                case "int":
                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                    intInputPatternController.setEditable(value);
                    break;
                case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    textInputPatternController.setEditable(value);
                    break;

            }
        }
    }

    public void setFieldsData(String aid) throws SQLException {
        this.aid = aid;

        query = "SELECT * FROM AbiturientDocumentsFor100balls WHERE id_abiturient = " + aid;

        Statement statement = ModelDBConnection.getConnection().createStatement();
        rset = statement.executeQuery(query);

        int columnIndex = 1;

        if (rset.next()) {
            for (int i = 0; i < fieldsControllers.length; i++) {
                switch (fieldsTypes[i]) {
                    case "date":
                        columnIndex = 9;

                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(rset.getString(columnIndex));
                        break;
                    case "int":

                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();

                        if (Pattern.compile("(id_o).*").matcher(fields[i]).matches()) {
                            columnIndex = 2;
                        }

                        intInputPatternController.setFieldData(rset.getString(columnIndex));

                        break;
                    case "varchar":

                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        if (Pattern.compile("(nameOf).*").matcher(fields[i]).matches()) {
                            columnIndex = 3;
                        } else if (Pattern.compile("(diplomaD).*").matcher(fields[i]).matches()) {
                            columnIndex = 4;
                        } else if (Pattern.compile("(diplomaS).*").matcher(fields[i]).matches()) {
                            columnIndex = 5;
                        } else if (Pattern.compile("(olympLevel).*").matcher(fields[i]).matches()) {
                            columnIndex = 6;
                        } else if (Pattern.compile("(olympLevel).*").matcher(fields[i]).matches()) {
                            columnIndex = 6;
                        } else if (Pattern.compile("(series_).*").matcher(fields[i]).matches()) {
                            columnIndex = 7;
                        } else if (Pattern.compile("(number_).*").matcher(fields[i]).matches()) {
                            columnIndex = 8;
                        } else if (Pattern.compile("(issued_).*").matcher(fields[i]).matches()) {
                            columnIndex = 10;
                        }

                        textInputPatternController.setFieldData(rset.getString(columnIndex));

                        break;
                }
            }
        }
    }

    public int checkData() {
        int errorCount = 0, currentErrorCode = 0;

        for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = dateInputPatternController.checkData();

                    break;
                case "int":
                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = intInputPatternController.checkData();

                    break;
                case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = textInputPatternController.checkData();

                    break;
            }

            //errorCount += currentErrorCode;
            //System.out.println(currentErrorCode);
        }

        return errorCount;
    }
}
