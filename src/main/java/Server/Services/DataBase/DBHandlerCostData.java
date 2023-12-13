package Server.Services.DataBase;

import Server.Config.ConstCostData;
import Server.Entity.CostData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerCostData extends ConnectToDB{
    public Integer addCost(CostData cost){
        String insert = "INSERT INTO "+ ConstCostData.TABLE + "(" + ConstCostData.TOTAL_COST_PRICE + "," +
                ConstCostData.DATE_OF_CALC+")"+" VALUES(?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setDouble(1, cost.getTotal_cost_price());
            pr.setString(2, cost.getDate_of_calc());
            pr.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<CostData> getCosts(){
        ArrayList<CostData> costs = new ArrayList<>();
        String select = "SELECT * FROM " +ConstCostData.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                CostData cost = new CostData();
                cost.setCost_data_id(resultSet.getInt(ConstCostData.COST_DATA_ID));
                cost.setTotal_cost_price(resultSet.getInt(ConstCostData.TOTAL_COST_PRICE));
                cost.setDate_of_calc(resultSet.getString(ConstCostData.DATE_OF_CALC));
                costs.add(cost);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return costs;
    }

    public Integer getCost(int id){
        Integer total = 0;
        String select = "SELECT * FROM " +ConstCostData.TABLE + " WHERE " + ConstCostData.COST_DATA_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                CostData cost = new CostData();
                cost.setCost_data_id(resultSet.getInt(ConstCostData.COST_DATA_ID));
                cost.setTotal_cost_price(resultSet.getInt(ConstCostData.TOTAL_COST_PRICE));
                cost.setDate_of_calc(resultSet.getString(ConstCostData.DATE_OF_CALC));
                total = cost.getTotal_cost_price();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return total;
    }

    public void deleteCost(int id){
        String delete = "DELETE FROM " + ConstCostData.TABLE + " WHERE " + ConstCostData.COST_DATA_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Себестоимость успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
