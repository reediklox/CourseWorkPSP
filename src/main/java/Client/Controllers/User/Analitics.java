package Client.Controllers.User;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Companies;
import Server.Entity.Expences;
import Server.Entity.Providers;
import Server.Services.DataBase.DBHandlerCompanies;
import Server.Services.DataBase.DBHandlerExpences;
import Server.Services.DataBase.DBHandlerProviders;
import Server.Services.DataBase.DBHandlerUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Analitics {
    @FXML
    private TableColumn<Companies, Integer> com_id;
    @FXML
    private TableColumn<Companies, String> com_name;
    @FXML
    private TableColumn<Companies, Integer> com_cost;
    @FXML
    private TableColumn<Expences, Integer> e_id;
    @FXML
    private TableColumn<Expences, String> e_type;
    @FXML
    private TableColumn<Expences, Integer> e_amount;
    @FXML
    private TableColumn<Providers, Integer> p_id;
    @FXML
    private TableColumn<Providers, String> p_num;
    @FXML
    private TableColumn<Providers, Integer> p_mat;
    @FXML
    private TableColumn<Providers, Integer> p_price;
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
    private TableView<Companies> companies; // replace 'String' on Server.Entity.Companies
    @FXML
    private TableView<Providers> providers; // replace 'String' on Server.Entity.Providers
    @FXML
    private TableView<Expences> expences; // replace 'String' on Server.Entity.Expences
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
    private Button com_a;
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
    DBHandlerCompanies dbHandlerCompanies = new DBHandlerCompanies();
    DBHandlerProviders dbHandlerProviders = new DBHandlerProviders();
    DBHandlerExpences dbHandlerExpences = new DBHandlerExpences();

    @FXML
    void initialize() {
        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        providers.setOnMouseClicked(event -> {
            reloadInfoProviders();
        });

        companies.setOnMouseClicked(event -> {
            reloadInfoCom();
        });
        com_a.setOnAction(event -> {
            BestPriceC.setText("" + dbHandlerCompanies.getBestPrice());
            MeanPriceC.setText("" + dbHandlerCompanies.getAvgPrice());
            WorstPriceC.setText("" + dbHandlerCompanies.getWorstPrice());
        });

        expences.setOnMouseClicked(event -> {
            reloadInfoExp();
        });
        ResultProviders.setOnAction(event -> {
            if(material_name.getText().isEmpty()){
                ResultLabel2.setText("НЕт данНЫх...");
                return;
            }
            BestPriceP.setText("" + dbHandlerProviders.getBestProviders(Integer.parseInt(material_name.getText())));
            MeanPriceP.setText("" + dbHandlerProviders.getAVGProviders(Integer.parseInt(material_name.getText())));
            WorstPriceP.setText("" + dbHandlerProviders.getWorstProviders(Integer.parseInt(material_name.getText())));
        });

        ResultMarj.setOnAction(event -> {
            if(ProductCount.getText().isEmpty() || ProductPrice.getText().isEmpty()){
                ResultLabel2.setText("НЕт данНЫх...");
                return;
            }

            int pCount = Integer.parseInt(ProductCount.getText());
            int pPrice = Integer.parseInt(ProductPrice.getText());

            double vir = pCount * pPrice;
            double exp = dbHandlerExpences.getTotalExpences();

            ResultMarjLabel.setText(((vir - exp) / vir * 100.0) + " %");
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
        p_price.setCellValueFactory(new PropertyValueFactory<Providers,Integer>("material_cost_per_piece"));
    }

    void reloadInfoExp(){
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
        e_id.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_id"));
        e_type.setCellValueFactory(new PropertyValueFactory<Expences,String>("expency_type"));
        e_amount.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_amount"));
    }
}
