package org.openjfx;

import javafx.application.Application;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {


    public Parent createContent() {
        // Box
        Box testBox = new Box(5, 5, 5);
        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.DARKGREEN);
        material.setDiffuseMap(new Image("texture.jpg"));
        testBox.setMaterial(material);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.Y_AXIS),
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, 0, -20)
        );

        // Build the Scene Graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(testBox);

        RotateTransition rotator = new RotateTransition();
        rotator.setNode(testBox);
        rotator.setDuration(Duration.seconds(5));
        rotator.setAxis(new Point3D(1, 0.5, 1));
        rotator.setFromAngle(0);
        rotator.setToAngle(360);
        rotator.setCycleCount(Transition.INDEFINITE);
        //rotator.setInterpolator(Interpolator.LINEAR);
        rotator.play();

        // Use a SubScene
        SubScene subScene = new SubScene(root, 300, 300);
        subScene.setFill(Color.web("#0a4f59"));
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);

        return group;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}