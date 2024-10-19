package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.fxyz3d.shapes.primitives.CuboidMesh;
import org.fxyz3d.utils.CameraTransformer;
import java.util.Random;

public class CircleCalc {
    public void CircleCalcStage(Stage primaryStage) {
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
        TextField radiusInput = new TextField("1");
        radiusInput.setPrefColumnCount(3);
        radiusInput.setTranslateX(333);
        radiusInput.setTranslateY(215);
        radiusLabel.setLabelFor(radiusInput);
        radiusLabel.setTranslateX(280);
        radiusLabel.setTranslateY(220);
        radiusInput.textProperty().addListener((_, _, newValue) -> {
            try {
                double radius = Double.parseDouble(newValue);
                circumferenceLabel.setText("Circumference: " + radius * 2 * Math.PI);
                areaLabel.setText("Area: " + radius * radius * Math.PI);
            } catch (NumberFormatException _) {
                circumferenceLabel.setText("Circumference: Invalid Input");
                areaLabel.setText("Area: Invalid Input");
            }
        });

        Group group = new Group(circle, centre, radiusLine, radiusLabel, radiusInput, circumferenceLabel, areaLabel);
        Scene scene = new Scene(group, 500, 500);
        primaryStage.setTitle("Circle Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
