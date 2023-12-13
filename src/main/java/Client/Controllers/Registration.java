package Client.Controllers;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Registration implements OpenWindowInt {
    double x, y = 0;

    @FXML
    private javafx.scene.control.TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private javafx.scene.control.Button RegistrationButton;

    @FXML
    private javafx.scene.control.Button BackButton;

    @FXML
    private Label FailLabel;

    @FXML
    private ImageView CloseButton;

    @FXML
    void initialize(){
        CloseButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) CloseButton.getScene().getWindow();
            stage.close();
        });
        BackButton.setOnAction(evt -> {
            try {
                OpenWindowInt.OpenWindow(BackButton, "/Client/Auth.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        RegistrationButton.setOnAction(event -> {
            sendRegData();
        });
    }

    /*private void OpenAuthWondow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource("Auth.fxml"));
        Stage stage1 = (Stage) BackButton.getScene().getWindow();
        stage1.close();
        Scene scene = new Scene(fxmlLoader.load(), 777, 608);
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(evt -> {
            x = evt.getSceneX();
            y = evt.getSceneY();
        });
        root.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });

        stage.setScene(scene);
        stage.show();
    }*/

    private void sendRegData(){

        String login, password;
        login = LoginField.getText();
        password = PasswordField.getText();

        if(login.isEmpty()){
            System.out.println("поле с логином пусто!");
            return;
        }

        if(password.isEmpty()){
            System.out.println("поле с паролем пусто!");
            return;
        }

        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("registration");writer.newLine();
            writer.write(login);writer.newLine();
            writer.write(password); writer.newLine();
            writer.flush();
            String response = reader.readLine();
            System.out.println(response);
            if(response.equals("success")){
                OpenWindowInt.OpenWindow(RegistrationButton, "/Client/Auth.fxml");
            }else{
                System.out.println("Ошибка регистрации");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
