package org.openjfx;

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

public class QuadraticSolver {
    public void QuadraticSolverStage(Stage primaryStage) {
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

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTranslateY(100);

        solve.setOnAction(_ -> {
            try {
                double a = Double.parseDouble(aInput.getText());
                double b = Double.parseDouble(bInput.getText());
                double c = Double.parseDouble(cInput.getText());
                double discriminant = b * b - 4 * a * c;

                XYChart.Series<Number, Number> series = new XYChart.Series<>();
                series.setName(a + "x^2 + " + b + "x + " + c);
                chart.getData().add(series);

                if (discriminant < 0) {
                    double turningPoint = -b / (2 * a);
                    roots.setText("Roots: No real roots");
                    for (int i = (int)turningPoint - 10; i <= turningPoint + 11; i++) {
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                } else if (discriminant == 0) {
                    double root = -b / (2 * a);
                    roots.setText("Roots: " + root);
                    for (int i = (int)root - 10; i <= root + 10; i++){
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                } else {
                    double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                    double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                    roots.setText("Roots: " + root1 + ", " + root2);
                    for (int i = (int)root2 - 10; i <= root1 + 10; i++){
                        series.getData().add(new XYChart.Data<>(i, a * i * i + b * i + c));
                    }
                }
            } catch (NumberFormatException _) { // turns out NullPointerException isn't thrown even if all three fields are blank
                Alert invalid = new Alert(Alert.AlertType.ERROR, "Invalid input; please only enter numbers in the fields");
                invalid.showAndWait();
            }
        });
        Button clear = new Button("Clear");
        clear.setTranslateY(500);
        clear.setOnAction(_ -> {
            roots.setText("Roots: ");
            chart.getData().clear();
        });

        Group group = new Group(aInput, bInput, cInput, solve, roots, chart, clear, aLabel, bLabel, cLabel);
        Scene scene = new Scene(group, 500, 600);
        primaryStage.setTitle("Quadratic Solver");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
