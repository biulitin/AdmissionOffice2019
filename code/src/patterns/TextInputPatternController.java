package code.src.patterns;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TextInputPatternController {
    @FXML
    private JFXTextField fieldData;

    @FXML
    private Text nameOfFiled;
    
    public String getFieldData() {
    	System.out.println(fieldData.getText());
    	return fieldData.getText();
    }
    
    public void setParameters(String nameOfFiled) {
    	this.nameOfFiled.setText(nameOfFiled);
    }
}
