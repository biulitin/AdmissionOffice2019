package controllers_tabs;

import java.sql.*;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import controllers_simple.*;
import backend.*;


public class AddressTabController {
    @FXML
    public GridPane parentGridPane;

    @FXML
    public GridPane childGridPane;

    @FXML
    public Pane buttonsPane;

    int countFields = 0;
    String[] fields, fieldsTypes, fieldsOriginalNames;
    FXMLLoader[] fieldsControllers;

    String aid;


    public void fillTab(FXMLLoader tabController, String aid) throws Exception {
        //parentGridPane.autosize();
        //childGridPane.autosize();

        ModelDBConnection.setDefaultConnectionParameters();
        //ModelDBConnection.setConnectionParameters("MSServer", "localhost", "Abiturient", "igor_sa", "200352");
        ModelDBConnection.initConnection();


        ResultSetMetaData rsmd = ModelDBConnection.getQueryMetaData(ModelDBConnection.getQueryByTabName("Адрес и контакты"));
        countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsOriginalNames = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
            fieldsOriginalNames[i] = rsmd.getColumnLabel(i + 1);
        }

        FXMLLoader loader;
        Pane newPane;

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "int":
                    if(Pattern.compile("(id_t).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,1);
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(985.0,35.0, 180.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientAdress"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    }
                    if(Pattern.compile("(id_r).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane,1,0);
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setWidthHeight(400.0,35.0, 100.0);
                        choiceInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientAdress"));
                        choiceInputPatternController.setFieldData("");
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(postcode).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add( newPane,0,0);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(190.0, 35.0, 60.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientAdress"));
                        break;
                    }
                    if(Pattern.compile("(adress).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,2);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(985.0, 35.0, 60.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "AbiturientAdress"));
                        break;
                    }
                    if(Pattern.compile("(email).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,4);
                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setWidthHeight(490.0, 35.0, 100.0);
                        textInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
                        break;
                    }
                    if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/PhoneNumberInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,3);
                        PhoneMaskInputPatternController phoneMaskInputPatternController = loader.getController();
                        phoneMaskInputPatternController.setWidthHeight(490.0, 35.0, 100.0);
                        phoneMaskInputPatternController.setParameters(fields[i], ModelDBConnection.getTranslationOfField(fields[i], "Abiturient"));
                        break;
                    }
            }
        }
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        newPane = (Pane) loader.load();
        buttonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        buttonsPane.getChildren().add(newPane);

        AddEditDeleteButtonsController addEditDeleteButtonsController = loader.getController();
        addEditDeleteButtonsController.setParameters("Адрес и контакты",tabController, fields, fieldsTypes, fieldsControllers);
        addEditDeleteButtonsController.hideButton(0, 2);
        addEditDeleteButtonsController.setWidthHideButtons(155.0, 35.0, 1);
        setFieldsData(aid);
        setEditable(false);
    }

    public void setEditable(Boolean value) {
        for (int i = 0; i < fieldsControllers.length; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    dateInputPatternController.setEditable(value);
                    break;
                case "double":
                    DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
                    doubleInputPatternController.setEditable(value);
                    break;
                case "int":
                    if(Pattern.compile("(id_).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        choiceInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches()){
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        boolInputPatternController.setEditable(value);
                        break;
                    } else {
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        intInputPatternController.setEditable(value);
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
                        PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                        phoneMaskInputPatternController.setEditable(value);
                        break;
                    }
                    if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
                        PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
                        passwordInputPatternController.setEditable(value);
                        break;
                    }
                    else {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        textInputPatternController.setEditable(value);
                        break;
                    }
            }
        }
    }


    public void setFieldsData(String aid) throws Exception {
        this.aid = aid;
        String[] addressData = ModelDBConnection.getAbiturientAddressByID(aid);

        if(addressData != null) {
            for (int i = 0; i < addressData.length; i++) {
                switch (fieldsTypes[i]) {
                    case "int":
                        if (Pattern.compile("(id_t).*").matcher(fields[i]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                        if (Pattern.compile("(id_r).*").matcher(fields[i]).matches()) {
                            ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                            choiceInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                    case "varchar":
                        if (Pattern.compile("(postcode).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                        if (Pattern.compile("(address).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                        if (Pattern.compile("(email).*").matcher(fields[i]).matches()) {
                            TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                            textInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                        if (Pattern.compile("(phone).*").matcher(fields[i]).matches()) {
                            PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                            phoneMaskInputPatternController.setFieldData(addressData[i]);
                            break;
                        }
                        //break;
                }
            }
        }
    }


    public void uploadFieldsDataToDataBase(String[] fieldsData) throws Exception {
        ModelDBConnection.updateAbiturientAddressByID(aid, fieldsOriginalNames, fieldsData);
    }


    public int checkData() {
        int errorCount = 0, currentErrorCode = 0;

        for (int i = 0; i < (fieldsControllers == null ? 0 : fieldsControllers.length); i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    DateInputPatternController dateInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = dateInputPatternController.checkData();
                    break;
                case "double":
                    DoubleInputPatternController doubleInputPatternController = fieldsControllers[i].getController();
                    currentErrorCode = doubleInputPatternController.checkData();
                    break;
                case "int":
                    if(Pattern.compile("(id_r).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                        if (currentErrorCode > 0) {
                            MessageProcessing.displayErrorMessage(1010);
                            return currentErrorCode;
                        }
                        break;
                    }
                    if(Pattern.compile("(id_t).*").matcher(fields[i]).matches() ){
                        ChoiceInputPatternController choiceInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = choiceInputPatternController.checkData();
                        if (currentErrorCode > 0) {
                            MessageProcessing.displayErrorMessage(1011);
                            return currentErrorCode;
                        }
                        break;
                    }
                    if(Pattern.compile("(need).*").matcher(fields[i]).matches() || Pattern.compile("(ha).*").matcher(fields[i]).matches() || Pattern.compile("(is).*").matcher(fields[i]).matches()){
                        BoolInputPatternController boolInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = boolInputPatternController.checkData();
                        break;
                    } else{
                        IntInputPatternController intInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = intInputPatternController.checkData();
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(phone).*").matcher(fields[i]).matches()){
                        PhoneMaskInputPatternController phoneMaskInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = phoneMaskInputPatternController.checkData();
                        break;
                    }
                    if(Pattern.compile("(passw).*").matcher(fields[i]).matches()){
                        PasswordPatternController passwordInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = passwordInputPatternController.checkData();
                        break;
                    }
                    else {
                        TextInputPatternController textInputPatternController = fieldsControllers[i].getController();
                        currentErrorCode = textInputPatternController.checkData();
                        break;
                    }
            }
            //errorCount += currentErrorCode;
            //System.out.println(currentErrorCode);
        }

        return errorCount;
    }
}
