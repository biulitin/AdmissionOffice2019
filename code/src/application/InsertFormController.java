package application;

import controllers_simple.*;
import backend.*;

import java.sql.*;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class InsertFormController {
    @FXML
    private Pane buttonsPane;

    @FXML
    private FlowPane fieldsPane;

	int countFields = 0;
	String[] fields, fieldsTypes, fieldsOriginalNames;
	FXMLLoader[] fieldsControllers;
    FXMLLoader tabController;

	String aid;

    public void createForm(FXMLLoader tabController) throws Exception {
    	ModelDBConnection.setDefaultConnectionParameters();
		//ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
		ModelDBConnection.initConnection();

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Добавление Абитуриента"));
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

		FXMLLoader loader;
		Pane newPane;
		this.tabController = tabController;

		for (int i = 0; i < countFields; i++) {
			switch (fieldsTypes[i]) {
				case "date":
					loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

					newPane = (Pane) loader.load();
					fieldsControllers[i] = loader;

					fieldsPane.getChildren().add(newPane);

					DateInputPatternController dateInputPatternController = loader.getController();
					dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					break;
				case "double":
					loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("../patterns_simple/DoubleInputPattern.fxml"));

					newPane = (Pane) loader.load();
					fieldsControllers[i] = loader;

					fieldsPane.getChildren().add(newPane);

					DoubleInputPatternController doubleInputPatternController = loader.getController();
					doubleInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					break;
				case "int":
					if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						ChoiceInputPatternController choiceInputPatternController = loader.getController();
						choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						choiceInputPatternController.setFieldData("");
						break;
					}
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						BoolInputPatternController boolInputPatternController = loader.getController();
						boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						boolInputPatternController.flowPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
						break;
					}
					/* If we don't need "is_enrolled" change select or:
                    if(Pattern.compile("(is_).*").matcher(fields[i]).matches())
                        break;*/
					else{
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						IntInputPatternController intInputPatternController = loader.getController();
						intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						break;
					}
				case "varchar":
					if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/PhoneNumberInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						PhoneMaskInputPatternController phoneMaskInputPatternController = loader.getController();
						phoneMaskInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						break;
					}
					if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/PasswordPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						PasswordPatternController passwordInputPatternController = loader.getController();
						passwordInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						break;
					}
					else {
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						TextInputPatternController textInputPatternController = loader.getController();
						textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						break;
					}
			}
		}

		loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

		buttonsPane.getChildren().removeAll();
		newPane = (Pane) loader.load();
		buttonsPane.getChildren().add(newPane);

		AddEditDeleteButtonsController addEditDeleteButtonsController = loader.getController();
		addEditDeleteButtonsController.hideButton2(1);
		addEditDeleteButtonsController.hideButton2(1);
		addEditDeleteButtonsController.setParameters("Добавление Абитуриента", tabController, fields, fieldsTypes, fieldsControllers);
	}
/*
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
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
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
    	String[] generalInfoData = ModelDBConnection.getAbiturientGeneralInfoByID(aid);

    	if(generalInfoData != null) {
            for (int i = 0; i < generalInfoData.length; i++) {
    			switch (fieldsTypes[i]) {
				case "date":
					DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
					dateInputPatternController.setFieldData(generalInfoData[i]);
					break;
				case "double":
					DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
					doubleInputPatternController.setFieldData(generalInfoData[i]);
					break;
				case "int":
					if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						choiceInputPatternController.setFieldData(generalInfoData[i]);
						break;
					}
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
						BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
						boolInputPatternController.setFieldData(generalInfoData[i]);
						break;
					} else {
						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
						intInputPatternController.setFieldData(generalInfoData[i]);
						break;
					}
				case "varchar":
					if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
						PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
						phoneMaskInputPatternController.setFieldData(generalInfoData[i]);
						break;
					}
					if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
						PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
						passwordInputPatternController.setFieldData(generalInfoData[i]);
						break;
					}
					else {
						TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
						textInputPatternController.setFieldData(generalInfoData[i]);
						break;
					}

    			}
            }
    	}
    }
*/
    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	ModelDBConnection.insertAbiturientInfo(fieldsData[0], fieldsOriginalNames, fieldsData);
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
				if (Pattern.compile("(id_g).*").matcher(fields[i]).matches() ){
					ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = choiceInputPatternController.checkData();
					if (currentErrorCode > 0) {
						MessageProcessing.displayErrorMessage(119);
						return currentErrorCode;
					}
					break;
				}
				if (Pattern.compile("(id_n).*").matcher(fields[i]).matches() ){
					ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = choiceInputPatternController.checkData();
					if (currentErrorCode > 0) {
						MessageProcessing.displayErrorMessage(116);
						return currentErrorCode;
					}
					break;
				}
				if(Pattern.compile("aid").matcher(fields[i]).matches() ){
					IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = intInputPatternController.checkData();
					if (currentErrorCode > 0) {
						MessageProcessing.displayErrorMessage(100);
						return currentErrorCode;
					}
					try {
						if(ModelDBConnection.getAbiturientGeneralInfoByID(intInputPatternController.getFieldData()) != null){
							currentErrorCode++;
							MessageProcessing.displayErrorMessage(103);
							return currentErrorCode;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					break;
				}
				if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
					BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
					currentErrorCode = boolInputPatternController.checkData();
					break;
				} else {
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