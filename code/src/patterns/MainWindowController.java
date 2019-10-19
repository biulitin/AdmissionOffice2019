package code.src.patterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.grios.tableadapter.DefaultTableAdapter;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class MainWindowController {
	int countColumns = 0, countRows = 0;
	String[] columns, columnsTypes;
	String[][] data;
	FXMLLoader[] columnsControllers;
	
	String url;
	Properties props;
	Connection conn;
	Statement st;
	ResultSet rs;
	
    @FXML
    private JFXButton insertButton;
    
	@FXML
	private FlowPane paneForElems;

	@FXML
	private TableView tableView;

	private DefaultTableAdapter dta;

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
	}
	
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
		/*st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

		dta = new DefaultTableAdapter(tableView, data, columns);

		rs.close();
		st.close();*/
	}
}