package Server.Controllers;

import Server.Entity.Users;
import Server.Services.DataBase.DBHandlerUsers;

public class RegController {
    public String registration(String login, String password){
        Users user = new Users(login, password);
        System.out.println("Логин: "+user.getLogin());
        System.out.println("Пароль: "+user.getPassword());
        DBHandlerUsers dbHandler = new DBHandlerUsers();
        if(dbHandler.addUser(user) == 1){
            return "success";
        }else{
            return "not_success";
        }
    }
}
