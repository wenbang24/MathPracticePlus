package org.openjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class CircleCalc extends Application {
    @Override
    public void start(Stage primaryStage) {
        // circle with center and radius line
        Circle circle = new Circle(250, 250, 200);
        circle.setFill(null);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        Circle centre = new Circle(250, 250, 5);
        Line radiusLine = new Line(250, 250, 450, 250);

        Label circumferenceLabel = new Label("Circumference: ");
        circumferenceLabel.setTranslateY(20);
        circumferenceLabel.setTranslateX(200);

        Label areaLabel = new Label("Area: ");
        areaLabel.setTranslateY(100);
        areaLabel.setTranslateX(200);

        Label radiusLabel = new Label("Radius: ");
        TextField radiusInput = new TextField("1"); // radius input
        radiusInput.setPrefColumnCount(3);
        radiusInput.setTranslateX(333);
        radiusInput.setTranslateY(215);
        radiusLabel.setLabelFor(radiusInput);
        radiusLabel.setTranslateX(280);
        radiusLabel.setTranslateY(220);
        // when the radius field changes
        radiusInput.textProperty().addListener((listener, oldValue, newValue) -> {
            try {
                double radius = Double.parseDouble(newValue);
                // calculate from radius and display
                circumferenceLabel.setText("Circumference: " + radius * 2 * Math.PI);
                areaLabel.setText("Area: " + radius * radius * Math.PI);
            } catch (NumberFormatException e) { // empty or not number
                circumferenceLabel.setText("Circumference: Invalid Input");
                areaLabel.setText("Area: Invalid Input");
            }
        });

        // add everything to a group and display
        Group group = new Group(circle, centre, radiusLine, radiusLabel, radiusInput, circumferenceLabel, areaLabel);
        Scene scene = new Scene(group, 500, 500);
        primaryStage.setTitle("Circle Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
