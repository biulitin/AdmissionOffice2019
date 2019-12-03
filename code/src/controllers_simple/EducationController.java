package controllers_simple;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class EducationController {

    @FXML
    public GridPane parentGridPane;

    @FXML
    public GridPane childGridPane;

    @FXML
    public Pane buttonsPane;

    String[] fields, fieldsTypes;
    FXMLLoader[] fieldsControllers;

    String url, query;
    Connection conn;
    CallableStatement cstmt;
    ResultSet rset;

    public void createForm() throws Exception {
        url = "jdbc:sqlserver://" + "localhost" + ":1433;databaseName=" + "Abiturient" + ";user="
                + "igor_sa" + ";password=" + "200352" + ";";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url);

        query = "SELECT AbiturientEducation.id_levelEducation, AbiturientEducation.id_typeEducation, " +
                "AbiturientEducation.name_eduInstitution, AbiturientEducation.dateOf_issue, " +
                "AbiturientEducation.series_document, AbiturientEducation.number_document, " +
                "AbiturientEducation.yearOf_graduation FROM AbiturientEducation join Abiturient " +
                "ON (Abiturient.aid = AbiturientEducation.id_abiturient);";

        cstmt = conn.prepareCall(query, 1004, 1007);
        rset = cstmt.executeQuery();

        int countStrings = rset.last() ? rset.getRow() : 0;
        rset.beforeFirst();

        ResultSetMetaData rsmd = rset.getMetaData();
        int countFields = rsmd.getColumnCount();

        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel(i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName(i + 1);
        }

        FXMLLoader loader;
        Pane newPane;

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    parentGridPane.add(newPane, 0, 3);

                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setParameters(fields[i]);
                    break;
                case "int":
                    if (Pattern.compile("(id_l).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,0);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if (Pattern.compile("(id_t).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,1);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        choiceInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    else {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/IntInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 2, 0);

                        IntInputPatternController intInputPatternController = loader.getController();
                        intInputPatternController.setParameters(fields[i]);
                        break;
                    }
                case "varchar":
                    if (Pattern.compile("(series).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 0, 0);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if (Pattern.compile("(number).*").matcher(fields[i]).matches()) {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane, 1, 0);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    else {
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane, 0, 2);

                        TextInputPatternController textInputPatternController = loader.getController();
                        textInputPatternController.setParameters(fields[i]);
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
        addEditDeleteButtonsController.setParameters("Образование", fields, fieldsTypes, fieldsControllers);
    }
}
