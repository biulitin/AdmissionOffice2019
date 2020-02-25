package controllers_tabs;

import backend.MessageProcessing;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.regex.Pattern;

import backend.ModelDBConnection;
import controllers_simple.*;
import javafx.util.Callback;

public class CompetitiveGroupsTabController {
    @FXML
    public FlowPane buttonsPane;

    @FXML
    public TableView<ObservableList> fieldsTable;
    public GridPane mainGridPane;

    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    int countFields;

    String query;
    Pane newPane;
    FXMLLoader loader, tabController;

    String aid;
    ObservableList<ObservableList> list = FXCollections.observableArrayList();

    public void fillTab(FXMLLoader tabController) throws Exception {
        mainGridPane.autosize();
        ModelDBConnection.setDefaultConnectionParameters();
        ModelDBConnection.initConnection();

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Конкурсные группы"));
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsOriginalNames = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel(i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
            fieldsOriginalNames[i] = rsmd.getColumnLabel(i + 1);
        }

        ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

        FXMLLoader loader;
        Pane newPane;
        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    if (Pattern.compile("(originalsReceivedDate)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(9));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        dateInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_speciality)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(id_formOfEducation)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(id_competitiveGroup)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(id_targetOrganization)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(3));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(haveBasisForBVI)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(haveBasisForQuota)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(havePreferredRight)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(6));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(competitiveScore)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0, 35.0, 0.0);
                        intInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(7));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(scoresIndAchievements)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0, 35.0, 0.0);
                        intInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(8));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
                    }
                    if (Pattern.compile("(priority)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        /*loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(10));
                            }
                        });

                        paneObservableList.add(newPane);*/
                        fieldsTable.getColumns().add(fieldData);
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

        setFieldsData("0");

        addEditDeleteButtonsController.setParameters("Конкурсные группы", tabController, fields, fieldsTypes, fieldsControllers);

        //setEditable(false);
    }

    public void openModalWindow() throws IOException, SQLException {
        Stage modalStage = new Stage();
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
                            choiceInputPatternController.setWidthHeight(270.0, 35.0, 140.0);
                        } else {
                            choiceInputPatternController.setWidthHeight(450.0, 35.0, 140.0);
                        }
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        choiceInputPatternController.setFieldData("");
                    } else if (fields[i].equals("competitiveScore") || fields[i].equals("scoresIndAchievements")) {
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        IntInputPatternController intInputPatternController = loader.getController();
                        if (fields[i].equals("competitiveScore")) {
                            intInputPatternController.setWidthHeight(250.0, 35.0, 140.0);
                        } else {
                            intInputPatternController.setWidthHeight(200.0, 35.0, 90.0);
                        }
                        intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                    } else if (fields[i].equals("priority") || Pattern.compile("(have).*").matcher(fields[i]).matches()) {
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        BoolInputPatternController boolInputPatternController = loader.getController();
                        if (Pattern.compile("(have).*").matcher(fields[i]).matches()) {
                            boolInputPatternController.setWidthHeight(65.0, 35.0);
                        } else {
                            boolInputPatternController.setWidthHeight(100.0, 35.0);
                        }
                        boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                    }
            }
        }

        BorderPane modalButtonsPane = new BorderPane();
        modalButtonsPane.setPrefWidth(450.0);
        modalButtonsPane.setPrefHeight(80.0);

        Button addButton = new Button();
        addButton.setAlignment(Pos.CENTER);
        addButton.setPrefWidth(134.0);
        addButton.setPrefHeight(26.0);
        addButton.setText("Сохранить");
        addButton.setStyle("-fx-background-color: #BDBDBD; -fx-background-radius: 100px; -fx-font-weight: bold; -fx-cursor: hand;");
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int errorsNumber = checkData();
                if (errorsNumber == 0) {
                    try {
                        addRow();
                        modalStage.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Errors: " + errorsNumber);
                }
            }
        });
        modalButtonsPane.setMargin(addButton, new Insets(20.0));
        modalButtonsPane.setRight(addButton);
        flowPane.getChildren().add(modalButtonsPane);

        //setEditable(true);

        Scene scene = new Scene(flowPane);
        modalStage.setScene(scene);
        modalStage.setWidth(500.0);
        modalStage.setHeight(400.0);
        modalStage.show();
    }

    public void setEditable(Boolean value) {
        for (int i = 0, j = 0; i < fieldsControllers.length; i++, j++) {
            if (j == countFields)
                j = 0;
            switch (fieldsTypes[j]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);
                    break;
                case "double":
                    DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
                    doubleInputPatternController.setEditable(value);
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                        break;
                    }
                    if (Pattern.compile("(have).*").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        boolInputPatternController.setEditable(value);
                        break;
                    }
                    if (Pattern.compile("(priority)").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        boolInputPatternController.setEditable(value);
                        break;
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        intInputPatternController.setEditable(value);
                        break;
                    }
                case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    textInputPatternController.setEditable(value);
                    break;
            }
        }
    }

    public void setFieldsData(String aid) throws Exception {
        this.aid = aid;
        String[] competitiveGroupsData = ModelDBConnection.getAbiturientCompetitiveGroupsByID(aid);

        if (competitiveGroupsData != null) {
            for (int i = 1; i < competitiveGroupsData.length / fields.length; i++)
                addRow();

            for (int i = 0, j = 0; i < competitiveGroupsData.length; i++, j++) {
                if (j == countFields)
                    j = 0;

                switch (fieldsTypes[j]) {
                    case "date":
                        if (Pattern.compile("(originalsReceivedDate)").matcher(fields[j]).matches()) {
                            DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                            dateInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        break;
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        if (Pattern.compile("(have).*").matcher(fields[j]).matches()) {
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        if (Pattern.compile("(competitiveScore)").matcher(fields[j]).matches()) {
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        if (Pattern.compile("(scoresIndAchievements)").matcher(fields[j]).matches()) {
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        if (Pattern.compile("(priority)").matcher(fields[j]).matches()) {
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(competitiveGroupsData[i]);
                        }
                        break;
                }
            }
        }
    }

    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
        if (!isEmpty())
            ModelDBConnection.updateAbiturientCompetitiveGroupsByID(aid, fieldsOriginalNames, fieldsData);
    }

    public int checkData() {
        int errorCount = 0, currentErrorCode = 0;

        if (isEmpty()) return 1;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if (j == countFields)
                j = 0;

            switch (fieldsTypes[j]) {
                case "date":
                    if (Pattern.compile("(scoresIndAchievements)").matcher(fields[j]).matches()) {
                        DateInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                    }
                    if (Pattern.compile("(have).*").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                    }
                    if (Pattern.compile("(priority)").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                    }
                    if (Pattern.compile("(competitiveScore)").matcher(fields[j]).matches()) {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    if (Pattern.compile("(scoresIndAchievements)").matcher(fields[j]).matches()) {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
            }
            errorCount += currentErrorCode;
            //System.out.println(currentErrorCode);
        }

        return errorCount;
    }

    public FXMLLoader[] addRow() throws IOException {
        FXMLLoader loader;
        Pane newPane;
        ObservableList<Pane> paneObservableList1 = FXCollections.observableArrayList();

        int oldSize = fieldsControllers.length;

        fieldsControllers = Arrays.copyOf(fieldsControllers, oldSize + countFields);

        for (int i = oldSize, j = 0; i < fieldsControllers.length; i++, j++) {
            if (j == countFields)
                j = 0;
            switch (fieldsTypes[j]) {
                case "date":
                    if (Pattern.compile("(originalsReceivedDate)").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j], "");
                        dateInputPatternController.setFieldData("");
                        paneObservableList1.add(newPane);
                    }
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0, 35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList1.add(newPane);

                    }
                    if (Pattern.compile("(have).*").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[j], "");
                        paneObservableList1.add(newPane);
                    }
                    if (Pattern.compile("(competitiveScore)").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0, 35.0, 0.0);
                        intInputPatternController.setParameters(fields[j], "");
                        paneObservableList1.add(newPane);
                    }
                    if (Pattern.compile("(scoresIndAchievements)").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0, 35.0, 0.0);
                        intInputPatternController.setParameters(fields[j], "");
                        paneObservableList1.add(newPane);
                    }
                    if (Pattern.compile("(priority)").matcher(fields[j]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(35.0, 35.0);
                        boolInputPatternController.setParameters(fields[j], "");
                        paneObservableList1.add(newPane);
                    }
            }
        }
        list.add(paneObservableList1);
        fieldsTable.getItems().setAll(list);
        return fieldsControllers;
    }

    public boolean isEmpty() {
        if (fieldsControllers == null)
            return true;

        if (fieldsControllers.length == 0)
            return true;

        int errorCount = 0, currentErrorCode = 0, countBooleanFields = 0;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if (j == countFields)
                j = 0;

            switch (fieldsTypes[j]) {
                case "date":
                    if (Pattern.compile("(originalsReceivedDate)").matcher(fields[j]).matches()) {
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = dateInputPatternController.checkData();
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                    }
                    if (Pattern.compile("(have).*").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                        countBooleanFields++;
                    }
                    if (Pattern.compile("(priority)").matcher(fields[j]).matches()) {
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                        countBooleanFields++;
                    }
                    if (Pattern.compile("(competitiveScore)").matcher(fields[j]).matches()) {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    if (Pattern.compile("(scoresIndAchievements)").matcher(fields[j]).matches()) {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
            }
            errorCount += currentErrorCode;
        }

        return (errorCount == fields.length - countBooleanFields);
    }

    public FXMLLoader[] deleteRow() throws Exception {
        int row = fieldsTable.getSelectionModel().getSelectedIndex();

        for (int i = row * countFields, j = (row + 1) * countFields; j < fieldsControllers.length; i++, j++) {
            fieldsControllers[i] = fieldsControllers[j];
        }

        fieldsControllers = Arrays.copyOfRange(fieldsControllers, 0, fieldsControllers.length - countFields);
        fieldsTable.getItems().remove(row);
        list.remove(row);

        if (fieldsControllers.length == 0)
            addRow();

        return fieldsControllers;
    }
}