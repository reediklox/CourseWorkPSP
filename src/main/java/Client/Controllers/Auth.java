package Client.Controllers;

import Client.Config.ConnectInfo;
import Client.Controllers.Intarfaces.OpenWindowInt;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Auth implements OpenWindowInt {
    double x, y = 0;
    @FXML
    private javafx.scene.control.TextField LoginInputField;

    @FXML
    private PasswordField PasswordInputField;

    @FXML
    private javafx.scene.control.Button EnterButton;

    @FXML
    private javafx.scene.control.Button CreateAccountButton;

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
        CreateAccountButton.setOnAction(evt -> {
            try {
                OpenWindowInt.OpenWindow(CreateAccountButton, "/Client/Registration.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        EnterButton.setOnAction(event -> {
            try {
                sendAuth();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*private void OpenWindow(Button button, String window) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientApp.class.getResource(window));
        Stage stage1 = (Stage) button.getScene().getWindow();
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

    private void sendAuth() throws IOException {
        String login = LoginInputField.getText();
        String pass = PasswordInputField.getText();

        if(login.isEmpty() && pass.isEmpty()){
            FailLabel.setText("Вы не заполнили поля для ввода!");
        }
        else if(login.isEmpty()){
            FailLabel.setText("Вы не заполнили поле \"Логин\"!");
        }
        else if (pass.isEmpty()) {
            FailLabel.setText("Вы не заполнили поле \"Пароль\"");
        }

        if (login.equals("ADMIN") && pass.equals("ADMIN")){
            OpenWindowInt.OpenWindow(EnterButton, "/Client/AdminWindow.fxml");
            return;
        }

        try(Socket clientSocket = new Socket(ConnectInfo.IP,ConnectInfo.PORT);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            writer.write("auth");writer.newLine();
            writer.write(login);writer.newLine();
            writer.write(pass); writer.newLine();
            writer.flush();
            String flag = reader.readLine();
            if(flag == null){
                FailLabel.setText("Неверный логин или пароль!");
                return;
            }
            System.out.println(flag);
            if(flag.equals("успешно")){
                String acc_login = reader.readLine();
                System.out.println(acc_login);
                OpenWindowInt.OpenWindow(EnterButton, "/Client/UserWindow.fxml");
            }else{
                System.out.println("Что-то пошло не так...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
