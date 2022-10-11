package com.smartparking.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Practice_3 extends Application {

    final int WINDOW_WIDTH = 720;
    final int WINDOW_HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUI_Practice_3.class.getResource("GUI_Practice_3.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        Image icon = new Image("images/logo_256x256.png");

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Smart Parking System - Control Unit Host Program");

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
