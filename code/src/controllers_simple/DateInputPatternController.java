package controllers_simple;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private DatePicker fieldData;

    @FXML
    private Text nameOfField;
    
    public String getFieldData() {
        if (fieldData.equals(""))
            return "";
        else {
            System.out.println(fieldData.getValue());
            return fieldData.getValue().toString();
        }
    }
    
    public void setParameters(String nameOfField) {
    	this.nameOfField.setText(nameOfField);
    }

    public void setWidthHeight(Double width, Double height) {
        this.flowPane.setPrefWidth(width);
        this.flowPane.setPrefHeight(height);

        this.nameOfField.setWrappingWidth(width*0.275);
        this.fieldData.setPrefWidth(width*0.577);
        this.fieldData.setPrefHeight(height*0.714);
    }

    public void setEditable(Boolean editChoice) {
        this.fieldData.setDisable(!editChoice);
    }

    public int checkData(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(String.valueOf(fieldData.getValue()));
            return 0;
        }
        // if null or incorrect value we always get null
        catch(ParseException e){
            return 1;
        }
    }
}
