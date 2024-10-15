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
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class App extends Application {

    public Parent createContent() {
        // Box
        Box dice = new Box(5, 5, 5);
        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.DARKGREEN);
        material.setDiffuseMap(new Image("texture.jpg"));
        dice.setMaterial(material);
        /*
        TriangleMesh box = new TriangleMesh();
        box.getPoints().addAll(2.5f, 2.5f, 2.5f);    // 0
        box.getPoints().addAll(2.5f, 2.5f, -2.5f);   // 1
        box.getPoints().addAll(2.5f, -2.5f, 2.5f);   // 2
        box.getPoints().addAll(2.5f, -2.5f, -2.5f);  // 3
        box.getPoints().addAll(-2.5f, 2.5f, 2.5f);   // 4
        box.getPoints().addAll(-2.5f, 2.5f, -2.5f);  // 5
        box.getPoints().addAll(-2.5f, -2.5f, 2.5f);  // 6
        box.getPoints().addAll(-2.5f, -2.5f, -2.5f); // 7

        box.getTexCoords().addAll(0, 0);

        //top
        box.getFaces().addAll(0, 2, 4);
        box.getFaces().addAll(2, 4, 6);
        // front
        box.getFaces().addAll(0, 1, 2);
        box.getFaces().addAll(1, 2, 3);
        // right
        box.getFaces().addAll(0, 1, 5);
        box.getFaces().addAll(0, 4, 5);
        // back
        box.getFaces().addAll(4, 5, 6);
        box.getFaces().addAll(5, 6, 7);
        // left
        box.getFaces().addAll(2, 6, 7);
        box.getFaces().addAll(2, 3, 7);
        // bottom
        box.getFaces().addAll(1, 3, 7);
        box.getFaces().addAll(1, 5, 7);

        PhongMaterial material = new PhongMaterial();
        //material.setDiffuseColor(Color.DARKGREEN);
        material.setDiffuseMap(new Image("texture.jpg"));
        MeshView dice = new MeshView(box);
        dice.setMaterial(material);
        */

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
        root.getChildren().add(dice);

        Random r = new Random();
        RotateTransition rotator = new RotateTransition();
        rotator.setNode(dice);
        rotator.setDuration(Duration.seconds(5));
        rotator.setAxis(new Point3D(r.nextDouble(), r.nextDouble(), r.nextDouble()));
        rotator.setFromAngle(0);
        rotator.setToAngle(1800);
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