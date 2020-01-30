package controllers_simple;

import controllers_tabs.*;

import java.util.regex.Pattern;

import application.InsertFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class AddEditDeleteButtonsController {
	private String tabName;
	private String[] fields, fieldsTypes;
	private FXMLLoader[] fieldsControllers;
	FXMLLoader tabController;

	@FXML
	private HBox buttonsBox;

    @FXML
    public Button addButton;

    @FXML
    public Button editButton;

    @FXML
    public Button deleteButton;

    @FXML
    void addButtonAction(ActionEvent event) {

    }

    @FXML
    void editButtonAction(ActionEvent event) {
    	String saveQuery = "";
    	Boolean activate;

    	switch(editButton.getText()) {
			case "Редактировать":
				editButton.setText("Сохранить");
				//each element have to be activated to editable mode
				activate = true;
				this.setEditable(activate);
				break;
			case "Сохранить":
				try {
					if (checkData() == 0) {
						//each element have to be activated to non-editable mode
						editButton.setText("Редактировать");
						activate = false;
	
						String[] fieldsData = getFieldsData();
						
						/*for(String curFieldsData : fieldsData)
							System.out.println(curFieldsData);*/

						//Выбор нужной операции передачи данных в БД в зависимости от вкладки
				    	switch (tabName) {
					    	case "SampleTab":
					    		InsertFormController insertFormController = tabController.getController();
					    		insertFormController.uploadFieldsDataToDataBase(fieldsData);
					    		break;
					    	case "Паспорт и ИНН":
					            PassportTabController passportTabController = tabController.getController();
					    		passportTabController.uploadFieldsDataToDataBase(fieldsData);
					    		break;
					    	case "Образование":
					            EducationTabController educationTabController = tabController.getController();
					            educationTabController.uploadFieldsDataToDataBase(fieldsData);
					    		break;
					    	case "Вступительные испытания":
					    		EntranceExamTabController entranceExamTabController = tabController.getController();
					    		entranceExamTabController.uploadFieldsDataToDataBase(fieldsData);
					    		break;
				    	}
	
		    			this.setEditable(activate);
					}
	    			break;
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
    	}
    }

    public String[] getFieldsData() {
    	if (fieldsControllers == null) return null;

    	String[] fieldsData = new String[fieldsControllers.length];

    	for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
			switch (fieldsTypes[i]) {
				case "date":
					DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
					fieldsData[i] = dateInputPatternController.getFieldData();
					if (fieldsData[i].equals("")) fieldsData[i] = "null";
					break;
				case "double":
					DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
					fieldsData[i] = doubleInputPatternController.getFieldData();
					if (fieldsData[i].equals("")) fieldsData[i] = "null";
					break;
				case "int":
					if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
						ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = choiceInputPatternController.getFieldData();
						if (fieldsData[i].equals("0")) fieldsData[i] = "null";
						break;
					}
					if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()  || Pattern.compile("(is).*").matcher(fields[i]).matches()){
						BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = boolInputPatternController.getFieldData();
						break;
					}
					/* If we don't need "is_enrolled" change select or:
                    if(Pattern.compile("(is_).*").matcher(fields[i]).matches())
                        break;*/
					else{
						IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = intInputPatternController.getFieldData();
						if (fieldsData[i].equals("")) fieldsData[i] = "null";
						break;
					}
				case "varchar":
					if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
						PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = phoneMaskInputPatternController.getFieldData();
						if (fieldsData[i].equals("")) fieldsData[i] = "null";
						break;
					}
					if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
						PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = passwordInputPatternController.getFieldData();
						if (fieldsData[i].equals("")) fieldsData[i] = "null";
						break;
					}
					else {
						TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
						fieldsData[i] = textInputPatternController.getFieldData();
						if (fieldsData[i].equals("")) fieldsData[i] = "null";
						break;
					}
			}
			fieldsData[i] = "'" + fieldsData[i] + "'";
		}
    	return fieldsData;
    }

    @FXML
    void deleteButtonAction(ActionEvent event) {

    }

    public void setParameters(String tabName, FXMLLoader tabController, String[] fields, String[] fieldsTypes, FXMLLoader[] fieldsControllers) {
        this.tabName = tabName;
        this.tabController = tabController;
    	this.fields = fields.clone();
        this.fieldsTypes = fieldsTypes.clone();
        this.fieldsControllers = fieldsControllers.clone();

        setEditable(false);

        //Here will be switch/case according to the tabName (on some AddButton/DeleteButton have to be hidden)
    }

    public void setWidthHeight(Double width, Double height) {
        buttonsBox.setPrefWidth(width);
        buttonsBox.setPrefHeight(height);

        this.addButton.setPrefWidth(width*0.3);
        this.addButton.setPrefHeight(height*0.35);
		this.editButton.setPrefWidth(width*0.33);
		this.editButton.setPrefHeight(height*0.35);
		this.deleteButton.setPrefWidth(width*0.3);
		this.deleteButton.setPrefHeight(height*0.35);
    }

	public void setWidthHideButtons(Double width, Double height, Integer visibleButtons) {
		buttonsBox.setPrefWidth(width);
		buttonsBox.setPrefHeight(height);

		this.addButton.setPrefHeight(height*0.35);
		this.editButton.setPrefHeight(height*0.35);
		this.deleteButton.setPrefHeight(height*0.35);

		if (visibleButtons == 3) {
			this.addButton.setPrefWidth(width*0.3);
			this.editButton.setPrefWidth(width*0.33);
			this.deleteButton.setPrefWidth(width*0.3);
		} else if (visibleButtons == 2) {
			this.addButton.setPrefWidth(width*0.45);
			this.editButton.setPrefWidth(width*0.45);
			this.deleteButton.setPrefWidth(width*0.45);
		} else if (visibleButtons == 1) {
			this.addButton.setPrefWidth(width*0.85);
			this.editButton.setPrefWidth(width*0.85);
			this.deleteButton.setPrefWidth(width*0.85);
		}
	}

    public void hideButton(int numberOfButton) {
    	this.addButton.setVisible(numberOfButton == 0 ? false : true);
    	this.editButton.setVisible(numberOfButton == 1 ? false : true);
    	this.deleteButton.setVisible(numberOfButton == 2 ? false : true);

		//this.buttonsBox.getChildren().remove(numberOfButton);
	}

	public void hideButton2(int numberOfButton) {
		this.buttonsBox.getChildren().remove(numberOfButton);
	}

    public void setEditable(Boolean value) {
    	this.addButton.setDisable(!value);
    	this.deleteButton.setDisable(!value);

		for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
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

    public int checkData() {
    	// Проверка в зависимости от вкладки
    	switch (tabName) {
	    	case "SampleTab":
	    		InsertFormController insertFormController = tabController.getController();
	    		return insertFormController.checkData();
	    	case "Паспорт и ИНН":
	            PassportTabController passportTabController = tabController.getController();
	    		return passportTabController.checkData();
	    	case "Образование":
	            EducationTabController educationTabController = tabController.getController();
	    		return educationTabController.checkData();
	    	case "Вступительные испытания":
	    		EntranceExamTabController entranceExamTabController = tabController.getController();
	    		return entranceExamTabController.checkData();
	    	default:
	    		return 0;
    	}
    }
}
