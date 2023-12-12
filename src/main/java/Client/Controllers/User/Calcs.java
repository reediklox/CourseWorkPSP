package Client.Controllers.User;

import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Calcs {
    @FXML
    public Button Calcs;
    @FXML
    public Button DataLists;
    @FXML
    public Button Analitics;
    @FXML
    public Button Forecasting;
    @FXML
    public Button Comparison;

    @FXML
    public Text UserLogin;
    @FXML
    public ImageView CloseButton;

    @FXML
    public Button CountButton;
    @FXML
    public Label ResultCost;
    @FXML
    public Label ResultLabel;
    @FXML
    public TextField IDInput;

    @FXML
    public TextField PrecentAmount;
    @FXML
    public Button CountButton1;
    @FXML
    public Label ResultCost1;
    @FXML
    public Label ResultLabel1;
    @FXML
    public TextField IDInput1;

    @FXML
    public TextField Count;
    @FXML
    public Button CountButton2;
    @FXML
    public Label ResultCost2;
    @FXML
    public Label ResultLabel2;

    @FXML
    void initialize(){
        UserLogin.setText("reedik");

        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
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
