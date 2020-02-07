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

    String aid;

    public void fillTab(FXMLLoader tabController) throws Exception {
        ModelDBConnection.setDefaultConnectionParameters();
        //ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Доп. сведения"));
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
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                    break;
                case "int":
                    if (Pattern.compile("(id_cat).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(678.0, 35.0, 160.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    } else {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        IntInputPatternController intInputPatternController = loader.getController();
                        // intInputPatternController.setWidthHeight(340.0, 35.0, 80.8);
                        intInputPatternController.setWidthHeight(210.0, 35.0, 80.8);
                        intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        break;
                    }
                case "varchar":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    TextInputPatternController textInputPatternController = loader.getController();
                    textInputPatternController.setWidthHeight(210.0, 35.0, 70.8);
                    // textInputPatternController.setWidthHeight(285.0, 35.0, 45.74);
                    textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
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
        addEditDeleteButtonsController.setParameters("Доп. сведения", tabController, fields, fieldsTypes, fieldsControllers);

        setEditable(false);
        setFieldsData("0");
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

    public void setFieldsData(String aid) throws Exception {
        this.aid = aid;

        query = ModelDBConnection.getQueryByTabName("Доп. сведения");

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
