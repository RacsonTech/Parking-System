package com.smartparking.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    private double x, y;


    public static void main(String[] args) {

        // Launch GUI
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        // Create a connection to the local MySQL DB
//        MySqlConnection mySqlConnection = new MySqlConnection(true);

//        ParkingGarage parkingGarage = new ParkingGarage();
//        ParkingGarage parkingGarage = new ParkingGarage(mySqlConnection);


        // Load parking garage data from the database
//        loadData(parkingGarage, mySqlConnection.getConnection());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        Parent root = loader.load();
//        MainController mainController = loader.getController();
//        mainController.initData(parkingGarage);


        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);     // Set stage borderless



        // Allows to drag the window with the mouse when the stage is borderless
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        primaryStage.show();
    }

//    static void loadData(ParkingGarage parkingGarage, Connection connection) throws SQLException{
//
//        System.out.println("Initializing the Parking Garage Program ");
//        Statement statement = connection.createStatement();
//
////        Statement statement = connection.createStatement(       // Allows to move up and down the rows and update them
////                ResultSet.TYPE_SCROLL_SENSITIVE,
////                ResultSet.CONCUR_UPDATABLE);
//
//        getGarageInfo(statement, parkingGarage);
//        getCameraList(statement, parkingGarage);
//        getDisplayList(statement, parkingGarage);
//        getSectionList(statement, parkingGarage);
//        getLevelList(statement, parkingGarage);
//        statement.close();
//    }

//    static void getGarageInfo(Statement statement, ParkingGarage parkingGarage) throws SQLException {
//
//        // Prepare the query
//        String query = ("select id, name, total_spaces, available_spaces, levels from garage");
//
//        // Execute the query statement
//        System.out.println("Obtaining garage information from the database");
//        ResultSet resultSet = statement.executeQuery(query);
//
//        // Store the result
//        System.out.println("\n--- Garage Information----");
//        resultSet.next();
//        parkingGarage.setId(resultSet.getInt("id"));
//        parkingGarage.setGarageName(resultSet.getString("name"));
//        parkingGarage.setTotalSpaces(resultSet.getInt("total_spaces"));
//        parkingGarage.setAvailableSpaces(resultSet.getInt("available_spaces"));
//        parkingGarage.setNumLevels(resultSet.getInt("levels"));
//
//        parkingGarage.printGarageInfo();
//
//        resultSet.close();
//    }
//
//    static void getCameraList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
//        int cameraId;
//        int sectionId;
//        String ipAddress;
//        ArrayList<Camera> cameraArrayList = new ArrayList<>();
//
//        // Prepare a new query
//        String query = ("select id, section, ipaddress from cameras");
//
//        // Execute the query
//        ResultSet resultSet = statement.executeQuery(query);
//
//        // Store the result
//        while (resultSet.next()) {
//            cameraId = resultSet.getInt("id");
//            sectionId = resultSet.getInt("section");
//            ipAddress = resultSet.getString("ipaddress");
//
//            Camera camera = new Camera(cameraId, sectionId, ipAddress);
//
//            cameraArrayList.add(camera);
//        }
//
//        parkingGarage.setCameraArrayList(cameraArrayList);
//
//        parkingGarage.printCameraList();
//
//        resultSet.close();
//    }
//
//    static void getDisplayList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
//        int displayId;
//        int sectionId;
//        int displayNumber;
//        String ipAddress;
//        ArrayList<Display> displayArrayList = new ArrayList<>();
//
//        // Prepare a new query
//        String query = ("SELECT id, section, number, ipAddress from displays");
//
//        // Execute the query
//        ResultSet resultSet = statement.executeQuery(query);
//
//        // Store the result
//        while (resultSet.next()) {
//            displayId = resultSet.getInt("id");
//            sectionId = resultSet.getInt("section");
//            displayNumber = resultSet.getInt("number");
//            ipAddress = resultSet.getString("ipaddress");
//
//            Display display = new Display(displayId, sectionId, displayNumber, ipAddress);
//
//            displayArrayList.add(display);
//        }
//
//        parkingGarage.setDisplayArrayList(displayArrayList);
//
//        parkingGarage.printDisplayList();
//
//        resultSet.close();
//    }
//
//    static void getSectionList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
//        int id;
//        int levelId;
//        int totalSpaces;
//        int availableSpaces;
//        ArrayList<Section> sectionArrayList = new ArrayList<>();
//
//        // Prepare a new query
//        String query = ("SELECT id, level_id, total_spaces, available_spaces from sections");
//
//        // Execute the query
//        ResultSet resultSet = statement.executeQuery(query);
//
//        // Store the result
//        while (resultSet.next()) {
//            id = resultSet.getInt("id");
//            levelId = resultSet.getInt("level_id");
//            totalSpaces = resultSet.getInt("total_spaces");
//            availableSpaces = resultSet.getInt("available_spaces");
//
//            Section section = new Section(id, levelId, totalSpaces, availableSpaces);
//
//            sectionArrayList.add(section);
//        }
//
//        parkingGarage.setSectionArrayList(sectionArrayList);
//
//        parkingGarage.printSectionList();
//
//        resultSet.close();
//    }
//
//    static void getLevelList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
//        int id;
//        int totalSpaces;
//        int availableSpaces;
//        ArrayList<Level> levelArrayList = new ArrayList<>();
//
//        // Prepare a new query
//        String query = ("SELECT id, total_spaces, available_spaces from levels");
//
//        // Execute the query
//        ResultSet resultSet = statement.executeQuery(query);
//
//        // Store the result
//        while (resultSet.next()) {
//            id = resultSet.getInt("id");
//            totalSpaces = resultSet.getInt("total_spaces");
//            availableSpaces = resultSet.getInt("available_spaces");
//
//            Level level = new Level(id, totalSpaces, availableSpaces);
//
//            levelArrayList.add(level);
//        }
//
//        parkingGarage.setLevelArrayList(levelArrayList);
//
//        parkingGarage.printLevelList();
//
//        resultSet.close();
//    }

}
