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

public class AdditionalInfoTabController {
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

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Доп. сведения"));
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
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setResizable(false);
                        fieldData.setMinWidth(180.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        DateInputPatternController dateInputPatternController = loader.getController();
                        dateInputPatternController.setWidthHeight(fieldData.getWidth()*0.90, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[i],"");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
                            }
                        });
                        fieldsTable.getColumns().add(fieldData);
                        paneObservableList.add(newPane);
                    }
                    break;

                case "int":
                    if(Pattern.compile("(id_categ).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setPrefWidth(200.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[i], "");
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
                    break;

                case "varchar":
                    if(Pattern.compile("(name).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setPrefWidth(200.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
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
                    if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setPrefWidth(160.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
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
                    if(Pattern.compile("(number).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setPrefWidth(160.0);

                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
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
                    if(Pattern.compile("(issued).*").matcher(fields[i]).matches() ){
                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],"AbiturientExtraInfo"));
                        fieldData.setPrefWidth(160.0);
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
                        textInputPatternController.setParameters(fields[i], "");
                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
                                return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
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

        addEditDeleteButtonsController.setParameters("Доп. сведения", tabController, fields, fieldsTypes, fieldsControllers);

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
    	String[] additionalInfoData = ModelDBConnection.getAbiturientExtraInfoByID(aid);

    	if(additionalInfoData != null) {
        	for(int i = 1; i < additionalInfoData.length / fields.length; i++)
        		addRow();

            for (int i = 0, j = 0; i < additionalInfoData.length; i++, j++) {
                if(j == countFields)
                    j=0;

                switch (fieldsTypes[j]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(additionalInfoData[i]);
                        break;
                    case "int":
                        if (Pattern.compile("(id_category).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(additionalInfoData[i]);
                        }
                        break;
                    case "varchar":
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        textInputPatternController.setFieldData(additionalInfoData[i]);
                        break;
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	//Выгружаем данные только если в таблицу была добавлена хотя бы 1 строка
    	if (!isEmpty())
    		ModelDBConnection.updateAbiturientExtraInfoByID(aid, fieldsOriginalNames, fieldsData);
    	else
    		ModelDBConnection.deleteAbiturientExtraInfoByID(aid, fieldsOriginalNames, fieldsData);
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
	                if (Pattern.compile("(id_category).*").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(1210);
							return currentErrorCode;
						}
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
                        dateInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.90, 35.0, 0.0);
                        dateInputPatternController.setParameters(fields[j],"");
                        paneObservableList1.add(newPane);
                    }
                    break;
                case "int":
                    if(Pattern.compile("(id_category).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList1.add(newPane);
                    }
                    break;
                case "varchar":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;
                    TextInputPatternController textInputPatternController = loader.getController();
                    textInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
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
	                if (Pattern.compile("(id_category).*").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                }
	                break;
	            case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = textInputPatternController.checkData();
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

    void widthColumnListener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int i = fieldsTable.getVisibleLeafIndex(listerColumn);
                for (int j = i; j < fieldsControllers.length; j = j + countFields) {
                    switch (fieldsTypes[i]) {
                        case "int":
                            if (Pattern.compile("(id_categ).*").matcher(fields[i]).matches()) {
                                ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
                                choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            break;
                        case "varchar":
                            if (Pattern.compile("(name).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                                TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
                                textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
                            }
                            if (Pattern.compile("(number).*").matcher(fields[i]).matches()) {
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