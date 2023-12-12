package Server.Services.DataBase;

import Server.Config.ConstAttrition;
import Server.Entity.Attrition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerAttrition extends ConnectToDB{
    public Integer addAttrition(Attrition attritions){
        String insert = "INSERT INTO "+ ConstAttrition.TABLE + "(" +
                ConstAttrition.EQUIP_NAME + "," +
                ConstAttrition.EQUIP_LIFE+ "," +
                ConstAttrition.DATE_OF_EQUIP_PURCHASE + "," +
                ConstAttrition.EQUIP_COST+")"+" VALUES(?,?,?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, attritions.getEquip_name());
            pr.setInt(2, attritions.getEquip_life());
            pr.setString(3, attritions.getDate_of_equip_purchase());
            pr.setInt(4, attritions.getEquip_cost());

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Attrition> getAttritions(){
        ArrayList<Attrition> attritions = new ArrayList<>();
        String select = "SELECT * FROM " +ConstAttrition.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Attrition attrition = new Attrition();
                attrition.setAttrition_id(resultSet.getInt(ConstAttrition.ATTRITION_ID));
                attrition.setEquip_name(resultSet.getString(ConstAttrition.EQUIP_NAME));
                attrition.setEquip_life(resultSet.getInt(ConstAttrition.EQUIP_LIFE));
                attrition.setDate_of_equip_purchase(resultSet.getString(ConstAttrition.DATE_OF_EQUIP_PURCHASE));
                attrition.setEquip_cost(resultSet.getInt(ConstAttrition.EQUIP_COST));
                attritions.add(attrition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return attritions;
    }

    public void deleteAttrition(int id){
        String delete = "DELETE FROM " + ConstAttrition.TABLE + " WHERE " + ConstAttrition.ATTRITION_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Амортизация успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
