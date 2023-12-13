package Server;

import Server.Controllers.AuthController;
import Server.Controllers.RegController;
import Server.Controllers.UserController;
import Server.Entity.*;
import Server.Services.DataBase.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApp {
    public static void main(String[] args)throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(8001)){
            System.out.println("Сервер запущен...");
            ConnectToDB connectToDB = new ConnectToDB();
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
                                String menu = reader.readLine();
                                switch (menu) {
                                    case "registration":
                                    {
                                        System.out.println("~~~Добавление нового пользователя~~~");
                                        String login = reader.readLine();
                                        String password = reader.readLine();
                                        System.out.println(login + " " + password);
                                        RegController regController = new RegController();
                                        writer.write(regController.registration(login, password));
                                        writer.flush();
                                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                    } break;
                                    case "auth":
                                    {
                                        System.out.println("~~~Авторизация пользователя~~~");

                                        String login = reader.readLine();
                                        String password = reader.readLine();

                                        System.out.println(login+ " " +password);

                                        AuthController auth = new AuthController();
                                        Users user = auth.authentication(login, password);

                                        if(user==null){
                                            writer.write("безуспешно");writer.newLine();
                                            writer.flush();
                                        }else {
                                            writer.write("успешно");writer.newLine();
                                            writer.write(user.getLogin());writer.newLine();
                                            DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();
                                            dbHandlerUsers.setActivityTrue(user.getUser_id());
                                            writer.flush();
                                        }
                                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                                    }break;
                                    case "getUsers":
                                    {
                                        System.out.println("~~~Вывод пользователей~~~");
                                        ArrayList<Users> users = new ArrayList<Users>();
                                        DBHandlerUsers db = new DBHandlerUsers();
                                        users = db.getUsers();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(users);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "deleteUser": {
                                        System.out.println("~~~Удаление пользователя~~~");
                                        String id = reader.readLine();
                                        DBHandlerUsers dbHandlerUsers = new DBHandlerUsers();
                                        dbHandlerUsers.deleteUser(Integer.parseInt(id));
                                    }break;
                                    case "getProviders":{
                                        System.out.println("~~~Вывод поставщиков~~~");
                                        ArrayList<Providers> providers = new ArrayList<Providers>();
                                        DBHandlerProviders db = new DBHandlerProviders();
                                        providers = db.getProviders();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(providers);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "deleteProvider":{
                                        System.out.println("~~~Удаление поставщика~~~");
                                        String id = reader.readLine();
                                        DBHandlerProviders dbHandlerUsers = new DBHandlerProviders();
                                        dbHandlerUsers.deleteProvider(Integer.parseInt(id));
                                    }break;
                                    case "addProvider": {
                                        System.out.println("~~~Добавление поставщика~~~");
                                        String number = reader.readLine();
                                        Integer mId = Integer.parseInt(reader.readLine());
                                        Integer count = Integer.parseInt(reader.readLine());
                                        Integer cost = Integer.parseInt(reader.readLine());
                                        System.out.println(number + " " + mId + " " + count + " " + cost);
                                        DBHandlerProviders dbHandlerProviders = new DBHandlerProviders();
                                        Providers providers = new Providers(number, mId, count, cost);
                                        dbHandlerProviders.addProvider(providers);
                                    }break;
                                    case "getMaterials":{
                                        System.out.println("~~~Вывод сырья~~~");
                                        ArrayList<Materials> materials = new ArrayList<Materials>();
                                        DBHandlerMaterials db = new DBHandlerMaterials();
                                        materials = db.getMaterials();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(materials);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addMaterial":{
                                        System.out.println("~~~Добавление сырья~~~");
                                        String name = reader.readLine();
                                        System.out.println(name);
                                        DBHandlerMaterials dbHandlerMaterials = new DBHandlerMaterials();
                                        Materials materials = new Materials(name);
                                        dbHandlerMaterials.addMaterial(materials);
                                    }break;
                                    case "deleteMaterial":{
                                        System.out.println("~~~Удаление сырья~~~");
                                        String id = reader.readLine();
                                        DBHandlerMaterials dbHandlerMaterials = new DBHandlerMaterials();
                                        dbHandlerMaterials.deleteMaterial(Integer.parseInt(id));
                                    }break;
                                    case "getExpences": {
                                        System.out.println("~~~Вывод затрат~~~");
                                        ArrayList<Expences> expences = new ArrayList<Expences>();
                                        DBHandlerExpences db = new DBHandlerExpences();
                                        expences = db.getExpences();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(expences);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addExpence":{
                                        System.out.println("~~~Добавление затраты~~~");
                                        String type = reader.readLine();
                                        Integer amount = Integer.parseInt(reader.readLine());
                                        System.out.println(type + " " + amount);
                                        DBHandlerExpences dbHandlerExpences = new DBHandlerExpences();
                                        Expences expences = new Expences(type, amount);
                                        dbHandlerExpences.addExpence(expences);
                                    }break;
                                    case "deleteExpence":{
                                        System.out.println("~~~Удаление затраты~~~");
                                        String id = reader.readLine();
                                        DBHandlerExpences dbHandlerExpences= new DBHandlerExpences();
                                        dbHandlerExpences.deleteExpence(Integer.parseInt(id));
                                    }break;
                                    case "getCompanies": {
                                        System.out.println("~~~Вывод компаний~~~");
                                        ArrayList<Companies> companies = new ArrayList<Companies>();
                                        DBHandlerCompanies db = new DBHandlerCompanies();
                                        companies = db.getCompanies();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(companies);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addCompany":{
                                        System.out.println("~~~Добавление компании~~~");
                                        String name = reader.readLine();
                                        String addr = reader.readLine();
                                        String num = reader.readLine();
                                        Integer price = Integer.parseInt(reader.readLine());
                                        System.out.println(name + " " + addr + " " + num + " " + price);
                                        DBHandlerCompanies dbHandlerCompanies = new DBHandlerCompanies();
                                        Companies companies = new Companies(name, addr, num, price);
                                        dbHandlerCompanies.addCompany(companies);
                                    }break;
                                    case "deleteCompany":{
                                        System.out.println("~~~Удаление компании~~~");
                                        String id = reader.readLine();
                                        DBHandlerCompanies dbHandlerCompanies= new DBHandlerCompanies();
                                        dbHandlerCompanies.deleteCompany(Integer.parseInt(id));
                                    }break;
                                    case "getAttrition":{
                                        System.out.println("~~~Вывод амортизаций~~~");
                                        ArrayList<Attrition> attritions = new ArrayList<Attrition>();
                                        DBHandlerAttrition db = new DBHandlerAttrition();
                                        attritions = db.getAttritions();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(attritions);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addAttrition": {
                                        System.out.println("~~~Добавление амортизации~~~");
                                        String name = reader.readLine();
                                        Integer life = Integer.parseInt(reader.readLine());
                                        String date = reader.readLine();
                                        Integer cost = Integer.parseInt(reader.readLine());
                                        System.out.println(name + " " + life + " " + date + " " + cost);
                                        DBHandlerAttrition dbHandlerAttrition = new DBHandlerAttrition();
                                        Attrition attrition = new Attrition(name, life, date, cost);
                                        dbHandlerAttrition.addAttrition(attrition);
                                    }break;
                                    case "deleteAttrition":{
                                        System.out.println("~~~Удаление амортизации~~~");
                                        String id = reader.readLine();
                                        DBHandlerAttrition dbHandlerAttrition= new DBHandlerAttrition();
                                        dbHandlerAttrition.deleteAttrition(Integer.parseInt(id));
                                    }break;
                                    case "getTax":{
                                        System.out.println("~~~Вывод налогов~~~");
                                        ArrayList<Tax> taxes = new ArrayList<Tax>();
                                        DBHandlerTax db = new DBHandlerTax();
                                        taxes = db.getTaxs();
                                        try {
                                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                            objectOutputStream.writeObject(taxes);
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }break;
                                    case "addTax":{
                                        System.out.println("~~~Добавление налога~~~");
                                        String type = reader.readLine();
                                        Integer percent = Integer.parseInt(reader.readLine());
                                        System.out.println(type + " " + percent);
                                        DBHandlerTax dbHandlerTax = new DBHandlerTax();
                                        Tax tax = new Tax(type, percent);
                                        dbHandlerTax.addTax(tax);
                                    }break;
                                    case "deleteTax":{
                                        System.out.println("~~~Удаление налога~~~");
                                        String id = reader.readLine();
                                        DBHandlerTax dbHandlerTax= new DBHandlerTax();
                                        dbHandlerTax.deleteTax(Integer.parseInt(id));
                                    }break;

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