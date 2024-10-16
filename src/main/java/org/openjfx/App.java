package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.shape.CullFace;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxyz3d.shapes.primitives.SpringMesh;
import org.fxyz3d.shapes.primitives.CuboidMesh;
import org.fxyz3d.utils.CameraTransformer;

import java.sql.Time;
import java.util.Random;

public class App extends Application {

    private boolean isFaceUp(Rotate rotateX, Rotate rotateY, Rotate rotateZ) {
        // Define the threshold angles that indicate when a face is facing up
        // Assuming we're checking for a face to align with the Y-axis (i.e., a 90-degree rotation on X or Z)
        double threshold = 1;  // Allow a margin of error (floating point inaccuracy)
        double angleX = Math.abs(rotateX.getAngle() % 360);
        double angleZ = Math.abs(rotateZ.getAngle() % 360);

        // Check if the cuboid is rotated to face up (i.e., X or Z is near 0, 90, 180, or 270 degrees)
        return (Math.abs(angleX - 90) < threshold || Math.abs(angleX - 270) < threshold) ||
                (Math.abs(angleZ) < threshold || Math.abs(angleZ - 180) < threshold);
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

        AnimationTimer timer = new AnimationTimer() {
            Random r = new Random();
            int counter = r.nextInt(12) + 6;
            @Override
            public void handle(long now) {
                rX.setAngle(rX.getAngle() + 5);
                rY.setAngle(rY.getAngle() + 6);
                rZ.setAngle(rZ.getAngle() + 7);

                if (isFaceUp(rX, rY, rZ)) {
                    //System.out.println("Face aligning " + counter + " more times");
                    counter--;
                }
                if (counter == 0) {
                    stop();
                }
            }
        };
        timer.start();

        Group group = new Group(cameraTransform, dice);

        Scene scene = new Scene(group, 600, 400, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BISQUE);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dice Roller");
        primaryStage.show();
    }
}