/*
    Controller for tab: 100б.
 */

package controllers_tabs;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import controllers_simple.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

public class AdditionalInfoTabController {
    @FXML
    public FlowPane buttonsPane;

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

    String aid;

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

    public void setEditable(Boolean value) {
        for (int i = 0; i < fieldsControllers.length; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);

                    break;
                case "int":
                    if (Pattern.compile("(id_cat).*").matcher(fields[i]).matches()) {
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

        query = "SELECT AbiturientExtraInfo.nameOfDocument, AbiturientExtraInfo.series_document," +
                "AbiturientExtraInfo.number_document, AbiturientExtraInfo.dateOf_issue," +
                "AbiturientExtraInfo.issued_by\n" +
                "FROM AbiturientExtraInfo WHERE id_abiturient = " + aid;

        int columnIndex = 1;

        Statement statement = ModelDBConnection.getConnection().createStatement();
        rset = statement.executeQuery(query);
        if (rset.next()) {
            for (int i = 0; i < fieldsControllers.length; i++) {
                switch (fieldsTypes[i]) {
                    case "date":
                        columnIndex = 7;

                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(rset.getString(columnIndex));

                        break;
                    case "int":
                        if (Pattern.compile("(id_cat).*").matcher(fields[i]).matches()) {
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
                        } else if (Pattern.compile("(series_).*").matcher(fields[i]).matches()) {
                            columnIndex = 4;
                        } else if (Pattern.compile("(number_).*").matcher(fields[i]).matches()) {
                            columnIndex = 5;
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
                    if (Pattern.compile("(id_cat).*").matcher(fields[i]).matches()) {
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
