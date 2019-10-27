package application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import controllers_simple.*;
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
		url = "jdbc:sqlserver://" + "localhost" + ":1433;databaseName=" + "Abiturient" + ";user="
				+ "igor_sa" + ";password=" + "200352" + ";";
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		conn = DriverManager.getConnection(url);
		
		query = "select * from Abiturient";
		cstmt = conn.prepareCall(query, 1004, 1007);
		rset = cstmt.executeQuery();

		int countStrings = rset.last() ? rset.getRow() : 0;
		rset.beforeFirst();
		
		ResultSetMetaData rsmd = rset.getMetaData();
		int countFields = rsmd.getColumnCount();

    	fields = new String[countFields];
    	fieldsTypes = new String[countFields];
    	fieldsControllers = new FXMLLoader[countFields];
    	
		
	    for (int i = 0; i < countFields; i++) {
	    	fields[i] = rsmd.getColumnLabel (i + 1);
	    	fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
	        /*System.out.println("\tColumnLabel : '" + rsmd.getColumnLabel (i + 1) + "', " + 
	                           "\tColumnType  : '" + rsmd.getColumnTypeName (i + 1));*/
	    }

		FXMLLoader loader;
		Pane newPane;

    	//Sample values
    	/*fields[0] = "Требуется ли общежитие?";
    	fieldsTypes[0] = "boolean";
    	fields[1] = "Тип паспорта";
    	fieldsTypes[1] = "choice"; // Think over
    	fields[2] = "Дата рождения";
    	fieldsTypes[2] = "date";
    	fields[3] = "Средний балл аттестата";
    	fieldsTypes[3] = "double precision";
    	fields[4] = "Год окончания ОУ";
    	fieldsTypes[4] = "integer";
    	fields[5] = "Пароль";
    	fieldsTypes[5] = "password"; // Think over
    	fields[6] = "ФИО";
    	fieldsTypes[6] = "text";*/
    	
    	for (int i = 0; i < countFields; i++) {
			switch (fieldsTypes[i]) {
			case "boolean":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        BoolInputPatternController boolInputPatternController = loader.getController();
		        boolInputPatternController.setParameters(fields[i]);
				break;
			case "choice": // Think over
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        ChoiceInputPatternController choiceInputPatternController = loader.getController();
		        choiceInputPatternController.setParameters(fields[i]);
				break;
			case "date":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        DateInputPatternController dateInputPatternController = loader.getController();
		        dateInputPatternController.setParameters(fields[i]);
				break;
			case "double":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/DoubleInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        DoubleInputPatternController doubleInputPatternController = loader.getController();
		        doubleInputPatternController.setParameters(fields[i]);
				break;
			case "int":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        IntInputPatternController intInputPatternController = loader.getController();
		        intInputPatternController.setParameters(fields[i]);
				break;
			case "password": // Think over
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/PasswordPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        PasswordPatternController passwordInputPatternController = loader.getController();
		        passwordInputPatternController.setParameters(fields[i]);
				break;
			case "varchar":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        fieldsControllers[i] = loader;

		        fieldsPane.getChildren().add(newPane);

		        TextInputPatternController textInputPatternController = loader.getController();
		        textInputPatternController.setParameters(fields[i]);
				break;
			}
    	}
    }
}
