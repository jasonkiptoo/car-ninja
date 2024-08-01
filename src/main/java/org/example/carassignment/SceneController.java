package org.example.carassignment;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.carassignment.Car;

public class SceneController {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showInitialScene() {
        try {
            Parent root = FXMLLoader.load(SceneController.class.getResource("/view/InitialScene.fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCarDetails(Car car) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/view/DetailedScene.fxml"));
            Parent root = loader.load();
//            DetailedSceneController controller = loader.getController();
//            controller.initialize(car);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
