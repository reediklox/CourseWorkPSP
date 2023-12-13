package Server.Controllers;

import Server.Entity.Tax;
import Server.Services.DataBase.DBHandlerTax;

import java.util.ArrayList;

public class TaxController {
    public static void AddTax(String type, String percent){
        Integer per = Integer.parseInt(percent);
        Tax tax = new Tax(type, per);
        DBHandlerTax dbHandler = new DBHandlerTax();
        dbHandler.addTax(tax);
    }

    public static ArrayList<Tax> getTaxes(){
        ArrayList<Tax> employees = new ArrayList<Tax>();
        DBHandlerTax db = new DBHandlerTax();
        employees = db.getTaxs();
        return employees;
    }

    public static void deleteTax(int id){
        DBHandlerTax db =new DBHandlerTax();
        db.deleteTax(id);
    }
}
