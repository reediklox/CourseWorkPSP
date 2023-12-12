package Server.Entity;


import Server.Entity.Interface.DateValidate;
import Server.Entity.Interface.IntegerValidate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class Attrition {
    @Setter
    private Integer attrition_id;
    @Setter
    private String equip_name;
    private Integer equip_life;
    private String date_of_equip_purchase;
    private Integer equip_cost;

    public void setEquip_life(Integer equip_life) {
        this.equip_life = IntegerValidate.SetInteger(equip_life);
    }

    public void setDate_of_equip_purchase(String date_of_equip_purchase) {
        this.date_of_equip_purchase = DateValidate.setDate(date_of_equip_purchase);
    }

    public void setEquip_cost(Integer equip_cost) {
        this.equip_cost = IntegerValidate.SetInteger(equip_cost);
    }

    public Attrition() {}
    public Attrition(Integer attrition_id, String equip_name, Integer equip_life, String date_of_equip_purchase, Integer equip_cost) {
        this.attrition_id = attrition_id;
        this.equip_name = equip_name;
        setEquip_life(equip_life);
        setDate_of_equip_purchase(date_of_equip_purchase);
        setEquip_cost(equip_cost);
    }

    @Override
    public String toString(){
        return "Attrition{" +
                "attrition_id=" + attrition_id +
                ", equip_name='" + equip_name + '\'' +
                ", equip_life='" + equip_life + '\'' +
                ", date_of_equip_purchase='" + date_of_equip_purchase + '\'' +
                ", equip_cost='" + equip_cost + '}';
    }


}
