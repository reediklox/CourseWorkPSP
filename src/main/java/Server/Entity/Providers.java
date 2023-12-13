package Server.Entity;

import Server.Entity.Interface.IntegerValidate;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class Providers implements Serializable {
    private Integer provider_id;
    private String mobile_number;
    @Setter
    private Integer material_id;

    private Integer material_count;
    private Integer material_cost_per_piece;

    private String default_number = "292764127";

    public void setProvider_Id(Integer provider_id){
        this.provider_id = IntegerValidate.SetInteger(provider_id);
    }

    public void setMobile_number(String mobile_number){
        if (mobile_number.length() != 9) {mobile_number = this.default_number;}
        this.mobile_number = mobile_number;
    }

    public void setMaterial_count(Integer material_count){
        this.material_count = IntegerValidate.SetInteger(material_count);
    }
    public void setMaterial_cost_per_piece(Integer material_cost_per_piece){
        this.material_cost_per_piece = IntegerValidate.SetInteger(material_cost_per_piece);
    }


    public Providers() {}

    public Providers(Integer provider_id,
                     String mobile_number,
                     Integer material_id,
                     Integer material_count,
                     Integer material_cost_per_piece){
        setProvider_Id(provider_id);
        setMobile_number(mobile_number);
        this.material_id = material_id;
        setMaterial_count(material_count);
        setMaterial_cost_per_piece(material_cost_per_piece);
    }

    public Providers(String mobile_number,
                     Integer material_id,
                     Integer material_count,
                     Integer material_cost_per_piece){
        setMobile_number(mobile_number);
        this.material_id = material_id;
        setMaterial_count(material_count);
        setMaterial_cost_per_piece(material_cost_per_piece);
    }

    @Override
    public String toString(){
        return "Providers{" +
                "provider_id=" + provider_id +
                ", mobile_number='" + mobile_number + '\'' +
                ", material_id='" + material_id + '\'' +
                ", material_count='" + material_count + '\'' +
                ", material_cost_per_piece='" + material_cost_per_piece + '}';
    }
}
