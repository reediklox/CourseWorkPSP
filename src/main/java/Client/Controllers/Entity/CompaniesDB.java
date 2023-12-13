package Client.Controllers.Entity;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Companies;
import Server.Entity.Expences;
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

public class CompaniesDB {
    @FXML
    private TableColumn<Companies, Integer> company_id;
    @FXML
    private TableColumn<Companies, String> company_name;
    @FXML
    private TableColumn<Companies, String> company_addr;
    @FXML
    private TableColumn<Companies, String> company_num;
    @FXML
    private TableColumn<Companies, Integer> price;
    @FXML
    private TableView<Companies> companies;
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
    private TextField addr_for_add;
    @FXML
    private TextField num_for_add;
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
        ExpencesDB.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(ExpencesDB, "/Client/ExpencesDB.fxml");
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
            if(name_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }
            if(addr_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }if(num_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }if(price_for_add.getText().isEmpty()){
                ResultLabel2.setText("Воу воу, нет данных");
                return;
            }

            addCompanies(name_for_add.getText(), addr_for_add.getText(), num_for_add.getText(), price_for_add.getText());
        });
        companies.setOnMouseClicked(event -> {
            reloadInfo();
        });
        DeleteButton.setOnAction(event -> {
            if(id_for_delete.getText().isEmpty())
            {
                ResultLabel2.setText("воу воу, нет данных");
            }
            deleteCompanies();
        });
    }

    void reloadInfo(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getCompanies");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<Companies> materials = (ArrayList<Companies>) object;
                    System.out.println(materials.size());
                    printCompanies(materials);
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

    void printCompanies(ArrayList<Companies> materials1){
        for(Companies m:materials1){
            System.out.println(m.toString());
        }
        ObservableList<Companies> observableList = FXCollections.observableArrayList(materials1);
        companies.setItems(observableList);
        company_id.setCellValueFactory(new PropertyValueFactory<Companies,Integer>("company_id"));
        company_name.setCellValueFactory(new PropertyValueFactory<Companies,String>("company_name"));
        company_addr.setCellValueFactory(new PropertyValueFactory<Companies,String>("company_address"));
        company_num.setCellValueFactory(new PropertyValueFactory<Companies,String>("company_mobile_number"));
        price.setCellValueFactory(new PropertyValueFactory<Companies,Integer>("offered_price"));
    }

    void deleteCompanies(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            String id = id_for_delete.getText();
            writer.write("deleteCompany");writer.newLine();
            writer.write(id);
            writer.flush();
            id_for_delete.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addCompanies(String name, String addr, String num, String pri){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addCompany");writer.newLine();
            writer.write(name);writer.newLine();
            writer.write(addr);writer.newLine();
            writer.write(num);writer.newLine();
            writer.write(pri);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
