package application;

import controllers_simple.*;
import controllers_tabs.*;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.grios.tableadapter.DefaultTableAdapter;

import backend.ModelDBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class MainWindowController {
    @FXML
    public FlowPane buttonsPane;

    @FXML
    public FlowPane returnInformationField;
    public FlowPane mainField;
    
    @FXML
    private Tab tabCompetitiveGroups, tabEntranceExams, tabPrivileges, tabBasisFor100balls, tabEducation, tabAddressAndContacts, tabPassportAndINN, tabExtraInfo;
    
    @FXML
    private TabPane tabsPane;
    
    int countColumns = 0, countRows = 0, countFields = 0;
	String[] columns, columnsTypes;
	String[][] data;
	FXMLLoader[] columnsControllers;

	String[] fields, fieldsTypes;
	FXMLLoader[] fieldsControllers;

	String url, query;
	Connection conn;
	CallableStatement cstmt;
	ResultSet rset;
	Properties props;
	Statement st;
	ResultSet rs;
    
	@FXML
	private FlowPane paneForElems;

	@FXML
	private TableView tableView;

	private DefaultTableAdapter dta;

	public void fillInPatterns() throws Exception {
		ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
		ModelDBConnection.initConnection();

		query = "SELECT Abiturient.aid, Abiturient.registrationdate, " +
				"Abiturient.SName, Abiturient.id_gender, Abiturient.id_nationality," +
				"Abiturient.FName, Abiturient.Birthday, Abiturient.needHostel," +
				"Abiturient.MName, Abiturient.id_returnReason," +
				"Abiturient.returnDate, Abiturient.is_enrolled\n" +
				"FROM Abiturient JOIN ReturnReasons ON\n" +
				"(ReturnReasons.id=Abiturient.id_returnReason)\n" +
				"JOIN Nationality ON\n" +
				"(Nationality.id=Abiturient.id_nationality)\n" +
				"JOIN Gender ON\n" +
				"Gender.id=Abiturient.id_gender;";
		cstmt = ModelDBConnection.getConnection().prepareCall(query, 1004, 1007);
		rset = cstmt.executeQuery();

		rset.beforeFirst();

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(query);
		countFields = rsmd.getColumnCount();

		fields = new String[countFields];
		fieldsTypes = new String[countFields];
		fieldsControllers = new FXMLLoader[countFields];

		for (int i = 0; i < countFields; i++) {
			fields[i] = rsmd.getColumnLabel(i + 1);
			fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
		}

        fillMainInfo(countFields);
        addButtons();
        
        setEditable(false);
    }

	FXMLLoader loader;
	Pane newPane;

    public void addButtons() throws Exception {
        FXMLLoader buttonsLoader = new FXMLLoader();

        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        buttonsPane.getChildren().add(newButtonsPane);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        addEditDeleteButtonsController.setParameters("АРМ по приему в ВУЗ", fields, fieldsTypes, fieldsControllers);
        addEditDeleteButtonsController.setWidthHideButtons(320.0, 35.0, 3);
    }

    public void fillMainInfo(int countFields) throws Exception {
		for (int i = 0; i < countFields; i++) {
			switch (fieldsTypes[i]) {
				case "date":
					loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

					newPane = (Pane) loader.load();
					fieldsControllers[i] = loader;

                    DateInputPatternController dateInputPatternController = loader.getController();
					if (fields[i].equals("returnDate")) {
						returnInformationField.getChildren().add(newPane);
                        dateInputPatternController.setWidthHeight(330.0, 35.0, 100.0);
					} else {
						mainField.getChildren().add(newPane);
                        dateInputPatternController.setWidthHeight(350.0, 35.0, 150.0);
					}
					dateInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					break;
				case "int":
					loader = new FXMLLoader();
					if (Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
						loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						ChoiceInputPatternController choiceInputPatternController = loader.getController();

						if (fields[i].equals("id_returnReason")) {
							returnInformationField.getChildren().add(newPane);
							choiceInputPatternController.setWidthHeight(900.0,35.0, 100.0);
						} else {
							mainField.getChildren().add(newPane);
							choiceInputPatternController.setWidthHeight(285.0,35.0, 150.0);
						}
						choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						choiceInputPatternController.setFieldData("");
					} else if (fields[i].equals("needHostel") || fields[i].equals("is_enrolled")) {
						loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						BoolInputPatternController boolInputPatternController = loader.getController();
						if (fields[i].equals("is_enrolled")) {
							returnInformationField.getChildren().add(newPane);
						} else {
							mainField.getChildren().add(newPane);
						}
                        boolInputPatternController.setWidthHeight(200.0, 35.0);
						boolInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					}
					else {
						loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;

						mainField.getChildren().add(newPane);
						IntInputPatternController intInputPatternController = loader.getController();
						intInputPatternController.setWidthHeight(350.0, 35.0, 150.0);
						intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					}
					break;
				case "varchar":
					loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

					newPane = (Pane) loader.load();
					fieldsControllers[i] = loader;

					mainField.getChildren().add(newPane);
					TextInputPatternController textInputPatternController = loader.getController();
					textInputPatternController.setWidthHeight(350.0, 35.0, 150.0);
					textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
					break;
			}
		}

    }

    public void fillTabsContent() throws Exception {
    	//Фрагмент отрисовки содержимого всех вкладок
    	
    	/*FXMLLoader loader = new FXMLLoader();
    	
    	loader.setLocation(getClass().getResource("../patterns_simple/CompetitiveGroupsTab.fxml"));
    	CompetitiveGroupsTabController competitiveGroupsTabController = loader.getController();
        competitiveGroupsTabController.fillTab();
        AnchorPane root;
        root = (AnchorPane) loader.load();
    	
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));
        Pane newButtonsPane = (Pane) buttonsLoader.load();
    	
        //loader.setLocation(getClass().getResource("../patterns_simple/IndividualAchievementsPattern.fxml"));
    	//loader.setLocation(getClass().getResource("../patterns_simple/Education.fxml"));
    	//loader.setLocation(getClass().getResource("../patterns_simple/PassportTab.fxml"));
        tabsPane.getTabs().get(3).setContent((Node) buttonsLoader.load());*/
    }
    
/*
	public void prepareTable() throws Exception {
		// Connection to DB
		url = "jdbc:postgresql://localhost/postgres";
		props = new Properties();
		props.setProperty("user", "postgres");
		props.setProperty("password", "grandopera");
		conn = DriverManager.getConnection(url, props);
		conn.setAutoCommit(false);

		// Read info about columns and their types
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery("select column_name, data_type " 
										+ "from information_schema.columns "
										+ "where information_schema.columns.table_name='clients_1';");
		rs.last();
		countColumns = rs.getRow();
		rs.beforeFirst();

		columns = new String[countColumns];
		columnsTypes = new String[countColumns];
		columnsControllers = new FXMLLoader[countColumns];
		int i = 0;
		
		FXMLLoader loader;
		Pane newPane;
		
		while (rs.next()) {
			System.out.println(rs.getString(1) + " " + rs.getString(2));

			columns[i] = rs.getString(1);
			columnsTypes[i] = rs.getString(2);
			i++;

			// Create a form for input of data into the column according to its type
			switch (rs.getString(2)) {
			case "integer":
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("IntInputPattern.fxml"));

				newPane = (Pane) loader.load();
				columnsControllers[i - 1] = loader;

				paneForElems.getChildren().add(newPane);

				IntInputPatternController intInputPatternController = loader.getController();
				intInputPatternController.setParameters(rs.getString(1));
				break;
			case "double precision":
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("DoubleInputPattern.fxml"));

				newPane = (Pane) loader.load();
				columnsControllers[i - 1] = loader;

				paneForElems.getChildren().add(newPane);

				DoubleInputPatternController doubleInputPatternController = loader.getController();
				doubleInputPatternController.setParameters(rs.getString(1));
				break;
			case "password":
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("PasswordPattern.fxml"));

				newPane = (Pane) loader.load();
				columnsControllers[i - 1] = loader;

				paneForElems.getChildren().add(newPane);

				PasswordPatternController passwordPatternController = loader.getController();
				passwordPatternController.setParameters(rs.getString(1));
				break;
			case "text":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("TextInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        columnsControllers[i - 1] = loader;
		        
		        paneForElems.getChildren().add(newPane);

		        TextInputPatternController textInputPatternController = loader.getController();
		        textInputPatternController.setParameters(rs.getString(1));
				break;
			case "date":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("DateInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        columnsControllers[i - 1] = loader;
		        
		        paneForElems.getChildren().add(newPane);

		        DateInputPatternController dateInputPatternController = loader.getController();
		        dateInputPatternController.setParameters(rs.getString(1));
				break;
			case "choice":
				loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("ChoiceInputPattern.fxml"));

				newPane = (Pane) loader.load();
				columnsControllers[i - 1] = loader;

				paneForElems.getChildren().add(newPane);

				ChoiceInputPatternController choiceInputPatternController = loader.getController();
				choiceInputPatternController.setParameters(rs.getString(1));
				break;
			case "boolean":
		        loader = new FXMLLoader();
		        loader.setLocation(getClass().getResource("BooleanInputPattern.fxml"));

		        newPane = (Pane) loader.load();
		        columnsControllers[i - 1] = loader;
		        
		        paneForElems.getChildren().add(newPane);

		        BoolInputPatternController boolInputPatternController = loader.getController();
		        boolInputPatternController.setParameters(rs.getString(1));
				break;
			}
		}

		// Filling the created table with data
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery("select * from clients_1;");
		rs.last();
		countRows = rs.getRow();
		rs.beforeFirst();

		data = new String[countRows][countColumns];
		i = 0;
		while (rs.next()) {
			for (int j = 0; j < countColumns; j++) {
				data[i][j] = rs.getString(j + 1);
				System.out.print(data[i][j] + " ");
			}
			i++;
			System.out.println();
		}

		dta = new DefaultTableAdapter(tableView, data, columns);

		rs.close();
		st.close();
	}*/

    @FXML
    void inputeDataFromGUI(ActionEvent event) throws Exception {
    	inputeDataFromGUI();
    }

	public void inputeDataFromGUI() throws Exception {
		String query = "insert into clients_1 values (";
		int i = 0;
		for(Node s : paneForElems.getChildren()) {
			switch (columnsTypes[i]) {
                case "boolean":
                    BoolInputPatternController boolInputPatternController = columnsControllers[i].getController();
                    query += "'" + boolInputPatternController.getFieldData() + "',";
                    break;
                case "integer":
                    IntInputPatternController intInputPatternController = columnsControllers[i].getController();
                    query += "'" + intInputPatternController.getFieldData() + "',";
                    break;
                case "double precision":
                    DoubleInputPatternController doubleInputPatternController = columnsControllers[i].getController();
                    query += "'" + doubleInputPatternController.getFieldData() + "',";
                    break;
                case "password":
                    PasswordPatternController passwordPatternController = columnsControllers[i].getController();
                    query += "'" + passwordPatternController.getFieldData() + "',";
                    break;
                case "text":
                    TextInputPatternController textInputPatternController = columnsControllers[i].getController();
                    query += "'" + textInputPatternController.getFieldData() + "',";
                    break;
                case "date":
                    DateInputPatternController dateInputPatternController = columnsControllers[i].getController();
                    query += "'" + dateInputPatternController.getFieldData() + "',";
                    break;
                case "choice":
                    ChoiceInputPatternController choiceInputPatternController = columnsControllers[i].getController();
                    query += "'" + choiceInputPatternController.getFieldData() + "',";
                    break;
			}
			i++;
		}
		
		query = query.substring(0, query.length() - 1) + ");";
		System.out.println(query);

		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		st.executeUpdate(query);
		
		// Filling the created table with data
		st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		rs = st.executeQuery("select * from student;");
		rs.last();
		countRows = rs.getRow();
		rs.beforeFirst();

		data = new String[countRows][countColumns];
		i = 0;
		while (rs.next()) {
			for (int j = 0; j < countColumns; j++) {
				data[i][j] = rs.getString(j + 1);
				System.out.print(data[i][j] + " ");
			}
			i++;
			System.out.println();
		}
		System.out.println(Arrays.deepToString(data));

		dta = new DefaultTableAdapter(tableView, data, columns);

		rs.close();
		st.close();
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
}