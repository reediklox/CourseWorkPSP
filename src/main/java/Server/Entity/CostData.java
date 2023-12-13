package Server.Entity;

import Server.Entity.Interface.DateValidate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class CostData implements Serializable {
    @Setter
    private Integer cost_data_id;
    @Setter
    private Integer total_cost_price;
    private String date_of_calc;

    public void setDate_of_calc(String date_of_calc){
        this.date_of_calc = DateValidate.setDate(date_of_calc);
    }

    public CostData() {}

    public CostData(Integer cost_data_id, Integer total_cost_price, String date_of_calc){
        this.cost_data_id = cost_data_id;
        this.total_cost_price = total_cost_price;
        setDate_of_calc(date_of_calc);
    }

    public CostData(Integer total_cost_price, String date_of_calc){
        this.total_cost_price = total_cost_price;
        setDate_of_calc(date_of_calc);
    }

    @Override
    public String toString(){
        return "CostData{" +
                "cost_data_id=" + cost_data_id +
                ", total_cost_price='" + total_cost_price + '\'' +
                ", date_of_calc='" + date_of_calc + '}';
    }
}
