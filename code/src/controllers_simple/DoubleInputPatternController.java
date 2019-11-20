package controllers_simple;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.util.regex.Pattern;

public class DoubleInputPatternController {
    @FXML
    public FlowPane flowPane;

    @FXML
    private TextField fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        if (fieldData.getText().trim().isEmpty())
            return "";
        else {
            System.out.println(fieldData.getText());
            return fieldData.getText();
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
        if(fieldData.getText().trim().isEmpty())
            return 1;
        if(Pattern.compile("(?!0)(\\d*\\.)?\\d+").matcher(fieldData.getText()).matches() || Pattern.compile("(0)(\\.\\d+)?").matcher(fieldData.getText()).matches())
            return 0;
        else return 2;
    }
}
