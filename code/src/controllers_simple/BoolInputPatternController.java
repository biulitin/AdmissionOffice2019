package controllers_simple;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;


public class BoolInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private CheckBox fieldData;

    public Boolean getFieldData() {
        if (!fieldData.isSelected())
            return false;
        else {
            System.out.println(fieldData.isSelected());
            return fieldData.isSelected();
        }
    }

    public void setParameters(String nameOfField) {
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