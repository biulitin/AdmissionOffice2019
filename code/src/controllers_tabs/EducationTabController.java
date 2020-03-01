package controllers_tabs;

import java.sql.*;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import controllers_simple.*;
import backend.*;

public class EducationTabController {
    @FXML
    public GridPane parentGridPane;

    @FXML
    public GridPane childGridPane;

    @FXML
    public Pane buttonsPane;

    int countFields = 0;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;

    String aid;

    public void fillTab(FXMLLoader tabController) throws Exception {
    	ModelDBConnection.setDefaultConnectionParameters();
    	//ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
		ModelDBConnection.initConnection();

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Образование"));
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

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    parentGridPane.add(newPane, 0, 3);

                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setWidthHeight(445.0, 35.0, 160.0);
                    dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                    break;
                case "int":
                    if (Pattern.compile("(id_l).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,0);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(678.0, 35.0, 160.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    }
                    if (Pattern.compile("(id_t).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,1);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(678.0, 35.0, 160.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    }
                    if (Pattern.compile("(yearOf_).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 2, 0);

                        IntInputPatternController intInputPatternController = loader.getController();
                        // intInputPatternController.setWidthHeight(340.0, 35.0, 80.8);
                        intInputPatternController.setWidthHeight(210.0, 35.0, 80.8);
                        intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        break;
                    }
                    break;
                case "varchar":
                    if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 0, 0);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(210.0, 35.0, 70.8);
                        // textInputPatternController.setWidthHeight(285.0, 35.0, 45.74);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        break;
                    }
                    if (Pattern.compile("(number).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 1, 0);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(210.0, 35.0, 70.8);
                        // textInputPatternController.setWidthHeight(290.0, 35.0, 39.9);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
                        break;
                    }
                    if (Pattern.compile("(name_).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane, 0, 2);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(678.0, 35.0, 160.0);
                        // textInputPatternController.setWidthHeight(480.0, 35.0, 67.44);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientEducation"));
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
        addEditDeleteButtonsController.setParameters("Образование", tabController, fields, fieldsTypes, fieldsControllers);

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
    	String[] educationData = ModelDBConnection.getAbiturientEducationByID(aid);

    	if(educationData != null) {
            for (int i = 0; i < educationData.length; i++) {
                switch (fieldsTypes[i]) {
                    case "date":
                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                        dateInputPatternController.setFieldData(educationData[i]);
                        break;
                    case "int":
                        if (Pattern.compile("(id_).*").matcher(fields[i]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(educationData[i]);
                            break;
                        } else {
    						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
    						intInputPatternController.setFieldData(educationData[i]);
    						break;
                        }
                    case "varchar":
                        if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(educationData[i]);
                            break;
                        }
                        if (Pattern.compile("(number).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(educationData[i]);
                            break;
                        }
                        if (Pattern.compile("(issued).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(educationData[i]);
                            break;
                        }
                        if (Pattern.compile("(Birthp).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(educationData[i]);
                            break;
                        }
                        if (Pattern.compile("(inn)").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(educationData[i]);
                            break;
                        }
                        break;
                }
            }
    	}
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
    	ModelDBConnection.updateAbiturientEducationByID(aid, fieldsOriginalNames, fieldsData);
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
					if(Pattern.compile("(id_t).*").matcher(fields[i]).matches() ){
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(911);
							return currentErrorCode;
						}
						break;
					}
					if(Pattern.compile("(id_l).*").matcher(fields[i]).matches() ){
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = choiceInputPatternController.checkData();
						if (currentErrorCode > 0) {
							MessageProcessing.displayErrorMessage(910);
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