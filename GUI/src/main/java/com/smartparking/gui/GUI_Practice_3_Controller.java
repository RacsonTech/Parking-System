package com.smartparking.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Practice_3_Controller {

    public Pane center_pane;

    @FXML
    public void handleCloseMenuItemAction() {
        Platform.exit();
    }

    @FXML
    public void handleAboutMenuAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("about.fxml"));
        Parent aboutPage = loader.load();
        Scene aboutPageScene = new Scene(aboutPage);

        Stage window = (Stage)center_pane.getScene().getWindow();

        window.setScene(aboutPageScene);
    }
}
