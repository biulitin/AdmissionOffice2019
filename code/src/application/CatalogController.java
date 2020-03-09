package application;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.util.Arrays;
import java.util.regex.Pattern;

public class CatalogController {
    public TableView fieldsTable;
    public Pane buttonsPane;

    int countFields, catalogType;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;
    String tableOriginalName;

    ObservableList<ObservableList> list = FXCollections.observableArrayList();

    public void fillForm(FXMLLoader tabController, String table) throws Exception {

        switch (table){
        	//Обычные справочники (3 поля: id, name, codeFIS)
            case "Пол":
                tableOriginalName = "Gender";
                catalogType = 1;
                break;
            case "Гражданство":
                tableOriginalName = "Nationality";
                catalogType = 1;
                break;
            case "Причины возврата":
                tableOriginalName = "ReturnReasons";
                catalogType = 1;
                break;
            case "Типы паспортов":
                tableOriginalName = "TypePassport";
                catalogType = 1;
                break;
            case "Регионы":
                tableOriginalName = "Region";
                catalogType = 1;
                break;
            case "Типы населенных пунктов":
                tableOriginalName = "TypeSettlement";
                catalogType = 1;
                break;
            case "Формы вступительных испытаний":
                tableOriginalName = "FormOfExam";
                catalogType = 1;
                break;
            case "Языки вступительных испытаний":
                tableOriginalName = "LanguageOfExam";
                catalogType = 1;
                break;
            case "Основания для оценки":
                tableOriginalName = "BasisMark";
                catalogType = 1;
                break;
            case "Олимпиады":
                tableOriginalName = "Olympiad";
                catalogType = 1;
                break;
            case "Уровни образования":
                tableOriginalName = "LevelEducation";
                catalogType = 1;
                break;
            case "Типы образования":
                tableOriginalName = "TypeEducation";
                catalogType = 1;
                break;
            case "Специальности":
                tableOriginalName = "Speciality";
                catalogType = 1;
                break;
            case "Формы обучения":
                tableOriginalName = "FormOfEducation";
                catalogType = 1;
                break;
            case "Конкурсные группы ":
                tableOriginalName = "CompetitiveGroup";
                catalogType = 1;
                break;
            case "Целевые организации":
                tableOriginalName = "TargetOrganization";
                catalogType = 1;
                break;
            case "Типы БВИ":
                tableOriginalName = "TypeOfBVI";
                catalogType = 1;
                break;
            case "Типы Квоты":
                tableOriginalName = "TypeOfQuote";
                catalogType = 1;
                break;
            case "Типы Преимущественного права":
                tableOriginalName = "TypeOfPreferredRight";
                catalogType = 1;
                break;
            case "Категории допсведений":
                tableOriginalName = "CategoryOfExtraInfo";
                catalogType = 1;
                break;

            //Справочники ВИ и ИД (4 поля: id, name, codeFIS, min_score/max_score)
            case "Перечень вступительных испытаний":
                tableOriginalName = "EntranceExam";
                catalogType = 2;
                break;
            case "Перечень индивидуальных достижений":
                tableOriginalName = "IndividualAchievement";
                catalogType = 2;
                break;

            //Справочники Пользователи (4 поля: id, login, password, fio)
            case "Пользователи":
                tableOriginalName = "Users";
                catalogType = 3;
                break;

            //Справочники План приема (6 полей: id_speciality, id_formOfEducation, id_competitiveGroup, id_targetOrganization, amount_of_places, amount_of_places_quota)
            case "План приема":
                tableOriginalName = "AdmissionPlan";
                catalogType = 4;
                break;
        }

        ModelDBConnection.setDefaultConnectionParameters();
        //ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();

        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName(table));
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

