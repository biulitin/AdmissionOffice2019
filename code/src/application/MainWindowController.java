package application;

import backend.MessageProcessing;
import backend.ModelDBConnection;

import controllers_simple.*;
import controllers_tabs.*;

import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainWindowController {
	String aid;


	//Область с основной информацией
	@FXML
	public FlowPane returnInformationField;
	public FlowPane mainField;

	public FlowPane buttonsPane;

	GeneralInfoController generalInfoController = new GeneralInfoController();


	//Таблица с абитуриентами
	@FXML
	private TableView<ObservableList> allAbiturientsTable;

	private static ObservableList<ObservableList> abiturientData;


	//Вкладки
	@FXML
	private TabPane tabsPane;

	@FXML
	private Tab tabCompetitiveGroups, tabEntranceExams, 
				tabIndividualAchievements, tabPrivileges, 
				tabBasisFor100balls, tabEducation, tabAddressAndContacts, 
				tabPassportAndINN, tabExtraInfo;

	ArrayList<FXMLLoader> tabsControllers;


	//Справочники
	@FXML
	public Menu itemsCatalog;


	//Область с основной информацией
	public class GeneralInfoController {
		int countFields = 0;

		String[] fields, fieldsTypes, fieldsOriginalNames;
		FXMLLoader[] fieldsControllers;

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
			abiturientData = FXCollections.observableArrayList();
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
						if (Pattern.compile("(id_).*").matcher(fields[i]).matches() ) {
							loader = new FXMLLoader();
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
							break;
						}
						if (fields[i].equals("needHostel") || fields[i].equals("is_enrolled")) {
							loader = new FXMLLoader();
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
							break;
						} else {
							loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

							newPane = (Pane) loader.load();
							fieldsControllers[i] = loader;

							mainField.getChildren().add(newPane);
							IntInputPatternController intInputPatternController = loader.getController();
							intInputPatternController.setWidthHeight(350.0, 35.0, 150.0);
							intInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
						}

						//Если поле aid, то нужно еще добавить его в таблицу как столбец
						if (Pattern.compile("(aid)").matcher(fields[i]).matches()) {
							addColumn(fields[i], i);
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

						//Если поле *Name, то нужно еще добавить его в таблицу как столбец
						if (Pattern.compile(".*(Name)").matcher(fields[i]).matches()) {
							addColumn(fields[i], i);
						}
						break;
				}
			}

			addButtons(tabController);
			setEditable(false);
		}


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


	    public void setFieldsData(String aid) throws Exception {
	        String[] generalData = ModelDBConnection.getAbiturientGeneralInfoByID(aid);

	        if(generalData != null) {
	            for (int i = 0; i < generalData.length; i++) {
	                switch (fieldsTypes[i]) {
	                    case "date":
	                        DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
	                        dateInputPatternController.setFieldData(generalData[i]);
	                        break;
	                    case "int":
	                        if (Pattern.compile("(id_).*").matcher(fields[i]).matches()) {
	                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
	                            choiceInputPatternController.setFieldData(generalData[i]);
	                            break;
	                        }
	                        else if (fields[i].equals("needHostel") || fields[i].equals("is_enrolled")) {
	                            BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
	                            boolInputPatternController.setFieldData(generalData[i]);
	                            break;
	                        }
	                        else {
	                            IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                            intInputPatternController.setFieldData(generalData[i]);
	                        }
	                        break;
	                    case "varchar":
	                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
	                            textInputPatternController.setFieldData(generalData[i]);
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
	                    if (Pattern.compile("returnDate").matcher(fields[i]).matches()){
	                        break;
	                    } else {
	                    	DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
	                    	currentErrorCode = dateInputPatternController.checkData();
	                    	break;
	                    }
					case "double":
						DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
						currentErrorCode = doubleInputPatternController.checkData();
						break;
					case "int":
	                    if(Pattern.compile("aid").matcher(fields[i]).matches() ){
	                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
	                        currentErrorCode = intInputPatternController.checkData();
	                        if (currentErrorCode > 0) {
	                            MessageProcessing.displayErrorMessage(100);
	                            return currentErrorCode;
	                        }
	                        try {
	                            if(!aid.equals(intInputPatternController.getFieldData())) {
	                                if (ModelDBConnection.getAbiturientGeneralInfoByID(intInputPatternController.getFieldData()) != null) {
	                                    currentErrorCode++;
	                                    MessageProcessing.displayErrorMessage(103);
	                                    return currentErrorCode;
	                                }
	                            }
	                        } catch (SQLException e) {
	                            e.printStackTrace();
	                        }
	                        break;
	                    }
					    if (Pattern.compile("id_returnReason").matcher(fields[i]).matches()){
	                        break;
	                    }
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
				//errorCount += currentErrorCode;
			}

			return errorCount;
		}


		public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
			ModelDBConnection.updateAbiturientGeneralInfoByID(aid, fieldsOriginalNames, fieldsData);
		}


		public void deleteAbiturientDataFromDataBase(String[] fieldsData) throws SQLException {
			ModelDBConnection.deleteAbiturientByID(aid, fieldsOriginalNames, fieldsData);
		}
	}


	//Таблица с абитуриентами
	public void addColumn(String field, int columnIndex) {
		TableColumn fieldData = new TableColumn<>(ModelDBConnection.getTranslationOfField(field, "Abiturient"));
		fieldData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
				return new SimpleStringProperty(param.getValue().get(columnIndex).toString());
			}
		});
		allAbiturientsTable.getColumns().add(fieldData);
	}


    public void getSelectedAbiturient(MouseEvent mouseEvent) {
        try {
        	if(allAbiturientsTable.getSelectionModel().getSelectedIndex() > -1) {
				setFieldsData(((ObservableList)(allAbiturientsTable.getSelectionModel().getSelectedItems().get(0))).get(0).toString());
				fillTabsContent(allAbiturientsTable.getSelectionModel().getSelectedItems().get(0).get(0).toString());
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public static void refreshTable() throws Exception {
		abiturientData.clear();
		ModelDBConnection.getAbiturientInfo(abiturientData);
	}


	//Вкладки
	public void fillTabsContent(String aid) throws Exception {
		tabsControllers = new ArrayList<FXMLLoader>();

		addCompetitiveGroupsTab(aid);
		addIndividualAchievements(aid);
		addEducationTab(aid);
		addPassportTab(aid);
		addOlympiadsTab(aid);
		addExtraInfoTab(aid);
		addEntranceExamTab(aid);
		addPrivilegeTab(aid);
		addAddressTab(aid);
	}

	public void addCompetitiveGroupsTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/CompetitiveGroupsTab.fxml"));

		tabCompetitiveGroups.setContent((Node) tabLoader.load());
		CompetitiveGroupsTabController competitiveGroupsTabController = tabLoader.getController();
		competitiveGroupsTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addAddressTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/AddressTab.fxml"));

		tabAddressAndContacts.setContent((Node) tabLoader.load());
		AddressTabController addresTabController = tabLoader.getController();
		addresTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addIndividualAchievements(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/IndividualAchievementsTab.fxml"));

		tabIndividualAchievements.setContent((Node) tabLoader.load());
		IndividualAchievementsTabController individualAchievementsTabController = tabLoader.getController();
		individualAchievementsTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addEducationTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/EducationTab.fxml"));

		tabEducation.setContent((Node) tabLoader.load());
		EducationTabController educationTabController = tabLoader.getController();
		educationTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addPassportTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/PassportTab.fxml"));

		tabPassportAndINN.setContent((Node) tabLoader.load());
		PassportTabController passportTabController = tabLoader.getController();
		passportTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addOlympiadsTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/OlympiadsTab.fxml"));

		tabBasisFor100balls.setContent((Node) tabLoader.load());
		OlympiadsTabController olympiadsTabController = tabLoader.getController();
		olympiadsTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addExtraInfoTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/AdditionalInfoTab.fxml"));

		tabExtraInfo.setContent((Node) tabLoader.load());
		AdditionalInfoTabController additionalInfoTabController = tabLoader.getController();
		additionalInfoTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addEntranceExamTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/EntranceExamTab.fxml"));

		tabEntranceExams.setContent((Node) tabLoader.load());
		EntranceExamTabController entranceExamTabController = tabLoader.getController();
		entranceExamTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}

	public void addPrivilegeTab(String aid) throws Exception {
		FXMLLoader tabLoader;
		tabLoader = new FXMLLoader();
		tabLoader.setLocation(getClass().getResource("../patterns_tabs/PrivilegeTab.fxml"));

		tabPrivileges.setContent((Node) tabLoader.load());
		PrivilegeTabController privilegeTabController = tabLoader.getController();
		privilegeTabController.fillTab(tabLoader, aid);

		tabsControllers.add(tabLoader);
	}



	//Справочники
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


	//Общие методы основного окна
	public void fillTab(FXMLLoader tabController) throws Exception {
		generalInfoController.fillTab(tabController);

		ModelDBConnection.getAbiturientInfo(abiturientData);
		allAbiturientsTable.setItems(abiturientData);

		fillTabsContent(aid);
	}


    public void setFieldsData(String aid) throws Exception {
        this.aid = aid;

        generalInfoController.setFieldsData(aid);
    }


	public void setEditable(Boolean value) {
		
	}


	public int checkData() {
		return generalInfoController.checkData();
	}


	public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
		generalInfoController.uploadFieldsDataToDataBase(fieldsData);
	}


	public void deleteAbiturientDataFromDataBase(String[] fieldsData) throws SQLException {
		generalInfoController.deleteAbiturientDataFromDataBase(fieldsData);
	}
}
