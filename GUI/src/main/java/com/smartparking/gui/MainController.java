package com.smartparking.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Pane paneOverview;
    public Pane paneLiveView;
    public Pane paneGarageSettings;
    public Pane paneLevels;
    public Pane paneSections;
    public Pane paneCameras;
    public Pane paneDisplays;
    public Pane paneDevelopment;
    public Pane paneAbout;
    public Label labelTestMySQLConnection;
    public Label labelDisplayMySQLAddress;
    public Label overviewTitle;
    public Label overviewCapacity;
    public Label overviewAvailable;
    public Label overviewCapLvl1;
    public Label overviewCapLvl2;
    public Label overviewCapLvl3;
    public Label overviewCapLvl4;
    public Label overviewAvailableLvl1;
    public Label overviewAvailableLvl2;
    public Label overviewAvailableLvl3;
    public Label overviewAvailableLvl4;
    public Label overviewFull;
    public Label overviewPercentFullLvl1;
    public Label overviewPercentFullLvl2;
    public Label overviewPercentFullLvl3;
    public Label overviewPercentFullLvl4;

    private ParkingGarage garage;
    private MySqlConnection mySqlConnection;

    @Override
    // Runs before GUI shows up.
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Establish a connection to the parking system MySQL DB.
        mySqlConnection = new MySqlConnection(true);

        try {
            // Create the parking garage object and load data from the DB.
            garage = new ParkingGarage(mySqlConnection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        overviewTitle.setText("Parking Garage " + garage.getGarageName());
        overviewCapacity.setText(String.valueOf(garage.getTotalSpaces()));
        overviewAvailable.setText(String.valueOf(garage.getAvailableSpaces()));

        double available = 1.0 * garage.getAvailableSpaces();
        double capacity = 1.0 * garage.getTotalSpaces();
        int fullPercent = (int) (Math.round(100.0 * ((capacity - available) / capacity)));
        
        overviewFull.setText(fullPercent + "% full");

        // For each level in the garage...
        for (Level level : garage.getLevelList()) {
            
            available = 1.0 * level.getAvailableSpaces();
            capacity = 1.0 * level.getTotalSpaces();
            fullPercent = (int) (Math.round(100.0 * ((capacity - available) / capacity)));


            switch (level.getId()) {
                case 1 -> {
                    overviewCapLvl1.setText(String.valueOf((int) capacity));
                    overviewAvailableLvl1.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl1.setText(String.valueOf(fullPercent + "%"));
                }
                case 2 -> {
                    overviewCapLvl2.setText(String.valueOf((int) capacity));
                    overviewAvailableLvl2.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl2.setText(String.valueOf(fullPercent + "%"));
                }
                case 3 -> {
                    overviewCapLvl3.setText(String.valueOf((int) capacity));
                    overviewAvailableLvl3.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl3.setText(String.valueOf(fullPercent + "%"));
                }
                case 4 -> {
                    overviewCapLvl4.setText(String.valueOf((int) capacity));
                    overviewAvailableLvl4.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl4.setText(String.valueOf(fullPercent) + "%");
                }
            }
        }





        paneOverview.toFront();
    }

//    void initData(ParkingGarage parkingGarage) {
//        garage = parkingGarage;
//
//    }

    //  ==============   Handles for Menu Buttons  =================
    @FXML
    public void handleOverviewButtonClick() {
        paneOverview.toFront();
    }

    @FXML
    public void handleLiveViewButtonClick() {
        paneLiveView.toFront();
    }

    @FXML
    public void handleGarageSettingsButtonClick() {
        paneGarageSettings.toFront();
    }

    @FXML
    public void handleLevelsButtonClick() {
        paneLevels.toFront();
    }

    @FXML
    public void handleSectionsButtonClick() {
        paneSections.toFront();
    }

    @FXML
    public void handleCamerasButtonClick() {
        paneCameras.toFront();
    }

    @FXML
    public void handleDisplaysButtonClick() {
        paneDisplays.toFront();
    }

    @FXML
    public void handleDevelopmentButtonClick() {
        paneDevelopment.toFront();
    }

    @FXML
    public void handleAboutButtonClick() {
        paneAbout.toFront();
    }

    @FXML
    public void handleExitButtonClick() {
        Platform.exit();
    }

    //  ==============   Handles for Development Pane  =================

    @FXML
    public void handleTestMySQLConnection() {

        // Clear GUI Label
        labelTestMySQLConnection.setText("");
        labelTestMySQLConnection.getStyleClass().remove("error-text");
        labelTestMySQLConnection.getStyleClass().remove("warning-text");

        // Read credentials from the db properties dile
        MySqlConnection mySqlConnection = new MySqlConnection();
        String result = mySqlConnection.readMySqlCredentials();

        if(result != null) {
            labelTestMySQLConnection.getStyleClass().add("error-text");
            labelTestMySQLConnection.setText(result);
            return;
        }

        result = mySqlConnection.testConnect();

        if(result != null) {
            labelTestMySQLConnection.getStyleClass().add("error-text");
            labelTestMySQLConnection.setText(result);
        }

        result = mySqlConnection.disconnect();

        if(result != null) {
            labelTestMySQLConnection.getStyleClass().add("warning-text");
            labelTestMySQLConnection.setText("Connection OK. Could not close connection.");
        } else {
            labelTestMySQLConnection.setText("Connection Successful");
        }
    }

    @FXML
    public void handleDisplayMySQLServerAddress () {
        // Clear GUI Label
        labelDisplayMySQLAddress.setText("");
        labelDisplayMySQLAddress.getStyleClass().remove("error-text");
        labelDisplayMySQLAddress.getStyleClass().remove("warning-text");

        // Read credentials from the db properties dile
        MySqlConnection mySqlConnection = new MySqlConnection();
        String result = mySqlConnection.readMySqlCredentials();

        if(result != null) {
            labelDisplayMySQLAddress.getStyleClass().add("error-text");
            labelDisplayMySQLAddress.setText(result);
        } else {
            labelDisplayMySQLAddress.setText(mySqlConnection.getServerAddress());
        }
    }

}
