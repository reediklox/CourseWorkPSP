package Server.Services.DataBase;

import Server.Config.ConstCompanies;
import Server.Entity.Companies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandlerCompanies extends ConnectToDB {
    public Integer addCompany(Companies companies){
        String insert = "INSERT INTO "+ ConstCompanies.TABLE + "(" +
                ConstCompanies.COMPANY_NAME + "," +
                ConstCompanies.COMPANY_ADDRESS+ "," +
                ConstCompanies.COMPANY_MOBILE_NUMBER + "," +
                ConstCompanies.OFFERED_PRICE+")"+" VALUES(?,?,?,?)";

        try {
            PreparedStatement pr = getConnection().prepareStatement(insert);

            pr.setString(1, companies.getCompany_name());
            pr.setString(2, companies.getCompany_address());
            pr.setString(3, companies.getCompany_mobile_number());
            pr.setInt(4, companies.getOffered_price());
            pr.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public ArrayList<Companies> getCompanies(){
        ArrayList<Companies> companies = new ArrayList<>();
        String select = "SELECT * FROM " +ConstCompanies.TABLE;
        try{
            PreparedStatement pr =getConnection().prepareStatement(select);
            ResultSet resultSet = pr.executeQuery();
            while (resultSet.next()){
                Companies company = new Companies();
                company.setCompany_id(resultSet.getInt(ConstCompanies.COMPANY_ID));
                company.setCompany_name(resultSet.getString(ConstCompanies.COMPANY_NAME));
                company.setCompany_address(resultSet.getString(ConstCompanies.COMPANY_ADDRESS));
                company.setCompany_mobile_number(resultSet.getString(ConstCompanies.COMPANY_MOBILE_NUMBER));
                company.setOffered_price(resultSet.getInt(ConstCompanies.OFFERED_PRICE));
                companies.add(company);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return companies;
    }

    public void deleteCompany(int id){
        String delete = "DELETE FROM " + ConstCompanies.TABLE + " WHERE " + ConstCompanies.COMPANY_ID + "='" + id + "'; ";
        try{
            PreparedStatement pr = getConnection().prepareStatement(delete);
            pr.executeUpdate();
            System.out.println("~~~Принятое на сервер ID~~~\n" + id);
            System.out.println("Компания успешно удалена!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
