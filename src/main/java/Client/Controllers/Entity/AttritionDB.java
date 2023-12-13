package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Attrition;
import Server.Entity.Companies;
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

public class AttritionDB {
    @FXML
    private TableColumn<Attrition, Integer> a_id;
    @FXML
    private TableColumn<Attrition, String> a_name;
    @FXML
    private TableColumn<Attrition, Integer> a_life;
    @FXML
    private TableColumn<Attrition, String> a_date;
    @FXML
    private TableColumn<Attrition, Integer> a_price;
    @FXML
    private TableView<Attrition> attrition;
    @FXML
    private Label ResultLabel2;
    @FXML
    private Button DeleteButton;
    @FXML
    private TextField id_for_delete;
    @FXML
    private TextField id_for_add;
    @FXML
    private TextField name_for_add;
    @FXML
    private TextField life_for_add;
    @FXML
    private TextField date_for_add;
    @FXML
    private TextField price_for_add;
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
        TaxDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(TaxDB, "/Client/TaxDB.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        AddButton.setOnAction(event -> {
            if(name_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }
            if(life_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }if(date_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }if(price_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }

            addAttrition(name_for_add.getText(), life_for_add.getText(), date_for_add.getText(), price_for_add.getText());
        });
        attrition.setOnMouseClicked(event -> {
            reloadInfo();
        });
        DeleteButton.setOnAction(event -> {
            if(id_for_delete.getText().isEmpty())
            {
                ResultLabel2.setText("воу воу, нет данных");
            }
            deleteAttrition();
        });
    }

    void reloadInfo(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getAttrition");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Attrition> materials = (ArrayList<Attrition>) object;
                    System.out.println(materials.size());
                    printAttrition(materials);
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

    void printAttrition(ArrayList<Attrition> materials1){
        for(Attrition m:materials1){
            System.out.println(m.toString());
        }
        ObservableList<Attrition> observableList = FXCollections.observableArrayList(materials1);
        attrition.setItems(observableList);
        a_id.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("attrition_id"));
        a_name.setCellValueFactory(new PropertyValueFactory<Attrition,String>("equip_name"));
        a_life.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("equip_life"));
        a_date.setCellValueFactory(new PropertyValueFactory<Attrition,String>("date_of_equip_purchase"));
        a_price.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("equip_cost"));
    }

    void deleteAttrition(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = id_for_delete.getText();
            writer.write("deleteAttrition");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addAttrition(String name, String life, String date, String pri){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addAttrition");writer.newLine();
            writer.write(name);writer.newLine();
            writer.write(life);writer.newLine();
            writer.write(date);writer.newLine();
            writer.write(pri);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
