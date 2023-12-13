package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Providers;
import Server.Entity.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ProvidersDB {
    @FXML
    private TableColumn<Providers, Integer> provider_id;
    @FXML
    private TableColumn<Providers, String> mobile_number;
    @FXML
    private TableColumn<Providers, Integer> material_id;
    @FXML
    private TableColumn<Providers, Integer> material_count;
    @FXML
    private TableColumn<Providers, Integer> material_cost_per_piece;
    @FXML
    private TableView<Providers> providers;
    @FXML
    private Label ResultLabel2;
    @FXML
    private Button DeleteButton;
    @FXML
    private TextField id_for_delete;
    @FXML
    private TextField id_for_add;
    @FXML
    private TextField num_for_add;
    @FXML
    private TextField mat_for_add;
    @FXML
    private TextField count_for_add;
    @FXML
    private TextField price_for_add;
    @FXML
    private Button AddButton;
    @FXML
    private Button UsersDB;
    @FXML
    private Button MaterialsDB;
    @FXML
    private Button ExpencesDB;
    @FXML
    private Button CompaniesDB;
    @FXML
    private Button AttritionDB;
    @FXML
    private Button TaxDB;

    @FXML
    private ImageView CloseButton;

    @FXML
    void initialize(){
        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });
        AddButton.setOnAction(event -> {
            if(num_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет номера");
                return;
            }
            if(mat_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет id материала");
                return;
            }
            if(count_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет количества");
                return;
            }
            if(price_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет цены");
                return;
            }
            addProvider(num_for_add.getText(), mat_for_add.getText(),count_for_add.getText(), price_for_add.getText());
        });
        providers.setOnMouseClicked(event -> {
            reloadInfo();
        });
        DeleteButton.setOnAction(event -> {
            deleteProvider();
        });
        UsersDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(UsersDB, "/Client/UsersDB.fxml");
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
        TaxDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(TaxDB, "/Client/TaxDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void reloadInfo(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getProviders");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Providers> providers = (ArrayList<Providers>) object;
                    System.out.println(providers.size());
                    printProviders(providers);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printProviders(ArrayList<Providers> providers1){
        for(Providers p:providers1){
            System.out.println(p.toString());
        }
        ObservableList<Providers> observableList = FXCollections.observableArrayList(providers1);
        providers.setItems(observableList);
        provider_id.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("provider_id"));
        mobile_number.setCellValueFactory(new PropertyValueFactory<Providers,String>("mobile_number"));
        material_id.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_id"));
        material_count.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_count"));
        material_cost_per_piece.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_cost_per_piece"));
    }

    void deleteProvider(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = id_for_delete.getText();
            writer.write("deleteProvider");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addProvider(String num, String mat, String cou, String pri){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addProvider");writer.newLine();
            writer.write(num);writer.newLine();
            writer.write(mat); writer.newLine();
            writer.write(cou); writer.newLine();
            writer.write(pri); writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
