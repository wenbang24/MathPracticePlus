package org.openjfx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class QuadraticSolver extends Application {
    @Override
    public void start(Stage primaryStage) {
        // inputs for coefficients
        Label aLabel = new Label("a:");
        TextField aInput = new TextField("1");
        aInput.setPrefColumnCount(3);
        aInput.setTranslateX(50);
        aLabel.setLabelFor(aInput);
        aLabel.setTranslateX(30);

        Label bLabel = new Label("b:");
        TextField bInput = new TextField("-1");
        bInput.setPrefColumnCount(3);
        bInput.setTranslateX(150);
        bLabel.setLabelFor(bInput);
        bLabel.setTranslateX(130);

        Label cLabel = new Label("c:");
        TextField cInput = new TextField("-6");
        cInput.setPrefColumnCount(3);
        cInput.setTranslateX(250);
        cLabel.setLabelFor(cInput);
        cLabel.setTranslateX(230);

        Button solve = new Button("Solve");
        solve.setTranslateY(30);
        Label roots = new Label("Roots: ");
        roots.setTranslateY(70);

        // graph to plot quadratic made from two axes
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTranslateY(100);

        // when solve button is clicked, solve the quadratic
        solve.setOnAction(action -> {
            try {
                double a = Double.parseDouble(aInput.getText());
                double b = Double.parseDouble(bInput.getText());
                double c = Double.parseDouble(cInput.getText());
                double discriminant = b * b - 4 * a * c;

                // series of (x, y) coordinates
                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(a + "x^2 + " + b + "x + " + c);
                chart.getData().add(series);

                if (discriminant < 0) { // no roots
                    // quadratic centered here
                    double turningPoint = -b / (2 * a);
                    roots.setText("Roots: No real roots");
                    // generate 20 points
                    for (int i = (int)turningPoint - 10; i <= turningPoint + 11; i++) {
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                } else if (discriminant == 0) { // one root
                    double root = -b / (2 * a);
                    roots.setText("Roots: " + root);
                    // generate 20 points
                    for (int i = (int)root - 10; i <= root + 10; i++){
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                } else { // two roots
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    roots.setText("Roots: " + root1 + ", " + root2);
                    // generate 20 points
                    for (int i = (int)root2 - 10; i <= root1 + 10; i++){
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                }
            } catch (NumberFormatException e) { // turns out NullPointerException isn't thrown even if all three fields are blank
                // tell user that their input is invalid
                Alert invalid = new Alert(Alert.AlertType.ERROR, "Invalid input; please only enter numbers in the fields");
                invalid.showAndWait();
            }
        });
        // clear graph
        Button clear = new Button("Clear");
        clear.setTranslateY(500);
        clear.setOnAction(action -> {
            roots.setText("Roots: ");
            chart.getData().clear(); // delete all plots
        });

        // add everything to a group and display
        Group group = new Group(aInput, bInput, cInput, solve, roots, chart, clear, aLabel, bLabel, cLabel);
        Scene scene = new Scene(group, 500, 600);
        primaryStage.setTitle("Quadratic Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
