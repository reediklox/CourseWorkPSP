package Server.Services.DataBase;

import Server.Config.ConstExpences;
import Server.Entity.Expences;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerExpences extends ConnectToDB{
    public Integer addExpence(Expences expence){
        String insert = "INSERT INTO "+ ConstExpences.TABLE + "(" + ConstExpences.EXPENCY_TYPE + "," +
                ConstExpences.EXPENCY_AMOUNT+")"+" VALUES(?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, expence.getExpency_type());
            pr.setInt(2, expence.getExpency_amount());
            pr.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Expences> getExpences(){
        ArrayList<Expences> expences = new ArrayList<>();
        String select = "SELECT * FROM " +ConstExpences.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Expences expence = new Expences();
                expence.setExpency_id(resultSet.getInt(ConstExpences.EXPENCY_ID));
                expence.setExpency_type(resultSet.getString(ConstExpences.EXPENCY_TYPE));
                expence.setExpency_amount(resultSet.getInt(ConstExpences.EXPENCY_AMOUNT));
                expences.add(expence);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return expences;
    }

    public void deleteExpence(int id){
        String delete = "DELETE FROM " + ConstExpences.TABLE + " WHERE " + ConstExpences.EXPENCY_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Затрата успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
