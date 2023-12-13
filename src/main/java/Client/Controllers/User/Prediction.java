package Client.Controllers.User;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import Server.Entity.Expences;
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
import org.w3c.dom.ranges.Range;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Prediction {
    @FXML
    private TableColumn<Expences, Integer> id;
    @FXML
    private TableColumn<Expences, String> type;
    @FXML
    private TableColumn<Expences, Integer> amount;
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
    private Button ResultButton;

    @FXML
    private Label ResultLabel2;
    @FXML
    private Label PredictResult;

    @FXML
    private Text UserLogin;

    @FXML
    private ImageView CloseButton;

// Block to update
    @FXML
    private TableView<Expences> expences; // replace 'String' on Server.Entity.Excences
// End of the block to update
    @FXML
    private CheckBox Winter;
    @FXML
    private CheckBox Spring;
    @FXML
    private CheckBox Summer;
    @FXML
    private CheckBox Autumn;
    DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();
    DBHandlerExpences dbHandlerExpences = new DBHandlerExpences();

    @FXML
    void initialize(){

        UserLogin.setText(dbHandlerUsers.getUserLogin());

        CloseButton.setOnMouseClicked(event -> {
            dbHandlerUsers.setActivityFalse();
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });

        expences.setOnMouseClicked(event -> {
            reloadInfoExp();
        });

        ResultButton.setOnAction(event -> {
            if (!Spring.isSelected() && !Summer.isSelected() && !Autumn.isSelected() && !Winter.isSelected()){
                ResultLabel2.setText("Ошибка!");
                return;
            }
            double perc = 0;
            if(Winter.isSelected()){
                perc = RandomGenerator.getDefault().nextDouble(0.2, 0.3);
            }else{
                perc = RandomGenerator.getDefault().nextDouble(0.01, 0.15);
            }

            double CU = dbHandlerExpences.getCUExpences();

            double res = CU + CU * perc;
            PredictResult.setText("" + res);
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
        Comparison.setOnAction(event -> {
            try {
                OpenWindowInt.OpenWindow(Comparison, "/Client/ComparisonWin.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Winter.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Spring.setOnAction(event -> {
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Summer.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
            if (Autumn.isSelected()){
                Autumn.setSelected(false);
            }
        });

        Autumn.setOnAction(event -> {
            if (Spring.isSelected()){
                Spring.setSelected(false);
            }
            if (Summer.isSelected()){
                Summer.setSelected(false);
            }
            if (Winter.isSelected()){
                Winter.setSelected(false);
            }
        });
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
        id.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_id"));
        type.setCellValueFactory(new PropertyValueFactory<Expences,String>("expency_type"));
        amount.setCellValueFactory(new PropertyValueFactory<Expences,Integer>("expency_amount"));
    }
}
