package Client.Controllers.User;

import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Prediction {
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
    private Button ResultButton;

    @FXML
    private Label ResultLabel2;
    @FXML
    private Label PredictResult;

    @FXML
    private Text UserLogin;

    @FXML
    private ImageView CloseButton;

// Block to update
    @FXML
    private TableView<String> expences; // replace 'String' on Server.Entity.Excences
// End of the block to update
    @FXML
    private CheckBox Winter;
    @FXML
    private CheckBox Spring;
    @FXML
    private CheckBox Summer;
    @FXML
    private CheckBox Autumn;

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
        Comparison.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Comparison, "/Client/ComparisonWin.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Winter.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Spring.setOnAction(event -> {
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Summer.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Autumn.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
        });
    }
}
