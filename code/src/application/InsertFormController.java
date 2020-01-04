package application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.util.regex.Pattern;

import controllers_simple.*;
import backend.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class InsertFormController {
    @FXML
    private Pane buttonsPane;

    @FXML
    private FlowPane fieldsPane;

	int countFields = 0;
	String[] fields, fieldsTypes;
	FXMLLoader[] fieldsControllers;

	String url, query;
	Properties props;
	Connection conn;
	CallableStatement cstmt;
	ResultSet rset;

    public void createForm() throws Exception {
		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
		ModelDBConnection.initConnection();

		query = "select * from Abiturient";

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(query);
		countFields = rsmd.getColumnCount();

    	fields = new String[countFields];
    	fieldsTypes = new String[countFields];
    	fieldsControllers = new FXMLLoader[countFields];

	    for (int i = 0; i < countFields; i++) {
	    	fields[i] = rsmd.getColumnLabel(i + 1);
	    	fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
	        /*System.out.println("\tColumnLabel : '" + rsmd.getColumnLabel (i + 1) + "', " + 
	                           "\tColumnType  : '" + rsmd.getColumnTypeName (i + 1));*/
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
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						fieldsPane.getChildren().add(newPane);

						BoolInputPatternController boolInputPatternController = loader.getController();
						boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
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
		addEditDeleteButtonsController.setParameters("SampleTab", fields, fieldsTypes, fieldsControllers);
		
		setEditable(false);
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
}