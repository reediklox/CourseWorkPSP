package Client.Controllers.User;

import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Services.DataBase.DBHandlerUsers;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Analitics {
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

// Block to update
    @FXML
    private TableView<String> companies; // replace 'String' on Server.Entity.Companies
    @FXML
    private TableView<String> providers; // replace 'String' on Server.Entity.Providers
    @FXML
    private TableView<String> expences; // replace 'String' on Server.Entity.Expences
// End of the block to update

    @FXML
    private BarChart ExpGraph;

    @FXML
    private Label ResultLabel1;
    @FXML
    private Label BestPriceC;
    @FXML
    private Label MeanPriceC;
    @FXML
    private Label WorstPriceC;
    @FXML

    private Label ResultLabel2;
    @FXML
    private TextField material_name;
    @FXML
    private Label BestPriceP;
    @FXML
    private Label MeanPriceP;
    @FXML
    private Label WorstPriceP;
    @FXML
    private Button ResultProviders;

    @FXML
    private TextField ProductCount;
    @FXML
    private TextField ProductPrice;
    @FXML
    private Button ResultMarj;
    @FXML
    private Label ResultMarjLabel;

    DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();

    @FXML
    void initialize() {
        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
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
