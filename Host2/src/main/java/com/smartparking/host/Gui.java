package com.smartparking.host;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Gui extends Application {
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
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, COLOR_MAIN);

        Image icon = new Image("logo_256x256.png");
        stage.getIcons().add(icon);
        stage.setTitle("Smart Parking System - Control Unit Host Program");

        Text text = new Text();
        text.setText("Main  Status  ");
        text.setX(20);
        text.setY(20);
        text.setFont(Font.font(FONT_MAIN, 12));
        text.setFill(FONT_COLOR_DARK);

        Line line = new Line();
        line.setStartX(1);
        line.setStartY(30);
        line.setEndX(WINDOW_WIDTH);
        line.setEndY(30);
        line.setStrokeWidth(3);
        line.setStroke(COLOR_DARK);

        Rectangle sideMenu = new Rectangle();
        sideMenu.setX(1);
        sideMenu.setY(30);
        sideMenu.setWidth(180);
        sideMenu.setHeight(WINDOW_HEIGHT);
        sideMenu.setFill(COLOR_LIGHT);
        sideMenu.setStrokeWidth(3);
        sideMenu.setStroke(COLOR_CONTRAST);

        Polygon triangle = new Polygon();
        triangle.getPoints().setAll(
                200.0, 200.0,
                300.0, 300.0,
                200.0, 300.0
        );
        triangle.setFill(Color.YELLOW);

        Circle circle = new Circle();
        circle.setCenterX(350);
        circle.setCenterY(350);
        circle.setRadius(50);
        circle.setFill(Color.ORANGE);

        Image image = new Image("Logo.PNG");
        ImageView imageView = new ImageView(image);
        imageView.setX(200);
        imageView.setY(100);
        imageView.setFitHeight(20);
        imageView.setFitWidth(215);

        root.getChildren().add(text);
        root.getChildren().add(line);
        root.getChildren().add(sideMenu);
        root.getChildren().add(triangle);
        root.getChildren().add(circle);
        root.getChildren().add(imageView);

//        stage.setWidth(720);
//        stage.setHeight(480);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
