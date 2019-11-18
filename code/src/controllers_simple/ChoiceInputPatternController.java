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
        System.out.println(fieldData.getSelectionModel().getSelectedItem());
        return fieldData.getSelectionModel().getSelectedItem().toString();
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
}
