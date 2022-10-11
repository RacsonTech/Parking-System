package com.smartparking.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {

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
}
