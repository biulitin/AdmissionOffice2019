package controllers_simple;


import javafx.geometry.NodeOrientation;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;


public class PassportTabController {
    public GridPane parentGridPane;
    public GridPane childGridPane;
    public Pane buttonsPane;
    String[] fields, fieldsTypes;
    FXMLLoader[] fieldsControllers;

    String url, query;
    Connection conn;
    CallableStatement cstmt;
    ResultSet rset;

    public void createForm() throws Exception {
        parentGridPane.autosize();
        childGridPane.autosize();
        url = "jdbc:sqlserver://" + "localhost" + ":1433;databaseName=" + "Abiturient" + ";user="
                + "igor_sa" + ";password=" + "200352" + ";";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url);

        query = "SELECT AbiturientPassport.id_typePassport, AbiturientPassport.series,AbiturientPassport.number,AbiturientPassport.dateOf_issue,AbiturientPassport.issued_by,Abiturient.Birthplace,Abiturient.inn \n" +
                "FROM AbiturientPassport JOIN Abiturient ON\n" +
                "(Abiturient.aid=AbiturientPassport.id_abiturient);";
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
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
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

                    childGridPane.add(newPane,2,0);
                    DateInputPatternController dateInputPatternController = loader.getController();
                    // dateInputPatternController.setWidthHeight(290.0,10.0);
                    dateInputPatternController.setParameters(fields[i]);
                    break;
                case "int":
                    if(Pattern.compile("(id_t).*").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,0);
                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        // choiceInputPatternController.setWidthHeight(270.0,10.0);
                        choiceInputPatternController.setParameters(fields[i]);
                        break;
                    }
                case "varchar":
                    if(Pattern.compile("(series)").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add( newPane,0,0);
                        TextInputPatternController textInputPatternController = loader.getController();
                        // textInputPatternController.setWidthHeight(190.0,10.0);
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if(Pattern.compile("(number)").matcher(fields[i]).matches() ){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        childGridPane.add(newPane,1,0);
                        TextInputPatternController textInputPatternController = loader.getController();
                        // textInputPatternController.setWidthHeight(190.0,10.0);
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if(Pattern.compile("(issued).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,2);
                        TextInputPatternController textInputPatternController = loader.getController();
                        // textInputPatternController.setWidthHeight(600.0,10.0);
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if(Pattern.compile("(Birthp).*").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,3);
                        TextInputPatternController textInputPatternController = loader.getController();
                        // textInputPatternController.setWidthHeight(600.0,10.0);
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    if(Pattern.compile("(inn)").matcher(fields[i]).matches()){
                        loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("../patterns_simple/TextInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        parentGridPane.add(newPane,0,4);
                        TextInputPatternController textInputPatternController = loader.getController();
                        // textInputPatternController.setWidthHeight(600.0,10.0);
                        textInputPatternController.setParameters(fields[i]);
                        break;
                    }
                    break;
            }
        }
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        buttonsPane.getChildren().removeAll();
        newPane = (Pane) loader.load();
        buttonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        buttonsPane.getChildren().add(newPane);


        AddEditDeleteButtonsController addEditDeleteButtonsController = loader.getController();
        addEditDeleteButtonsController.setParameters("Паспорт и ИНН", fields, fieldsTypes, fieldsControllers);
    }
}
