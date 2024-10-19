package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;
import org.fxyz3d.shapes.primitives.CuboidMesh;
import org.fxyz3d.utils.CameraTransformer;
import java.util.Random;
import java.util.function.Function;

public class App extends Application {
    int counter1 = 0;
    int counter2 = 0;

    private boolean isFaceUp(Rotate rotateX) {
        double threshold = 2;  // allow a margin of error
        double angleX = Math.abs(rotateX.getAngle() % 360);
        return (Math.abs(angleX) < threshold) || (Math.abs(angleX - 90) < threshold) || (Math.abs(angleX - 180) < threshold) || (Math.abs(angleX - 270) < threshold);
    }

    @Override
    public void start(Stage primaryStage) {
        Button diceRoller = new Button("Dice Roller");
        diceRoller.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent unused) { // can't do underscore
                PerspectiveCamera camera = new PerspectiveCamera(true);
                camera.setNearClip(0.1);
                camera.setFarClip(10000.0);
                camera.setTranslateX(10);
                camera.setTranslateZ(-100);
                camera.setFieldOfView(18);

                CameraTransformer cameraTransform = new CameraTransformer();
                cameraTransform.getChildren().add(camera);

                CuboidMesh dice1 = new CuboidMesh(10f, 10f, 10f);
                dice1.setTextureModeImage("texture.jpg");
                CuboidMesh dice2 = new CuboidMesh(10f, 10f, 10f);
                dice2.setTextureModeImage("texture.jpg");
                Translate translate = new Translate(20, 0, 0);
                dice2.getTransforms().add(translate);

                Rotate rX1 = new Rotate(0, Rotate.X_AXIS);
                Rotate rY1 = new Rotate(0, Rotate.Y_AXIS);
                Rotate rZ1 = new Rotate(0, Rotate.Z_AXIS);
                dice1.getTransforms().addAll(rX1, rY1, rZ1);
                Rotate rX2 = new Rotate(0, Rotate.X_AXIS);
                Rotate rY2 = new Rotate(0, Rotate.Y_AXIS);
                Rotate rZ2 = new Rotate(0, Rotate.Z_AXIS);
                dice2.getTransforms().addAll(rX2, rY2, rZ2);
                Random r = new Random();

                AnimationTimer timer1 = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        rX1.setAngle(rX1.getAngle() + 5 + r.nextInt(10));
                        rY1.setAngle(rY1.getAngle() + 5 + r.nextInt(10));
                        rZ1.setAngle(rZ1.getAngle() + 5 + r.nextInt(10));

                        if (isFaceUp(rX1)) {
                            //System.out.println("Face 1 aligning " + counter1 + " more times");
                            counter1 -= 1;
                        }
                        if (counter1 == 0) {
                            stop();
                        }
                    }
                };
                AnimationTimer timer2 = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        rX2.setAngle(rX2.getAngle() - 5 - r.nextInt(10));
                        rY2.setAngle(rY2.getAngle() - 5 - r.nextInt(10));
                        rZ2.setAngle(rZ2.getAngle() - 5 - r.nextInt(10));

                        if (isFaceUp(rX2)) {
                            //System.out.println("Face 2 aligning " + counter2 + " more times");
                            counter2 -= 1;
                        }
                        if (counter2 == 0) {
                            stop();
                        }
                    }
                };
                Button roll = new Button("Roll");
                Scale scale = new Scale(0.15, 0.15, 0.15);
                Translate buttonTranslate = new Translate(47, -10, 0);
                roll.getTransforms().addAll(scale, buttonTranslate);
                EventHandler<ActionEvent> event = _ -> {
                    counter1 = r.nextInt(6) + 3;
                    counter2 = r.nextInt(6) + 3;
                    rX1.setAngle(rX1.getAngle() + 50);
                    rY1.setAngle(rY1.getAngle() + 50);
                    rZ1.setAngle(rZ1.getAngle() + 50);
                    rX2.setAngle(rX2.getAngle() + 50);
                    rY2.setAngle(rY2.getAngle() + 50);
                    rZ2.setAngle(rZ2.getAngle() + 50);
                    timer1.start();
                    timer2.start();
                };
                roll.setOnAction(event);

                Group group = new Group(cameraTransform, dice1, dice2, roll);

                Scene scene = new Scene(group, 600, 400, true, SceneAntialiasing.BALANCED);
                //scene.setFill(Color.BISQUE);
                scene.setCamera(camera);

                Stage diceStage = new Stage();
                diceStage.setTitle("Dice Roller");
                diceStage.setScene(scene);
                diceStage.show();
            }
        });

        Button quadraticSolver = new Button("Quadratic Solver");
        quadraticSolver.setTranslateY(35);
        quadraticSolver.setOnAction(_ -> {
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
            LineChart chart = new LineChart(xAxis, yAxis);
            chart.setTranslateY(100);

            solve.setOnAction(_ -> {
                try {
                    double a = Double.parseDouble(aInput.getText());
                    double b = Double.parseDouble(bInput.getText());
                    double c = Double.parseDouble(cInput.getText());
                    double discriminant = b * b - 4 * a * c;

                    XYChart.Series series = new XYChart.Series();
                    series.setName(a + "x^2 + " + b + "x + " + c);
                    chart.getData().add(series);

                    if (discriminant < 0) {
                        double turningPoint = -b / (2 * a);
                        roots.setText("Roots: No real roots");
                        for (int i = (int)turningPoint - 10; i <= turningPoint + 11; i++) {
                            series.getData().add(new XYChart.Data(i, a * i * i + b * i + c));
                        }
                    } else if (discriminant == 0) {
                        double root = -b / (2 * a);
                        roots.setText("Roots: " + root);
                        for (int i = (int)root - 10; i <= root + 10; i++){
                            series.getData().add(new XYChart.Data(i, a * i * i + b * i + c));
                        }
                    } else {
                        double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                        double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                        roots.setText("Roots: " + root1 + ", " + root2);
                        for (int i = (int)root2 - 10; i <= root1 + 10; i++){
                            series.getData().add(new XYChart.Data(i, a * i * i + b * i + c));
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
            Stage quadraticStage = new Stage();
            quadraticStage.setTitle("Quadratic Solver");
            quadraticStage.setScene(scene);
            quadraticStage.show();
        });

        Button circleCalc = new Button("Circle Calculator");
        circleCalc.setTranslateY(70);
        circleCalc.setOnAction(_ -> {
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
            Stage circleStage = new Stage();
            circleStage.setTitle("Circle Calculator");
            circleStage.setScene(scene);
            circleStage.show();
        });

        Group group = new Group(diceRoller, quadraticSolver, circleCalc);
        Scene scene = new Scene(group, 200, 150);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Practice Plus");
        primaryStage.show();
    }
}