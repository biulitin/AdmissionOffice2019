package controllers_tabs;


import javafx.geometry.NodeOrientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;

import controllers_simple.*;
import backend.*;

public class PassportTabController {
    public GridPane parentGridPane;
    public GridPane childGridPane;
    public Pane buttonsPane;

    int countFields = 0;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;

    String aid;

    public void fillTab(FXMLLoader tabController) throws Exception {
        parentGridPane.autosize();
        childGridPane.autosize();

        ModelDBConnection.setDefaultConnectionParameters();
        //ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
		ModelDBConnection.initConnection();

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Паспорт и ИНН"));
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

        FXMLLoader loader;
        Pane newPane;

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    childGridPane.add(newPane,2,0);
                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setWidthHeight(210.0, 35.0, 50.0);
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientPassport"));
                    break;
                case "int":
                    if(Pattern.compile("(id_t).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,0);
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(300.0,35.0, 100.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientPassport"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    }
                    break;
                case "varchar":
                    if(Pattern.compile("(series).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add( newPane,0,0);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(190.0, 35.0, 50.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientPassport"));
                        break;
                    }
                    if(Pattern.compile("(number).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane,1,0);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(190.0, 35.0, 50.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientPassport"));
                        break;
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,2);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(490.0, 35.0, 100.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientPassport"));
                        break;
                    }
                    if(Pattern.compile("(Birthp).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,3);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(490.0, 35.0, 100.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
                        break;
                    }
                    if(Pattern.compile("(inn)").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,4);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(490.0, 35.0, 100.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
                        break;
                    }
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
        addEditDeleteButtonsController.setParameters("Паспорт и ИНН", tabController, fields, fieldsTypes, fieldsControllers);
        addEditDeleteButtonsController.hideButton(0, 2);
        addEditDeleteButtonsController.setWidthHideButtons(155.0, 35.0, 1);
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


    public void setFieldsData(String aid) throws Exception {
    	this.aid = aid;
    	String[] passportData = ModelDBConnection.getAbiturientPassportByID(aid);

    	if(passportData != null) {
            for (int i = 0; i < passportData.length; i++) {
                switch (fieldsTypes[i]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(passportData[i]);
                        break;
                    case "int":
                        if (Pattern.compile("(id_t).*").matcher(fields[i]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        break;
                    case "varchar":
                        if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        if (Pattern.compile("(number).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        if (Pattern.compile("(issued).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        if (Pattern.compile("(Birthp).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        if (Pattern.compile("(inn)").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(passportData[i]);
                            break;
                        }
                        break;
                }
            }
    	}
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	ModelDBConnection.updateAbiturientPassportByID(aid, fieldsOriginalNames, fieldsData);
    }


    public int checkData() {
    	int errorCount = 0, currentErrorCode = 0;

		for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
			switch (fieldsTypes[i]) {
				case "date":
					DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = dateInputPatternController.checkData();
					break;
				case "double":
					DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = doubleInputPatternController.checkData();
					break;
				case "int":
					if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(1110);
							return currentErrorCode;
						}
						break;
					}
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
						BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = boolInputPatternController.checkData();
						break;
					} else{
						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = intInputPatternController.checkData();
						break;
					}
				case "varchar":
					if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
						PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = phoneMaskInputPatternController.checkData();
						break;
					}
					if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
						PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = passwordInputPatternController.checkData();
						break;
					}
					else {
						TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = textInputPatternController.checkData();
						break;
					}
			}
			//errorCount += currentErrorCode;
			//System.out.println(currentErrorCode);
		}

		return errorCount;
    }
}