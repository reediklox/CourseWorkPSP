package Server.Services.DataBase;

import Server.Entity.Users;
import Server.Config.Configs;
import Server.Config.ConstUsers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
public class DBHandlerUsers extends ConnectToDB{

    public Integer addUser(Users user){
        String insert = "INSERT INTO "+ ConstUsers.TABLE + "(" + ConstUsers.LOGIN + "," + ConstUsers.PASSWORD+","+
                ConstUsers.ACTIVE+")"+" VALUES(?,?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, user.getLogin());
            pr.setString(2, user.getPassword());
            pr.setBoolean(3, user.getActive());

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Users> getUsers(){
        ArrayList<Users> users = new ArrayList<>();
        String select = "SELECT * FROM " +ConstUsers.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Users user = new Users();
                user.setUser_id(resultSet.getInt(ConstUsers.USER_ID));
                user.setLogin(resultSet.getString(ConstUsers.LOGIN));
                user.setPassword(resultSet.getString(ConstUsers.PASSWORD));
                user.setActive(resultSet.getBoolean(ConstUsers.ACTIVE));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void deleteUser(int id){
        String delete = "DELETE FROM " + ConstUsers.TABLE + " WHERE " + ConstUsers.USER_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Пользователь успешно удален!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Users getUserForAuth(String login){
        ResultSet resSet = null;
        Users users = new Users();
        String select = "SELECT * FROM "+ConstUsers.TABLE+" WHERE " +
                ConstUsers.LOGIN + "=?";
        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1,login);
            resSet = prSt.executeQuery();
            while (resSet.next()){
                users.setUser_id(resSet.getInt("user_id"));
                users.setLogin(resSet.getString("login"));
                users.setPassword(resSet.getString("password"));
                users.setActive(resSet.getBoolean("active"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

}
