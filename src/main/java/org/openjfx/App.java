package org.openjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // dice roller button
        Button diceRoller = new Button("Dice Roller");
        diceRoller.setMinWidth(200);
        diceRoller.setMinHeight(100);
        // create new instance of diceRoller class
        diceRoller.setOnAction(action -> new DiceRoller().start(new Stage()));

        // quadratic solver button
        Button quadraticSolver = new Button("Quadratic Solver");
        quadraticSolver.setMinWidth(200);
        quadraticSolver.setMinHeight(100);
        quadraticSolver.setTranslateY(100);
        // create new instance of quadraticSolver class
        quadraticSolver.setOnAction(action -> new QuadraticSolver().start(new Stage()));

        // circle calculator button
        Button circleCalc = new Button("Circle Calculator");
        circleCalc.setMinWidth(200);
        circleCalc.setMinHeight(100);
        circleCalc.setTranslateY(200);
        // create new instance of circleCalc class
        circleCalc.setOnAction(action -> new CircleCalc().start(new Stage()));

        // group buttons together and add to scene
        Group group = new Group(diceRoller, quadraticSolver, circleCalc);
        Scene scene = new Scene(group, 200, 300);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Practice Plus");
        primaryStage.show();
    }
}