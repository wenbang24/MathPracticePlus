package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.transform.Rotate;
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
        double threshold = 2;  // Allow a margin of error
        double angleX = Math.abs(rotateX.getAngle() % 360);
        return (Math.abs(angleX) < threshold) || (Math.abs(angleX - 90) < threshold) || (Math.abs(angleX - 180) < threshold) || (Math.abs(angleX - 270) < threshold);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateX(10);
        camera.setTranslateZ(-100);
        camera.setFieldOfView(20);

        CameraTransformer cameraTransform = new CameraTransformer();
        cameraTransform.getChildren().add(camera);
        //cameraTransform.ry.setAngle(-30.0);
        //cameraTransform.rx.setAngle(-15.0);

        CuboidMesh dice = new CuboidMesh(10f, 10f, 10f);
        dice.setTextureModeImage("texture.jpg");

        Rotate rX = new Rotate(0, Rotate.X_AXIS);
        Rotate rY = new Rotate(0, Rotate.Y_AXIS);
        Rotate rZ = new Rotate(0, Rotate.Z_AXIS);
        dice.getTransforms().addAll(rX, rY, rZ);
        Random r = new Random();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rX.setAngle(rX.getAngle() + 5 + r.nextInt(10));
                rY.setAngle(rY.getAngle() + 5 + r.nextInt(10));
                rZ.setAngle(rZ.getAngle() + 5 + r.nextInt(10));

                if (isFaceUp(rX)) {
                    System.out.println("Face aligning " + counter + " more times");
                    counter -= 1;
                }
                if (counter == 0) {
                    stop();
                }
            }
        };
        Button roll = new Button("Roll");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                counter = r.nextInt(6) + 6;
                rX.setAngle(rX.getAngle() + 50);
                rY.setAngle(rY.getAngle() + 50);
                rZ.setAngle(rZ.getAngle() + 50);
                timer.start();
            }
        };
        roll.setOnAction(event);

        Group group = new Group(cameraTransform, dice, roll);

        Scene scene = new Scene(group, 600, 400, true, SceneAntialiasing.BALANCED);
        //scene.setFill(Color.BISQUE);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Roller");
        primaryStage.show();
    }
}