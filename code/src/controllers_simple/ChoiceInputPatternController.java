package controllers_simple;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class ChoiceInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private ChoiceBox<?> fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        if (checkData() != 0)
            return "";
        else {
            System.out.println(fieldData.getSelectionModel().getSelectedItem());
            return fieldData.getSelectionModel().getSelectedItem().toString();
        }
    }

    public void setParameters(String nameOfField) {
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
        if(fieldData.getSelectionModel().isEmpty())
            return 1;
        else return 0;
    }
}
