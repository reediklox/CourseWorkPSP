package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
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

public class UsersDB implements OpenWindowInt {
    @FXML
    private TableColumn<Users, Integer> user_id;
    @FXML
    private TableColumn<Users, String> login;
    @FXML
    private TableColumn<Users, String> password;
    @FXML
    private TableColumn<Users, Boolean> active;
    @FXML
    private TableView<Users> users;
    @FXML
    private Label ResultLabel2;
    @FXML
    private Button DeleteButton;
    @FXML
    private TextField user_id_for_delete;
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
    private Button TaxDB;

    @FXML
    private ImageView CloseButton;

    @FXML
    void initialize(){
        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        users.setOnMouseClicked(event -> {
            reloadInfo();
        });

        DeleteButton.setOnAction(event -> {
            deleteUser();
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
            writer.write("getUsers");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Users> users = (ArrayList<Users>) object;
                    System.out.println(users.size());
                    printUsers(users);
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

    void printUsers(ArrayList<Users> users1){
        for(Users u:users1){
            System.out.println(u.toString());
        }
        ObservableList<Users> observableList = FXCollections.observableArrayList(users1);
        users.setItems(observableList);
        user_id.setCellValueFactory(new PropertyValueFactory<Users,Integer>("user_id"));
        login.setCellValueFactory(new PropertyValueFactory<Users,String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<Users,String>("password"));
        active.setCellValueFactory(new PropertyValueFactory<Users,Boolean>("active"));
    }

    void deleteUser(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = user_id_for_delete.getText();
            writer.write("deleteUser");writer.newLine();
            writer.write(id);
            writer.flush();
            user_id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
