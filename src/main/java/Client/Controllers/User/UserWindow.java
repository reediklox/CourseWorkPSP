package Client.Controllers.User;

import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class UserWindow {
    @FXML
    private Button Calcs;

    @FXML
    private Button DataLists;

    @FXML
    private Button Analitics;

    @FXML
    private Button Forecasting;

    @FXML
    private Button Comparison;

    @FXML
    private Text UserLogin;

    @FXML
    private ImageView CloseButton;

    @FXML
    void initialize(){
        UserLogin.setText("reedik");

        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        Calcs.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Calcs, "/Client/Calcs.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        DataLists.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(DataLists, "/Client/Lists.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Analitics.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Analitics, "/Client/Analitics.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Forecasting.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Forecasting, "/Client/Prediction.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Comparison.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Comparison, "/Client/ComparisonWin.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
