package controllers_tabs;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import controllers_simple.*;
import javafx.util.Callback;

import javax.xml.soap.Text;

public class IndividualAchievementsTabController {
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

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Индивидуальные достижения"));
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
                    if (Pattern.compile("(dateOf_issue)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(160.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        dateInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_individualAchievements)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
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
                        paneObservableList.add(newPane);
                    }
                    if (Pattern.compile("(score)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0, 35.0, 0.0);
                        intInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(nameOfDocument)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    if (Pattern.compile("(series_document)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
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
                    if (Pattern.compile("(number_document)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    if (Pattern.compile("(issued_by)").matcher(fields[i]).matches()) {
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "AbiturientCompetitiveGroup"));
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(6));
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

        setFieldsData("0");

        addEditDeleteButtonsController.setParameters("Индивидуальные достижения", tabController, fields, fieldsTypes, fieldsControllers);

        setEditable(false);
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
                    if (Pattern.compile("(score)").matcher(fields[j]).matches()) {
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
        String[] individualAchievementsData = ModelDBConnection.getAbiturientIndividualAchievementsByID(aid);

        if (individualAchievementsData != null) {
            for (int i = 1; i < individualAchievementsData.length / fields.length; i++)
                addRow();

            for (int i = 0, j = 0; i < individualAchievementsData.length; i++, j++) {
                if (j == countFields)
                    j = 0;

                switch (fieldsTypes[j]) {
                    case "date":
                        if (Pattern.compile("(dateOf_issue)").matcher(fields[j]).matches()) {
                            DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                            dateInputPatternController.setFieldData(individualAchievementsData[i]);
                        }
                        break;
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(individualAchievementsData[i]);
                        }
                        if (Pattern.compile("(score)").matcher(fields[j]).matches()) {
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(individualAchievementsData[i]);
                        }
                        break;
                    case "varchar":
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        textInputPatternController.setFieldData(individualAchievementsData[i]);
                        break;
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	if (!isEmpty())
    		ModelDBConnection.updateAbiturientIndividualAchievementsByID(aid, fieldsOriginalNames, fieldsData);
    	else
    		ModelDBConnection.deleteAbiturientIndividualAchievementsByID(aid, fieldsOriginalNames, fieldsData);
    }


    public int checkData() {
    	int errorCount = 0, currentErrorCode = 0;

        //Если в таблицу ничего не добавляли, то никаких проверок не осуществляем
        if (isEmpty()) return 0;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if (j == countFields)
                j = 0;

            switch (fieldsTypes[j]) {
                case "date":
                    if (Pattern.compile("(dateOf_issue)").matcher(fields[j]).matches()) {
                        DateInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                    }
					if (currentErrorCode > 0) {
						MessageProcessing.displayErrorMessage(410);
						return currentErrorCode;
					}
                    if (Pattern.compile("(score)").matcher(fields[j]).matches()) {
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


    public  FXMLLoader[] addRow() throws IOException {
        FXMLLoader loader;
        Pane newPane;
        ObservableList<Pane> paneObservableList1 = FXCollections.observableArrayList();

        int oldSize = fieldsControllers.length;

        fieldsControllers = Arrays.copyOf(fieldsControllers,oldSize + countFields);

        for (int i = oldSize, j=0; i < fieldsControllers.length; i++, j++) {
            if(j == countFields)
                j=0;
            switch (fieldsTypes[j]){
                case "date":
                    if(Pattern.compile("(date).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(160.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j],"");
                        paneObservableList1.add(newPane);
                    }
                    break;
                case "int":
                    if(Pattern.compile("(id_ind).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList1.add(newPane);
                    }
                    if(Pattern.compile("(score)").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(100.0,35.0, 0.0);
                        intInputPatternController.setParameters(fields[j], "");
                        paneObservableList1.add(newPane);
                    }
                    break;
                case "varchar":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;
                    TextInputPatternController textInputPatternController = loader.getController();
                    textInputPatternController.setWidthHeight(150.0,35.0, 0.0);
                    textInputPatternController.setParameters(fields[j], "");
                    paneObservableList1.add(newPane);
                    break;
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
                    if (Pattern.compile("(dateOf_issue)").matcher(fields[j]).matches()) {
                        DateInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                    }
                    if (Pattern.compile("(score)").matcher(fields[j]).matches()) {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                    }
                    break;
                case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = textInputPatternController.checkData();
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