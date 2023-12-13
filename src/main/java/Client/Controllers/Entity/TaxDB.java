package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Expences;
import Server.Entity.Tax;
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

public class TaxDB {
    @FXML
    private TableColumn<Tax, Integer> t_id;
    @FXML
    private TableColumn<Tax, String> t_type;
    @FXML
    private TableColumn<Tax, Integer> t_per;
    @FXML
    private TableView<Tax> tax;
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
        AddButton.setOnAction(event -> {
            if(type_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }
            if(percent_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }

            addTax(type_for_add.getText(), percent_for_add.getText());
        });
        tax.setOnMouseClicked(event -> {
            reloadInfo();
        });
        DeleteButton.setOnAction(event -> {
            deleteTax();
        });
    }

    void reloadInfo(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getTax");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Tax> materials = (ArrayList<Tax>) object;
                    System.out.println(materials.size());
                    printTax(materials);
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

    void printTax(ArrayList<Tax> materials1){
        for(Tax m:materials1){
            System.out.println(m.toString());
        }
        ObservableList<Tax> observableList = FXCollections.observableArrayList(materials1);
        tax.setItems(observableList);
        t_id.setCellValueFactory(new PropertyValueFactory<Tax,Integer>("tax_id"));
        t_type.setCellValueFactory(new PropertyValueFactory<Tax,String>("tax_type"));
        t_per.setCellValueFactory(new PropertyValueFactory<Tax,Integer>("tax_percent"));
    }

    void deleteTax(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = id_for_delete.getText();
            writer.write("deleteTax");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addTax(String type, String per){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addTax");writer.newLine();
            writer.write(type);writer.newLine();
            writer.write(per);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
