package Server.Services.DataBase;

import Server.Config.ConstMaterials;
import Server.Entity.Materials;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerMaterials extends ConnectToDB{
    public Integer addMaterial(Materials material){
        String insert = "INSERT INTO "+ ConstMaterials.TABLE + "(" + ConstMaterials.MATERIAL_NAME + ")"+" VALUES(?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, material.getMaterial_name());

            pr.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Materials> getMaterials(){
        ArrayList<Materials> materials = new ArrayList<>();
        String select = "SELECT * FROM " +ConstMaterials.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Materials material = new Materials();
                material.setMaterial_id(resultSet.getInt(ConstMaterials.MATERIAL_ID));
                material.setMaterial_name(resultSet.getString(ConstMaterials.MATERIAL_NAME));
                materials.add(material);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return materials;
    }

    public void deleteMaterial(int id){
        String delete = "DELETE FROM " + ConstMaterials.TABLE + " WHERE " + ConstMaterials.MATERIAL_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Сырье успешно удалено!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
