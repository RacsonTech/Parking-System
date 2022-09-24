package com.smartparking.host;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;


public class Gui extends Application {
    final Color COLOR_MAIN = Color.SILVER;
    final Color COLOR_LIGHT = Color.LIGHTGREY;
    final Color COLOR_DARK = Color.web("5F5F5F");
    final Color COLOR_CONTRAST = Color.web("232323");

    final Color FONT_DARK = Color.BLACK;
    final Color FONT_LIGHT = Color.WHITE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, COLOR_MAIN);
        Image icon = new Image("logo_256x256.png");
        stage.getIcons().add(icon);
        stage.setTitle("Smart Parking System - Control Unit Host Program");
        stage.setWidth(720);
        stage.setHeight(480);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
