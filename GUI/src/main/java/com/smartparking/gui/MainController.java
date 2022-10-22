package com.smartparking.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public ChoiceBox<String> liveViewLevelChoiceBox;
    public ChoiceBox<String> liveViewSectionChoiceBox;
    public ChoiceBox<String> liveViewCameraChoiceBox;
    public Button liveViewConnectButton;
    public TableView<CameraLog> liveViewTable;
    public TableColumn<CameraLog, String> liveViewTableColumnTime;
    public TableColumn<CameraLog, Integer> liveViewTableColumnCamera;
    public TableColumn<CameraLog, Integer> liveViewTableColumnSpaces;

    private ParkingGarage garage;
    private MySqlConnection mySqlConnection;

    @Override
    // Runs before GUI shows up.
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Establish a connection to the parking system MySQL DB.
        mySqlConnection = new MySqlConnection(true);

        try {
            // Create the parking garage object and load it with data from the DB.
            garage = new ParkingGarage(mySqlConnection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        updateOverviewPane();   // Default section to show at program launch
        paneOverview.toFront();

        loadLiveViewData();
    }


    //  ==============   Overview Pane Methods  =================

    private void updateOverviewPane() {
        System.out.println("\n--- Update Garage Overview GUI----");
        overviewTitle.setText("Parking Garage " + garage.getGarageName());
        overviewCapacity.setText(String.valueOf(garage.getTotalSpaces()));
        overviewAvailable.setText(String.valueOf(garage.getAvailableSpaces()));
        overviewFull.setText(garage.getPercentFull() + "% full");
        System.out.println("Garage Name: " + garage.getGarageName());
        System.out.println("Capacity: " + garage.getTotalSpaces());
        System.out.println("Available Spaces: " + garage.getAvailableSpaces());
        System.out.println("Percent Full: " + garage.getPercentFull() + "% full");

        // For each level in the garage...
        for (Level level : garage.getLevelList()) {

            switch (level.getId()) {
                case 1 -> {
                    overviewCapLvl1.setText(String.valueOf(level.getTotalSpaces()));
                    overviewAvailableLvl1.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl1.setText(level.getPercentFull() + "%");
                }
                case 2 -> {
                    overviewCapLvl2.setText(String.valueOf(level.getTotalSpaces()));
                    overviewAvailableLvl2.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl2.setText(level.getPercentFull() + "%");
                }
                case 3 -> {
                    overviewCapLvl3.setText(String.valueOf(level.getTotalSpaces()));
                    overviewAvailableLvl3.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl3.setText(level.getPercentFull() + "%");
                }
                case 4 -> {
                    overviewCapLvl4.setText(String.valueOf(level.getTotalSpaces()));
                    overviewAvailableLvl4.setText(String.valueOf(level.getAvailableSpaces()));
                    overviewPercentFullLvl4.setText(level.getPercentFull() + "%");
                }
            }
            System.out.println("Level " + level.getId() + ": | Cap: " + level.getTotalSpaces() +
                    " | Free: " + level.getAvailableSpaces() + " | " + level.getPercentFull() + "% full");
        }
    }

    private void loadLiveViewData() {

        // Populate the Level Choice Box values
//        liveViewLevelChoiceBox.setItems(FXCollections.observableArrayList(garage.getLevelIdList()));
        reloadLiveViewChoiceBoxes();

        // Register the event handlers.
        liveViewLevelChoiceBox.setOnAction(this::handleLevelChoiceBoxAction);
        liveViewSectionChoiceBox.setOnAction(this::handleSectionChoiceBoxAction);
        liveViewCameraChoiceBox.setOnAction(this::handleCameraChoiceBoxAction);
    }

    public void handleOverviewRefreshButton() throws SQLException {
        garage.reloadOverviewData(mySqlConnection.getConnection());
        updateOverviewPane();
    }

    //  ==============   Handles for Menu Buttons  =================
    @FXML
    public void handleOverviewButtonClick() throws SQLException {
        garage.reloadOverviewData(mySqlConnection.getConnection());
        updateOverviewPane();
        paneOverview.toFront();
    }

    @FXML
    public void handleLiveViewButtonClick() {
        reloadLiveViewTable();
        reloadLiveViewChoiceBoxes();
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
        mySqlConnection.disconnect();
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

    //  ==============   Handles for Live View Pane  =================
    public void handleLevelChoiceBoxAction(ActionEvent event) {

        if (liveViewLevelChoiceBox.getValue() != null) {
            int levelId = Integer.parseInt(liveViewLevelChoiceBox.getValue());

            liveViewSectionChoiceBox.getItems().clear();
            liveViewCameraChoiceBox.getItems().clear();
            liveViewConnectButton.setDisable(true);

            // Populate the Section choice box
            liveViewSectionChoiceBox.getItems().setAll(garage.getSectionIdListByLevel(levelId));
        }
    }

    public void handleSectionChoiceBoxAction(ActionEvent event) {

        if (liveViewSectionChoiceBox.getValue() != null) {
            int sectionId = Integer.parseInt(liveViewSectionChoiceBox.getValue());
            liveViewCameraChoiceBox.getItems().clear();
            liveViewConnectButton.setDisable(true);

            // Populate the Camera choice box
            liveViewCameraChoiceBox.getItems().setAll(garage.getCameraIdListBySection(sectionId));
        }
    }
    
    public void handleCameraChoiceBoxAction(ActionEvent event) {
        if(liveViewCameraChoiceBox.getValue() != null) {
            liveViewConnectButton.setDisable(false);
        }
    }

    public void handleLiveViewConnectButton(ActionEvent actionEvent) {
        System.out.println("Connect button pressed");
//        TODO
    }

    private void reloadLiveViewTable() {
        // Populate the table
        liveViewTableColumnTime.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        liveViewTableColumnCamera.setCellValueFactory(new PropertyValueFactory<>("cameraId"));
        liveViewTableColumnSpaces.setCellValueFactory(new PropertyValueFactory<>("changedSpaces"));

        try {
            liveViewTable.setItems(FXCollections.observableArrayList(garage.getCameraLogList(mySqlConnection.getConnection())));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nLive View Table reloaded");
    }

    private void reloadLiveViewChoiceBoxes() {
        liveViewCameraChoiceBox.getItems().clear();
        liveViewSectionChoiceBox.getItems().clear();
        liveViewLevelChoiceBox.getItems().clear();
        liveViewLevelChoiceBox.setItems(FXCollections.observableArrayList(garage.getLevelIdList()));
    }
}
