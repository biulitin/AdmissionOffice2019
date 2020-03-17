package application;

import backend.MessageProcessing;
import backend.ModelDBConnection;
import controllers_simple.*;
import controllers_tabs.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.grios.tableadapter.DefaultTableAdapter;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;

public class MainWindowController {
    @FXML
    public FlowPane buttonsPane;

    @FXML
    public FlowPane returnInformationField;
    public FlowPane mainField;
    public Menu itemsCatalog;

    @FXML
    private Tab tabCompetitiveGroups, tabEntranceExams, tabIndividualAchievements, tabPrivileges, tabBasisFor100balls, tabEducation, tabAddressAndContacts, tabPassportAndINN, tabExtraInfo;
    
    @FXML
    private TabPane tabsPane;
    
    int countColumns = 0, countRows = 0, countFields = 0;
	String[] columns, columnsTypes;
	String[][] data;
	FXMLLoader[] columnsControllers;

    String[] fields, fieldsTypes, fieldsOriginalNames;
	FXMLLoader[] fieldsControllers;

	String url, query;
	Connection conn;
	CallableStatement cstmt;
	ResultSet rset;
	Properties props;
	Statement st;
	ResultSet rs;

    String aid;

	ObservableList<ObservableList> list = FXCollections.observableArrayList();
    
	@FXML
	private FlowPane paneForElems;

	@FXML
	private TableView<ObservableList> fieldsTable;

	private DefaultTableAdapter dta;
    private ActionEvent actionEvent;

    public void fillTab(FXMLLoader tabController) throws Exception {
		ModelDBConnection.setDefaultConnectionParameters();
		ModelDBConnection.initConnection();

		ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("АРМ по приему в ВУЗ"));
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

		ObservableList<Pane> paneObservableList = FXCollections.observableArrayList();

		FXMLLoader loader;
		Pane newPane;

		for (int i = 0; i < countFields; i++) {
			switch (fieldsTypes[i]) {
				case "int":
					if (Pattern.compile("(aid)").matcher(fields[i]).matches()) {
						TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
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
					if (Pattern.compile("SName").matcher(fields[i]).matches()) {
						TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
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
					if (Pattern.compile("FName").matcher(fields[i]).matches()) {
						TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
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
								return new SimpleObjectProperty<>((Pane) param.getValue().get(2));
							}
						});
						widthColumnListener(fieldData);
						fieldsTable.getColumns().add(fieldData);
						paneObservableList.add(newPane);
					}
					if (Pattern.compile("MName").matcher(fields[i]).matches()) {
						TableColumn<ObservableList, Pane> fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
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
		setFieldsData();
		fillMainInfo(countFields);
        addButtons(tabController);
        setEditable(false);
    }

	FXMLLoader loader;
	Pane newPane;

    public void addButtons(FXMLLoader tabController) throws Exception {
        FXMLLoader buttonsLoader = new FXMLLoader();

        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        buttonsPane.getChildren().add(newButtonsPane);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        addEditDeleteButtonsController.setParameters("АРМ по приему в ВУЗ", tabController, fields, fieldsTypes, fieldsControllers);
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
                        dateInputPatternController.setWidthHeight(330.0, 35.0, 180.0);
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
							choiceInputPatternController.setWidthHeight(900.0,35.0, 180.0);
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
		addCompetitiveGroupsTab();
		addIndividualAchievements();
		addEducationTab();
		addPassportTab();
		addOlympiadsTab();
		addExtraInfoTab();
        addEntranceExamTab();
        addPrivilegeTab();
    }

    public void addCompetitiveGroupsTab() throws Exception {
    	FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/CompetitiveGroupsTab.fxml"));

		tabCompetitiveGroups.setContent((Node) tabLoader.load());
		CompetitiveGroupsTabController competitiveGroupsTabController = tabLoader.getController();
		competitiveGroupsTabController.fillTab(tabLoader);
	}

	public void addIndividualAchievements() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/IndividualAchievementsTab.fxml"));

        tabIndividualAchievements.setContent((Node) tabLoader.load());
		IndividualAchievementsTabController individualAchievementsTabController = tabLoader.getController();
        individualAchievementsTabController.fillTab(tabLoader);
	}

	public void addEducationTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/EducationTab.fxml"));

