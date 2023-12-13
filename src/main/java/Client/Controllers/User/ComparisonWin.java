package Client.Controllers.User;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.CostData;
import Server.Services.DataBase.DBHandlerCostData;
import Server.Services.DataBase.DBHandlerExpences;
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
import java.util.ArrayList;

public class ComparisonWin {
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
    private TableView<CostData> cost_data; // replace 'String' on Server.Entity.CostData
// End of the block to update

    @FXML
    private Label ResultLabel2;
    @FXML
    private Label CompResult;
    @FXML
    private Button ResultButton;
    @FXML
    private TextField CostIDField;
    @FXML
    private TextField CountField;

    @FXML
    private TableColumn<CostData, Integer> id_cost;
    @FXML
    private TableColumn<CostData, Integer> cost_cost;
    @FXML
    private TableColumn<CostData, String> date_cost;

    DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();
    DBHandlerCostData dbHandlerCostData = new DBHandlerCostData();
    DBHandlerExpences dbHandlerExpences = new DBHandlerExpences();

    @FXML
    void initialize() {
        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });
        cost_data.setOnMouseClicked(event -> {
            reloadInfoCost();
        });

        ResultButton.setOnAction(event -> {
            if(CostIDField.getText().isEmpty() || CountField.getText().isEmpty()){
                ResultLabel2.setText("Ошибка!");
                return;
            }

            double PLAN = dbHandlerExpences.getAVGExpences() / Integer.parseInt(CountField.getText());
            double FACT = dbHandlerCostData.getCost(Integer.parseInt(CostIDField.getText()));

            double result = (PLAN / FACT) * 100;
            CompResult.setText(result + " %");
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
        id_cost.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("cost_data_id"));
        cost_cost.setCellValueFactory(new PropertyValueFactory<CostData,Integer>("total_cost_price"));
        date_cost.setCellValueFactory(new PropertyValueFactory<CostData,String>("date_of_calc"));
    }
}
