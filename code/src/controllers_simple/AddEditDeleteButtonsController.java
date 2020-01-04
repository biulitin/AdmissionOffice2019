package controllers_simple;

import controllers_simple.*;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class AddEditDeleteButtonsController {
	private String[] fields, fieldsTypes;
	private FXMLLoader[] fieldsControllers;

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
				activate = true;
				this.setEditable(activate);
				//loop: each element have to be activated to editable mode
				for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
					switch (fieldsTypes[i]) {
						case "date":
							DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
							dateInputPatternController.setEditable(activate);
							break;
						case "double":
							DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
							doubleInputPatternController.setEditable(activate);
							break;
						case "int":
							if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
								ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
								choiceInputPatternController.setEditable(activate);
								break;
							}
							if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
								BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
								boolInputPatternController.setEditable(activate);
								break;
							} else {
								IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
								intInputPatternController.setEditable(activate);
								break;
							}
						case "varchar":
							if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
								PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
								phoneMaskInputPatternController.setEditable(activate);
								break;
							}
							if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
								PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
								passwordInputPatternController.setEditable(activate);
								break;
							}
							else {
								TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
								textInputPatternController.setEditable(activate);
								break;
							}

					}
				}
				break;
			case "Сохранить":
				editButton.setText("Редактировать");
				activate = false;
    			for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
    				switch (fieldsTypes[i]) {
    					case "date":
    						DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
    						saveQuery += dateInputPatternController.getFieldData() + ";";
    						break;
    					case "double":
    						DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
    						saveQuery += doubleInputPatternController.getFieldData() + ";";
    						break;
    					case "int":
    						if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
    							ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
    							saveQuery += choiceInputPatternController.getFieldData() + ";";
    							break;
    						}
    						if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()  || Pattern.compile("(is).*").matcher(fields[i]).matches()){
    							BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
    							saveQuery += boolInputPatternController.getFieldData() + ";";
    							break;
    						}
    						/* If we don't need "is_enrolled" change select or:
    	                    if(Pattern.compile("(is_).*").matcher(fields[i]).matches())
    	                        break;*/
    						else{
    							IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
    							saveQuery += intInputPatternController.getFieldData() + ";";
    							break;
    						}
    					case "varchar":
    						if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
    							PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
    							saveQuery += phoneMaskInputPatternController.getFieldData() + ";";
    							break;
    						}
    						if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
    							PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
    							saveQuery += passwordInputPatternController.getFieldData() + ";";
    							break;
    						}
    						else {
    							TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
    							saveQuery += textInputPatternController.getFieldData() + ";";
    							break;
    						}
    				}
    			}
    			System.out.println(saveQuery);
    			//loop: each element have to be activated to non-editable mode
    			this.setEditable(activate);
				for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
					switch (fieldsTypes[i]) {
						case "date":
							DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
							if (dateInputPatternController.checkData() == 0) {
								dateInputPatternController.setEditable(activate);
								break;
							}
							else
								break;
						case "double":
							DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
							if (doubleInputPatternController.checkData() == 0) {
								doubleInputPatternController.setEditable(activate);
								break;
							}
							else
								break;
						case "int":
							if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
								ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
								if (choiceInputPatternController.checkData() == 0) {
									choiceInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							}
							if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
								BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
								if (boolInputPatternController.checkData() == 0) {
									boolInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							} else{
								IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
								if (intInputPatternController.checkData() == 0) {
									intInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							}
						case "varchar":
							if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
								PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
								if (phoneMaskInputPatternController.checkData() == 0) {
									phoneMaskInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							}
							if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
								PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
								if (passwordInputPatternController.checkData() == 0) {
									passwordInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							}
							else {
								TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
								if (textInputPatternController.checkData() == 0) {
									textInputPatternController.setEditable(activate);
									break;
								}
								else
									break;
							}

					}
				}
    			break;
    	}
    }

    @FXML
    void deleteButtonAction(ActionEvent event) {

    }

    public void setParameters(String tabName, String[] fields, String[] fieldsTypes, FXMLLoader[] fieldsControllers) {
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

    public void setEditable(Boolean value) {
    	this.addButton.setDisable(!value);
    	this.deleteButton.setDisable(!value);
    }
}
