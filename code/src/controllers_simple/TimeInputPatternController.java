package controllers_simple;

import javafx.fxml.FXML;
import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.text.Text;

public class TimeInputPatternController {
    @FXML
    private JFXTimePicker fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        System.out.println(fieldData.getValue());
        return fieldData.getValue().toString();
    }

    public void setParameters(String nameOfField) {
        this.nameOfField.setText(nameOfField);
    }
}