        //Обычные справочники (3 поля: id, name, codeFIS)
        if (catalogType == 1) {
	        for (int i = 0; i < countFields; i++) {
	            switch (fieldsTypes[i]){
	                case "int":
	                    if(Pattern.compile("(id)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	
	                    }
	                    break;
	                case "varchar":
	                    if(Pattern.compile("(name)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    if(Pattern.compile("(codeFIS)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    break;
	            }
	        }
        }
        //Справочники ВИ и ИД (4 поля: id, name, codeFIS, min_score/max_score)
        if (catalogType == 2) {
	        for (int i = 0; i < countFields; i++) {
	            switch (fieldsTypes[i]){
	                case "int":
	                    if(Pattern.compile("(id)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile(".*(_score)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
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
	                case "varchar":
	                    if(Pattern.compile("(name)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    if(Pattern.compile("(codeFIS)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    break;
	            }
	        }
        }
        //Справочники Пользователи (4 поля: id, login, password, fio)
        if (catalogType == 3) {
	        for (int i = 0; i < countFields; i++) {
	            switch (fieldsTypes[i]){
	                case "int":
	                    if(Pattern.compile("(id)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(0));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	                    }
	                    break;
	                case "varchar":
	                    if(Pattern.compile("(login)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    if(Pattern.compile("(passw).*").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/PasswordPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
	                        passwordInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        passwordInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile("(fio)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
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
	                    break;
	            }
	        }
        }
        //Справочники План приема (6 полей: id_speciality, id_formOfEducation, id_competitiveGroup, id_targetOrganization, amount_of_places, amount_of_places_quota)
        if (catalogType == 4) {
	        for (int i = 0; i < countFields; i++) {
	            switch (fieldsTypes[i]){
	                case "int":
	                    if(Pattern.compile("(id_speciality)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], tableOriginalName));
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
	                    if(Pattern.compile("(id_formOfEducation)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], tableOriginalName));
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
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(1));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        choiceInputPatternController.setFieldData("");
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile("(id_competitiveGroup)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], tableOriginalName));
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
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        choiceInputPatternController.setFieldData("");
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile("(id_targetOrganization)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], tableOriginalName));
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
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(3));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        choiceInputPatternController.setFieldData("");
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile("(amount_of_places)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(4));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	                    }
	                    if(Pattern.compile("(amount_of_places_quota)").matcher(fields[i]).matches() ){
	                        TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i],tableOriginalName));
	                        fieldData.setPrefWidth(160.0);
	
	                        loader = new FXMLLoader();
	                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));
	
	                        newPane = (Pane) loader.load();
	                        fieldsControllers[i] = loader;
	                        IntInputPatternController intInputPatternController = loader.getController();
	                        intInputPatternController.setWidthHeight(fieldData.getWidth()*0.85,35.0, 0.0);
	                        intInputPatternController.setParameters(fields[i], "");
	                        fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Pane>, ObservableValue<Pane>>() {
	                            public ObservableValue<Pane> call(TableColumn.CellDataFeatures<ObservableList, Pane> param) {
	                                return new SimpleObjectProperty<>((Pane) param.getValue().get(5));
	                            }
	                        });
	                        widthColumnListener(fieldData);
	                        fieldsTable.getColumns().add(fieldData);
	                        paneObservableList.add(newPane);
	                    }
	                    break;
	                case "varchar":
	                    break;
	            }
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

        setFieldsData(table);
        addEditDeleteButtonsController.setParameters("Справочник", tabController, fields, fieldsTypes, fieldsControllers);
        addEditDeleteButtonsController.hideButton(2);

        setEditable(false);

    }


    public void setEditable(Boolean value) {
        for (int i = 0, j = 0; i < fieldsControllers.length; i++, j++) {
            if(j == countFields)
                j=0;
            switch (fieldsTypes[j]) {
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                    } else {
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    intInputPatternController.setEditable(value);
                    }
                    break;
                case "varchar":
					if(Pattern.compile("(passw).*").matcher(fields[j]).matches()){
						PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
						passwordInputPatternController.setEditable(value);
						break;
					} else {
	                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
	                    textInputPatternController.setEditable(value);
	                    break;
					}
            }
        }
    }


    public void setFieldsData(String table) throws Exception {
        String[] catalogData = ModelDBConnection.getCatalogData(table);

        if(catalogData != null){
            for(int i = 1; i < catalogData.length / fields.length; i++)
                addRow();

            for (int i = 0, j = 0; i < catalogData.length; i++, j++) {
                if(j == countFields)
                    j=0;
                switch (fieldsTypes[j]) {
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(catalogData[i]);
                        } else {
	                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                        intInputPatternController.setFieldData(catalogData[i]);
                        }
                        break;
                    case "varchar":
                    	if(Pattern.compile("(passw).*").matcher(fields[j]).matches()){
                    		PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
                    		passwordInputPatternController.setFieldData(catalogData[i]);
	                        break;
                    	} else {
	                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
	                        textInputPatternController.setFieldData(catalogData[i]);
	                        break;
                    	}
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
        if (!isEmpty()) {
        	//Отдельный случай для Плана приема
        	if(catalogType == 4)
        		ModelDBConnection.updateAdmissionPlan(fieldsOriginalNames,fieldsData,tableOriginalName);
        	else
        		ModelDBConnection.updateCatalogDataById(fieldsOriginalNames,fieldsData,tableOriginalName);
        } else {
        	//если решим удалять строки из справочника
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
                case "int":
	                if (Pattern.compile("(id_speciality)").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(1306);
							return currentErrorCode;
						}
						break;
	                }
	                if(Pattern.compile("(id_formOfEducation)").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(1307);
							return currentErrorCode;
						}
						break;
	                }
	                if(Pattern.compile("(id_competitiveGroup)").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(1308);
							return currentErrorCode;
						}
						break;
	                }
	                if(Pattern.compile("(id_targetOrganization)").matcher(fields[j]).matches() ){
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                    break;
	                }
	                if(Pattern.compile("(amount_of_places)").matcher(fields[j]).matches() ){
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = intInputPatternController.checkData();
	                    break;
	                }
	                if(Pattern.compile("(amount_of_places_quota)").matcher(fields[j]).matches() ){
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = intInputPatternController.checkData();
	                    break;
	                } else {
	                    IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = intInputPatternController.checkData();
	                    if (currentErrorCode > 0) {
	                        MessageProcessing.displayErrorMessage(1304);
	                        return currentErrorCode;
	                    }
	                    break;
	                }
                case "varchar":
                    if(Pattern.compile("(name)").matcher(fields[j]).matches() ){
	                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = textInputPatternController.checkData();
	                        if (currentErrorCode > 0) {
	                            MessageProcessing.displayErrorMessage(1305);
	                            return currentErrorCode;
	                        }
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
        ObservableList<Pane> paneObservableList1 = FXCollections.observableArrayList();

        int oldSize = fieldsControllers.length;

        fieldsControllers = Arrays.copyOf(fieldsControllers,oldSize + countFields);

        for (int i = oldSize, j=0; i < fieldsControllers.length; i++, j++) {
            if(j == countFields)
                j=0;
            switch (fieldsTypes[j]){
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[j]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(((TableColumn)fieldsTable.getColumns().get(j)).getWidth()*0.85,35.0, 0.0);
                        choiceInputPatternController.setParameters(fields[j], "");
                        choiceInputPatternController.setFieldData("");
                        paneObservableList1.add(newPane);
                        break;
                    } else {
	                    loader = new FXMLLoader();
	                    loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

	                    newPane = (Pane) loader.load();
	                    fieldsControllers[i] = loader;
	                    IntInputPatternController intInputPatternController = loader.getController();
	                    intInputPatternController.setWidthHeight(((TableColumn)fieldsTable.getColumns().get(j)).getWidth()*0.85,35.0, 0.0);
	                    intInputPatternController.setParameters(fields[j], "");
	                    paneObservableList1.add(newPane);
	                    break;
                    }
                case "varchar":
                	if(Pattern.compile("(passw).*").matcher(fields[j]).matches()){
	                    loader = new FXMLLoader();
	                    loader.setLocation(getClass().getResource("../patterns_simple/PasswordPattern.fxml"));
	
	                    newPane = (Pane) loader.load();
	                    fieldsControllers[i] = loader;
	                    PasswordPatternController passwordInputPatternController = loader.getController();
	                    passwordInputPatternController.setWidthHeight(((TableColumn)fieldsTable.getColumns().get(j)).getWidth()*0.85,35.0, 0.0);
	                    passwordInputPatternController.setParameters(fields[j], "");
	                    paneObservableList1.add(newPane);
	                    break;
                	} else {
	                    loader = new FXMLLoader();
	                    loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));
	
	                    newPane = (Pane) loader.load();
	                    fieldsControllers[i] = loader;
	                    TextInputPatternController textInputPatternController = loader.getController();
	                    textInputPatternController.setWidthHeight(((TableColumn)fieldsTable.getColumns().get(j)).getWidth()*0.85,35.0, 0.0);
	                    textInputPatternController.setParameters(fields[j], "");
	                    paneObservableList1.add(newPane);
	                    break;
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

        int errorCount = 0, currentErrorCode = 0, countBooleanFields = 0;;

        for (int i = 0, j = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++, j++) {
            if(j == countFields)
                j=0;

            switch (fieldsTypes[j]) {
                case "int":
	                if (Pattern.compile("(id_).*").matcher(fields[j]).matches()) {
	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                    currentErrorCode = choiceInputPatternController.checkData();
	                    break;
	                } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                        break;
	                }
                case "varchar":
                    TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = textInputPatternController.checkData();
                    break;
            }
            errorCount += currentErrorCode;
        }

        return (errorCount == fields.length - countBooleanFields);
    }


    /*public FXMLLoader[] deleteRow() throws Exception {
        int row = fieldsTable.getSelectionModel().getSelectedIndex();

        for(int i = row * countFields, j = (row + 1) * countFields; j < fieldsControllers.length; i++, j++) {
            fieldsControllers[i] = fieldsControllers[j];
        }

        //Значение id у выбранной строки(если решим удалять из справочников)
        IntInputPatternController intInputPatternControllers = fieldsControllers[row*countFields].getController();
        intInputPatternControllers.getFieldData();

        fieldsControllers = Arrays.copyOfRange(fieldsControllers,0,fieldsControllers.length - countFields);
        fieldsTable.getItems().remove(row);
        list.remove(row);

        if (fieldsControllers.length == 0)
            addRow();

        return fieldsControllers;
    }*/


    void widthColumnListener(final TableColumn listerColumn) {
        listerColumn.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                int i = fieldsTable.getVisibleLeafIndex(listerColumn);
                for (int j = i; j < fieldsControllers.length; j = j + countFields) {
                    switch (fieldsTypes[i]) {
                        case "int":
        	                if (Pattern.compile("(id_).*").matcher(fields[i]).matches()) {
        	                    ChoiceInputPatternController choiceInputPatternController = fieldsControllers[j].getController();
        	                    choiceInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
        	                } else {
        	                	IntInputPatternController intInputPatternController = fieldsControllers[j].getController();
        	                	intInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
        	                }
                            break;
                        case "varchar":
                        	if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
                        		PasswordPatternController passwordInputPatternController = fieldsControllers[j].getController();
                        		passwordInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
    	                        break;
                        	} else {
	                            TextInputPatternController textInputPatternController = fieldsControllers[j].getController();
	                            textInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
	                            break;
                        	}
                    }
                }
            }
        });
    }
}