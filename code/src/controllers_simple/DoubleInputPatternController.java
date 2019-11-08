package controllers_simple;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class DoubleInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private TextField fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        System.out.println(fieldData.getText());
        return fieldData.getText();
    }

    public void setParameters(String nameOfField) {
        this.nameOfField.setText(nameOfField);
    }

    public void setWidthHeight(Double width, Double height) {
        this.flowPane.setPrefWidth(width);
        this.flowPane.setPrefHeight(height);

        this.fieldData.setPrefWidth(width*0.577);
        this.fieldData.setPrefHeight(height*0.714);
    }
}
