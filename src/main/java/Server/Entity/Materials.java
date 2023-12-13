package Server.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Materials implements Serializable {
    private Integer material_id;
    private String material_name;

    public Materials() {}

    public Materials(Integer material_id, String material_name){
        this.material_id = material_id;
        this.material_name = material_name;
    }
    public Materials(String material_name){
        this.material_name = material_name;
    }

    @Override
    public String toString(){
        return "Materials{" +
                "material_id=" + material_id +
                ", material_name='" + material_name + '}';
    }
}
