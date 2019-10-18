package code.src.patterns;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;

public class PasswordPatternController {

    @FXML
    private PasswordField fieldData;

    @FXML
    private Text nameOfField;

    public String getFieldData() {
        System.out.println(fieldData.getText());
        return fieldData.getText();
    }

    public void setParameters(String nameOfField) {
        this.nameOfField.setText(nameOfField);
    }
}
