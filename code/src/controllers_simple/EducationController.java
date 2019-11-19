package controllers_simple;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.*;

public class EducationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> levelOfEd;

    @FXML
    private ChoiceBox<?> typeOfEd;

    @FXML
    private TextField series;

    @FXML
    private TextField number;

    @FXML
    private DatePicker dateOfIssue;

    @FXML
    private TextField byWhomIssued;

    @FXML
    private Button editBtn;

    @FXML
    private Button saveBtn;

    String numberMatcher = "^-?\\d+$";

    @FXML
    void edit(ActionEvent event) {
        if(!checkFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Пожалуйста, заполните все поля");
            alert.showAndWait();
        } else {
            //levelOfEd.setEditable(true);
            //typeOfEd.setEditable(true);
            /*series.setEditable(true);
            number.setEditable(true);
            dateOfIssue.setEditable(true);
            byWhomIssued.setEditable(true);*/
            levelOfEd.setDisable(false);
            typeOfEd.setDisable(false);
            series.setDisable(false);
            number.setDisable(false);
            dateOfIssue.setDisable(false);
            byWhomIssued.setDisable(false);
            editBtn.setDisable(true);
            saveBtn.setDisable(false);
        }

    }

    @FXML
    void save(ActionEvent event) {
        if(!checkFields()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Предупреждение");
            alert.setHeaderText("Пожалуйста, заполните все поля");
            alert.showAndWait();
        } else {
            //levelOfEd.setEditable(false);
            //typeOfEd.setEditable(false);
            /*series.setEditable(false);
            number.setEditable(false);
            dateOfIssue.setEditable(false);
            byWhomIssued.setEditable(false);*/
            levelOfEd.setDisable(true);
            typeOfEd.setDisable(true);
            series.setDisable(true);
            number.setDisable(true);
            dateOfIssue.setDisable(true);
            byWhomIssued.setDisable(true);
            editBtn.setDisable(false);
            saveBtn.setDisable(true);
        }
    }

    boolean checkFields() {
        if (levelOfEd.getSelectionModel().isEmpty() || typeOfEd.getSelectionModel().isEmpty() || series.getText().trim().isEmpty() ||
                number.getText().trim().isEmpty() || dateOfIssue.equals("") || byWhomIssued.getText().trim().isEmpty()  ||
                !series.getText().trim().matches(numberMatcher) || !number.getText().trim().matches(numberMatcher))
            return false;
        else
            return true;
    }

    @FXML
    void initialize() {
        //editBtn.setDisable(true);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        dateOfIssue.setConverter(converter);
        dateOfIssue.setPromptText("ДД/ММ/ГГГГ");
    }
}