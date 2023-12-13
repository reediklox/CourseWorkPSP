package Client.Controllers.User;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.CostData;
import Server.Entity.Expences;
import Server.Entity.Tax;
import Server.Services.DataBase.DBHandlerCostData;
import Server.Services.DataBase.DBHandlerExpences;
import Server.Services.DataBase.DBHandlerTax;
import Server.Services.DataBase.DBHandlerUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Calcs {
    @FXML
    private TableView<CostData> cost_table;
    @FXML
    private TableColumn<CostData, Integer> cost_id;
    @FXML
    private TableColumn<CostData, Integer> cost;
    @FXML
    private TableColumn<CostData, String> date;
    @FXML
    private TableView<Tax> tax_table;
    @FXML
    private TableColumn<Tax, Integer> tax_id;
    @FXML
    private TableColumn<Tax, String> tax_type;
    @FXML
    private TableColumn<Tax, Integer> tax_percent;
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
    private Button CountButton;
    @FXML
    private Label ResultCost;
    @FXML
    private Label ResultLabel;
    @FXML
    private TextField IDInput;

    @FXML
    private TextField PrecentAmount;
    @FXML
    private Button CountButton1;
    @FXML
    private Label ResultCost1;
    @FXML
    private Label ResultLabel1;
    @FXML
    private TextField IDInput1;

    @FXML
    private TextField Count;
    @FXML
    private Button CountButton2;
    @FXML
    private Label ResultCost2;
    @FXML
    private Label ResultLabel2;

    @FXML
    private TextField InputField;
    DBHandlerExpences dbHandlerExpences = new DBHandlerExpences();
    DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();
    DBHandlerCostData dbHandlerCostData = new DBHandlerCostData();
    DBHandlerTax dbHandlerTax = new DBHandlerTax();

    @FXML
    void initialize(){
        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        tax_table.setOnMouseClicked(event -> {
            reloadInfoTax();
        });

        CountButton2.setOnAction(event -> {
            Double dohod = Double.parseDouble(Count.getText()) * Double.parseDouble(ResultCost1.getText());
            Integer total = dbHandlerExpences.getTotalExpences();
            Double percent = dbHandlerTax.getPercent(Integer.parseInt(IDInput1.getText()));

            if (percent == 0.0){
                percent = 0.2;
            }

            ResultCost2.setText("" + ((dohod - total) * percent));
        });

        CountButton1.setOnAction(event -> {
            if (IDInput.getText().isEmpty() || PrecentAmount.getText().isEmpty()){
                ResultLabel1.setText("воу воу, данных нет");
                return;
            }

            Integer gettedCost = dbHandlerCostData.getCost(Integer.parseInt(IDInput.getText()));

            double total = gettedCost + (gettedCost * (Double.parseDouble(PrecentAmount.getText()) / 100));

            ResultCost1.setText("" + total);

        });

        cost_table.setOnMouseClicked(event -> {
            reloadInfoCost();
        });
        CountButton.setOnAction(event -> {
            if(InputField.getText().isEmpty()){
                ResultLabel.setText("воу воу, нет данных");
                return;
            }
            Integer total = dbHandlerExpences.getTotalExpences();
            String count = ""+(total / Integer.parseInt(InputField.getText()));
            ResultCost.setText(count);
            addCost(count, new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()));
        });
        DataLists.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(DataLists, "/Client/Lists.fxml");
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
        cost_table.setItems(observableList);
        cost_id.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("cost_data_id"));
        cost.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("total_cost_price"));
        date.setCellValueFactory(new PropertyValueFactory<CostData,String>("date_of_calc"));
    }

    void reloadInfoTax(){
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
        tax_table.setItems(observableList);
        tax_id.setCellValueFactory(new PropertyValueFactory<Tax,Integer>("tax_id"));
        tax_type.setCellValueFactory(new PropertyValueFactory<Tax,String>("tax_type"));
        tax_percent.setCellValueFactory(new PropertyValueFactory<Tax,Integer>("tax_percent"));
    }

    void addCost(String price, String date){
        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("addCost");writer.newLine();
            writer.write(price);writer.newLine();
            writer.write(date);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
