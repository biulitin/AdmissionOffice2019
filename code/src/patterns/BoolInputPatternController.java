package code.src.patterns;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;


public class BoolInputPatternController {
    @FXML
    private CheckBox checkbox;

    public Boolean getFieldData() {
        System.out.println(checkbox.isSelected());
        return checkbox.isSelected();
    }

    public void setParameters(String nameOfField) {
        this.checkbox.setText(nameOfField);
    }

    public void setWidthHeight(Double width, Double height) {
        this.checkbox.setPrefWidth(width);
        this.checkbox.setPrefHeight(height);
    }
}