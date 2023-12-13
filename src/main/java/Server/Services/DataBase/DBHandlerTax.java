package Server.Services.DataBase;

import Server.Config.ConstTax;
import Server.Entity.Tax;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerTax extends ConnectToDB{
    public Integer addTax(Tax tax){
    String insert = "INSERT INTO "+ ConstTax.TABLE + "(" + ConstTax.TAX_TYPE + "," +
            ConstTax.TAX_PERCENT+")"+" VALUES(?,?)";

    try {
        PreparedStatement pr = getConnection().prepareStatement(insert);

        pr.setString(1, tax.getTax_type());
        pr.setInt(2, tax.getTax_percent());
        pr.executeUpdate();
        return 1;
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }

    return 0;
}

    public ArrayList<Tax> getTaxs(){
        ArrayList<Tax> taxes = new ArrayList<>();
        String select = "SELECT * FROM " +ConstTax.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Tax tax = new Tax();
                tax.setTax_id(resultSet.getInt(ConstTax.TAX_ID));
                tax.setTax_type(resultSet.getString(ConstTax.TAX_TYPE));
                tax.setTax_percent(resultSet.getInt(ConstTax.TAX_PERCENT));
                taxes.add(tax);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return taxes;
    }

    public Double getPercent(int id){
        double res = 0;
        String select = "SELECT * FROM " + ConstTax.TABLE + " WHERE " + ConstTax.TAX_ID + "='" + id + "'; ";

        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Tax tax = new Tax();
                tax.setTax_id(resultSet.getInt(ConstTax.TAX_ID));
                tax.setTax_type(resultSet.getString(ConstTax.TAX_TYPE));
                tax.setTax_percent(resultSet.getInt(ConstTax.TAX_PERCENT));
                res = (double) tax.getTax_percent() / 100;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    public void deleteTax(int id){
        String delete = "DELETE FROM " + ConstTax.TABLE + " WHERE " + ConstTax.TAX_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Налог успешно удален!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
