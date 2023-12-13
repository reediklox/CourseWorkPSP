package Server.Controllers;

import Server.Entity.Users;
import Server.Services.DataBase.DBHandlerUsers;
import Server.Services.DeEnCode;

public class AuthController {
    public Users authentication(String login, String password) {
        DBHandlerUsers dbHandler = new DBHandlerUsers();
        Users user = new Users();
        user = dbHandler.getUserForAuth(login);
        if(user != null){
            if(password.equals(DeEnCode.decode(user.getPassword()))){
                System.out.println("пароль верный");
                return user;
            }else{
                System.out.println("пароль не верный");
                return null;
            }
        }else{
            System.out.println("пароль или логин не верный");
            return null;
        }
    }
}
