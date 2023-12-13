package Client.Controllers.User;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Attrition;
import Server.Entity.Companies;
import Server.Entity.CostData;
import Server.Entity.Providers;
import Server.Services.DataBase.DBHandlerUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Lists {
    @FXML
    private TableColumn<Providers, Integer> p_id;
    @FXML
    private TableColumn<Providers, String> p_num;
    @FXML
    private TableColumn<Providers, Integer> p_mat;
    @FXML
    private TableColumn<Providers, Integer> p_count;
    @FXML
    private TableColumn<Providers, Integer> p_price;
    @FXML
    private TableColumn<CostData, Integer> c_id;
    @FXML
    private TableColumn<CostData, Integer> c_cost;
    @FXML
    private TableColumn<CostData, String> c_date;
    @FXML
    private TableColumn<Companies, Integer> com_id;
    @FXML
    private TableColumn<Companies, String> com_name;
    @FXML
    private TableColumn<Companies, Integer> com_cost;
    @FXML
    private TableColumn<Attrition, Integer> a_id;
    @FXML
    private TableColumn<Attrition, String> a_name;
    @FXML
    private TableColumn<Attrition, Integer> a_life;
    @FXML
    private TableColumn<Attrition, String> a_date;
    @FXML
    private TableColumn<Attrition, Integer> a_cost;
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

    @FXML
    private Label ResultLabel1;
    @FXML
    private Label ResultLabel2;


// Block to update
    @FXML
    private TableView<Providers> providers; // replace 'String' on Server.Entity.Providers
    @FXML
    private TableView<CostData> cost_data; // replace 'String' on Server.Entity.CostData
    @FXML
    private TableView<Companies> companies; // replace 'String' on Server.Entity.Companies
    @FXML
    private TableView<Attrition> attritions; // replace 'String' on Server.Entity.Attritions
// End of the block to update
    DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();

    @FXML
    void initialize(){
        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        providers.setOnMouseClicked(event -> {
            reloadInfoProviders();
        });

        cost_data.setOnMouseClicked(event -> {
            reloadInfoCost();
        });

        companies.setOnMouseClicked(event -> {
            reloadInfoCom();
        });

        attritions.setOnMouseClicked(event -> {
            reloadInfoAttr();
        });

        Calcs.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Calcs, "/Client/Calcs.fxml");
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

    void reloadInfoAttr(){
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
        attritions.setItems(observableList);
        a_id.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("attrition_id"));
        a_name.setCellValueFactory(new PropertyValueFactory<Attrition,String>("equip_name"));
        a_life.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("equip_life"));
        a_date.setCellValueFactory(new PropertyValueFactory<Attrition,String>("date_of_equip_purchase"));
        a_cost.setCellValueFactory(new PropertyValueFactory<Attrition,Integer>("equip_cost"));
    }

    void reloadInfoCom(){
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
        com_id.setCellValueFactory(new PropertyValueFactory<Companies,Integer>("company_id"));
        com_name.setCellValueFactory(new PropertyValueFactory<Companies,String>("company_name"));
        com_cost.setCellValueFactory(new PropertyValueFactory<Companies,Integer>("offered_price"));
    }

    void reloadInfoCost(){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("getCost");writer.newLine();
            writer.flush();
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                try {
                    Object object = objectInputStream.readObject();
                    ArrayList<CostData> materials = (ArrayList<CostData>) object;
                    System.out.println(materials.size());
                    printCost(materials);
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

    void printCost(ArrayList<CostData> materials1){
        for(CostData m:materials1){
            System.out.println(m.toString());
        }
        ObservableList<CostData> observableList = FXCollections.observableArrayList(materials1);
        cost_data.setItems(observableList);
        c_id.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("cost_data_id"));
        c_cost.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("total_cost_price"));
        c_date.setCellValueFactory(new PropertyValueFactory<CostData,String>("date_of_calc"));
    }

    void reloadInfoProviders(){
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
        p_id.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("provider_id"));
        p_num.setCellValueFactory(new PropertyValueFactory<Providers,String>("mobile_number"));
        p_mat.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_id"));
        p_count.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_count"));
        p_price.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_cost_per_piece"));
    }
}
