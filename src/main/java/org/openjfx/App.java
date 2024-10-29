package org.openjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button diceRoller = new Button("Dice Roller");
        diceRoller.setMinWidth(200);
        diceRoller.setMinHeight(100);
        diceRoller.setOnAction(action -> new DiceRoller().start(new Stage()));

        Button quadraticSolver = new Button("Quadratic Solver");
        quadraticSolver.setMinWidth(200);
        quadraticSolver.setMinHeight(100);
        quadraticSolver.setTranslateY(100);
        quadraticSolver.setOnAction(action -> new QuadraticSolver().start(new Stage()));

        Button circleCalc = new Button("Circle Calculator");
        circleCalc.setMinWidth(200);
        circleCalc.setMinHeight(100);
        circleCalc.setTranslateY(200);
        circleCalc.setOnAction(action -> new CircleCalc().start(new Stage()));

        Group group = new Group(diceRoller, quadraticSolver, circleCalc);
        Scene scene = new Scene(group, 200, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Practice Plus");
        primaryStage.show();
    }
}