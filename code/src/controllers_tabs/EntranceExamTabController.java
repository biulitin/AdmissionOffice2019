package controllers_tabs;

import backend.ModelDBConnection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javafx.scene.layout.GridPane;
import controllers_simple.*;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

public class EntranceExamTabController {
    public Pane buttonsPane;
    public TableView<ObservableList> fieldsTable;
    public GridPane mainGridPane;
    int countFields;
    String[] fields, fieldsTypes;
    FXMLLoader[] fieldsControllers;
    String url, query;
    Connection conn;
    CallableStatement cstmt;
    ResultSet rset;

    ObservableList<ObservableList> list = FXCollections.observableArrayList();
    ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

    public void fillTab() throws SQLException, IOException {
        mainGridPane.autosize();
        ModelDBConnection.setDefaultConnectionParameters();
        ModelDBConnection.initConnection();

        query = "select AbiturientEntranceExam.id_entranceExam,AbiturientEntranceExam.id_formOfExam,AbiturientEntranceExam.id_languageOfExam,\n" +
                "AbiturientEntranceExam.groupExam,AbiturientEntranceExam.dateOf_exam,\n" +
                "AbiturientEntranceExam.score,AbiturientEntranceExam.has_100,\n" +
                "(select Abiturient.needSpecConditions from Abiturient)needSpecConditions \n" +
                "from AbiturientEntranceExam;";

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(query);
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
        }


        FXMLLoader loader;
        Pane newPane;
        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]){
                case "date":
                    if(Pattern.compile("(date).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(160.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[i],"");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                case "int":
                    if(Pattern.compile("(id_ent).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);

                    }
                   if(Pattern.compile("(id_la).*").matcher(fields[i]).matches() ){
                       TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                       loader = new FXMLLoader();
                       loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                       newPane = (Pane) loader.load();
                       fieldsControllers[i] = loader;
                       ChoiceInputPatternController choiceInputPatternController = loader.getController();
                       choiceInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                       choiceInputPatternController.setParameters(fields[i],"");
                       fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                           public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                               return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                           }
                       });
                       fieldsTable.getColumns().add(fieldData);
                       choiceInputPatternController.setFieldData("");
                       paneObservableList.add(newPane);
                   }
                   if(Pattern.compile("(id_form).*").matcher(fields[i]).matches() ){
                       TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                       loader = new FXMLLoader();
                       loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                       newPane = (Pane) loader.load();
                       fieldsControllers[i] = loader;
                       ChoiceInputPatternController choiceInputPatternController = loader.getController();
                       choiceInputPatternController.setWidthHeight(130.0,35.0, 0.0);
                       choiceInputPatternController.setParameters(fields[i], "");
                       fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                           public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                               return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                           }
                       });
                       fieldsTable.getColumns().add(fieldData);
                       choiceInputPatternController.setFieldData("");
                       paneObservableList.add(newPane);
                   }
                    if(Pattern.compile("(score)").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0,35.0, 0.0);
                        intInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(has_).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(50.0,35.0);
                        boolInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(6));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(need).*").matcher(fields[i]).matches() ) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        mainGridPane.add(newPane,0,1);
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(350.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEntranceExam"));
                    }
                   break;

                case "varchar":
                    if(Pattern.compile("(grou).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(3));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    break;
            }
        }
        list.add(paneObservableList);
        fieldsTable.getItems().addAll(list);

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        newPane = (Pane) loader.load();
        buttonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        buttonsPane.getChildren().add(newPane);

        AddEditDeleteButtonsController addEditDeleteButtonsController = loader.getController();
        addEditDeleteButtonsController.setParameters("Вступительные испытания", fields, fieldsTypes, fieldsControllers);

        setEditable(false);
    }

    public void setEditable(Boolean value) {
        for (int i = 0; i < fieldsControllers.length; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);
                    break;
                case "double":
                    DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
                    doubleInputPatternController.setEditable(value);
                    break;
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()){
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        boolInputPatternController.setEditable(value);
                        break;
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        intInputPatternController.setEditable(value);
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
                        PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                        phoneMaskInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
                        PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
                        passwordInputPatternController.setEditable(value);
                        break;
                    }
                    else {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        textInputPatternController.setEditable(value);
                        break;
                    }
            }
        }
    }

    public void setFieldsData(String aid) throws SQLException {
        query = "select AbiturientEntranceExam.id_entranceExam,AbiturientEntranceExam.id_formOfExam,AbiturientEntranceExam.id_languageOfExam,\n" +
                "AbiturientEntranceExam.groupExam,AbiturientEntranceExam.dateOf_exam,\n" +
                "AbiturientEntranceExam.score,AbiturientEntranceExam.has_100,\n" +
                "(select Abiturient.needSpecConditions from Abiturient)needSpecConditions\n" +
                "from AbiturientEntranceExam WHERE AbiturientEntranceExam.id_abiturient = "+aid+";";
        Statement statement = ModelDBConnection.getConnection().createStatement();
        rset= statement.executeQuery(query);
        if (rset.next()){
            for (int i = 0; i < fieldsControllers.length; i++) {
                switch (fieldsTypes[i]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(rset.getString(5));
                        break;
                    case "int":
                        if (Pattern.compile("(id_ent).*").matcher(fields[i]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(rset.getString(1));
                        }
                        if(Pattern.compile("(id_la).*").matcher(fields[i]).matches() ){
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(rset.getString(3));
                        }
                        if(Pattern.compile("(id_form).*").matcher(fields[i]).matches() ){
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(rset.getString(2));
                        }
                        if(Pattern.compile("(score)").matcher(fields[i]).matches() ){
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(rset.getString(6));
                        }
                        if(Pattern.compile("(has_).*").matcher(fields[i]).matches() ){
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(rset.getString(7));
                        }
                        if(Pattern.compile("(need).*").matcher(fields[i]).matches() ) {
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(rset.getString(8));
                        }
                        break;

                    case "varchar":
                        if (Pattern.compile("(grou).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(rset.getString(4));
                        }
                        break;
                }
            }
        }
    }
}

