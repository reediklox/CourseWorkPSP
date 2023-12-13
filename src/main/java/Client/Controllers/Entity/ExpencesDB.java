package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Expences;
import Server.Entity.Materials;
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

public class ExpencesDB {
    @FXML
    private TableColumn<Expences, Integer> expence_id;
    @FXML
    private TableColumn<Expences, String> expence_type;
    @FXML
    private TableColumn<Expences, Integer> expence_amount;
    @FXML
    private TableView<Expences> expences;
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
    private TextField amount_for_add;
    @FXML
    private Button AddButton;
    @FXML
    private Button UsersDB;
    @FXML
    private Button ProvidersDB;
    @FXML
    private Button MaterialsDB;
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
        AddButton.setOnAction(event -> {
            if(type_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }
            if(amount_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }

            addExpences(type_for_add.getText(), amount_for_add.getText());
        });
        expences.setOnMouseClicked(event -> {
            reloadInfo();
        });
        DeleteButton.setOnAction(event -> {
            deleteExpences();
        });
    }

    void reloadInfo(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getExpences");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Expences> materials = (ArrayList<Expences>) object;
                    System.out.println(materials.size());
                    printExpences(materials);
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

    void printExpences(ArrayList<Expences> materials1){
        for(Expences m:materials1){
            System.out.println(m.toString());
        }
        ObservableList<Expences> observableList = FXCollections.observableArrayList(materials1);
        expences.setItems(observableList);
        expence_id.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_id"));
        expence_type.setCellValueFactory(new PropertyValueFactory<Expences,String>("expency_type"));
        expence_amount.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_amount"));
    }

    void deleteExpences(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = id_for_delete.getText();
            writer.write("deleteExpence");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addExpences(String type, String amount){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addExpences");writer.newLine();
            writer.write(type);writer.newLine();
            writer.write(amount);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
