package com.smartparking.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class GUI_Practice_2_Controller_Main {
    @FXML
    public void handleCloseMenuItemAction() {
        Platform.exit();
    }
}
