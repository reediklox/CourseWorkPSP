package Server.Services.DataBase;


import Server.Config.ConstProviders;
import Server.Entity.Providers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerProviders extends ConnectToDB{
    public Integer addProvider(Providers providers){
        String insert = "INSERT INTO "+ ConstProviders.TABLE + "(" +
                ConstProviders.MOBILE_NUMBER + "," +
                ConstProviders.MATERIAL_ID+ "," +
                ConstProviders.MATERIAL_COUNT + "," +
                ConstProviders.MATERIAL_COST_PER_PIECE+")"+" VALUES(?,?,?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, providers.getMobile_number());
            pr.setInt(2, providers.getMaterial_id());
            pr.setInt(3, providers.getMaterial_count());
            pr.setInt(4, providers.getMaterial_cost_per_piece());
            pr.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Providers> getProviders(){
        ArrayList<Providers> providers = new ArrayList<>();
        String select = "SELECT * FROM " +ConstProviders.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Providers provider = new Providers();
                provider.setProvider_Id(resultSet.getInt(ConstProviders.PROVIDER_ID));
                provider.setMobile_number(resultSet.getString(ConstProviders.MOBILE_NUMBER));
                provider.setMaterial_id(resultSet.getInt(ConstProviders.MATERIAL_ID));
                provider.setMaterial_count(resultSet.getInt(ConstProviders.MATERIAL_COUNT));
                provider.setMaterial_cost_per_piece(resultSet.getInt(ConstProviders.MATERIAL_COST_PER_PIECE));
                providers.add(provider);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return providers;
    }

    public Integer getBestProviders(int id){
        Integer price = 0;
        String select = "SELECT * FROM " +ConstProviders.TABLE + " WHERE " + ConstProviders.MATERIAL_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Providers provider = new Providers();
                provider.setProvider_Id(resultSet.getInt(ConstProviders.PROVIDER_ID));
                provider.setMobile_number(resultSet.getString(ConstProviders.MOBILE_NUMBER));
                provider.setMaterial_id(resultSet.getInt(ConstProviders.MATERIAL_ID));
                provider.setMaterial_count(resultSet.getInt(ConstProviders.MATERIAL_COUNT));
                provider.setMaterial_cost_per_piece(resultSet.getInt(ConstProviders.MATERIAL_COST_PER_PIECE));
                if(provider.getMaterial_cost_per_piece() > price);{
                    price = provider.getMaterial_cost_per_piece();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return price;
    }

    public Double getAVGProviders(int id){
        Integer price = 0;
        int count = 0;
        String select = "SELECT * FROM " +ConstProviders.TABLE + " WHERE " + ConstProviders.MATERIAL_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Providers provider = new Providers();
                provider.setProvider_Id(resultSet.getInt(ConstProviders.PROVIDER_ID));
                provider.setMobile_number(resultSet.getString(ConstProviders.MOBILE_NUMBER));
                provider.setMaterial_id(resultSet.getInt(ConstProviders.MATERIAL_ID));
                provider.setMaterial_count(resultSet.getInt(ConstProviders.MATERIAL_COUNT));
                provider.setMaterial_cost_per_piece(resultSet.getInt(ConstProviders.MATERIAL_COST_PER_PIECE));

                price += provider.getMaterial_cost_per_piece();
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (double) (price / count);
    }

    public Integer getWorstProviders(int id){
        Integer price = getBestProviders(id);
        String select = "SELECT * FROM " +ConstProviders.TABLE + " WHERE " + ConstProviders.MATERIAL_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Providers provider = new Providers();
                provider.setProvider_Id(resultSet.getInt(ConstProviders.PROVIDER_ID));
                provider.setMobile_number(resultSet.getString(ConstProviders.MOBILE_NUMBER));
                provider.setMaterial_id(resultSet.getInt(ConstProviders.MATERIAL_ID));
                provider.setMaterial_count(resultSet.getInt(ConstProviders.MATERIAL_COUNT));
                provider.setMaterial_cost_per_piece(resultSet.getInt(ConstProviders.MATERIAL_COST_PER_PIECE));
                if(provider.getMaterial_cost_per_piece() < price);{
                    price = provider.getMaterial_cost_per_piece();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return price;
    }

    public void deleteProvider(int id){
        String delete = "DELETE FROM " + ConstProviders.TABLE + " WHERE " + ConstProviders.PROVIDER_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Поставщик успешно удален!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
