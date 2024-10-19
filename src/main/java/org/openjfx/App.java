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
import org.openjfx.*;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button diceRoller = new Button("Dice Roller");
        diceRoller.setOnAction(_ -> {
            new DiceRoller().DiceRollerStage(new Stage());
        });

        Button quadraticSolver = new Button("Quadratic Solver");
        quadraticSolver.setTranslateY(35);
        quadraticSolver.setOnAction(_ -> {
            new QuadraticSolver().QuadraticSolverStage(new Stage());
        });

        Button circleCalc = new Button("Circle Calculator");
        circleCalc.setTranslateY(70);
        circleCalc.setOnAction(_ -> {
            new CircleCalc().CircleCalcStage(new Stage());
        });

        Group group = new Group(diceRoller, quadraticSolver, circleCalc);
        Scene scene = new Scene(group, 200, 150);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Practice Plus");
        primaryStage.show();
    }
}