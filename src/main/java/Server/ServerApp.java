package Server;

import Server.Controllers.RegController;
import Server.Services.DataBase.ConnectToDB;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args)throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(8081)){
            System.out.println("Сервер запущен...");
            ConnectToDB connectToDB=new ConnectToDB();
            connectToDB.getConnection();
            while (true)
                try{
                    Socket socket = serverSocket.accept();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try (BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));
                                 BufferedWriter writer = new BufferedWriter(
                                         new OutputStreamWriter(socket.getOutputStream()))) {
                                System.out.println("work");
                                String menu = reader.readLine(); // принимаем пункт меню
                                System.out.println(menu); // выводим пункт меню в консоль
                                switch (menu) {
                                    case "registration":
                                    {
                                        System.out.println("~~~Add new User~~~");
                                        String login = reader.readLine();
                                        String password = reader.readLine();
                                        System.out.println(login + " " + password);
                                        RegController regController = new RegController();
                                        writer.write(regController.registration(login, password));
                                        writer.flush();
                                    }
                                    case "getUsers":
                                    {

                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}