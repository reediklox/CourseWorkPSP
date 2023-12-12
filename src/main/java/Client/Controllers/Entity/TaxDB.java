package Client.Controllers.Entity;

import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class TaxDB {
    @FXML
    private TableView tax;
    @FXML
    private Label ResultLabel2;
    @FXML
    private Button DeleteButton;
    @FXML
    private TextField id_for_delete;
    @FXML
    private TextField id_for_add;
    @FXML
    private TextField type_for_add;
    @FXML
    private TextField percent_for_add;
    @FXML
    private Button AddButton;
    @FXML
    private Button UsersDB;
    @FXML
    private Button ProvidersDB;
    @FXML
    private Button MaterialsDB;
    @FXML
    private Button ExpencesDB;
    @FXML
    private Button CompaniesDB;
    @FXML
    private Button AttritionDB;

    @FXML
    private ImageView CloseButton;

    @FXML
    void initialize(){
        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        UsersDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(UsersDB, "/Client/UsersDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ProvidersDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(ProvidersDB, "/Client/ProvidersDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        MaterialsDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(MaterialsDB, "/Client/MaterialsDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ExpencesDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(ExpencesDB, "/Client/ExpencesDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        CompaniesDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(CompaniesDB, "/Client/CompaniesDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        AttritionDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(AttritionDB, "/Client/AttritionDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
