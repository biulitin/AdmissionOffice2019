package controllers_tabs;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import controllers_simple.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
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
                            fieldData.setResizable(false);
                            fieldData.setMinWidth(200.0);

                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            DateInputPatternController dateInputPatternController = loader.getController();
                            dateInputPatternController.setWidthHeight(fieldData.getWidth()*0.90, 35.0, 0.0);
                            dateInputPatternController.setParameters(fields[i],"");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
                                }
                            });
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        break;
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
                        break;
                    case "varchar":
                        if(Pattern.compile("(name).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            fieldData.setPrefWidth(220.0);

                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(fieldData.getPrefWidth()*0.85,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
                                }
                            });
                            widthColumnListener(fieldData);
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            fieldData.setPrefWidth(200.0);

                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(fieldData.getPrefWidth()*0.85,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                                }
                            });
                            widthColumnListener(fieldData);
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(num).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            fieldData.setPrefWidth(200.0);

                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(fieldData.getPrefWidth()*0.85,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                                }
                            });
                            widthColumnListener(fieldData);
                            fieldsTable.getColumns().add(fieldData);
                            paneObservableList.add(newPane);
                        }
                        if(Pattern.compile("(issued).*").matcher(fields[i]).matches() ){
                            TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientDocumentsBVI"));
                            fieldData.setPrefWidth(250.0);

                            loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            TextInputPatternController textInputPatternController = loader.getController();
                            textInputPatternController.setWidthHeight(fieldData.getPrefWidth()*0.85,35.0, 0.0);
                            textInputPatternController.setParameters(fields[i], "");
                            fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                                public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                    return new SimpleObjectProperty<>((Pane) param.getValue().get(3));
                                }
                            });
                            widthColumnListener(fieldData);
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


    public void setFieldsData(String aid) throws Exception {
        this.aid = aid;
        String[] privilegeData = null;
        switch(choicePrivilege.getSelectionModel().selectedItemProperty().getValue().toString()){
            case "БВИ":
                privilegeData = ModelDBConnection.getAbiturientBVIByID(aid);
                break;
            case "Квота":
                 privilegeData = ModelDBConnection.getAbiturientQuotaByID(aid);
                break;
            case "Преимущественное право":
                privilegeData = ModelDBConnection.getAbiturientPreferredRightByID(aid);
                break;
            default:
                break;
        }
        if(privilegeData != null) {
            for (int i = 1; i < privilegeData.length / fields.length; i++)
                addRow();

            for (int i = 0, j = 0; i < privilegeData.length; i++, j++) {
                if (j == countFields)
                    j = 0;
                switch (fieldsTypes[j]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(privilegeData[i]);
                        break;
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(privilegeData[i]);
                        }
                        break;
                    case "varchar":
                        if (Pattern.compile("(name).*").matcher(fields[j]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(privilegeData[i]);
                        }
                        if (Pattern.compile("(series).*").matcher(fields[j]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(privilegeData[i]);
                        }
                        if (Pattern.compile("(num).*").matcher(fields[j]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(privilegeData[i]);
                        }
                        if (Pattern.compile("(issued).*").matcher(fields[j]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(privilegeData[i]);
                        }
                        break;
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
        //Выгружаем данные только если в таблицу была добавлена хотя бы 1 строка
        switch(choicePrivilege.getSelectionModel().selectedItemProperty().getValue().toString()){
            case "БВИ":
                if (!isEmpty())
                    ModelDBConnection.updateAbiturientBVIByID(aid, fieldsOriginalNames, fieldsData);
                else
                    ModelDBConnection.deleteAbiturientDocumentsBVIByID(aid, fieldsOriginalNames, fieldsData);
                break;
            case "Квота":
                if (!isEmpty())
                    ModelDBConnection.updateAbiturientQuotaByID(aid, fieldsOriginalNames, fieldsData);
                else
                    ModelDBConnection.deleteAbiturientDocumentsQuotaByID(aid, fieldsOriginalNames, fieldsData);
                break;
            case "Преимущественное право":
                if (!isEmpty())
                    ModelDBConnection.updateAbiturienPreferredRightByID(aid, fieldsOriginalNames, fieldsData);
                else
                    ModelDBConnection.deleteAbiturientDocumentsPreferredRightByID(aid, fieldsOriginalNames, fieldsData);
                break;
            default:
                break;
        }
    }


    public int checkData() {
        int errorCount = 0, currentErrorCode = 0;

        //Если в таблицу ничего не добавляли, но выбран элемент в выпадающем списке, то ошибка
        if (isEmptyOnlyTable()) {
            switch(choicePrivilege.getSelectionModel().selectedItemProperty().getValue().toString()){
	            case "БВИ":
	                MessageProcessing.displayErrorMessage(516);
	                break;
	            case "Квота":
	                MessageProcessing.displayErrorMessage(616);
	                break;
	            case "Преимущественное право":
	                MessageProcessing.displayErrorMessage(716);
	                break;
            }
        	return 1;
        }

        //Если в таблицу ничего не добавляли и в выпадающем списке ничего не выбрано, 
        //то никаких проверок не осуществляем
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
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                        if (currentErrorCode > 0) {
                            switch(choicePrivilege.getSelectionModel().selectedItemProperty().getValue().toString()){
                                case "БВИ":
                                    MessageProcessing.displayErrorMessage(510);
                                    break;
                                case "Квота":
                                    MessageProcessing.displayErrorMessage(610);
                                    break;
                                case "Преимущественное право":
                                    MessageProcessing.displayErrorMessage(710);
                                    break;
                            }
                            return currentErrorCode;
                        }
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(name).*").matcher(fields[j]).matches()) {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(series).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(num).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[j]).matches() ){
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
                        dateInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j - 1).getWidth()*0.90, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j],"");
                        paneObservableList.add(newPane);
                    }
                    break;
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                    	if (fieldsControllers[j] == null) {
                    		loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                            newPane = (Pane) loader.load();
                            fieldsControllers[i] = loader;
                            ChoiceInputPatternController choiceInputPatternController = loader.getController();
                            choiceInputPatternController.setWidthHeight(410.0,35.0, 0.0);
                            choiceInputPatternController.setParameters(fields[j], "");
                            choiceInputPatternController.setFieldData("");
                    	} else
                    		fieldsControllers[i] = fieldsControllers[j];
                    }
                    break;
                case "varchar":
                    if(Pattern.compile("(name).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j - 1).getWidth()*0.85,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(series).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j - 1).getWidth()*0.85,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(num).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j - 1).getWidth()*0.85,35.0, 0.0);
                        textInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);

                    }
                    if(Pattern.compile("(issued).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j - 1).getWidth()*0.85,35.0, 0.0);
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
                    if(Pattern.compile("(series).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(num).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    break;
            }
            errorCount += currentErrorCode;
        }
        return (errorCount == fields.length - countBooleanFields);
    }

    public boolean isEmptyOnlyTable() {
        int errorCount = 0, currentErrorCode = 0, countChoiceFields = 0;;

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
                        countChoiceFields++;
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(name).*").matcher(fields[j]).matches()) {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(series).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(num).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[j]).matches() ){
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                    }
                    break;
            }
            errorCount += currentErrorCode;
        }
        return (errorCount == fields.length - countChoiceFields);
    }


    public FXMLLoader[] deleteRow() throws Exception {
        int row = fieldsTable.getSelectionModel().getSelectedIndex();

        //Если удаляется единственная строка в таблице, то 
        //просто сбрасываем значения во всех элементах таблицы, а выпадающий список не трогаем
        if (fieldsControllers.length == fieldsOriginalNames.length) {
            for (int i = 0, j = 0; i < fieldsControllers.length; i++, j++) {
                if (j == countFields)
                    j = 0;
                switch (fieldsTypes[j]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData("");
                        break;
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                        	break;
                        }
                        break;
                    case "varchar":
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        textInputPatternController.setFieldData("");
                        break;
                }
            }
        } else {
	        for(int i = row * countFields, j = (row + 1) * countFields; j < fieldsControllers.length; i++, j++) {
	            fieldsControllers[i] = fieldsControllers[j];
	        }

	        fieldsControllers = Arrays.copyOfRange(fieldsControllers,0,fieldsControllers.length - countFields);
	        fieldsTable.getItems().remove(row);
	        list.remove(row);
        }

        if (fieldsControllers.length == 0)
            addRow();

        return fieldsControllers;
    }

    void widthColumnListener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int i = fieldsTable.getVisibleLeafIndex(listerColumn) + 1;
                for (int j = i; j < fieldsControllers.length; j = j + countFields) {
                    switch (fieldsTypes[i]) {
                        case "varchar":
                            if (Pattern.compile("(name).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(num).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(issued).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            break;
                    }
                }
            }
        });
    }
}