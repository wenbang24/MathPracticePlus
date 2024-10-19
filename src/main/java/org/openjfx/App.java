package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import org.fxyz3d.shapes.primitives.CuboidMesh;
import org.fxyz3d.utils.CameraTransformer;
import java.util.Random;

public class App extends Application {
    int counter = 0;

    private boolean isFaceUp(Rotate rotateX) {
        double threshold = 2;  // allow a margin of error
        double angleX = Math.abs(rotateX.getAngle() % 360);
        return (Math.abs(angleX) < threshold) || (Math.abs(angleX - 90) < threshold) || (Math.abs(angleX - 180) < threshold) || (Math.abs(angleX - 270) < threshold);
    }

    @Override
    public void start(Stage primaryStage) {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(10);
        camera.setTranslateZ(-100);
        camera.setFieldOfView(20);

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
                    System.out.println("Face aligning " + counter + " more times");
                    counter -= 1;
                }
                if (counter == 0) {
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
                    System.out.println("Face aligning " + counter + " more times");
                    counter -= 1;
                }
                if (counter == 0) {
                    stop();
                }
            }
        };
        Button roll = new Button("Roll");
        EventHandler<ActionEvent> event = _ -> {
            counter = r.nextInt(6) + 3;
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

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Roller");
        primaryStage.show();
    }
}