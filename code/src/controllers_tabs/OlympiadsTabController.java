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
        ModelDBConnection.setDefaultConnectionParameters();
        //ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("100б"));
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel(i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
        }

        FXMLLoader loader;
        Pane newPane;

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setWidthHeight(445.0, 35.0, 160.0);
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                    break;
                case "int":
                    if (Pattern.compile("(id_oly).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(678.0, 35.0, 160.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    } else {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(210.0, 35.0, 80.8);
                        intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                        break;
                    }
                case "varchar":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    TextInputPatternController textInputPatternController = loader.getController();
                    textInputPatternController.setWidthHeight(210.0, 35.0, 70.8);
                    textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                    break;
            }
        }

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        newPane = (Pane) loader.load();
        buttonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        buttonsPane.getChildren().add(newPane);

        AddEditDeleteButtonsController addEditDeleteButtonsController = loader.getController();
        addEditDeleteButtonsController.setParameters("100б", tabController, fields, fieldsTypes, fieldsControllers);

        addEditDeleteButtonsController.hideButton(0);
        addEditDeleteButtonsController.setWidthHideButtons(250.0, 50.0, 2);

        setEditable(false);
        setFieldsData("0");
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
            addEditDeleteButtonsController.hideButton2(0);
            addEditDeleteButtonsController.setWidthHideButtons(250.0, 35.0, 2);
        }
        else if (numberOfVisibleButtons == 1) {
            addEditDeleteButtonsController.hideButton2(0);
            addEditDeleteButtonsController.hideButton2(1);
            addEditDeleteButtonsController.setWidthHideButtons(200.0, 50.0, 1);
        }
        addEditDeleteButtonsController.setEditable(false);
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
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                case "int":
                    loader = new FXMLLoader();
                    if (Pattern.compile("(id_oly).*").matcher(fields[i]).matches()) {
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(450.0, 35.0, 140.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                    } else {
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(210.0, 35.0, 80.8);
                        intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                    }
                case "varchar":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    flowPane.getChildren().add(newPane);

                    TextInputPatternController textInputPatternController = loader.getController();
                    textInputPatternController.setWidthHeight(450.0, 35.0, 140.0);
                    textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientDocumentsFor100balls"));
                    break;
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

    public void setEditable(Boolean value) {
        for (int i = 0; i < fieldsControllers.length; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);
                    break;
                case "int":
                    if (Pattern.compile("(id_oly).*").matcher(fields[i]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        intInputPatternController.setEditable(value);
                    }

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

        query = ModelDBConnection.getQueryByTabName("100б");

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
                        if (Pattern.compile("(id_oly).*").matcher(fields[i]).matches()) {
                            columnIndex = 2;

                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(rset.getString(columnIndex));
                        } else {
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(rset.getString(columnIndex));
                        }

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
                    if (Pattern.compile("(id_oly).*").matcher(fields[i]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();

                        if (currentErrorCode > 0) {
                            MessageProcessing.displayErrorMessage(30);
                            return currentErrorCode;
                        }
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }

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
