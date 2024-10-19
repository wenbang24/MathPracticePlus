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
        diceRoller.setOnAction(_ -> new DiceRoller().DiceRollerStage(new Stage()));

        Button quadraticSolver = new Button("Quadratic Solver");
        quadraticSolver.setTranslateY(35);
        quadraticSolver.setOnAction(_ -> new QuadraticSolver().QuadraticSolverStage(new Stage()));

        Button circleCalc = new Button("Circle Calculator");
        circleCalc.setTranslateY(70);
        circleCalc.setOnAction(_ -> new CircleCalc().CircleCalcStage(new Stage()));

        Group group = new Group(diceRoller, quadraticSolver, circleCalc);
        Scene scene = new Scene(group, 200, 150);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Practice Plus");
        primaryStage.show();
    }
}