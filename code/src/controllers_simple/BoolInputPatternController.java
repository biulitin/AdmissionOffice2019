package controllers_simple;
import backend.ModelDBConnection;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;


public class BoolInputPatternController {
	String fieldOriginalName;
	
    @FXML
    public FlowPane flowPane;

    @FXML
    private CheckBox fieldData;

    public Boolean getFieldData() {
        if (checkData() != 0)
            return false;
        else {
            System.out.println(fieldData.isSelected());
            return fieldData.isSelected();
        }
    }
    
    public void setFieldData(String data) {
    	fieldData.setSelected(data.equals("0") ? false : true);
    }

    public void setParameters(String originalNameOfField, String nameOfField) {
    	this.fieldOriginalName = originalNameOfField;
        this.fieldData.setText(nameOfField);
    }

    public void setWidthHeight(Double width, Double height) {
        this.flowPane.setPrefWidth(width);
        this.flowPane.setPrefHeight(height);

        this.fieldData.setPrefWidth(width*0.926);
        this.fieldData.setPrefHeight(height*0.714);
    }

    public void setEditable(Boolean editChoice) {
        this.fieldData.setDisable(!editChoice);
    }

    public int checkData(){
        return 0;
    }
}