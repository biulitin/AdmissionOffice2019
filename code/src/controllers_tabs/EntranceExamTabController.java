package controllers_tabs;

import backend.*;
import controllers_simple.*;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javafx.scene.layout.GridPane;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class EntranceExamTabController {
    public Pane buttonsPane;
    public TableView<ObservableList> fieldsTable;
    public GridPane mainGridPane;
    int countFields;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    String aid;

    ObservableList<ObservableList> list = FXCollections.observableArrayList();

    public void fillTab(FXMLLoader tabController, String aid) throws Exception {
        mainGridPane.autosize();
        ModelDBConnection.setDefaultConnectionParameters();
    	//ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Вступительные испытания"));
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsOriginalNames = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
            fieldsOriginalNames[i] = rsmd.getColumnLabel(i + 1);
        }

        ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

        FXMLLoader loader;
        Pane newPane;
        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]){
                case "date":
                    if(Pattern.compile("(date).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        fieldData.setResizable(false);
                        fieldData.setMinWidth(180.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setParameters(fields[i],"");
                        dateInputPatternController.setWidthHeight(fieldData.getWidth()*0.90, 35.0, 0.0);
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
                	if(Pattern.compile("(id_ent).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setParameters(fields[i], "");
                        choiceInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
                            }
                        });
                        widthColumnListener(fieldData);
                        fieldsTable.getColumns().add(fieldData);
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);
                    }
                	if(Pattern.compile("(id_la).*").matcher(fields[i]).matches() ){
                       TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                       fieldData.setPrefWidth(160.0);

                       loader = new FXMLLoader();
                       loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                       newPane = (Pane) loader.load();
                       fieldsControllers[i] = loader;
                       ChoiceInputPatternController choiceInputPatternController = loader.getController();
                       choiceInputPatternController.setParameters(fields[i],"");
                       choiceInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                       fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                           public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                               return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
                           }
                       });
                       widthColumnListener(fieldData);
                       fieldsTable.getColumns().add(fieldData);
                       choiceInputPatternController.setFieldData("");
                       paneObservableList.add(newPane);
                   	}
                	if(Pattern.compile("(id_form).*").matcher(fields[i]).matches() ){
                       TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                       fieldData.setPrefWidth(160.0);

                       loader = new FXMLLoader();
                       loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                       newPane = (Pane) loader.load();
                       fieldsControllers[i] = loader;
                       ChoiceInputPatternController choiceInputPatternController = loader.getController();
                       choiceInputPatternController.setParameters(fields[i], "");
                       choiceInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                       fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                           public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                               return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
                           }
                       });
                       widthColumnListener(fieldData);
                       fieldsTable.getColumns().add(fieldData);
                       choiceInputPatternController.setFieldData("");
                       paneObservableList.add(newPane);
                   	}
                	if(Pattern.compile("(score)").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientEntranceExam"));
                        fieldData.setPrefWidth(110.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setParameters(fields[i], "");
                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
                            }
                        });
                        widthColumnListener(fieldData);
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
                        fieldData.setPrefWidth(125.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setParameters(fields[i], "");
                        textInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
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

        setFieldsData(aid);

        addEditDeleteButtonsController.setParameters("Вступительные испытания", tabController, fields, fieldsTypes, fieldsControllers);

        setEditable(false);
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
    	String[] entranceExamsData = ModelDBConnection.getAbiturientEntranceExamsByID(aid);

    	if(entranceExamsData != null) {
        	for(int i = 1; i < entranceExamsData.length / fields.length; i++)
        		addRow();

            for (int i = 0, j = 0; i < entranceExamsData.length; i++, j++) {
                if(j == countFields)
                    j=0;

                switch (fieldsTypes[j]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(entranceExamsData[i]);
                        break;
                    case "int":
                        if (Pattern.compile("(id_ent).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        if(Pattern.compile("(id_la).*").matcher(fields[j]).matches() ){
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        if(Pattern.compile("(id_form).*").matcher(fields[j]).matches() ){
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        if(Pattern.compile("(score)").matcher(fields[j]).matches() ){
                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                            intInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        if(Pattern.compile("(has_).*").matcher(fields[j]).matches() ){
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        if(Pattern.compile("(need).*").matcher(fields[j]).matches() ) {
                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                            boolInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        break;
                    case "varchar":
                        if (Pattern.compile("(grou).*").matcher(fields[j]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(entranceExamsData[i]);
                        }
                        break;
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	//Выгружаем данные только если в таблицу была добавлена хотя бы 1 строка
    	if (!isEmpty())
    		ModelDBConnection.updateAbiturientEntranceExamsByID(aid, fieldsOriginalNames, fieldsData);
    	else
    		ModelDBConnection.deleteAbiturientEntranceExamsByID(aid, fieldsOriginalNames, fieldsData);
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
	                if (Pattern.compile("(id_ent).*").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(310);
							return currentErrorCode;
						}
	                }
	                if(Pattern.compile("(id_la).*").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                }
	                if(Pattern.compile("(id_form).*").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(311);
							return currentErrorCode;
						}
	                }
	                if(Pattern.compile("(score)").matcher(fields[j]).matches() ){
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = intInputPatternController.checkData();
	                }
	                if(Pattern.compile("(has_).*").matcher(fields[j]).matches() ){
	                    BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = boolInputPatternController.checkData();
	                }
	                if(Pattern.compile("(need).*").matcher(fields[j]).matches() ) {
	                    BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = boolInputPatternController.checkData();
	                }
	                break;
	            case "varchar":
	                if (Pattern.compile("(grou).*").matcher(fields[j]).matches()) {
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


    public FXMLLoader[] addRow() throws IOException {
        FXMLLoader loader;
        Pane newPane;
        ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

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
                        dateInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.90, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j],"");
                        paneObservableList.add(newPane);
                    }
                    break;
                case "int":
                    if(Pattern.compile("(id_ent).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(id_la).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j],"");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(id_form).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(score)").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
                        intInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(has_).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(50.0,35.0);
                        boolInputPatternController.setParameters(fields[j], "");
                        paneObservableList.add(newPane);
                    }
                    if(Pattern.compile("(need).*").matcher(fields[j]).matches() ) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(350.0, 35.0);
                        boolInputPatternController.setParameters(fields[j], ModelDBConnection.getTranslationOfField(fields[j], "AbiturientEntranceExam"));
                    }
                    break;
                case "varchar":
                    if(Pattern.compile("(grou).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
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
	                if (Pattern.compile("(id_ent).*").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                }
	                if(Pattern.compile("(id_la).*").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                }
	                if(Pattern.compile("(id_form).*").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                }
	                if(Pattern.compile("(score)").matcher(fields[j]).matches() ){
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = intInputPatternController.checkData();
	                }
	                if(Pattern.compile("(has_).*").matcher(fields[j]).matches() ){
	                    BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = boolInputPatternController.checkData();
	                    countBooleanFields++;
	                }
	                if(Pattern.compile("(need).*").matcher(fields[j]).matches() ) {
	                    BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = boolInputPatternController.checkData();
	                    countBooleanFields++;
	                }
	                break;
	            case "varchar":
	                if (Pattern.compile("(grou).*").matcher(fields[j]).matches()) {
	                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = textInputPatternController.checkData();
	                }
	                break;
            }
			errorCount += currentErrorCode;
		}

		return (errorCount == fields.length - countBooleanFields);
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

    void widthColumnListener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int i = fieldsTable.getVisibleLeafIndex(listerColumn);
                for (int j = i; j < fieldsControllers.length; j = j + countFields) {
                    switch (fieldsTypes[i]) {
                        case "int":
                            if (Pattern.compile("(id_ent).*").matcher(fields[i]).matches()) {
                                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
                                    choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(id_ent).*").matcher(fields[i]).matches()) {
                                ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
                                choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(id_form).*").matcher(fields[i]).matches()) {
                                ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
                                choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(id_la).*").matcher(fields[i]).matches()) {
                                ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
                                choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(score)").matcher(fields[i]).matches()) {
                                IntInputPatternController intInputPatternController = fieldsControllers[j].getController();
                                intInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            break;
                        case "varchar":
                            if (Pattern.compile("(grou).*").matcher(fields[i]).matches()) {
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