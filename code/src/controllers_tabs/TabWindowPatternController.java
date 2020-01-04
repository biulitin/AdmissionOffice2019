package controllers_tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class TabWindowPatternController {
	String fieldOriginalName;

    @FXML
    private Tab nameOfField;

    @FXML
    private FlowPane paneForElems;

    @FXML
    private Pane contentArea;

    @FXML
    private Pane buttonsArea;

    public void setParameters(String nameOfField) {
        this.nameOfField.setText(nameOfField);
    }

}