		tabEducation.setContent((Node) tabLoader.load());
		EducationTabController educationTabController = tabLoader.getController();
		educationTabController.fillTab(tabLoader);
	}

	public void addPassportTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/PassportTab.fxml"));

		tabPassportAndINN.setContent((Node) tabLoader.load());
		PassportTabController passportTabController = tabLoader.getController();
		passportTabController.fillTab(tabLoader);
	}

	public void addOlympiadsTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/OlympiadsTab.fxml"));

		tabBasisFor100balls.setContent((Node) tabLoader.load());
		OlympiadsTabController olympiadsTabController = tabLoader.getController();
		olympiadsTabController.fillTab(tabLoader);
	}

	public void addExtraInfoTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/AdditionalInfoTab.fxml"));

		tabExtraInfo.setContent((Node) tabLoader.load());
		AdditionalInfoTabController additionalInfoTabController = tabLoader.getController();
		additionalInfoTabController.fillTab(tabLoader);
	}

	public void addEntranceExamTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/EntranceExamTab.fxml"));

        tabEntranceExams.setContent((Node) tabLoader.load());
		EntranceExamTabController entranceExamTabController = tabLoader.getController();
		entranceExamTabController.fillTab(tabLoader);
	}

	public void addPrivilegeTab() throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/PrivilegeTab.fxml"));

		tabPrivileges.setContent((Node) tabLoader.load());
		PrivilegeTabController privilegeTabController = tabLoader.getController();
		privilegeTabController.fillTab(tabLoader);
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

		dta = new DefaultTableAdapter(fieldsTable, data, columns);

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
					if (Pattern.compile("(id_).*").matcher(fields[i]).matches() ) {
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						choiceInputPatternController.setEditable(value);
						break;
					}
					if (Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()) {
						BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
						boolInputPatternController.setEditable(value);
						break;
					} else {
						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
						intInputPatternController.setEditable(value);
						break;
					}
				case "varchar":
					if (Pattern.compile("(phone).*").matcher(fields[i]).matches()) {
						PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
						phoneMaskInputPatternController.setEditable(value);
						break;
					}
					if (Pattern.compile("(passw).*").matcher(fields[i]).matches()) {
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
                    if (Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                        if (currentErrorCode > 0) {
                            MessageProcessing.displayErrorMessage(15);
                            return currentErrorCode;
                        }
                        break;
                    }
                    if (Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                        break;
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                        break;
                    }
                case "varchar":
                    if (Pattern.compile("(phone).*").matcher(fields[i]).matches()){
                        PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = phoneMaskInputPatternController.checkData();
                        break;
                    }
                    if (Pattern.compile("(passw).*").matcher(fields[i]).matches()){
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
            errorCount += currentErrorCode;
        }

        return errorCount;
    }

    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
        ModelDBConnection.updateAbiturientPassportByID(aid, fieldsOriginalNames, fieldsData);
    }

    public void onActionCatalog(ActionEvent actionEvent) throws Exception {
      MenuItem selectedCatalog =  (MenuItem)actionEvent.getTarget();

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("../application/Catalog.fxml"));
      AnchorPane root = loader.load();
      CatalogController catalogController = loader.getController();
      catalogController.fillForm(loader,selectedCatalog.getText());

      Stage stage = new Stage();
      stage.setScene(new Scene(root));
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(tabsPane.getParent().getScene().getWindow());
      stage.show();
    }


	public void setFieldsData() throws Exception {
		String[] mainWindowData = ModelDBConnection.getAbiturientData();

		if (mainWindowData != null) {
			for (int i = 1; i < mainWindowData.length / fields.length; i++)
				addRow();
		}
		for (int i = 0, j = 0; i < mainWindowData.length; i++, j++) {
			if (j == countFields)
				j=0;

			switch (fieldsTypes[j]) {
				case "int":
					if (Pattern.compile("(aid)").matcher(fields[i]).matches()) {
						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
						intInputPatternController.setFieldData(mainWindowData[i]);
						break;
					}
				case "varchar":
					if (Pattern.compile(".*(Name)").matcher(fields[i]).matches()) {
						TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
						textInputPatternController.setFieldData(mainWindowData[i]);
						break;
					}
			}
		}
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
				case "int":
					if(Pattern.compile("aid").matcher(fields[j]).matches() ){
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;
						IntInputPatternController intInputPatternController = loader.getController();
						intInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
						intInputPatternController.setParameters(fields[j], "");
						paneObservableList.add(newPane);
						break;
					}
				case "varchar":
					if (Pattern.compile(".*(Name)").matcher(fields[i]).matches()) {
						loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));
						newPane = (Pane) loader.load();
						fieldsControllers[i] = loader;
						IntInputPatternController intInputPatternController = loader.getController();
						intInputPatternController.setWidthHeight(fieldsTable.getColumns().get(j).getWidth()*0.85,35.0, 0.0);
						intInputPatternController.setParameters(fields[j], "");
						paneObservableList.add(newPane);
						break;
					}
			}
		}
		list.add(paneObservableList);
		fieldsTable.getItems().setAll(list);
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
							if (Pattern.compile("(aid)").matcher(fields[i]).matches()) {
								IntInputPatternController intInputPatternController = fieldsControllers[j].getController();
								intInputPatternController.setWidthHeight((double) newValue * 0.85, 35.0, 0.0);
								break;
							}
						case "varchar":
							if (Pattern.compile(".*(Name)").matcher(fields[i]).matches()) {
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
