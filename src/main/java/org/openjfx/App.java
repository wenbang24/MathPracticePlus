package org.openjfx;

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

        Rotate rX = new Rotate(0 * Math.PI / 180, Rotate.X_AXIS);
        Rotate rY = new Rotate(0 * Math.PI / 180, Rotate.Y_AXIS);
        Rotate rZ = new Rotate(0 * Math.PI / 180, Rotate.Z_AXIS);
        dice.getTransforms().addAll(rX, rY, rZ);

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(10), actionEvent -> {
                            rX.setAngle(rX.getAngle() + 1);
                            rY.setAngle(rY.getAngle() + 2);
                            rZ.setAngle(rZ.getAngle() + 3);
                        }
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Group group = new Group(cameraTransform, dice);

        Scene scene = new Scene(group, 600, 400, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BISQUE);
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.setTitle("FXyz3D Sample");
        primaryStage.show();
    }
}