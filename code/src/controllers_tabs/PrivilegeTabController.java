package controllers_tabs;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import controllers_simple.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.regex.Pattern;

public class PrivilegeTabController {
    public Pane buttonsPane;
    public TableView<ObservableList> fieldsTable;
    public GridPane mainGridPane;
    public ChoiceBox choicePrivilege;

    int countFields;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    String aid;

    ObservableList<ObservableList> list = FXCollections.observableArrayList();

    public void fillTab(FXMLLoader tabController) {
        ModelDBConnection.setDefaultConnectionParameters();
        ModelDBConnection.initConnection();

        choicePrivilege.getItems().addAll("", "БВИ", "Квота", "Преимущественное право");

        choicePrivilege.getSelectionModel().selectedItemProperty().addListener((observableValue, old_val, new_val) -> {
            try {
                getPrivilegeTypes(tabController,new_val.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    void getPrivilegeTypes(FXMLLoader tabController, String privilege) throws Exception {
        if(mainGridPane.getChildren().size() > 3)
            mainGridPane.getChildren().remove(3);

        fieldsTable.getColumns().clear();
        fieldsTable.getItems().clear();
        buttonsPane.getChildren().clear();
        list.clear();
        System.out.println(privilege);
        if(!privilege.equals("")){
            ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName(privilege));
            countFields = rsmd.getColumnCount();

            fields = new String[countFields];
            fieldsTypes = new String[countFields];
            fieldsOriginalNames = new String[countFields];
            fieldsControllers = new FXMLLoader[countFields];

            FXMLLoader loader;
            Pane newPane;
            loader = new FXMLLoader();

            for (int i = 0; i < countFields; i++){
                fields[i] = rsmd.getColumnLabel (i + 1);
                fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
                fieldsOriginalNames[i] = rsmd.getColumnLabel(i + 1);
            }

            ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

            for (int i = 0; i < countFields; i++) {
                switch (fieldsTypes[i]){
                    case "date":
                        if(Pattern.compile("(date).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            DateInputPatternController dateInputPatternController = loader.getController();
                            dateInputPatternController.setWidthHeight(175.0, 35.0, 0.0);
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
                        if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
                            loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            ChoiceInputPatternController choiceInputPatternController = loader.getController();
                            choiceInputPatternController.setWidthHeight(410.0,35.0, 0.0);
                            choiceInputPatternController.setParameters(fields[i],"");
                            choiceInputPatternController.setFieldData("");
                            mainGridPane.add(newPane,0,1);
                        }
                    case "varchar":
                        if(Pattern.compile("(name).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
                                }
                            });
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                                }
                            });
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(num).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                                }
                            });
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(issued).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
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

            addEditDeleteButtonsController.setParameters("Привилегии", tabController, fields, fieldsTypes, fieldsControllers);

            setEditable(false);
        }

    }

    public void setEditable(Boolean value) {
        for (int i = 0, j = 0; i < fieldsControllers.length; i++, j++) {
            if(j == countFields)
                j=0;
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
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(need).*").matcher(fields[j]).matches() || Pattern.compile("(ha).*").matcher(fields[j]).matches()){
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        boolInputPatternController.setEditable(value);
                        break;
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        intInputPatternController.setEditable(value);
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(phone).*").matcher(fields[j]).matches()){
                        PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                        phoneMaskInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(passw).*").matcher(fields[j]).matches()){
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

    public int checkData() {
        int errorCount = 0, currentErrorCode = 0;

        //Если в таблицу ничего не добавляли, то никаких проверок не осуществляем
        if (isEmpty()) return 0;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if(j == countFields)
                j=0;

            switch (fieldsTypes[j]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = dateInputPatternController.checkData();
                    break;
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                        if (currentErrorCode > 0) {
                            MessageProcessing.displayErrorMessage(510);
                            return currentErrorCode;
                        }
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(name).*").matcher(fields[j]).matches()) {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(num).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }

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
        ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

        int oldSize = fieldsControllers.length;

        fieldsControllers = Arrays.copyOf(fieldsControllers,oldSize + countFields);

        for (int i = oldSize, j = 0; i < fieldsControllers.length; i++, j++) {
            if(j == countFields)
                j = 0;
            switch (fieldsTypes[j]){
                case "date":
                    if(Pattern.compile("(date).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(175.0, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j],"");
                        paneObservableList.add(newPane);
                    }
                    break;
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(410.0,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                    }
                    break;
                case "varchar":
                    if(Pattern.compile("(name).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(series).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(num).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);

                    }
                    if(Pattern.compile("(issued).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(175.0,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    break;
            }
        }
        list.add(paneObservableList);
        fieldsTable.getItems().setAll(list);
        return fieldsControllers;
    }

    public boolean isEmpty() {
        if (fieldsControllers == null)
            return true;

        if (fieldsControllers.length == 0)
            return true;

        int errorCount = 0, currentErrorCode = 0, countBooleanFields = 0;;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if(j == countFields)
                j=0;

            switch (fieldsTypes[j]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = dateInputPatternController.checkData();
                    break;
                case "int":
                    if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(name).*").matcher(fields[j]).matches()) {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(num).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[i]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    break;
            }
            errorCount += currentErrorCode;
        }
        return (errorCount == fields.length - countBooleanFields ? true : false);
    }

    public FXMLLoader[] deleteRow() throws Exception {
        int row = fieldsTable.getSelectionModel().getSelectedIndex();

        for(int i = row * countFields, j = (row + 1) * countFields; j < fieldsControllers.length; i++, j++) {
            fieldsControllers[i] = fieldsControllers[j];
        }

        fieldsControllers = Arrays.copyOfRange(fieldsControllers,0,fieldsControllers.length - countFields);
        fieldsTable.getItems().remove(row);
        list.remove(row);

        if (fieldsControllers.length == 0)
            addRow();

        return fieldsControllers;
    }
}
