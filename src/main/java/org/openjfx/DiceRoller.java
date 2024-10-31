package org.openjfx;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
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

public class DiceRoller extends Application {
    int counter1 = 0; // how many more times the first dice can align
    int counter2 = 0; // how many more times the second dice can align

    private static boolean isFaceUp(Rotate rotateX) {
        double threshold = 2;  // allow a margin of error
        double angleX = Math.abs(rotateX.getAngle() % 360);
        return (Math.abs(angleX) < threshold) || (Math.abs(angleX - 90) < threshold) || (Math.abs(angleX - 180) < threshold) || (Math.abs(angleX - 270) < threshold);
    }

    @Override
    public void start(Stage primaryStage) {
        // move camera a bit to get both dice in
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(10);
        camera.setTranslateZ(-100);
        camera.setFieldOfView(18);

        CameraTransformer cameraTransform = new CameraTransformer();
        cameraTransform.getChildren().add(camera);

        // make two cubes and wrap the dice texture around them
        CuboidMesh dice1 = new CuboidMesh(10f, 10f, 10f);
        dice1.setTextureModeImage("texture.jpg");
        CuboidMesh dice2 = new CuboidMesh(10f, 10f, 10f);
        dice2.setTextureModeImage("texture.jpg");
        // move the second dice over
        Translate translate = new Translate(20, 0, 0);
        dice2.getTransforms().add(translate);

        // rotate animations for both dice
        Rotate rX1 = new Rotate(0, Rotate.X_AXIS);
        Rotate rY1 = new Rotate(0, Rotate.Y_AXIS);
        Rotate rZ1 = new Rotate(0, Rotate.Z_AXIS);
        dice1.getTransforms().addAll(rX1, rY1, rZ1);
        Rotate rX2 = new Rotate(0, Rotate.X_AXIS);
        Rotate rY2 = new Rotate(0, Rotate.Y_AXIS);
        Rotate rZ2 = new Rotate(0, Rotate.Z_AXIS);
        dice2.getTransforms().addAll(rX2, rY2, rZ2);
        Random r = new Random();

        // animate the Rotates for dice1
        AnimationTimer timer1 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // change by a random amount
                rX1.setAngle(rX1.getAngle() + 5 + r.nextInt(10));
                rY1.setAngle(rY1.getAngle() + 5 + r.nextInt(10));
                rZ1.setAngle(rZ1.getAngle() + 5 + r.nextInt(10));

                // dice1 can align one less time
                if (isFaceUp(rX1)) {
                    counter1 -= 1;
                }
                // if dice1 has run out of aligns, stop it
                if (counter1 == 0) {
                    stop();
                }
            }
        };
        // animate the Rotates for dice2
        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // change by a random amount
                rX2.setAngle(rX2.getAngle() - 5 - r.nextInt(10));
                rY2.setAngle(rY2.getAngle() - 5 - r.nextInt(10));
                rZ2.setAngle(rZ2.getAngle() - 5 - r.nextInt(10));

                // dice2 can align one less time
                if (isFaceUp(rX2)) {
                    counter2 -= 1;
                }
                // if dice2 has run out of aligns, stop it
                if (counter2 == 0) {
                    stop();
                }
            }
        };
        // button to roll the dice
        Button roll = new Button("Roll");
        Scale scale = new Scale(0.15, 0.15, 0.15); // make it smaller
        Translate buttonTranslate = new Translate(47, -10, 0);
        roll.getTransforms().addAll(scale, buttonTranslate);
        EventHandler<ActionEvent> event = action -> { // on click, teleport the dice, reset the counters, and role
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

        // add everything to a group and show it
        Group group = new Group(cameraTransform, dice1, dice2, roll);
        Scene scene = new Scene(group, 600, 400, true, SceneAntialiasing.BALANCED);
        scene.setCamera(camera); // use the modified camera from the start

        primaryStage.setTitle("Dice Roller");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
