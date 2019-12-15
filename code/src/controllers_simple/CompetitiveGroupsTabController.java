package controllers_simple;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Pattern;

public class CompetitiveGroupsTabController {
    @FXML
    public FlowPane buttonsPane;

    String[] fields, fieldsTypes;
    FXMLLoader[] fieldsControllers;
    int countFields;

    String url, query;
    Connection conn;
    CallableStatement cstmt;
    ResultSet rset;
    Properties props;
    Statement st;
    ResultSet rs;

    FXMLLoader loader;
    Pane newPane;

    public void fillTab() throws IOException, SQLException, ClassNotFoundException {
        prepareData();
        addButtons(buttonsPane, 2);
    }

    public void addButtons(Pane pane, int numberOfVisibleButtons) throws IOException {
        FXMLLoader buttonsLoader = new FXMLLoader();
        buttonsLoader.setLocation(getClass().getResource("../patterns_simple/AddEditDeleteButtons.fxml"));

        pane.getChildren().removeAll();
        Pane newButtonsPane = (Pane) buttonsLoader.load();
        pane.getChildren().add(newButtonsPane);
        pane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        AddEditDeleteButtonsController addEditDeleteButtonsController = buttonsLoader.getController();
        if (numberOfVisibleButtons == 2) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.setWidthHideButtons(250.0, 50.0, 2);
        }
        else if (numberOfVisibleButtons == 1) {
            addEditDeleteButtonsController.hideButton(0);
            addEditDeleteButtonsController.hideButton(1);
            addEditDeleteButtonsController.setWidthHideButtons(200.0, 50.0, 1);
        }
    }

    public void prepareData() throws ClassNotFoundException, SQLException {
        url = "jdbc:sqlserver://" + "localhost" + ":1433;databaseName=" + "Abiturient" + ";user="
                + "igor_sa" + ";password=" + "200354" + ";";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(url);

        query = "SELECT AbiturientCompetitiveGroup.id_speciality, AbiturientCompetitiveGroup.id_competitiveGroup," +
                "AbiturientCompetitiveGroup.id_targetOrganization, AbiturientCompetitiveGroup.id_formOfEducation," +
                "AbiturientCompetitiveGroup.originalsReceivedDate, AbiturientCompetitiveGroup.is_enrolled\n" +
                "FROM AbiturientCompetitiveGroup";
        cstmt = conn.prepareCall(query, 1004, 1007);
        rset = cstmt.executeQuery();

        rset.beforeFirst();

        ResultSetMetaData rsmd = rset.getMetaData();
        countFields = rsmd.getColumnCount();
        fields = new String[countFields];
        fieldsTypes = new String[countFields];
        fieldsControllers = new FXMLLoader[countFields];

        for (int i = 0; i < countFields; i++) {
            fields[i] = rsmd.getColumnLabel (i + 1);
            fieldsTypes[i] = rsmd.getColumnTypeName (i + 1);
        }
    }

    public void openModalWindow() throws IOException {
        Stage modalStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < countFields; i++) {
            switch (fieldsTypes[i]) {
                case "date":
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../patterns_simple/DateInputPattern.fxml"));

                    newPane = (Pane) loader.load();
                    fieldsControllers[i] = loader;

                    flowPane.getChildren().add(newPane);

                    DateInputPatternController dateInputPatternController = loader.getController();
                    dateInputPatternController.setWidthHeight(350.0, 35.0, 140.0);
                    dateInputPatternController.setParameters(fields[i]);
                case "int":
                    loader = new FXMLLoader();
                    if (Pattern.compile("(id_).*").matcher(fields[i]).matches()) {
                        loader.setLocation(getClass().getResource("../patterns_simple/ChoiceInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        ChoiceInputPatternController choiceInputPatternController = loader.getController();
                        if (fields[i].equals("id_formOfEducation")) {
                            choiceInputPatternController.setWidthHeight(450.0, 35.0, 250.0);
                        } else {
                            choiceInputPatternController.setWidthHeight(450.0, 35.0, 140.0);
                        }
                        choiceInputPatternController.setParameters(fields[i]);
                    } else if (fields[i].equals("is_enrolled")) {
                        loader.setLocation(getClass().getResource("../patterns_simple/BoolInputPattern.fxml"));

                        newPane = (Pane) loader.load();
                        fieldsControllers[i] = loader;

                        flowPane.getChildren().add(newPane);

                        BoolInputPatternController boolInputPatternController = loader.getController();
                        boolInputPatternController.setWidthHeight(100.0, 35.0);
                        boolInputPatternController.setParameters(fields[i]);
                    }
            }
        }

        FlowPane modalButtonsPane = new FlowPane();
        addButtons(modalButtonsPane, 1);
        modalButtonsPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        modalButtonsPane.setPrefWidth(450.0);
        flowPane.getChildren().add(modalButtonsPane);

        Scene scene = new Scene(flowPane);
        modalStage.setScene(scene);
        modalStage.setWidth(500.0);
        modalStage.setHeight(350.0);
        modalStage.show();
    }
}
