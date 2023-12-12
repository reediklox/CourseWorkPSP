package Client.Controllers.Intarfaces;

import Client.ClientApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public interface OpenWindowInt {

    static void OpenWindow(Button button, String window) throws IOException {
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource(window));
        Stage stage1 = (Stage) button.getScene().getWindow();
        stage1.close();
        Scene scene = new Scene(fxmlLoader.load(), 777, 608);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(evt -> {
            x.set(evt.getSceneX());
            y.set(evt.getSceneY());
        });
        root.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x.get());
            stage.setY(evt.getScreenY() - y.get());
        });

        stage.setScene(scene);
        stage.show();
    }

}
