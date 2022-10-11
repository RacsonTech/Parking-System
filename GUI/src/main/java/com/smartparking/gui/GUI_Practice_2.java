package com.smartparking.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI_Practice_2 extends Application {
    final Color COLOR_MAIN = Color.SILVER;
    final Color COLOR_LIGHT = Color.LIGHTGREY;
    final Color COLOR_DARK = Color.web("5F5F5F");
    final Color COLOR_CONTRAST = Color.web("232323");

    final Color FONT_COLOR_DARK = Color.BLACK;
    final Color FONT_COLOR_LIGHT = Color.WHITE;
    final String FONT_MAIN = "Verdana";

    final int WINDOW_WIDTH = 720;
    final int WINDOW_HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Practice_2.class.getResource("GUI_Practice_2.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        Image icon = new Image("images/logo_256x256.png");

        stage.getIcons().add(icon);
        stage.setTitle("Smart Parking System - Control Unit Host Program");

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
