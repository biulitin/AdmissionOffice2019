package controllers_simple;

import java.util.regex.Pattern;

import backend.ModelDBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ChoiceInputPatternController {
	String fieldOriginalName;
	
    @FXML
    public FlowPane flowPane;

    @FXML
    private ChoiceBox<String> fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        if (checkData() > 1)
            return "";
        else {
            //System.out.println(fieldData.getSelectionModel().getSelectedItem() + fieldData.getSelectionModel().getSelectedIndex());
            return String.valueOf(fieldData.getSelectionModel().getSelectedIndex());
            //return fieldData.getSelectionModel().getSelectedItem().toString();
        }
    }

    public void setFieldData(String data) {
    	String tableName = "";

    	switch(fieldOriginalName) {
			case "id_gender": tableName = "Gender"; break;
			case "id_nationality": tableName = "Nationality"; break;
			case "id_returnReason": tableName = "ReturnReasons"; break;
			case "id_typePassport": tableName = "TypePassport"; break;
			case "id_region": tableName = "Region"; break;
			case "id_typeSettlement": tableName = "TypeSettlement"; break;
			case "id_entranceExam": tableName = "EntranceExam"; break;
			case "id_formOfExam": tableName = "FormOfExam"; break;
			case "id_languageOfExam": tableName = "LanguageOfExam"; break;
			case "id_basisMark": tableName = "BasisMark"; break;
			case "id_olympiad": tableName = "Olympiad"; break;
			case "id_levelEducation": tableName = "LevelEducation"; break;
			case "id_typeEducation": tableName = "TypeEducation"; break;
			case "id_individualAchievements": tableName = "IndividualAchievement"; break;
			case "id_speciality": tableName = "Speciality"; break;
			case "id_formOfEducation": tableName = "FormOfEducation"; break;
			case "id_competitiveGroup": tableName = "CompetitiveGroup"; break;
			case "id_targetOrganization": tableName = "TargetOrganization"; break;
			case "id_typeOfBVI": tableName = "TypeOfBVI"; break;
			case "id_categoryOfExtraInfo": tableName = "CategoryOfExtraInfo"; break;
			case "id_quotaType": tableName = "TypeOfQuote"; break;
			case "id_preemptitiveRight": tableName = "TypeOfPreferredRight"; break;
    	}

    	this.fieldData.getItems().clear();
    	this.fieldData.getItems().addAll(ModelDBConnection.getNamesFromTableOrderedById(tableName));
    	if(data.equals(""))
    		this.fieldData.setValue("");
    	else
    		this.fieldData.getSelectionModel().select(Integer.valueOf(data));
    }

    public void setParameters(String originalNameOfField, String nameOfField) {
    	this.fieldOriginalName = originalNameOfField;
        this.nameOfField.setText(nameOfField);
    }

    public void setWidthHeight(Double paneWidth, Double paneHeight, Double labelWidth) {
        this.flowPane.setPrefWidth(paneWidth);
        this.flowPane.setPrefHeight(paneHeight);

        this.nameOfField.setWrappingWidth(labelWidth);
        this.fieldData.setPrefWidth(paneWidth - labelWidth - 40.0);
        this.fieldData.setPrefHeight(paneHeight*0.714);
    }

    public void setEditable(Boolean editChoice) {
        this.fieldData.setDisable(!editChoice);
    }

    public int checkData(){
        if(fieldData.getSelectionModel().isEmpty() || fieldData.getSelectionModel().getSelectedIndex() == 0)
            return 1;
        else return 0;
    }
    
    
}
