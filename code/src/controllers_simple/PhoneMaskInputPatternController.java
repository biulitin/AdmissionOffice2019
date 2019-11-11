package controllers_simple;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import controllers_simple.MaskField;

public class PhoneMaskInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private MaskField fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        System.out.println(fieldData.getText());
        return fieldData.getText();
    }

    public void setParameters(String nameOfField) { this.nameOfField.setText(nameOfField); }

    public void setWidthHeight(Double width, Double height) {
        this.flowPane.setPrefWidth(width);
        this.flowPane.setPrefHeight(height);

        this.nameOfField.setWrappingWidth(width*0.275);
        this.fieldData.setPrefWidth(width*0.577);
        this.fieldData.setPrefHeight(height*0.714);
    }
}
