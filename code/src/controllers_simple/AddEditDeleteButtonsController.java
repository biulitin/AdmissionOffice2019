package controllers_simple;

import controllers_simple.*;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class AddEditDeleteButtonsController {
	private String[] fields, fieldsTypes;
	private FXMLLoader[] fieldsControllers;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    void addButtonAction(ActionEvent event) {

    }

    @FXML
    void editButtonAction(ActionEvent event) {
    	String saveQuery = "";
    	
    	switch(editButton.getText()) {
    		case "Редактировать":
    			editButton.setText("Сохранить");
    			//loop: each element have to be activated to editable mode
    			break;
    		case "Сохранить":
    			editButton.setText("Редактировать");
    			for (int i = 0; i < fieldsControllers.length; i++) {
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
    						if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()){
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
        
        //Here will be switch/case according to the tabName (on some AddButton/DeleteButton have to be hidden)
    }

    public void setWidthHeight(Double width, Double height) {
        /*this.flowPane.setPrefWidth(width);
        this.flowPane.setPrefHeight(height);

        this.fieldData.setPrefWidth(width*0.926);
        this.fieldData.setPrefHeight(height*0.714);*/
    }
}
